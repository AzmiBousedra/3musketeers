package appointmenttracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import appointmenttracking.lists.*;

// Controller Console in the class diagram
public class Console {

	static final String CSV_HEADER = "First Name,Last Name,Title,Specialty,Date,Start,End";

	private User currentUser;
	private PatientList patientList = new PatientList();
	private StaffList staffList = new StaffList();
	private ReferralList referralList = new ReferralList();
	private AppointmentList appointmentList = new AppointmentList();
	private SpecialtyList specialtyList = new SpecialtyList();
	private ServiceList serviceList = new ServiceList();
	private MedicationList medicationList = new MedicationList();
	private Patient selectedPatient;
	private Invoice selectedInvoice;
	private Consultation selectedConsultation;
	private Prescription draftPrescription;

	// CSV details HealthcareProfessional doesn't hold (title, specialty, separate
	// first/last name)
	private Map<HealthcareProfessional, CsvDetails> csvDetails = new HashMap<>();

	private record CsvDetails(String firstName, String lastName, String title, Specialty specialty) {
	}

	public StaffList getStaffList() {
		return staffList;
	}

	public SpecialtyList getSpecialtyList() {
		return specialtyList;
	}

	public ServiceList getServiceList() {
		return serviceList;
	}

	// Login
	public User login(int id, String password) {
		User user = findUserById(id);
		if (user == null || password == null || user.getPassword() == null || user.getPassword().isEmpty()
				|| !password.equals(user.getPassword())) {
			throw new IllegalArgumentException("invalid id or password");
		}
		currentUser = user;
		clearSelection();
		return user;
	}

	private User findUserById(int id) {
		Patient patient = patientList.findPatientById(id);
		if (patient != null) {
			return patient;
		}
		AdministrativeStaff admin = staffList.findStaff(id);
		if (admin != null) {
			return admin;
		}
		return staffList.findProfessionalById(id);
	}

	private void clearSelection() {
		selectedPatient = null;
		selectedInvoice = null;
		selectedConsultation = null;
		draftPrescription = null;
	}

	// UC-22 Import Data from CSV
	public int importAvailabilities(String fileName) throws IOException {
		int imported = 0;
		int lineNumber = 0;
		try (BufferedReader reader = Files.newBufferedReader(Path.of(fileName), StandardCharsets.UTF_8)) {
			String line;
			while ((line = reader.readLine()) != null) {
				lineNumber++;
				if (lineNumber == 1 || line.isBlank()) {
					continue;
				}

				String[] fields = line.split(",", -1);
				if (fields.length != 7) {
					System.out.println("Line " + lineNumber + " skipped: expected 7 columns, found " + fields.length);
					continue;
				}
				try {
					String firstName = fields[0].trim();
					String lastName = fields[1].trim();
					String password = ""; // the availability CSV has no password column
					String title = fields[2].trim();
					String specialtyName = fields[3].trim();
					LocalDate date = LocalDate.parse(fields[4].trim());
					LocalTime start = LocalTime.parse(fields[5].trim());
					LocalTime end = LocalTime.parse(fields[6].trim());
					if (firstName.isEmpty() || lastName.isEmpty() || !start.isBefore(end)) {
						throw new IllegalArgumentException("missing name or start is not before end");
					}

					Specialty specialty = specialtyList.findSpecialty(specialtyName);
					if (specialty == null) {
						specialty = new Specialty(specialtyName);
						specialtyList.addSpecialty(specialty);
					}

					HealthcareProfessional professional = findProfessional(firstName, lastName);
					if (professional == null) {

						int licenseNumber = staffList.getProfessionals().size() + 1;
						professional = new HealthcareProfessional(password, firstName, lastName, licenseNumber, false,
								specialty);
						staffList.addProfessional(professional);
						csvDetails.put(professional, new CsvDetails(firstName, lastName, title, specialty));
					}

					professional.getAvailability().addTimeSlot(new TimeSlot(date, start, end));
					imported++;
				} catch (RuntimeException e) {
					System.out.println("Line " + lineNumber + " skipped: " + e.getMessage());
				}
			}
		}
		return imported;
	}

	// UC-23 Export Availabilities to CSV
	public int exportAvailabilities(String fileName) throws IOException {
		int exported = 0;
		try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(Path.of(fileName), StandardCharsets.UTF_8))) {
			writer.println(CSV_HEADER);
			AvailabilityRange allDates = new AvailabilityRange(LocalDate.MIN, LocalDate.MAX);
			for (HealthcareProfessional professional : staffList.getProfessionals()) {
				CsvDetails details = csvDetails.get(professional);
				String firstName = details == null ? professional.getFullName() : details.firstName();
				String lastName = details == null ? "" : details.lastName();
				String title = details == null ? "" : details.title();
				String specialty = details == null ? "" : details.specialty().getName();
				for (TimeSlot slot : professional.getAvailability().findAvailableSlots(allDates)) {
					writer.println(String.join(",", firstName, lastName, title, specialty,
							slot.getDate().toString(), slot.getStartTime().toString(), slot.getEndTime().toString()));
					exported++;
				}
			}
		}
		return exported;
	}

	private HealthcareProfessional findProfessional(String firstName, String lastName) {
		for (HealthcareProfessional professional : staffList.getProfessionals()) {
			if (professional.getFullName().equalsIgnoreCase(firstName + " " + lastName)) {
				return professional;
			}
		}
		return null;
	}

	// UC-20 Search Availability
	public List<HealthcareProfessional> searchProfessionals(Specialty specialty, AvailabilityRange range) {

		return staffList.findAvailableProfessionals(specialty, range);
	}

	public List<TimeSlot> getAvailableSlots(HealthcareProfessional hcp, AvailabilityRange range) {

		return hcp.getAvailability().findAvailableSlots(range);
	}

	// UC-01 Book Appointment
	public Patient searchPatient(String insuranceNumber) {
		return patientList.findPatient(insuranceNumber);
	}

	public Patient selectPatient(String patientId) {
		int id = Integer.parseInt(patientId.trim());
		for (Patient p : patientList.getPatients()) {
			if (p.getId() == id) {
				return p;
			}
		}
		return null;
	}

	public Appointment requestAppointment(Patient patient, HealthcareProfessional hcp,
			MedicalService medicalService, TimeSlot timeSlot) {
		if (medicalService.requiresReferral()) {
			Referral referral = referralList.findReferral(patient, hcp, medicalService);
			if (referral == null) {
				throw new IllegalStateException("Referral needed but missing.");
			}
		}
		if (!timeSlot.isAvailable()) {
			throw new IllegalStateException("Time slot is no longer available.");
		}
		return appointmentList.createAppointment(patient, hcp, medicalService, timeSlot);
	}
}

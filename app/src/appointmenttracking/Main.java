package appointmenttracking;

import java.io.IOException;
import java.time.DateTimeException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.DayOfWeek;
import java.util.ArrayList;

public class Main {

	static final String DATA_DIR = Files.isDirectory(Path.of("data")) ? "data/" : "app/data/";

	public static void main(String[] args) {
		Console console = new Console();
		Scanner in = new Scanner(System.in);

		while (true) {
			System.out.println();
			System.out.println("=== Appointment Tracking System ===");
			System.out.println("1. Book an appointment");
			System.out.println("2. Search for availability");
			System.out.println("3. Make availability");
			System.out.println("4. Import availabilities from CSV");
			System.out.println("5. Export availabilities to CSV");
			System.out.println("0. Exit");
			System.out.print("Choice: ");
			if (!in.hasNextLine()) {
				return;
			}
			String choice = in.nextLine().trim();

			try {
				switch (choice) {
					case "1": { // UC-01 Book Appointment
						System.out.print("Enter RAMQ or insurance number: ");
						Patient found = console.searchPatient(in.nextLine().trim());
						if (found == null) {
							System.out.print("Patient not found. Register a new patient? (y/n): ");
							if (!in.nextLine().trim().equalsIgnoreCase("y")) {
								break;
							}
							System.out.print("First name: ");
							String firstName = in.nextLine().trim();
							System.out.print("Last name: ");
							String lastName = in.nextLine().trim();
							System.out.print("Date of birth (YYYY-MM-DD): ");
							LocalDate dateOfBirth = LocalDate.parse(in.nextLine().trim());
							System.out.print("Telephone: ");
							String phone = in.nextLine().trim();
							System.out.print("RAMQ number (blank if none): ");
							String ramq = blankToNull(in.nextLine());
							System.out.print("Insurance provider (blank if none): ");
							String insuranceProvider = blankToNull(in.nextLine());
							System.out.print("Insurance number (blank if none): ");
							String insuranceNumber = blankToNull(in.nextLine());
							found = console.registerPatient(firstName, lastName, dateOfBirth, phone, ramq,
									insuranceProvider, insuranceNumber);
							System.out.println("Patient registered.");
						}
						System.out.println("Patient found: ID=" + found.getId() + " (" + found.getFirstName() + " "
								+ found.getLastName() + ")");

						System.out.print("Enter patient ID to select: ");
						Patient patient = console.selectPatient(in.nextLine().trim());
						if (patient == null) {
							System.out.println("Invalid patient ID.");
							break;
						}

						System.out.print("Enter specialty: ");
						Specialty specialty = new Specialty(in.nextLine().trim());
						System.out.print("Start date (YYYY-MM-DD): ");
						LocalDate start = LocalDate.parse(in.nextLine().trim());
						System.out.print("End date (YYYY-MM-DD): ");
						LocalDate end = LocalDate.parse(in.nextLine().trim());

						AvailabilityRange dateRange = new AvailabilityRange(start, end);
						List<HealthcareProfessional> professionals = console.searchProfessionals(specialty, dateRange);
						if (professionals.isEmpty()) {
							System.out.println("No professionals available.");
							break;
						}

						for (int i = 0; i < professionals.size(); i++) {
							HealthcareProfessional p = professionals.get(i);
							System.out.println(
									"[" + i + "] " + p.getFullName() + " (License: " + p.getLicenseNumber() + ")");
						}
						System.out.print("Select professional index: ");
						HealthcareProfessional hcp = professionals.get(Integer.parseInt(in.nextLine().trim()));

						List<TimeSlot> slots = console.getAvailableSlots(hcp, dateRange);
						if (slots.isEmpty()) {
							System.out.println("No slots available.");
							break;
						}
						for (int i = 0; i < slots.size(); i++) {
							TimeSlot s = slots.get(i);
							System.out.println(
									"[" + i + "] " + s.getDate() + " " + s.getStartTime() + " - " + s.getEndTime());
						}
						System.out.print("Select slot index: ");
						TimeSlot timeSlot = slots.get(Integer.parseInt(in.nextLine().trim()));

						System.out.println("Services:");
						for (MedicalService s : console.getServiceList().getServices()) {
							System.out.println("  " + s.getServiceId() + ". " + s.getName() + " (" + s.getDuration()
									+ " min" + (s.requiresReferral() ? ", referral required" : "") + ")");
						}
						System.out.print("Enter service ID: ");
						int serviceId = Integer.parseInt(in.nextLine().trim());
						MedicalService medicalService = console.getServiceList().findService(serviceId);
						if (medicalService == null) {
							System.out.println("Service not found.");
							break;
						}

						Appointment appointment = console.requestAppointment(patient, hcp, medicalService, timeSlot);
						System.out.println("Appointment booked: " + appointment);
						break;
					}
					case "2": { // UC-20 Search Availability
						System.out.print("Enter specialty: ");
						String specialtyName = in.nextLine().trim();

						System.out.print("Start date (YYYY-MM-DD): ");
						LocalDate startDate = LocalDate.parse(in.nextLine().trim());

						System.out.print("End date (YYYY-MM-DD): ");
						LocalDate endDate = LocalDate.parse(in.nextLine().trim());

						if (startDate.isAfter(endDate)) {
							System.out.println("Invalid date range.");
							break;
						}

						Specialty specialty = new Specialty(specialtyName);
						AvailabilityRange range = new AvailabilityRange(startDate, endDate);

						List<HealthcareProfessional> professionals = console.searchProfessionals(specialty, range);

						if (professionals.isEmpty()) {
							System.out.println("No available professionals found.");
							break;
						}

						for (HealthcareProfessional professional : professionals) {
							System.out.println(
									professional.getFullName() + " (License: " + professional.getLicenseNumber() + ")");

							for (TimeSlot slot : console.getAvailableSlots(professional, range)) {
								System.out.println(
										"  " + slot.getDate() + " " + slot.getStartTime() + " - " + slot.getEndTime());
							}
						}
						break;
					}

					case "3": { // UC-21 Create Availability

						if (console.getStaffList().getProfessionals().isEmpty()) {
							System.out.println("No healthcare professionals found.Please Import CSV first");
							break;
						}
						System.out.println("Healthcare Professionals:");

						for (HealthcareProfessional p : console.getStaffList().getProfessionals()) {
							System.out.println(p.getLicenseNumber() + ". " + p.getFullName() + " ("
									+ p.getSpecialty().getName() + ")");
						}
						System.out.print("Enter professional license number: ");
						int licenseNumber = Integer.parseInt(in.nextLine().trim());

						HealthcareProfessional professional = console.getStaffList()
								.findProfessionalByLicense(licenseNumber);

						if (professional == null) {
							System.out.println("Professional not found.");
							break;
						}

						System.out.print("Start date (YYYY-MM-DD): ");
						LocalDate startDate = LocalDate.parse(in.nextLine().trim());

						System.out.print("End date (YYYY-MM-DD): ");
						LocalDate endDate = LocalDate.parse(in.nextLine().trim());

						System.out.print("Weekdays (ex: TUESDAY,WEDNESDAY): ");
						String[] days = in.nextLine().split(",");

						List<DayOfWeek> selectedDays = new ArrayList<>();
						for (String day : days) {
							selectedDays.add(DayOfWeek.valueOf(day.trim().toUpperCase()));
						}

						System.out.print("Start time (ex: 09:00): ");
						LocalTime startTime = LocalTime.parse(in.nextLine().trim());

						System.out.print("End time (ex: 12:00): ");
						LocalTime endTime = LocalTime.parse(in.nextLine().trim());

						professional.getAvailability().createAvailability(startDate, endDate, selectedDays, startTime,
								endTime);

						System.out.println("Availability created successfully.");
						break;
					}
					case "4": { // UC-22 Import Data from CSV
						List<Path> files;
						try (Stream<Path> listing = Files.list(Path.of(DATA_DIR))) {
							files = listing.filter(p -> p.toString().toLowerCase().endsWith(".csv")).sorted().toList();
						}
						if (files.isEmpty()) {
							System.out.println("No CSV files found in " + DATA_DIR);
							break;
						}
						System.out.println("CSV files in " + DATA_DIR + ":");
						for (int i = 0; i < files.size(); i++) {
							System.out.println("  " + (i + 1) + ". " + files.get(i).getFileName());
						}
						System.out.print("Pick a file (1-" + files.size() + "): ");
						int pick;
						try {
							pick = Integer.parseInt(in.nextLine().trim());
						} catch (NumberFormatException e) {
							pick = 0;
						}
						if (pick < 1 || pick > files.size()) {
							System.out.println("Invalid file number.");
							break;
						}
						int count = console.importAvailabilities(files.get(pick - 1).toString());
						System.out.println(
								count + " time slot(s) imported from " + files.get(pick - 1).getFileName() + ".");
						break;
					}
					case "5": { // UC-23 Export Availabilities to CSV
						System.out.print("Name of the export file (e.g. exporttest.csv): ");
						String name = in.nextLine().trim();
						if (name.isEmpty()) {
							System.out.println("No file name given.");
							break;
						}
						name = Path.of(name).getFileName().toString();
						if (!name.toLowerCase().endsWith(".csv")) {
							name += ".csv";
						}
						Path target = Path.of(DATA_DIR, name);
						if (Files.exists(target)) {
							System.out.print(name + " already exists. Overwrite? (y/n): ");
							if (!in.nextLine().trim().equalsIgnoreCase("y")) {
								System.out.println("Export cancelled.");
								break;
							}
						}
						int count = console.exportAvailabilities(target.toString());
						System.out.println(count + " time slot(s) exported to " + target + ".");
						break;
					}
					case "0":
						System.out.println("Goodbye.");
						return;
					default:
						System.out.println("Invalid choice.");
				}
			} catch (IOException e) {
				System.out.println("File error (" + e.getClass().getSimpleName() + "): " + e.getMessage());
			} catch (IllegalArgumentException e) {
				System.out.println("Invalid input: " + e.getMessage());
			} catch (DateTimeException e) {
				System.out.println("Invalid date or time: " + e.getMessage());
			} catch (IndexOutOfBoundsException e) {
				System.out.println("Invalid selection: that number is not in the list.");
			} catch (RuntimeException e) { // e.g. missing referral or slot already booked; keep the program running
				System.out.println("Error: " + e.getMessage());
			}
		}
	}

	private static String blankToNull(String value) {
		return value.isBlank() ? null : value.trim();
	}
}

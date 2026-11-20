package appointmenttracking.lists;

import java.util.ArrayList;
import java.util.List;

import appointmenttracking.Appointment;
import appointmenttracking.Patient;
import appointmenttracking.HealthcareProfessional;
import appointmenttracking.MedicalService;
import appointmenttracking.TimeSlot;
import appointmenttracking.types.TimeslotStatus;;

public class AppointmentList {
    private List<Appointment> appointments = new ArrayList<>();

    public Appointment createAppointment(Patient patient, HealthcareProfessional hcp, MedicalService service,
            TimeSlot slot) {
        Appointment appointment = new Appointment(patient, hcp, service, slot);
        slot.setStatus(TimeslotStatus.Booked);
        appointments.add(appointment);
        return appointment;
    }

    public Appointment findAppointmentByPatient(Patient patient) {
        for (Appointment appt : appointments) {
            if (appt.getPatient() != null && appt.getPatient().equals(patient)) {
                return appt;
            }
        }
        return null;
    }
}
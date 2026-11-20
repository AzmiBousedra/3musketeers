package appointmenttracking;

import java.time.LocalTime;

import appointmenttracking.types.AppointmentStatus;

public class Appointment {
    private int appointmentId;
    private AppointmentStatus status;
    private LocalTime arrivalTime;
    private Patient patient;
    private HealthcareProfessional hcp;
    private MedicalService service;
    private TimeSlot slot;

    public Appointment(Patient patient, HealthcareProfessional hcp, MedicalService service, TimeSlot slot) {
        this.patient = patient;
        this.hcp = hcp;
        this.service = service;
        this.slot = slot;
        this.status = AppointmentStatus.Confirmed;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public void setArrivalTime(LocalTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Patient getPatient() {
        return patient;
    }
}
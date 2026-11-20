package appointmenttracking;

import java.time.LocalTime;

import appointmenttracking.types.AppointmentStatus;

public class Appointment {
    private static int nextId = 1; // Could be implemented better

    private int appointmentId;
    private AppointmentStatus status;
    private LocalTime arrivalTime;
    private Patient patient;
    private HealthcareProfessional hcp;
    private MedicalService service;
    private TimeSlot slot;

    public Appointment(Patient patient, HealthcareProfessional hcp, MedicalService service, TimeSlot slot) {
        this.appointmentId = nextId++;
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

    public int getAppointmentId() {
        return appointmentId;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public Patient getPatient() {
        return patient;
    }

    public HealthcareProfessional getHcp() {
        return hcp;
    }

    public MedicalService getService() {
        return service;
    }

    public TimeSlot getSlot() {
        return slot;
    }
}
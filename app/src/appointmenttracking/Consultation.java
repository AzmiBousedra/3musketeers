package appointmenttracking;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Consultation {
    private String consultationId;
    private LocalDate date;
    private String observations;
    private String diagnosis;
    private String recommendations;
    private String followUpRequirements;
    private List<Prescription> prescriptions;

    public Consultation(String consultationId, LocalDate date, String observations,
            String diagnosis, String recommendations, String followUpRequirements) {
        this.consultationId = consultationId;
        this.date = date;
        this.observations = observations;
        this.diagnosis = diagnosis;
        this.recommendations = recommendations;
        this.followUpRequirements = followUpRequirements;
        this.prescriptions = new ArrayList<>();
    }

    public void addPrescription(Prescription prescription) {
        prescriptions.add(prescription);
    }

    public String getConsultationId() {
        return consultationId;
    }
}

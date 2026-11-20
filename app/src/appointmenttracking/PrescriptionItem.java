package appointmenttracking;

public class PrescriptionItem {
    private String dosage;
    private String frequency;
    private String duration;
    private Medication medication;

    public PrescriptionItem(Medication medication, String dosage, String frequency, String duration) {
        this.medication = medication;
        this.dosage = dosage;
        this.frequency = frequency;
        this.duration = duration;
    }
}
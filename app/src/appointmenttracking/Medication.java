package appointmenttracking;

public class Medication {
    private String medicationId;
    private String name;
    private String strength;

    public Medication(String medicationId, String name, String strength) {
        this.medicationId = medicationId;
        this.name = name;
        this.strength = strength;
    }

    public String getMedicationId() {
        return medicationId;
    }

    public String getName() {
        return name;
    }
}
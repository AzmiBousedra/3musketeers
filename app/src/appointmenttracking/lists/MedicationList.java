package appointmenttracking.lists;

import java.util.ArrayList;
import java.util.List;

import appointmenttracking.Medication;

public class MedicationList {
    private List<Medication> medications = new ArrayList<>();

    public void addMedication(Medication medication) {
        medications.add(medication);
    }

    public Medication findById(String medicationId) {
        for (Medication med : medications) {
            if (med.getMedicationId() != null && med.getMedicationId().equals(medicationId)) {
                return med;
            }
        }
        return null;
    }

    public List<Medication> findByName(String name) {
        List<Medication> matches = new ArrayList<>();
        for (Medication med : medications) {
            if (med.getName() != null && med.getName().toLowerCase().contains(name.toLowerCase())) {
                matches.add(med);
            }
        }
        return matches;
    }
}

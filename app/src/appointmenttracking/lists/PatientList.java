package appointmenttracking.lists;

import java.util.ArrayList;
import java.util.List;

import appointmenttracking.Patient;

public class PatientList {
    private List<Patient> patients = new ArrayList<>();

    public Patient findPatient(String insuranceNumber) {
        for (Patient patient : patients) {
            if (patient.getInsuranceNumber() != null && patient.getInsuranceNumber().equals(insuranceNumber)) {
                return patient;
            }
        }
        return null;
    }

}

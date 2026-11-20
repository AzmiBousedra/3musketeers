package appointmenttracking.lists;

import java.util.ArrayList;
import java.util.List;

import appointmenttracking.Patient;

public class PatientList {
    private List<Patient> patients = new ArrayList<>();

    public void addPatient(Patient patient) {
        patients.add(patient);
    }

    public Patient findPatient(String insuranceNumber) {
        for (Patient patient : patients) {
            // a patient is identified by RAMQ or private insurance number (UC-01)
            if (insuranceNumber.equalsIgnoreCase(patient.getInsuranceNumber())
                    || insuranceNumber.equalsIgnoreCase(patient.getRamqNumber())) {
                return patient;
            }
        }
        return null;
    }

    public Patient findPatientById(int patientId) {
        for (Patient patient : patients) {
            if (patient.getId() == patientId) {
                return patient;
            }
        }
        return null;
    }

    public List<Patient> getPatients() {
        return patients;
    }

}

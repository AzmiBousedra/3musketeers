package appointmenttracking;

import appointmenttracking.types.ReferralPriority;
import appointmenttracking.types.ReferralStatus;

public class Referral {
    private int referralId;
    private String clinicalReason;
    private ReferralPriority priority;
    private ReferralStatus status;
    private Patient patient;
    private HealthcareProfessional hcp;
    private MedicalService service;

    public Referral(int referralId, String clinicalReason, ReferralPriority priority,
            ReferralStatus status, Patient patient, HealthcareProfessional hcp, MedicalService service) {
        this.referralId = referralId;
        this.clinicalReason = clinicalReason;
        this.priority = priority;
        this.status = status;
        this.patient = patient;
        this.hcp = hcp;
        this.service = service;
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
}
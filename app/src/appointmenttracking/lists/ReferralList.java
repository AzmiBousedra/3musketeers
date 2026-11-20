package appointmenttracking.lists;

import java.util.ArrayList;
import java.util.List;

import appointmenttracking.Referral;
import appointmenttracking.Patient;
import appointmenttracking.HealthcareProfessional;
import appointmenttracking.MedicalService;

public class ReferralList {
    private List<Referral> referrals = new ArrayList<>();

    public Referral findReferral(Patient patient, HealthcareProfessional hcp, MedicalService service) {
        for (Referral r : referrals) {
            if (r.getPatient().equals(patient) && r.getHcp().equals(hcp) && r.getService().equals(service)) {
                return r;
            }
        }
        return null;
    }
}
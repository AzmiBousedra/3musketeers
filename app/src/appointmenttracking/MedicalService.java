package appointmenttracking;

public class MedicalService {
    private int serviceId;
    private String name;
    private int duration;
    private boolean referral;

    public MedicalService(int serviceId, String name, int duration, boolean referral) {
        this.serviceId = serviceId;
        this.name = name;
        this.duration = duration;
        this.referral = referral;
    }

    public boolean requiresReferral() {
        return referral;
    }

    public int getServiceId() {
        return serviceId;
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }
}
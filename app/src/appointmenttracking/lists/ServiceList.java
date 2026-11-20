package appointmenttracking.lists;

import java.util.ArrayList;
import java.util.List;

import appointmenttracking.MedicalService;

public class ServiceList {
    private List<MedicalService> services = new ArrayList<>();

    public void addService(MedicalService service) {
        services.add(service);
    }

    public List<MedicalService> getServices() {
        return services;
    }

    public MedicalService findService(int serviceId) {
        for (MedicalService service : services) {
            if (service.getServiceId() == serviceId) {
                return service;
            }
        }
        return null;
    }
}

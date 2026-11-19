package appointmenttracking;
import java.util.ArrayList;
import java.util.List;

public class StaffList {

	private List<HealthcareProfessional> professionals = new ArrayList<>();
	
	public void addProfessional(HealthcareProfessional professional) {
		professionals.add(professional);
	}
	
	public HealthcareProfessional findProfessionalByLicense(int licenseNumber) {
		for (HealthcareProfessional professional : professionals) {
			if (professional.getLicenseNumber()==licenseNumber) {
				return professional;
			}
		}
		return null;
	}
	
	public List<HealthcareProfessional>findProfessionals(Specialty specialty){
		List<HealthcareProfessional> matchingProfessionals = new ArrayList<>();
		
		for (HealthcareProfessional professional : professionals) {
			
			if (professional.getSpecialty().getName().equalsIgnoreCase(specialty.getName())){
				matchingProfessionals.add(professional);
			}
		}
		
		return matchingProfessionals;
	}
	
	public List<HealthcareProfessional> findAvailableProfessionals(
	        Specialty specialty, AvailabilityRange range) {

	    List<HealthcareProfessional> availableProfessionals = new ArrayList<>();

	    for (HealthcareProfessional professional : findProfessionals(specialty)) {
	        if (!professional.getAvailability().findAvailableSlots(range).isEmpty()) {
	            availableProfessionals.add(professional);
	        }
	    }

	    return availableProfessionals;
	}
	
	
	public List <HealthcareProfessional> getProfessionals(){
		return professionals;
	}
}

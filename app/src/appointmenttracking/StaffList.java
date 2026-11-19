package appointmenttracking;
import java.util.ArrayList;
import java.util.List;

public class StaffList {

	private List<HealthcareProfessional> professionals = new ArrayList<>();
	
	public void addProfessional(HealthcareProfessional professional) {
		professionals.add(professional);
	}
	
	public HealthcareProfessional findProfessional(int licenseNumber) {
		for (HealthcareProfessional professional : professionals) {
			if (professional.getLicenseNumber()==licenseNumber) {
				return professional;
			}
		}
		return null;
	}
	public List <HealthcareProfessional> getProfessionals(){
		return professionals;
	}
}

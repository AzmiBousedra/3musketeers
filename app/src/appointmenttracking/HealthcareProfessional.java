package appointmenttracking;

public class HealthcareProfessional {

	
	private int licenseNumber;
	private String firstName;
	private String lastName;
	private boolean external;
	private Availability availability;
	private Specialty specialty;
	
	public HealthcareProfessional(int licenseNumber,String firstName,String lastName,boolean external,Specialty specialty) {
		this.licenseNumber=licenseNumber;
		this.firstName=firstName;
		this.lastName=lastName;
		this.external=external;
		this.availability= new Availability();
		this.specialty=specialty;
	}
	
	public Availability getAvailability() {
		return availability;
	}
	public Specialty getSpecialty() {
		return specialty;
	}
	
	public boolean isExternal() {
		return external;
	}
	
	public int getLicenseNumber() {
		return licenseNumber;
	}
	
	public String getFullName() {
		return firstName + " " + lastName;
	}
}

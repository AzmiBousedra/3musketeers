package appointmenttracking;

public class HealthcareProfessional {

	
	private int licenseNumber;
	private String firstName;
	private String lastName;
	private boolean external;
	private Availability availability;
	
	public HealthcareProfessional(int licenseNumber,String firstName,String lastName,boolean external) {
		this.licenseNumber=licenseNumber;
		this.firstName=firstName;
		this.lastName=lastName;
		this.external=external;
		this.availability= new Availability();
	}
	
	public Availability getAvailability() {
		return availability;
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

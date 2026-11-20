package appointmenttracking;

public class AdministrativeStaff extends User {
    private String role;

    public AdministrativeStaff(String password, String firstName, String lastName, String role) {
        super(password, firstName, lastName);
        this.role = role;
    }

}

package appointmenttracking;

public class User {
    private static int next_id = 1; // Could be implemented better
    private final int id;
    private String password;
    private String firstName;
    private String lastName;

    public User(String password, String firstName, String lastName) {
        this.id = next_id++;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}

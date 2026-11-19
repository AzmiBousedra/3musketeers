package appointmenttracking;
import java.util.List;
public class Console {
	
	private StaffList staffList;

	    public Console(StaffList staffList) {
	        this.staffList = staffList;
	    }
	    public List<HealthcareProfessional> searchProfessionals(Specialty specialty, AvailabilityRange range) {
	    	
	    	return staffList.findAvailableProfessionals(specialty, range);
	    }

	    public List<TimeSlot> getAvailableSlots(HealthcareProfessional hcp,AvailabilityRange range) {

	        return hcp.getAvailability().findAvailableSlots(range);
	    }
	}
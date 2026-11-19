package appointmenttracking;

import java.util.List;
import java.util.ArrayList;
public class Availability {

	private List<TimeSlot> timeSlots=new ArrayList<>();

	public void addTimeSlot(TimeSlot slot) {
		timeSlots.add(slot);
		
	}
	
	public List<TimeSlot> findAvailableSlots(AvailabilityRange range){
		List<TimeSlot> availableSlots= new ArrayList<>();
		for (TimeSlot slot : timeSlots) {
			if (slot.isAvailable() && range.containsDate(slot.getDate())) {
                availableSlots.add(slot);
			}
		}
		
		return availableSlots;
	}
}

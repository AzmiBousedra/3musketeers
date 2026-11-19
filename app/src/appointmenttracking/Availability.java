package appointmenttracking;

import java.util.List;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
public class Availability {

	private List<TimeSlot> timeSlots=new ArrayList<>();

	public void addTimeSlot(TimeSlot slot) {
		for(TimeSlot existingSlot : timeSlots) {
			if(existingSlot.getDate().equals(slot.getDate()) && slot.getStartTime().isBefore(existingSlot.getEndTime())&& slot.getEndTime().isAfter(existingSlot.getStartTime())){
				throw new IllegalArgumentException("Time slot already exists");
			}
		}
		
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
	public void createAvailability(LocalDate startDate, LocalDate endDate,List<DayOfWeek> selectedDays,LocalTime startTime,LocalTime endTime) {
		if(startDate.isAfter(endDate) || !startTime.isBefore(endTime)) {
			throw new IllegalArgumentException("date or time is invalid");
		}
		
		List<TimeSlot> newSlots = new ArrayList<>();
		
		for(LocalDate date=startDate;!date.isAfter(endDate);date=date.plusDays(1)) {
			if(selectedDays.contains(date.getDayOfWeek())) {
			 for(TimeSlot existingSlot : timeSlots) {
				 if (existingSlot.getDate().equals(date)&& startTime.isBefore(existingSlot.getEndTime())&& endTime.isAfter(existingSlot.getStartTime())) {
			          throw new IllegalArgumentException("Time slot already exists");
				 }
				 
			 }
			 
			 newSlots.add(new TimeSlot (date,startTime,endTime));
			}
		}
			 
			 for (TimeSlot slot : newSlots) {
				 addTimeSlot(slot);
			 }
				
}
}
package appointmenttracking;

import java.time.LocalDate;
import java.time.LocalTime;

public class TimeSlot {

	private LocalDate date;
	private LocalTime startTime;
	private LocalTime endTime;
	private TimeSlotStatus status;
	private boolean available;
	
	public TimeSlot(LocalDate date,LocalTime startTime,LocalTime endTime) {
		this.date=date;
		this.startTime=startTime;
		this.endTime=endTime;
		this.status=TimeSlotStatus.Available;
		this.available=true;
	}
	
	 public void setStatus(TimeSlotStatus status) {
	      this.status = status;
	      this.available = (status == TimeSlotStatus.Available);
	    }
	
	public boolean isAvailable() {
		return available;
	}
	
	public LocalDate getDate() {
		return date;
	}
	
	public LocalTime getStartTime() {
		return startTime;
	}
	
	public LocalTime getEndTime() {
		return endTime;
	}
	
}

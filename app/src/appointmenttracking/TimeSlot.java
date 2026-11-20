package appointmenttracking;

import java.time.LocalDate;
import java.time.LocalTime;

import appointmenttracking.types.TimeslotStatus;

public class TimeSlot {

	private LocalDate date;
	private LocalTime startTime;
	private LocalTime endTime;
	private TimeslotStatus status;
	private boolean available;

	public TimeSlot(LocalDate date, LocalTime startTime, LocalTime endTime) {
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
		this.status = TimeslotStatus.Available;
		this.available = true;
	}

	public void setStatus(TimeslotStatus status) {
		this.status = status;
		this.available = (status == TimeslotStatus.Available);
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

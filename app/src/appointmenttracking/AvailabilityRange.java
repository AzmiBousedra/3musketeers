package appointmenttracking;
import java.time.LocalDate;
public class AvailabilityRange {

	private LocalDate startDate;
	private LocalDate endDate;
	
	public AvailabilityRange(LocalDate startDate,LocalDate endDate) {
		this.startDate=startDate;
		this.endDate=endDate;
	}
	
	public boolean containsDate(LocalDate date) {
		return !date.isBefore(startDate) && !date.isAfter(endDate);
	}
}

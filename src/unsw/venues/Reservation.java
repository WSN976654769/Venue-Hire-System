package unsw.venues;
import java.time.LocalDate;
/**
 * Saving reservation detail and sorting it by date
 * @author z5158229
 *
 */
public class Reservation implements Comparable <Reservation>{
	
	private String id;
	private LocalDate startdate;
	private LocalDate enddate;

	public Reservation(String id, LocalDate startdate,LocalDate enddate) {
		this.id = id;
		this.startdate = startdate;
		this.enddate = enddate;		
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public LocalDate getStartdate() {
		return startdate;
	}

	public void setStartdate(LocalDate startdate) {
		this.startdate = startdate;
	}

	public LocalDate getEnddate() {
		return enddate;
	}

	public void setEnddate(LocalDate enddate) {
		this.enddate = enddate;
	}
	
	public String getBookingDetail() {
		return  getId() + " from " + getStartdate() + " to "+ getEnddate() ;
	}
	
	/** ordering reservation by date*/
	public int compareTo(Reservation r) {
	   return  this.getStartdate().compareTo(r.getStartdate());	
	}
	
}

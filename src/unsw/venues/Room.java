package unsw.venues;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import org.json.JSONArray;
import org.json.JSONObject;
/**
 * Room Detail
 * @author z5158229
 *
 */
public class Room  {

	private String name;
	private String size;
	private ArrayList <Reservation> bookingList;
	
	public Room(String name,String size){
		this.name = name;
		this.size = size;
		bookingList = new ArrayList <Reservation>();
	}

	public String getRoom() {
		return name;
	}

	public void setRoom(String name) {
		this.name = name;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}
	
	/**adding reservation and sorting it by date*/
	public void addReservation(Reservation booking) {
		bookingList.add(booking);
		Collections.sort(bookingList);
	}
	
	public void removeReservation(Reservation booking) {
		bookingList.remove(booking);
	}
	
	/**checking room id*/
	public boolean checkBookingId(String id) {
		for(Reservation item : bookingList) {
			if(item.getId().equals(id)){
				Reservation remove = item;
				bookingList.remove(remove);
				return true;
			}
		}
		return false;
	}
	
	/**checking avalibale booking time*/
	public boolean checkBookingTime(Reservation b) {
	
		for(Reservation item : bookingList) {
			
			LocalDate start = item.getStartdate();
			LocalDate end = item.getEnddate();  
			
			//wrong cases
			if(start.compareTo(b.getStartdate())==0 || end.compareTo(b.getEnddate())==0) {
				return false;
			}
			else if( start.compareTo(b.getEnddate())<0 && start.compareTo(b.getStartdate())>0  ){
				return false;
			}
			else if(end.compareTo(b.getStartdate())>0 && end.compareTo(b.getEnddate())<0 ) {
				return false;
		    }
			else if(start.compareTo(b.getStartdate())<0 && end.compareTo(b.getEnddate())>0) {
				return false;
			}				
		}
		return true;		
	}
		
	/**saving avaliable booking into JSON*/
	public void saveBooking(JSONArray reservation) {
		for(Reservation item : bookingList) {			
			JSONObject bookingDetail = new JSONObject();		
			bookingDetail.put("id",item.getId() );
			bookingDetail.put("start",item.getStartdate() );
			bookingDetail.put("end",item.getEnddate() );	
			reservation.put(bookingDetail);
		}
	}
	
	
}



	
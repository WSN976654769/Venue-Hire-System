package unsw.venues;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
/**
 * Saving venue and roomList, large, medium and small are used to
 * record number of room for different size of each room. 
 * 
 */
public class Venue  {
	
	private String venue;
	private ArrayList <Room> roomList;
	private int  large;  //number of large room for each venue
	private int medium;  //number of medium ...
	private int small;   //number of small ...
	
	public Venue(String venue) {
		this.venue = venue;
		roomList = new ArrayList <Room> ();
	}
	
	public String getVenue() {
		return venue;
	}
	
	
	public int getLarge() {
		return large;
	}

	public void setLarge(int large) {
		this.large = large;
	}

	public int getMedium() {
		return medium;
	}

	public void setMedium(int medium) {
		this.medium = medium;
	}

	public int getSmall() {
		return small;
	}

	public void setSmall(int small) {
		this.small = small;
	}

	public void addLarge() {
		this.large++;
	}
	
	public void addMedium() {
		this.medium++;
	}
	
	public void addSmall() {
		this.small++;
	}
	
	public void addSize(String size) {
		if(size.equals("small"))
			addSmall();
		if(size.equals("medium"))
			addMedium();
		if(size.equals("large"))
			addLarge();
		
	}
	
	public void addRooms(String room, String size) {	
		Room newRoom = new Room(room,size);
		addSize(size);
		roomList.add(newRoom);
	}	
		
	/** check each venues whether has enough rooms for different size so that stasifying requirement*/
	public boolean checkSizeRequest(Size size) {
		
		if(size.getLarge() <= this.getLarge() && size.getMedium() <= this.getMedium() && size.getSmall() <= this.getSmall() )
			return true;
		return false;
	}
	
	/**if having enough rooms, then check each room's booking time, if not conflict, add new reservation. */
	public boolean bookingRoom(Size s, Reservation t,JSONObject result ) {
		
		JSONArray room = new JSONArray();
		
		//saving rooms of valid booking requirement
		ArrayList <Room> temp =  new ArrayList <Room> (); 
		
		int large = s.getLarge(); int medium = s.getMedium(); int small = s.getSmall();
		//checking each venue's rooms
		for(Room r : roomList) {
			
			//checking time and size. if it is avalibale, add it into temp				
			if(s.getLarge()!=0 && r.getSize().equals("large") && r.checkBookingTime(t)) {			
				s.removeLarge();
				temp.add(r);
			}		
			else if(s.getMedium()!=0 && r.getSize().equals("medium") && r.checkBookingTime(t)) {
				s.removeMedium();
				temp.add(r);
			}
			else if(s.getSmall()!=0 && r.getSize().equals("small") && r.checkBookingTime(t)) {
				s.removeSmall();
				temp.add(r);
			}					
		}
		
		// Only when stasifying all count of size requirements, making operation 
		if(s.getLarge()==0 && s.getMedium()==0 && s.getSmall()==0) {
			
			for(Room r : temp) {
				r.addReservation(t);
				room.put(r.getRoom());
			}	
			result.put("rooms", room);
			return true;
		
		}
	
		//if fail, restoring original count for different size requirement
		s.setLarge(large);
		s.setSmall(small);
		s.setMedium(medium);
		return false;
	}
	
	/**finding room which want to cancel */
	public boolean cancelRoom(String id) {
		
		int flag=0;
		for(Room r : roomList) {
			if(r.checkBookingId(id)) flag++;
		}
		
		if(flag==0) return false;
		return true;
	}
	
	public void listRooms(JSONArray result) { 
		for(Room r : roomList) {
			//++++System.out.println(r.getName() + ": " + r.getSize());//			
			JSONObject show = new JSONObject();	
			show.put("room",r.getRoom());
			
			JSONArray reservation = new JSONArray();
			r.saveBooking(reservation);
			show.put("reservations",reservation);			
			result.put(show);	
		}
	}
	
}

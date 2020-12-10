/**
 * 
 * @author Shuonan WANG (Kevin)
 * @zID    Z5158229
 * 
 */
package unsw.venues;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Venue Hire System for COMP2511.
 *
 * A basic prototype to serve as the "back-end" of a venue hire system. Input
 * and output is in JSON format.
 *
 * @author Shuonan WANG (Kevin)
 * @zID    Z5158229
 *
 */
public class VenueHireSystem  {

    /**
     * Constructs a venue hire system. Initially, the system contains no venues,
     * rooms, or bookings.
     */
	
	private ArrayList <Venue> venueList;
	
    public VenueHireSystem() {
    	venueList = new ArrayList<Venue>(); 	
    }

    private void processCommand(JSONObject json) {
        switch (json.getString("command")) {

        case "room":
            String venue = json.getString("venue");
            String room = json.getString("room");
            String size = json.getString("size");
            addRoom(venue, room, size);
            break;
       	
        case "request" :
            String id = json.getString("id");
            LocalDate start = LocalDate.parse(json.getString("start"));
            LocalDate end = LocalDate.parse(json.getString("end"));
            int small = json.getInt("small");
            int medium = json.getInt("medium");
            int large = json.getInt("large");

            JSONObject result = request(id, start, end, small, medium, large);
            System.out.println(result.toString(2));
            break;

        case "change" :
        	String id1 = json.getString("id");
	        LocalDate start1 = LocalDate.parse(json.getString("start"));
	        LocalDate end1 = LocalDate.parse(json.getString("end"));
	        int small1 = json.getInt("small");
	        int medium1 = json.getInt("medium");
	        int large1 = json.getInt("large");
	        JSONObject result1 = cancel(id1);   
	        if(result1.toString(2).contains("rejected")) {
	        	 System.out.println(result1.toString(2));
	        }else {  	
	        	result1 = request(id1, start1, end1, small1, medium1, large1);	        
	        	System.out.println(result1.toString(2));
	       }
            break;
            
        case "cancel": 	
        	 String id2 = json.getString("id");
        	 
        	 JSONObject result2 = cancel(id2);
        	 System.out.println(result2.toString(2));  	
            break;
        
        case "list":       	
        	String venue1 = json.getString("venue");   
        	
        	JSONArray result3 = list(venue1);
        	System.out.println(result3.toString(2));
        	break;
        }
    }

    private void addRoom(String venue, String room, String size) {
    	
    	for(Venue v : venueList) {
    		if(v.getVenue().equals(venue)) {
    			v.addRooms(room, size);		
    	    	return;
    		}
    	}
    	
    	/**if input venue not existed, created a venue first.*/
    	Venue newVenue = new Venue(venue);  	
    	newVenue.addRooms(room, size);
    	venueList.add(newVenue);
    }

    /**finding venues which stasify booking requirement
	 * only when stasifying all count of size requirements, making operation 
	 * */
    public JSONObject request(String id, LocalDate start, LocalDate end,
            int small, int medium, int large) {
    	
    	JSONObject result = new JSONObject();
        
    	Size s = new Size(small,medium,large);
        Reservation r = new Reservation(id,start,end);
        
        int flag=0;
        for(Venue v : venueList ) {
        	//System.out.println("=="+v.getVenue());

        	if(v.checkSizeRequest(s) && v.bookingRoom(s,r,result)) {
        		       		
        		result.put("venue",v.getVenue());
        		flag=1;	
        		break;
        	}
        }
        
        if(flag==0) {
    		result.put("status", "rejected");
    	}
    	else {
    		result.put("status", "success");
    	}
        return result;
    }

    /**finding room which want to cancel*/
    public JSONObject cancel(String id) {
    	JSONObject result = new JSONObject();
    	
    	int flag=0;
    	for(Venue v : venueList ) {
        	if(v.cancelRoom(id)) flag=1;
        }
    	
    	if(flag==0) {
    		result.put("status", "rejected");
    	}
    	else {
    		result.put("status", "success");
    	}	
    	return result;
    }
    	   
    public JSONArray list(String venue) {
    	JSONArray result = new JSONArray();
    	
    	for(Venue v : venueList ) {	
    		if(v.getVenue().equals(venue)) {
    			v.listRooms(result);   	
    		}  		
        }
    	return result;
    }
        
    public static void main(String[] args) {
        VenueHireSystem system = new VenueHireSystem();

        Scanner sc = new Scanner(System.in);
        
        while (sc.hasNext()) {
            String line = sc.nextLine();
            //System.out.println(line);
            if (!line.trim().equals("")) {
                JSONObject command = new JSONObject(line);
                system.processCommand(command);
            }
        }
        sc.close();
    }
}

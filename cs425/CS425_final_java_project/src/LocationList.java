import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class LocationList {
	
	public static final String URL = "jdbc:oracle:thin:@fourier.cs.iit.edu:1521:orcl";
	public static final String USER = "ewarman";
	public static final String PSWD = "A20317755";
	
	ArrayList<Room> rooms;
	String th_address, th_city, th_state, th_zip, th_name;
	int th_id;
	
	/*public LocationList() {
		th_id = 0;
		th_address = "";
		th_city = "";
		th_state = "";
		th_zip = "";
		th_name = "";
		rooms = new ArrayList<Room>();
	}*/
	
	public LocationList(int tid, String addr, String c, String s, String z, String n) {
		th_id = tid;
		th_address = addr;
		th_city = c;
		th_state = s;
		th_zip = z;
		th_name = n;
		rooms = new ArrayList<Room>();
		try {// Load Oracle JDBC Driver 
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			 // Connect to Oracle Database 
			Connection conn = DriverManager.getConnection(URL, USER, PSWD);
			Statement st = conn.createStatement();
			st.executeQuery("SELECT ROOM_ID, ROOM_NUM, CAPACITY FROM AAHMED31.THEATERINFO WHERE THEATER_ID = "+tid);
			ResultSet rs = st.getResultSet();

			while (rs.next()) {
				Room room = new Room(rs.getInt(1), rs.getInt(2), rs.getInt(3));
				rooms.add(room);
			}
			conn.close(); 
		} catch (Exception ex) { 
			System.err.println(ex.getMessage()); 
		}
	}
	
}

class Room {
	int room_id, room_num, room_cap;
	
	public Room() {
		room_id = 0;
		room_num = 0;
		room_cap = 0;
	}
	
	public Room(int rid, int num, int cap) {
		room_id = rid;
		room_num = num;
		room_cap = cap;
	}
}

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

public class ShowingsList {

	public static final String URL = "jdbc:oracle:thin:@fourier.cs.iit.edu:1521:orcl";
	public static final String USER = "ewarman";
	public static final String PSWD = "A20317755";
	
	ArrayList<Showing> schedule;
	public int shNum;
	
	public ShowingsList() {
		schedule = new ArrayList<Showing>();
		try {// Load Oracle JDBC Driver 
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			 // Connect to Oracle Database 
			Connection conn = DriverManager.getConnection(URL, USER, PSWD);
			Statement st = conn.createStatement();
			st.executeQuery("SELECT ROOM_ID, MOVIE_ID, SHOW_DATE, TICKET_PRICE, SHOWING_ID FROM AAHMED31.SCHEDULE");
			ResultSet rs = st.getResultSet();
			shNum = rs.getFetchSize();
			while (rs.next()) {
				Showing show = new Showing(rs.getDouble(4),rs.getInt(2), rs.getInt(1), rs.getDate(3), rs.getInt(5));
				schedule.add(show);
			}
			conn.close(); 
		} catch (Exception ex) { 
			System.err.println(ex.getMessage()); 
		}
		
		
		
	}
	
	public void setTitles() {
		HashMap<Integer, String> idTitleMap = new HashMap<Integer,String>();
		try {// Load Oracle JDBC Driver 
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			 // Connect to Oracle Database 
			Connection conn = DriverManager.getConnection(URL, USER, PSWD);
			Statement st = conn.createStatement();
			st.executeQuery("SELECT MOVIE_ID, TITLE FROM AAHMED31.MOVIES");
			ResultSet rs = st.getResultSet();
			//create mapping of movie ids to movie titles
			while (rs.next()) {
				idTitleMap.put(rs.getInt(1), rs.getString(2));
			}
			conn.close(); 
		} catch (Exception ex) { 
			System.err.println(ex.getMessage()); 
		}
		for (Showing show : schedule) {
			show.title = idTitleMap.get(show.mid);
		}
	}
	
	public void addShowing(int sid, int mid, int r_id, Date sd) {
		//java.sql.Date sqlDate=java.sql.Date.valueOf(""+job_d);
		//java.util.Date d = new java.util.Date();
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		String ddf = df.format(sd).toString();
		//java.sql.Date dbd = java.sql.Date.valueOf(ddf);
		try {// Load Oracle JDBC Driver 
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			 // Connect to Oracle Database 
			Connection conn = DriverManager.getConnection(URL, USER, PSWD);
			Statement st = conn.createStatement();
			st.executeUpdate("INSERT INTO AAHMED31.SCHEDULE VALUES ("+sid+", "+mid+", "+r_id+", to_date('"+ddf+"', 'MM/dd/yyyy'), 2.00, 0)");
			conn.close(); 
		} catch (Exception ex) { 
			System.err.println(ex.getMessage()); 
		}
	}
	
	public void setTheaters() {
		HashMap<Integer, Integer> idTheaterMap = new HashMap<Integer,Integer>();
		try {// Load Oracle JDBC Driver 
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			 // Connect to Oracle Database 
			Connection conn = DriverManager.getConnection(URL, USER, PSWD);
			Statement st = conn.createStatement();
			st.executeQuery("SELECT ROOM_ID, THEATER_ID FROM AAHMED31.THEATERINFO");
			ResultSet rs = st.getResultSet();
			//create mapping of movie ids to movie titles
			while (rs.next()) {
				idTheaterMap.put(rs.getInt(1), rs.getInt(2));
			}
			conn.close(); 
		} catch (Exception ex) { 
			System.err.println(ex.getMessage()); 
		}
		for (Showing show : schedule) {
			show.theater = idTheaterMap.get(show.rid);
		}
	}
	
	public HashSet<String> getUniqueTitles() {
		HashSet<String> titles = new HashSet<String>();
		for (Showing show : schedule) {
			titles.add(show.title);
		}
		return titles;
	}
	
	public HashSet<Integer> getUniqueTheaters() {
		HashSet<Integer> titles = new HashSet<Integer>();
		for (Showing show : schedule) {
			titles.add(show.theater);
		}
		return titles;
	}
}

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


public class MovieForum {

	public static final String URL = "jdbc:oracle:thin:@fourier.cs.iit.edu:1521:orcl";
	public static final String USER = "ewarman";
	public static final String PSWD = "A20317755";
	
	ArrayList<MovieThread> mf;
	
	public MovieForum() {
		mf = new ArrayList<MovieThread>();
		try {// Load Oracle JDBC Driver 
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			 // Connect to Oracle Database 
			Connection conn = DriverManager.getConnection(URL, USER, PSWD);
			Statement st = conn.createStatement();
			st.executeQuery("SELECT ID, USERNAME, MOVIE, STAR_NAME, DIRECTOR, TEXT FROM AAHMED31.MOVIETHREADS");
			ResultSet rs = st.getResultSet();

			while (rs.next()) {
				MovieThread thread = new MovieThread(rs.getInt(1),rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
				mf.add(thread);
			}
			conn.close(); 
		} catch (Exception ex) { 
			System.err.println(ex.getMessage()); 
		}
	}
	
	public ArrayList<MovieThread> arrangeByStars() {
		ArrayList<MovieThread> stars = new ArrayList<MovieThread>();
		for (MovieThread thread : mf) {
			if (thread.star != null) {
				stars.add(thread);
			}
		}
		return stars;
	}
	
	public ArrayList<MovieThread> arrangeByTitle() {
		ArrayList<MovieThread> titles = new ArrayList<MovieThread>();
		for (MovieThread thread : mf) {
			if (thread.movie != null) {
				titles.add(thread);
				//System.out.println("Thread added: "+thread.movie);
			}
		}
		return titles;
	}
	
	public ArrayList<MovieThread> arrangeByDirectors() {
		ArrayList<MovieThread> directors = new ArrayList<MovieThread>();
		for (MovieThread thread : mf) {
			if (thread.director != null) directors.add(thread);
		}
		return directors;
	}
	
	public void addMovieThread(String username, String str, String txt) {
		try {// Load Oracle JDBC Driver 
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			 // Connect to Oracle Database 
			Connection conn = DriverManager.getConnection(URL, USER, PSWD);
			Statement st = conn.createStatement();
			st.executeUpdate("INSERT INTO EWARMAN.MOVIETHREADS VALUES (null, '"+username+"', '"+str+"', null, null, "+txt+"')");
			conn.close(); 
		} catch (Exception ex) { 
			System.err.println(ex.getMessage()); 
		}
	}
	
	public void addStarThread(String username, String str, String txt) {
		try {// Load Oracle JDBC Driver 
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			 // Connect to Oracle Database 
			Connection conn = DriverManager.getConnection(URL, USER, PSWD);
			Statement st = conn.createStatement();
			st.executeUpdate("INSERT INTO EWARMAN.MOVIETHREADS VALUES (null, '"+username+"', null, '"+str+"', null, '"+txt+"')");
			conn.close(); 
		} catch (Exception ex) { 
			System.err.println(ex.getMessage()); 
		}
	}
}

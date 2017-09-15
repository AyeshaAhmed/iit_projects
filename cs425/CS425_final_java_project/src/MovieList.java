import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class MovieList {
	public static final String URL = "jdbc:oracle:thin:@fourier.cs.iit.edu:1521:orcl";
	public static final String USER = "ewarman";
	public static final String PSWD = "A20317755";
	
	
	ArrayList<Star> stars;
	ArrayList<Genre> genres;
	String title, director, description;
	public static int mid;
	
	public MovieList() {
		mid = 0;
		title = "";
		director = "";
		description = "";
		stars = new ArrayList<Star>();
		genres = new ArrayList<Genre>();
	}
	
	public MovieList(int mov_id, String t, String dir, String des) {
		mid = mov_id;
		title = t;
		director = dir;
		description = des;
		stars = new ArrayList<Star>();
		genres = new ArrayList<Genre>();
		try {// Load Oracle JDBC Driver 
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			 // Connect to Oracle Database 
			Connection conn = DriverManager.getConnection(URL, USER, PSWD);
			//get stars
			Statement st = conn.createStatement();
			st.executeQuery("SELECT MOVIE_ID, STARNAME FROM AAHMED31.STARS WHERE MOVIE_ID="+mov_id);
			ResultSet rs = st.getResultSet();
			//get genres
			Statement st2 = conn.createStatement();
			st2.executeQuery("SELECT MOVIE_ID, GENRETYPE FROM AAHMED31.GENRE WHERE MOVIE_ID="+mov_id);
			ResultSet rs2 = st2.getResultSet();
			//insert stars
			while (rs.next()) {
				Star star = new Star(rs.getInt(1), rs.getString(2));
				stars.add(star);
			}
			//insert genres
			while (rs2.next()) {
				Genre genre = new Genre(rs.getInt(1), rs.getString(2));
				genres.add(genre);
			}
			conn.close(); 
		} catch (Exception ex) { 
			System.err.println(ex.getMessage()); 
		}
	}
	
	public static void addMovie(int mid, String title, String dir, String des) {
		try {// Load Oracle JDBC Driver 
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			 // Connect to Oracle Database 
			Connection conn = DriverManager.getConnection(URL, USER, PSWD);
			Statement st = conn.createStatement();
			st.executeUpdate("INSERT INTO EWARMAN.MOVIES VALUES ('"+mid+"', '"+title+"', '"+dir+"', '"+des+"')");
			conn.close(); 
		} catch (Exception ex) { 
			System.err.println(ex.getMessage()); 
		}
	}
	
	public int findMovie(String title) {
		int mid = 0;
		try {// Load Oracle JDBC Driver 
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			 // Connect to Oracle Database 
			Connection conn = DriverManager.getConnection(URL, USER, PSWD);
			Statement st = conn.createStatement();
			st.executeQuery("SELECT MOVIE_ID FROM AAHMED31.MOVIES WHERE TITLE = '"+title+"'");
			ResultSet rs = st.getResultSet();
			if (rs.next()){
				mid = rs.getInt(1);
			}else mid = 0;
			conn.close(); 
		} catch (Exception ex) { 
			System.err.println(ex.getMessage()); 
		}
		return mid;
	}
}

class Star {
	int mid;
	String starname;
	
	public Star() {
		mid = 0;
		starname = "";
	}
	
	public Star(int mov_id, String n) {
		mid = mov_id;
		starname = n;
	}
}

class Genre {
	int mid;
	String type;
	
	public Genre() {
		mid = 0;
		type = "";
	}
	
	public Genre(int mov_id, String t) {
		mid = mov_id;
		type = t;
	}
}

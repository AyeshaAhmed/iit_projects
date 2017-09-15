import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class TheaterThread {
	
	public static final String URL = "jdbc:oracle:thin:@fourier.cs.iit.edu:1521:orcl";
	public static final String USER = "ewarman";
	public static final String PSWD = "A20317755";
	
	ArrayList<Comment> comments;
	int id, theater;
	String username, text;
	
	public TheaterThread() {
		id = 0;
		theater = 0;
		username = "";
		text = "";
		comments = new ArrayList<Comment>();
	}
	
	public TheaterThread(int i, int th, String u, String t) {
		id = i;
		theater = th;
		username = u;
		text = t;
		comments = new ArrayList<Comment>();
		try {// Load Oracle JDBC Driver 
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			 // Connect to Oracle Database 
			Connection conn = DriverManager.getConnection(URL, USER, PSWD);
			Statement st = conn.createStatement();
			st.executeQuery("SELECT USERNAME, TEXT FROM AAHMED31.THEATERCOMMENTS WHERE THREAD_ID="+i);
			ResultSet rs = st.getResultSet();

			while (rs.next()) {
				Comment comment = new Comment(rs.getString(1), rs.getString(2));
				comments.add(comment);
			}
			conn.close(); 
		} catch (Exception ex) { 
			System.err.println(ex.getMessage()); 
		}
	}
	public void addComment(int th_id, int num, String username, String text) {
		try {// Load Oracle JDBC Driver 
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			 // Connect to Oracle Database 
			Connection conn = DriverManager.getConnection(URL, USER, PSWD);
			Statement st = conn.createStatement();
			st.executeUpdate("INSERT INTO EWARMAN.THEATERCOMMENTS VALUES ('"+th_id+"', '"+num+"', '"+username+"', '"+text+"')");
			conn.close(); 
		} catch (Exception ex) { 
			System.err.println(ex.getMessage()); 
		}
	}
	
}

class Comment {
	String username, text;
	
	public Comment() {
		username = "";
		text = "";
	}
	
	public Comment(String u, String t) {
		username = u;
		text = t;
	}
}
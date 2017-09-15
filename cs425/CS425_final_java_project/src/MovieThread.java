import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


public class MovieThread {
	public static int movComsize;
	public static final String URL = "jdbc:oracle:thin:@fourier.cs.iit.edu:1521:orcl";
	public static final String USER = "ewarman";
	public static final String PSWD = "A20317755";
	
	ArrayList<Comment> comments;
	int id;
	String username, movie, star, director, text;
	
	public MovieThread(int i, String u, String m, String s, String d, String t) {
		id = i;
		username = u;
		movie = m;
		star = s;
		director = d;
		text = t;
		comments = new ArrayList<Comment>();
		try {// Load Oracle JDBC Driver 
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			 // Connect to Oracle Database 
			Connection conn = DriverManager.getConnection(URL, USER, PSWD);
			Statement st = conn.createStatement();
			st.executeQuery("SELECT USERNAME, TEXT FROM EWARMAN.MOVIECOMMENTS WHERE THREAD_ID="+i);
			ResultSet rs = st.getResultSet();
			movComsize = rs.getFetchSize();
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
			System.out.println("Insert");
			st.executeUpdate("INSERT INTO EWARMAN.MOVIECOMMENTS VALUES ('"+th_id+"', '"+num+"', '"+username+"', '"+text+"')");
			conn.close(); 
		} catch (Exception ex) { 
			System.err.println(ex.getMessage()); 
		}
	}

}
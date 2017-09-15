import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Movies {
	public static final String URL = "jdbc:oracle:thin:@fourier.cs.iit.edu:1521:orcl";
	public static final String USER = "ewarman";
	public static final String PSWD = "A20317755";

	ArrayList<MovieList> movies;
	public static int movNum;
	
	public Movies() {
		movies = new ArrayList<MovieList>();
		try {// Load Oracle JDBC Driver 
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			 // Connect to Oracle Database 
			Connection conn = DriverManager.getConnection(URL, USER, PSWD);
			Statement st = conn.createStatement();
			st.executeQuery("SELECT * FROM AAHMED31.MOVIES");
			ResultSet rs = st.getResultSet();
			movNum = rs.getFetchSize();
			while (rs.next()) {
				MovieList movie = new MovieList(rs.getInt(1),rs.getString(2), rs.getString(3), rs.getString(4));
				movies.add(movie);
			}
			conn.close(); 
		} catch (Exception ex) { 
			System.err.println(ex.getMessage()); 
		}
	}
}

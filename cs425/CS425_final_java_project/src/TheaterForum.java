import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class TheaterForum {

	public static final String URL = "jdbc:oracle:thin:@fourier.cs.iit.edu:1521:orcl";
	public static final String USER = "ewarman";
	public static final String PSWD = "A20317755";

	ArrayList<TheaterThread> tf;
	
	public TheaterForum() {
		tf = new ArrayList<TheaterThread>();
		try {// Load Oracle JDBC Driver 
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			 // Connect to Oracle Database 
			Connection conn = DriverManager.getConnection(URL, USER, PSWD);
			Statement st = conn.createStatement();
			st.executeQuery("SELECT * FROM AAHMED31.THEATERTHREADS");
			ResultSet rs = st.getResultSet();

			while (rs.next()) {
				TheaterThread thread = new TheaterThread(rs.getInt(1),rs.getInt(2), rs.getString(3), rs.getString(4));
				tf.add(thread);
			}
			conn.close(); 
		} catch (Exception ex) { 
			System.err.println(ex.getMessage()); 
		}
	}

}

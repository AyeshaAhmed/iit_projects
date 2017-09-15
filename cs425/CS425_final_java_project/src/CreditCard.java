import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class CreditCard {

	public static final String URL = "jdbc:oracle:thin:@fourier.cs.iit.edu:1521:orcl";
	public static final String USER = "ewarman";
	public static final String PSWD = "A20317755";
	
	String ccn, cvv, name, cardType, street1, street2, city, state, zip; 
	Date expDate;
	
	public CreditCard() {
		ccn = "";
		cvv = "";
		name = "";
		cardType = "";
		street1 = "";
		street2 = "";
		expDate = Date.valueOf("2000-01-01");
		city = "";
		state = "";
		zip = "";
	}
	
	public CreditCard(String cc, String cv, String n, String ct, Date e, String s1, String s2, String c, String st, String z) {
		ccn = cc;
		cvv = cv;
		name = n;
		cardType = ct;
		street1 = s1;
		street2 = s2;
		expDate = e;
		city = c;
		state = st;
		zip = z;
	}
	
	public void getInfo(String cc) {
		try {// Load Oracle JDBC Driver 
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			 // Connect to Oracle Database 
			Connection conn = DriverManager.getConnection(URL, USER, PSWD);
			Statement st = conn.createStatement();
			st.executeQuery("SELECT * FROM AAHMED31.CC WHERE CCN='"+cc+"'");
			ResultSet rs = st.getResultSet();
			//if no user with that username returned
			if (rs.next()) {
				ccn = rs.getString(1);
				cvv = rs.getString(2);
				name = rs.getString(3);
				cardType = rs.getString(4);
				expDate = rs.getDate(5);
				street1 = rs.getString(6);
				street2 = rs.getString(7);
				city = rs.getString(8);
				state =  rs.getString(9);
				zip =  rs.getString(10);
			}
			conn.close(); 
		} catch (Exception ex) { 
			System.err.println(ex.getMessage()); 
		}
	}
}

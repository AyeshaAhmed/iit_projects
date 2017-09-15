import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Guest {

	public static final String URL = "jdbc:oracle:thin:@fourier.cs.iit.edu:1521:orcl";
	public static final String USER = "ewarman";
	public static final String PSWD = "A20317755";
	
	CreditCard cc;
	
	public Guest() {
		cc = new CreditCard();
	}
	public Guest(String ccn, String cv, String n, String ct, Date e, String s1, String s2, String c, String st, String z) {
	}
	
	public void registerCC(String ccn, String cv, String n, Date e, String s1, String s2, String c, String st, String z){
		cc = new CreditCard(ccn, cv, n, "Visa", e, s1, s2, c, st, z);
		try {// Load Oracle JDBC Driver 
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			 // Connect to Oracle Database 
			Connection conn = DriverManager.getConnection(URL, USER, PSWD);
			Statement st1 = conn.createStatement();
			st1.executeUpdate("INSERT INTO AAHMED31.CC " + "VALUES ('"+ccn+"', '"+cv+"', '"+n+"', 'Visa', date '"+e+"', '"+s1+"', '"+s2+"', '"+c+"', '"+st+"', '"+z+"')");
			conn.close();
		} catch (Exception ex) { 
			System.err.println(ex.getMessage()); 
		}
	}
	
	public void registerUser(String n, String cc, String e) {
		try {// Load Oracle JDBC Driver 
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			 // Connect to Oracle Database 
			Connection conn = DriverManager.getConnection(URL, USER, PSWD);
			Statement st1 = conn.createStatement();
			st1.executeUpdate("INSERT INTO AAHMED31.THEATERUSERS " + "VALUES (NULL, NULL, '"+n+"', '"+cc+"', NULL, '"+e+"')");
			conn.close();
		} catch (Exception ex) { 
			System.err.println(ex.getMessage()); 
		}
	}
	
	public void purchaseTicket(int sid, int nt) {
		java.util.Date d = new java.util.Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(df.format(d));
		String ddf = df.format(d).toString();
		Date dbd = Date.valueOf(ddf);
		try {// Load Oracle JDBC Driver 
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			 // Connect to Oracle Database 
			Connection conn = DriverManager.getConnection(URL, USER, PSWD);
			Statement st1 = conn.createStatement();
			st1.executeUpdate("INSERT INTO AAHMED31.TICKETS " + "VALUES ('"+cc.ccn+"', '"+sid+"', '"+nt+"', date '"+dbd+"')");
			conn.close();
		} catch (Exception ex) { 
			System.err.println(ex.getMessage()); 
		}
	}
}

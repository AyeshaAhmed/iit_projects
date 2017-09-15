import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Date;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.DateFormat;


public class User {

	public static final String URL = "jdbc:oracle:thin:@fourier.cs.iit.edu:1521:orcl";
	public static final String USER = "ewarman";
	public static final String PSWD = "A20317755";
	
	String username, password, name, phone, email, rewardLevel, cc;
	int curPoints, totPoints;
	CreditCard ccn;
	
	public User() {
		username = "";
		password = "";
		name = "";
		phone = "";
		email = "";
		cc = "";
		ccn = new CreditCard();
		curPoints = 0;
		totPoints = 0;
	}
	
	public User(String u, String p, String n, String c, String ph, String e) {
		username = u;
		password = p;
		name = n;
		cc = c;
		ccn = new CreditCard();
		ccn.getInfo(cc);
		phone = ph;
		email = e;
		curPoints = 0;
		totPoints = 0;
	}
	
	public void registerCC(String c, String cv, String n, Date e, String s1, String s2, String ci, String st, String z){
		ccn = new CreditCard(c, cv, n, "Visa", e, s1, s2, c, st, z);
		try {// Load Oracle JDBC Driver 
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			 // Connect to Oracle Database 
			Connection conn = DriverManager.getConnection(URL, USER, PSWD);
			Statement st1 = conn.createStatement();
			st1.executeUpdate("INSERT INTO AAHMED31.CC " + "VALUES ('"+c+"', '"+cv+"', '"+n+"', 'Visa', date '"+e+"', '"+s1+"', '"+s2+"', '"+ci+"', '"+st+"', '"+z+"')");
			conn.close();
		} catch (Exception ex) { 
			System.err.println(ex.getMessage()); 
		}
		ccn.ccn = c;
		ccn.cvv = cv;
		ccn.name = n;
		ccn.cardType = "Visa";
		ccn.street1 = s1;
		ccn.street2 = s2;
		ccn.expDate = e;
		ccn.city = ci;
		ccn.state = st;
		ccn.zip = z;
	}
	
	public void registerUser(String u, String p, String n, String c, String ph, String e) {
		try {// Load Oracle JDBC Driver 
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			 // Connect to Oracle Database 
			Connection conn = DriverManager.getConnection(URL, USER, PSWD);
			Statement st1 = conn.createStatement();
			st1.executeUpdate("INSERT INTO AAHMED31.THEATERUSERS " + "VALUES ('"+u+"', '"+p+"', '"+n+"', '"+c+"', '"+ph+"', '"+e+"')");
			Statement st2 = conn.createStatement();
			st2.executeUpdate("INSERT INTO AAHMED31.POINTS VALUES ('"+u+"', '"+curPoints+"', '"+totPoints+"')");
			conn.close();
		} catch (Exception ex) { 
			System.err.println(ex.getMessage()); 
		}
		username = u;
		password = p;
		name = n;
		cc = c;
		phone = ph;
		email = e;
	}
	
	public boolean login(String usr, String pswd) {
		try {// Load Oracle JDBC Driver 
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			 // Connect to Oracle Database 
			Connection conn = DriverManager.getConnection(URL, USER, PSWD);
			Statement st = conn.createStatement();
			st.executeQuery("SELECT * FROM AAHMED31.THEATERUSERS WHERE USERNAME='"+usr+"'");
			ResultSet rs = st.getResultSet();
			//if no user with that username returned
			if (rs.next()) {
				username = rs.getString(1);
				password = rs.getString(2);
				name = rs.getString(3);
				cc = rs.getString(4);
				phone = rs.getString(5);
				email = rs.getString(6);
			}
			conn.close(); 
		} catch (Exception ex) { 
			System.err.println(ex.getMessage()); 
		}
		if (username.equals(usr) && password.equals(pswd)) {
			ccn.getInfo(cc);
			return true;
		}
		else {
			return false;
		}
	}
	
	public void getPointsInfo() {
		totPoints = 0;
		curPoints = 0;
		rewardLevel = "Beginner";
		try {// Load Oracle JDBC Driver 
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			 // Connect to Oracle Database 
			Connection conn = DriverManager.getConnection(URL, USER, PSWD);
			Statement st = conn.createStatement();
			st.executeQuery("SELECT TOTAL_POINTS, CURRENT_POINTS FROM AAHMED31.POINTS WHERE USERNAME='"+username+"'");
			ResultSet rs = st.getResultSet();
			if (rs.next()) {
				totPoints = rs.getInt(1);
				curPoints = rs.getInt(2);
				Statement st2 = conn.createStatement();
				st2.executeQuery("SELECT LEVEL_NAME FROM AAHMED31.POINTLEVEL WHERE LEVEL_BOUNDARY < "+totPoints+ "ORDER BY LEVEL_BOUNDARY DESC");
				ResultSet rs2 = st2.getResultSet();
				if (rs2.next()) rewardLevel=rs2.getString(1);
			}
			conn.close(); 
		} catch (Exception ex) { 
			System.err.println(ex.getMessage()); 
		}
	}
	
	public void update(String pswd, String c, String ph, String eml, String cvv, String name, String date, String s1, String s2, String city, String st, String zip) {
		Date d = Date.valueOf(date);
		try {// Load Oracle JDBC Driver 
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			 // Connect to Oracle Database 
			Connection conn = DriverManager.getConnection(URL, USER, PSWD);
			
			//remove old record and add updated record in DB
			Statement st1 = conn.createStatement();
			Statement st2 = conn.createStatement();
			Statement st3 = conn.createStatement();
			Statement st4 = conn.createStatement();
			st1.executeUpdate("Delete from AAHMED31.THEATERUSERS Where Name = '"+name+"'");
			st2.executeUpdate("Delete from AAHMED31.CC Where CC_Name = '"+name+"'");
			st3.executeUpdate("INSERT INTO AAHMED31.CC " + "VALUES ('"+c+"', '"+cvv+"', '"+name+"', 'Visa', date '"+d+"', '"+s1+"', '"+s2+"', '"+city+"', '"+st+"', '"+zip+"')");
			st4.executeUpdate("INSERT INTO AAHMED31.THEATERUSERS " + "VALUES ('"+username+"', '"+password+"', '"+name+"', '"+c+"', '"+phone+"', '"+email+"')");
			
			//Update user object values
			password = pswd;
			cc=c;
			phone = ph;
			email = eml;
			ccn.cvv = cvv;
			ccn.name = name;
			ccn.expDate = d;
			ccn.street1 = s1;
			ccn.street2 = s2;
			ccn.city = city;
			ccn.state = st;
			ccn.zip = zip;
			conn.close(); 
		} catch (Exception ex) { 
			System.err.println(ex.getMessage()); 
		}
	}
	
	public void purchaseTicket(int sid, int nt) {
		java.util.Date d = new java.util.Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String ddf = df.format(d).toString();
		Date dbd = Date.valueOf(ddf);
		try {// Load Oracle JDBC Driver 
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			 // Connect to Oracle Database 
			Connection conn = DriverManager.getConnection(URL, USER, PSWD);
			Statement st1 = conn.createStatement();
			st1.executeUpdate("INSERT INTO AAHMED31.TICKETS " + "VALUES ('"+cc+"', '"+sid+"', '"+nt+"', date '"+dbd+"')");
			System.out.println("Inserted Ticket");
			conn.close();
		} catch (Exception ex) { 
			System.err.println(ex.getMessage()); 
		}
	}
	
	public String incrementPoints(char c) {
		System.out.println(totPoints);
		System.out.println(curPoints);
		System.out.println(rewardLevel);
		
		HashMap<String, int []> pointsMap = new HashMap<String,int []>();
		try {// Load Oracle JDBC Driver 
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			 // Connect to Oracle Database 
			Connection conn = DriverManager.getConnection(URL, USER, PSWD);
			Statement st = conn.createStatement();
			st.executeQuery("SELECT * FROM EWARMAN.REWARDS");
			ResultSet rs = st.getResultSet();
			while (rs.next()) {
				int [] values = {rs.getInt(2), rs.getInt(3)};
				pointsMap.put(rs.getString(1), values);
			}
			conn.close(); 
		} catch (Exception ex) { 
			System.err.println(ex.getMessage()); 
		}
		
		int bonus = 0;
		if (c=='t') {
			bonus = pointsMap.get(rewardLevel)[1];
		}
		if (c=='c') {
			bonus = pointsMap.get(rewardLevel)[0];
		}
		System.out.println("Points before update: "+totPoints);
		totPoints += bonus;
		curPoints += bonus;
		System.out.println("Points after update: "+totPoints);
		
		String oldLevel = rewardLevel;
		
		updatePoints();
		getPointsInfo();
		if (!rewardLevel.equals(oldLevel)) {
			return "Your reward level increased from "+oldLevel+" to "+rewardLevel;
		}
		else return "";
	}
	
	public void updatePoints() {
		try {// Load Oracle JDBC Driver 
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			 // Connect to Oracle Database 
			Connection conn = DriverManager.getConnection(URL, USER, PSWD);
			Statement st = conn.createStatement();
			//System.out.println("UPDATE AAHMED31.POINTS SET CURRENT_POINTS="+curPoints+", TOTAL_POINTS="+totPoints+" WHERE USERNAME ='"+username+"')");
			st.executeQuery("UPDATE AAHMED31.POINTS SET CURRENT_POINTS='"+curPoints+"', TOTAL_POINTS='"+totPoints+"' WHERE USERNAME ='"+username+"'");
			conn.close(); 
		} catch (Exception ex) { 
			System.err.println(ex.getMessage()); 
		}
	}
}

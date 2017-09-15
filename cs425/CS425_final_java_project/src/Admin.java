import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;


public class Admin {

	public static final String URL = "jdbc:oracle:thin:@fourier.cs.iit.edu:1521:orcl";
	public static final String USER = "ewarman";
	public static final String PSWD = "A20317755";
	
	int id, theater;
	String type, username, password, name, address, phone, ssn;
	Vector<EmployeeTrain> emps;
	Vector<String> users;
	
	public Admin() {
		type = "";
		username = "";
		password = "";
		name = "";
		address = "";
		phone = "";
		ssn = "";
		id = 0;
		theater = 0;
		emps = new Vector<EmployeeTrain>();
		users = new Vector<String>();
	}
	
	public Admin(int i, int th, String t, String u, String p, String n, String a, String ph, String s) {
		type = t;
		username = u;
		password = p;
		name = n;
		address = a;
		phone = ph;
		ssn = s;
		id = i;
		theater = th;
		emps = new Vector<EmployeeTrain>();
		users = new Vector<String>();
	}
	
	public boolean login(String usr, String pswd) {
		try {// Load Oracle JDBC Driver 
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			 // Connect to Oracle Database 
			Connection conn = DriverManager.getConnection(URL, USER, PSWD);
			Statement st = conn.createStatement();
			st.executeQuery("SELECT * FROM AAHMED31.MANAGEMENT WHERE USERNAME = '"+usr+"'");
			ResultSet rs = st.getResultSet();
			//if no user with that username returned
			if (!rs.next()) {
				return false;
			}
			else {
				id = rs.getInt(1);
				theater = rs.getInt(2);
				type = rs.getString(3);
				username = rs.getString(4);
				password = rs.getString(5);
				name = rs.getString(6);
				address = rs.getString(7);
				phone = rs.getString(8);
				ssn = rs.getString(9);
			}
			
			Statement st2 = conn.createStatement();
			st2.executeQuery("SELECT * FROM AAHMED31.JOBTRAINING");
			ResultSet rs2 = st2.getResultSet();
			while(rs2.next())
			{

					int id = rs2.getInt(1);
					boolean j = (rs2.getInt(2) == 1);
					boolean s = (rs2.getInt(3) == 1);
					boolean t = (rs2.getInt(4) == 1);
					
					emps.add(new EmployeeTrain(id,j,s,t));

			}
			
			Statement st3 = conn.createStatement();
			st3.executeQuery("SELECT username FROM AAHMED31.THEATERUSERS WHERE username IS NOT NULL");
			ResultSet rs3 = st3.getResultSet();
			while(rs3.next())
			{
				users.add(rs3.getString(1));
			}
			
			conn.close(); 
		} catch (Exception ex) { 
			System.err.println(ex.getMessage()); 
		}
		if (username.equals(usr) && password.equals(pswd)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public User viewUser(String usr) {
		User user = new User();
		int totPoints = 0;
		int curPoints = 0;
		String rewardLevel = "Beginner";
		try {// Load Oracle JDBC Driver 
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			 // Connect to Oracle Database 
			Connection conn = DriverManager.getConnection(URL, USER, PSWD);
			Statement st = conn.createStatement();
			st.executeQuery("SELECT * FROM AAHMED31.THEATERUSERS WHERE USERNAME='"+usr+"'");
			ResultSet rs = st.getResultSet();
			//if no user with that username returned
			if (rs.next()) {
				user.username = rs.getString(1);
				user.password = rs.getString(2);
				user.name = rs.getString(3);
				user.cc = rs.getString(4);
				user.phone = rs.getString(5);
				user.email = rs.getString(6);
			}
			Statement st2 = conn.createStatement();
			st2.executeQuery("SELECT * FROM AAHMED31.CC WHERE CCN='"+user.cc+"'");
			ResultSet rs2 = st2.getResultSet();
			if(rs2.next())
			{
				user.ccn=new CreditCard(rs2.getString(1),rs2.getString(2),rs2.getString(3),rs2.getString(4), rs2.getDate(5), rs2.getString(6), rs2.getString(7), rs2.getString(8), rs2.getString(8), rs2.getString(9));
			}
			
			Statement st3 = conn.createStatement();
			st3.executeQuery("SELECT * FROM AAHMED31.POINTS WHERE USERNAME='"+user.username+"'");
			ResultSet rs3 = st3.getResultSet();
			
			if(rs3.next())
			{
				totPoints = rs3.getInt(3);
				curPoints = rs3.getInt(2);
				Statement st4 = conn.createStatement();
				st4.executeQuery("SELECT LEVEL_NAME FROM AAHMED31.POINTLEVEL WHERE LEVEL_BOUNDARY < "+totPoints+ "ORDER BY LEVEL_BOUNDARY DESC");
				ResultSet rs4 = st4.getResultSet();
				if (rs4.next()) rewardLevel=rs4.getString(1);
				user.curPoints=curPoints;
				user.totPoints=totPoints;
				user.rewardLevel=rewardLevel;
			}
			conn.close(); 
		} catch (Exception ex) { 
			System.err.println(ex.getMessage()); 
		}
		return user;
	}
	
	class EmployeeTrain
	{
		int id;
		boolean janitor;
		boolean salesrep;
		boolean ticketmaster;
		
		protected EmployeeTrain(int i, boolean j, boolean s, boolean t)
		{
			id = i;
			janitor = j;
			salesrep = s;
			ticketmaster = t;
		}
	};
}
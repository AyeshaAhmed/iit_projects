/*
CS 425 Homework 2 - Emily Warman, Andrew Caron, Ayesha Ahmed

Write the program that will allow the owner to set the credit points 
per review contribution. The program will not allow the owner to give 
more credit points per review to a SILVER member than a GOLD member.
*/

import java.util.*;
import java.sql.*;

public class Q5 {

	public static final String URL = "jdbc:oracle:thin:@fourier.cs.iit.edu:1521:orcl";
	// Owner login username and pwd
	public static final String USER = "ewarman";
	public static final String PSWD = "A20317755";
	public static String username = "", password="";
	
	public static void main(String [] args) throws SQLException{
		//login as owner
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter owner username:");
		username = scan.nextLine();
		System.out.println("Enter owner password");
		password = scan.nextLine();
		if (login(username,password)) {
			//output current rewards levels
			HashMap<String, Integer> currentPoints = getCurrentPoints();
			System.out.println("Current reward levels:");
			System.out.println("Silver = "+currentPoints.get("Silver"));
			System.out.println("Gold = "+currentPoints.get("Gold"));
			System.out.println("Platinum = "+currentPoints.get("Platinum"));
			
			//update a level
			System.out.println("Change which level?: ");
			String level = scan.nextLine();
			System.out.println("New number: ");
			int value = scan.nextInt();
			
			//validate value
			if (level.equals("Silver")) {
				while (value > currentPoints.get("Gold")) {
					System.out.println("Error: enter new value less than "+currentPoints.get("Gold"));
					value = scan.nextInt();
				}
			}
			if (level.equals("Gold")) {
				while (value > currentPoints.get("Platinum")) {
					System.out.println("Error: enter new value less than "+currentPoints.get("Platinum"));
					value = scan.nextInt();
				}
			}
			
			//update database
			update(level, value);
		}
		//login unsuccessful
		else System.out.println("Permission Denied");
		scan.close();
	}
	
	public static boolean login(String username, String password) {
		String dbUsername = "", dbPassword = "";
		try {// Load Oracle JDBC Driver 
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			 // Connect to Oracle Database 
			Connection conn = DriverManager.getConnection(URL, USER, PSWD);
			Statement st = conn.createStatement();
			st.executeQuery("SELECT USERNAME, SCHED_PASSWORD FROM AAHMED31.MANAGEMENT WHERE MAN_TYPE = 'Owner'");
			ResultSet rs = st.getResultSet();
			rs.next();
			dbUsername = rs.getString(1);
			dbPassword = rs.getString(2);
			conn.close(); 
		} catch (Exception ex) { 
			System.err.println(ex.getMessage()); 
		}
		if (dbUsername.equals(username) && dbPassword.equals(password)) return true;
		else return false;
	}
	
	public static HashMap<String, Integer> getCurrentPoints() {
		HashMap<String,Integer> currentPoints = new HashMap<String, Integer>();
		try {// Load Oracle JDBC Driver 
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			 // Connect to Oracle Database 
			Connection conn = DriverManager.getConnection(URL, USER, PSWD);
			Statement st = conn.createStatement();
			st.executeQuery("SELECT * FROM AAHMED31.POINTLEVEL");
			ResultSet rs = st.getResultSet();
			for(int i = 0; i<4; i++) {
				rs.next();
				currentPoints.put(rs.getString(1),rs.getInt(2));
			}
			conn.close(); 
		} catch (Exception ex) { 
			System.err.println(ex.getMessage()); 
		}
		return currentPoints;
	}
	
	public static void update(String level, int points) {
		try {// Load Oracle JDBC Driver 
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			 // Connect to Oracle Database 
			Connection conn = DriverManager.getConnection(URL, USER, PSWD);
			Statement st = conn.createStatement();
			st.executeUpdate("UPDATE AAHMED31.POINTLEVEL SET LEVEL_BOUNDARY=" +points+ "WHERE LEVEL_NAME='"+level+"'");
			System.out.println("Update successful");
			conn.close(); 
		} catch (Exception ex) { 
			System.err.println(ex.getMessage()); 
		}
	}
}

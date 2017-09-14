/*
CS 425 Homework 2 - Emily Warman, Ayesha Ahmed, Andrew Caron

Write the program that will allow a guest to be registered for the 
first time. The program prevents the guest from purchasing a ticket
if the guest has not entered his credit card information or his credit
card does not have sufficient funds. Write the CreditCardCo test object
that allows you to test this program.
*/

import java.util.*;
import java.sql.*;
import oracle.jdbc.*;
public class Q6{
	
	public static final String URL = "jdbc:oracle:thin:@fourier.cs.iit.edu:1521:orcl";
	// Owner login username and pwd
	public static final String USER = "";
	public static final String PSWD = "";
	String dbfn,dbln,dbCCN,dblog,dbtype,dbSure,dbemail;
	
	public static void main(String [] args) throws SQLException{
		Q6 q6 = new Q6();
		q6.run();
	}
	
	public void run() {
		Scanner scan = new Scanner(System.in);
		System.out.println("New user? Enter yes or no");
		String response = scan.next();
		
		while (!response.toLowerCase().equals("yes") && 
				!response.toLowerCase().equals("no")) {
			System.out.println("Response not understood. Enter yes or no.");
			response = scan.next();
		}
		
		//create a new user
		if (response.toLowerCase().equals("yes")) createUser();
		
		//find user in database
		try {// Load Oracle JDBC Driver 
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			 // Connect to Oracle Database 
			Connection conn = DriverManager.getConnection(URL, USER, PSWD);
			Statement st = conn.createStatement(); 
			st.executeUpdate("INSERT INTO RegisteredUsers " + "VALUES (username, password, name, ccn, address, city, state, zip, email, phone)");        
			conn.close(); 
		} catch (Exception ex) { 
			System.err.println(ex.getMessage()); 
		}
	}
	
	public void createUser() {
		
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Username:");
		String username = scan.next();
		System.out.println("Password:");
		String password = scan.next();
		System.out.println("Name:");
		String name = scan.next();
		System.out.println("CCN:");
		String address = scan.next();
		System.out.println("City:");
		String city = scan.next();
		System.out.println("State:");
		String state = scan.next();
		System.out.println("Zip:");
		String zip = scan.next();
		System.out.println("Phone Number:");
		String phone = scan.next();
		System.out.println("Email:");
		String email = scan.next();
		
		try {// Load Oracle JDBC Driver 
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			 // Connect to Oracle Database 
			Connection conn = DriverManager.getConnection(URL, USER, PSWD);
			Statement st = conn.createStatement(); 
			st.executeUpdate("INSERT INTO RegisteredUsers " + "VALUES (username, password, name, ccn, address, city, state, zip, email, phone)");        
			conn.close(); 
		} catch (Exception ex) { 
			System.err.println(ex.getMessage()); 
		}
	}

}


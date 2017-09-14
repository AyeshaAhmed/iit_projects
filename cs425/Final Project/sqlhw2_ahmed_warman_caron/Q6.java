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
import java.sql.Date;

public class Q6{
	
	public static final String URL = "jdbc:oracle:thin:@fourier.cs.iit.edu:1521:orcl";
	// Owner login username and pwd
	public static final String USER = "ewarman";
	public static final String PSWD = "A20317755";
	public String username = "", CCN = "";
	
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
		
		//create a new user or login to an existing account
		if (response.toLowerCase().equals("no")) login();
		else createUser();
		
		//try to purchase a $7 ticket
		purchase(7);
	}
	
	public void createUser() {
		
		Scanner scan = new Scanner(System.in);
		
		//make registered user tuple
		/*
		System.out.println("Username:");
		username = scan.nextLine();
		System.out.println("Password:");
		String password = scan.nextLine();
		System.out.println("Name:");
		String name = scan.nextLine();
		System.out.println("Phone Number:");
		String phone = scan.nextLine();
		System.out.println("Email:");
		String email = scan.nextLine();
		System.out.println("CCN:");
		CCN = scan.nextLine();
		
		//make credit card tuple
		System.out.println("CVV:");
		String cvv = scan.nextLine();
		System.out.println("Name on card:");
		String cardname = scan.nextLine();
		System.out.println("Card type:");
		String cardtype = scan.nextLine();
		System.out.println("Address:");
		String address = scan.nextLine();
		System.out.println("Address 2:");
		String address2 = scan.nextLine();
		System.out.println("Exp date:");
		String expdate = scan.nextLine();
		System.out.println("City:");
		String city = scan.nextLine();
		System.out.println("State:");
		String state = scan.nextLine();
		System.out.println("Zip:");
		String zip = scan.nextLine();
		*/
		
		username = "ltest";
		String password = "1234";
		String name = "L Test";
		String phone = "5051223456";
		String email = "ltest@gmail.com";
		CCN = "1222344456667888";
		
		String cvv = "123";
		String cardname = "L Test";
		String cardtype = "Visa";
		String address = "123 Main St.";
		String address2 = "Apt. 2";
		Date expdate = Date.valueOf("2018-01-02");
		String city = "Peoria";
		String state = "Illinois";
		String zip = "60666";
		
		try {// Load Oracle JDBC Driver 
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			 // Connect to Oracle Database 
			Connection conn = DriverManager.getConnection(URL, USER, PSWD);
			Statement st = conn.createStatement();
			st.executeUpdate("INSERT INTO AAHMED31.CC " + "VALUES ('"+CCN+"', '"+cvv+"', '"+cardname+"', '"+cardtype+"', date '"+expdate+"', '"+address+"', '"+address2+"', '"+city+"', '"+state+"', '"+zip+"')");
			System.out.println("CC info added");
			st.executeUpdate("INSERT INTO AAHMED31.THEATERUSERS " + "VALUES ('"+username+"', '"+password+"', '"+name+"', '"+CCN+"', '"+email+"', '"+phone+"')");
			System.out.println(username+" Added.");
			conn.close(); 
		} catch (Exception ex) { 
			System.err.println(ex.getMessage()); 
		}
		scan.close();
	}
	
	public void login() {
		//get username
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter your username");
		username = scan.next();
		String password = "";
		//find user in database
		try {// Load Oracle JDBC Driver 
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			 // Connect to Oracle Database 
			Connection conn = DriverManager.getConnection(URL, USER, PSWD);				
			Statement st = conn.createStatement(); 
			String query = "SELECT * FROM AAHMED31.THEATERUSERS WHERE USERNAME = "+username+";";
			st.executeQuery(query);
			ResultSet rs = st.getResultSet();
			CCN = rs.getString(5);
			password = rs.getString(3);
			conn.close(); 
		} catch (Exception ex) { 
			System.err.println(ex.getMessage()); 
		}
		
		//password validation
		if (!password.equals("")) {
			System.out.println("Enter your password: ");
			String input = scan.next();
			while (!input.equals(password)) {
				System.out.println("Incorrect password, try again");
				input = scan.next();
			}
			System.out.println("Logged in as: "+username);
		}
		else System.out.println("User not found");
		scan.close();
							
	}
	
	public void purchase(int price) {
		if (CCN.equals("")) System.out.println("No credit card information entered. Can't purchase");
		else {
			CreditCardCo auth = new CreditCardCo();
			int bal = auth.getBalance(CCN);
			if (bal<price) System.out.println("Insufficient funds");
			else {
				System.out.println("Purchased");
			}
		}
	}

}


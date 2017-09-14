/*
CS 425 Homework 2 - Emily Warman, Andrew Caron, Ayesha Ahmed

Write the program that will allow the owner to set the credit points 
per review contribution. The program will not allow the owner to give 
more credit points per review to a SILVER member than a GOLD member.
*/

import java.util.*;
import java.sql.*;

import oracle.jdbc.*;

public class Q5{
	public static final String URL = "jdbc: oracle:thin:@fourier.cs.iit.edu:1521:orcl";
// Enter your login and password
	public static final String USER = "ewarman";
	public static final String PSWD = "A20317755";

	double dbcpts;
	String dbrvw;
	String dbrwdstats;
	String pwd;
	
	public static void main(String [] args) {
		Q5 q5 = new Q5();
		q5.run();
	}

	public void run(){
		System.out.println("\t\t------Credit Points per Review Contribution-----");
		System.out.println("Enter your username: ");
		Scanner s1 = new Scanner (System.in);
		System.out.println("Enter your password: ");
		pwd = s1.next();
}
	public void getCreditInfo(double cred_pts){
		Connection con = null;
		Statement stmt = null;
		try{ 
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

			con = DriverManager.getConnection(URL, USER, PSWD);
			stmt = con.createStatement();

			CallableStatement cstmt = con.prepareCall("{call Q5(?,?,?)}");
			cstmt.registerOutParameter(1, Types.DOUBLE);
			cstmt.registerOutParameter(2, Types.VARCHAR);
			cstmt.registerOutParameter(3, Types.VARCHAR);
			cstmt.execute();
  
			dbcpts = cstmt.getDouble(1);
			dbrwdstats = cstmt.getString(2);
			dbrvw = cstmt.getString(3); 
  
			cstmt.close();
			stmt.close();
			con.close();
		}catch(SQLException se) {
			se.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace(); 
		}finally{
			try{
				if (stmt != null)
					stmt.close();
			}catch(SQLException se2){
			}
			try{
				if(con!=null)
					con.close();
			}catch(SQLException se){
				se.printStackTrace();
			}//end finally try
		}//end try
	}

	public void setCreditInfo(double cred_pts){
		System.out.println("Enter credit points");
		Scanner s4 = new Scanner(System.in);
		cred_pts = s4.nextDouble();
		if (reward_Status = "Silver"){
			"Silver".cred_pts < "Gold".cred_pts;
		}
		else if (reward_Status = “Gold”){
          “Gold”.cred_pts > “Silver”.cred_pts;
      }    
} 
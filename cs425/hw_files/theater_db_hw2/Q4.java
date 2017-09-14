/*
CS 425 Homework 2 - Emily Warman, Ayesha Ahmed, Andrew Caron
*/

import java.util.*;
import java.sql.*;
import oracle.jdbc.*;

public class Q4{
	public static int dbid;
	public static String dbtime;
	public static int dbdate;
	public static final String URL = "jdbc: oracle:thin:@fourier.cs.iit.edu:1521:orcl";
	// insert username and password
	public static final String USER = "ewarman";
	public static final String PSWD = "A20317755";


	public static void main(String [] args) throws SQLException{
		Q4 q4 = new Q4();
		q4.run();
	}
	
	public void run() throws SQLException{	
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter worker ID:\n");
		int id = scan.nextInt();
		System.out.println("Enter date:\n");
		String date = scan.next();
		System.out.println("Enter time:\n");
		int time = scan.nextInt();
		
		//if the shift can be scheduled, schedule it
		if (shiftAvailable(id, date, time) == true) {
			schedule(id, date, time);
		}
	}

	public boolean shiftAvailable(int id, String time, int date) throws SQLException{
		//connect to DB
		DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
		Connection con = DriverManager.getConnection(URL, USER, PSWD);
		Statement statement = con.createStatement();
		CallableStatement cstmt = con.prepareCall("{call Q4(?,?,?)}");
		cstmt.registerOutParameter(1, Types.INTEGER);	
		cstmt.registerOutParameter(2, Types.VARCHAR);	
		cstmt.registerOutParameter(3, Types.INTEGER);	
		ResultSet rs = cstmt.executeQuery();
		
		while (rs.next()) {
			dbid = rs.getInt("EMP_ID");
			dbtime = rs.getString("TIME");
			dbdate = rs.getInt("DATE");	
		}
		
		if(time.equals(dbtime) && date == dbdate){
			if((Integer)dbid != null){
				System.out.println("Conflict - cannot schedule worker.");
				return false;
			}
		}
		return true;
	}


	public void schedule(int id, String time, int date) throws SQLException{
		DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
		Connection con = DriverManager.getConnection(URL, USER, PSWD);
		CallableStatement cstmt = con.prepareCall("{call Q4(date,time,?)}");
		cstmt.setInt(1,id);
		System.out.println("Employee Successfully Scheduled");
	}
}

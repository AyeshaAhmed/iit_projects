import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class EmployeeList {
	
	public static final String URL = "jdbc:oracle:thin:@fourier.cs.iit.edu:1521:orcl";
	public static final String USER = "ewarman";
	public static final String PSWD = "A20317755";
	
	
	ArrayList<EmpSchedule> empscheds;
	ArrayList<EmployeeTrain> emptrain;
	int emp_id, hire_id;
	String name, address, phone, ssn;
	
	public EmployeeList() {
		emp_id = 0;
		hire_id = 0;
		name = "";
		address = "";
		phone = "";
		ssn = "";
		empscheds = new ArrayList<EmpSchedule>();
		emptrain = new ArrayList<EmployeeTrain>();
	}
	
	public EmployeeList(int emp, String n, String a, String p, String ss,  int hire) {
		emp_id = emp;
		hire_id = hire;
		name = n;
		address = a;
		phone = p;
		ssn = ss;
		empscheds = new ArrayList<EmpSchedule>();
		emptrain = new ArrayList<EmployeeTrain>();
		try {// Load Oracle JDBC Driver 
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			 // Connect to Oracle Database 
			Connection conn = DriverManager.getConnection(URL, USER, PSWD);
			Statement st = conn.createStatement();
			st.executeQuery("SELECT EMP_ID, JOB_DATE, THEATER_ID, JOB_TYPE FROM AAHMED31.EMPSCHEDULE WHERE EMP_ID="+emp+"");
			ResultSet rs = st.getResultSet();
			while (rs.next()) {
				EmpSchedule empsched = new EmpSchedule(rs.getInt(1), rs.getDate(2), rs.getInt(3), rs.getString(4));
				empscheds.add(empsched);
			}
			
			Statement st2 = conn.createStatement();
			st2.executeQuery("SELECT * FROM AAHMED31.JOBTRAINING WHERE EMP_ID = "+emp+"");
			ResultSet rs2 = st2.getResultSet();
			int id = rs2.getInt(1);
			boolean j = (rs2.getInt(2) == 1);
			boolean s = (rs2.getInt(3) == 1);
			boolean t = (rs2.getInt(4) == 1);
			emptrain.add(new EmployeeTrain(id,j,s,t));
			
			conn.close(); 
		} catch (Exception ex) { 
			System.err.println(ex.getMessage()); 
		}
	}
	public static void addSched(int emp_id, Date job_d, int th_id, String job_t) {
		//java.sql.Date sqlDate=java.sql.Date.valueOf(""+job_d);
		//java.util.Date d = new java.util.Date();
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		String ddf = df.format(job_d).toString();
		//java.sql.Date dbd = java.sql.Date.valueOf(ddf);
		try {// Load Oracle JDBC Driver 
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			 // Connect to Oracle Database 
			Connection conn = DriverManager.getConnection(URL, USER, PSWD);
			Statement st = conn.createStatement();
			st.executeUpdate("INSERT INTO AAHMED31.EMPSCHEDULE VALUES ('"+emp_id+"', to_date('"+ddf+"', 'MM/dd/yyyy'), '"+th_id+"', '"+job_t+"')");
			conn.close(); 
		} catch (Exception ex) { 
			System.err.println(ex.getMessage()); 
		}
	}
	public static void delSched(int emp_id, Date job_d, int th_id, String job_t) {
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		String ddf = df.format(job_d).toString();
		//java.sql.Date dbd = java.sql.Date.valueOf(ddf);
		try {// Load Oracle JDBC Driver 
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			 // Connect to Oracle Database 
			Connection conn = DriverManager.getConnection(URL, USER, PSWD);
			Statement st = conn.createStatement();
			st.executeUpdate("DELETE FROM AAHMED31.EMPSCHEDULE WHERE EMP_ID = "+emp_id+" AND JOB_DATE = to_date('"+ddf+"', 'MM/dd/yyyy') AND THEATER_ID = "+th_id+" AND JOB_TYPE = '"+job_t+"'");
			conn.close(); 
		} catch (Exception ex) { 
			System.err.println(ex.getMessage()); 
		}
	}
	public static boolean checkDate(int emp_id, Date job_d) {
		boolean sameDate = false;
		//java.sql.Date sqlDate=java.sql.Date.valueOf(""+job_d);
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		String ddf = df.format(job_d).toString();
		//java.sql.Date dbd = java.sql.Date.valueOf(ddf);
		try {// Load Oracle JDBC Driver 
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			 // Connect to Oracle Database 
			Connection conn = DriverManager.getConnection(URL, USER, PSWD);
			Statement st = conn.createStatement();
			st.executeQuery("SELECT EMP_ID FROM AAHMED31.EMPSCHEDULE WHERE EMP_ID ="+emp_id+" AND JOB_DATE = to_date('"+ddf+"', 'MM/dd/yyyy')");
			ResultSet rs = st.getResultSet();
			if(rs.next()){
				sameDate = true;
			}else{
				sameDate = false;
			}
			conn.close(); 
		} catch (Exception ex) { 
			System.err.println(ex.getMessage()); 
		}
		return sameDate;
	}
	
	public static ArrayList<EmpShift> getShifts(int emp_id) {
		ArrayList<EmpShift> shifts = new ArrayList<EmpShift>();
		try {// Load Oracle JDBC Driver 
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			 // Connect to Oracle Database 
			Connection conn = DriverManager.getConnection(URL, USER, PSWD);
			Statement st = conn.createStatement();
			st.executeQuery("SELECT * FROM AAHMED31.EMPSCHEDULE WHERE EMP_ID ='"+emp_id+"'");
			ResultSet rs = st.getResultSet();
			while (rs.next()) {
				EmpShift shift = new EmpShift(rs.getInt(1), rs.getDate(2), rs.getInt(3), rs.getString(4));
				shifts.add(shift);
			}
			conn.close(); 
		} catch (Exception ex) { 
			System.err.println(ex.getMessage()); 
		}
		return shifts;
	}
	
	class EmpSchedule
	{
		int emp, theater;
		Date j_date;
		String j_type;
		
		public EmpSchedule() {
		}
		
		public EmpSchedule(int eid, Date d, int tid, String t)
		{
			emp = eid;
			theater = tid;
			j_date = d;
			j_type = t;
		}
	}
}

class EmpShift
{
	int emp, theater;
	Date j_date;
	String j_type;
	
	public EmpShift() {
	}
	
	public EmpShift(int eid, Date d, int tid, String t)
	{
		emp = eid;
		theater = tid;
		j_date = d;
		j_type = t;
	}
}
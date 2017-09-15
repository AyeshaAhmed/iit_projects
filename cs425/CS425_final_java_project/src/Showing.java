import java.sql.Date;

public class Showing {
	
	String title;
	double price;
	int theater;
	int mid;
	int rid;
	int sid;
	Date date;
	
	public Showing() {
	}
	
	public Showing(double p, int mi, int ri, Date d, int si) {
		price = p;
		mid = mi;
		rid = ri;
		sid = si;
		title = "";
		theater = 0;
		date = d;
	}
	
	public String toString() {
		return "price = "+price+", title = "+title+", theater="+theater+", date="+date.toString();
	}
	
}

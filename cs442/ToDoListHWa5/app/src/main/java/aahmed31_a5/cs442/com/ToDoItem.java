package aahmed31_a5.cs442.com;

import java.text.SimpleDateFormat;
import java.util.Date;
import android.text.format.Time;



public class ToDoItem {

  String task;
  Date created;
  int counter = 0;
  Time today;
  public int getNum() {return counter;}
  public void setNum(int c){counter = c;}
  public String getTask() {return task;}
  public Date getCreated() {return created;}

  //public String getTimeString() {return today.format("%k:%M:%S");}
  public ToDoItem(String _task) {
    this(_task, new Date(java.lang.System.currentTimeMillis()));
  }

  public ToDoItem(String _task, Date _created) {
    task = _task;
    created = _created;
    //counter++;

    today = new Time(Time.getCurrentTimezone());
    today.setToNow();
  }

  public String storeItem(){
        String date_str = new java.text.SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(created);
        String num_str = Integer.toString(counter);
        String items =num_str+"\t"+task+"\t"+date_str;
        return items;
  }

  @Override
  public String toString() {
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
    String dateString = sdf.format(created);
      String timeString = today.format("%k:%M:%S");
    return "(" + timeString +") " + task;
  }
}
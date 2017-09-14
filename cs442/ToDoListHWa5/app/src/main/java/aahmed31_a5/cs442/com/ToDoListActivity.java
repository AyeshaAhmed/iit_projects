package aahmed31_a5.cs442.com;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.StringTokenizer;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


public class ToDoListActivity extends Activity implements NewItemFragment.OnNewItemAddedListener {
  
  private ArrayList<ToDoItem> todoItems;
  private ToDoItemAdapter aa;
  private SharedPrefs sharedPrefs;

  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Inflate your view
    setContentView(R.layout.main);
      
    // Get references to the Fragments
    FragmentManager fm = getFragmentManager();
    ToDoListFragment todoListFragment = 
      (ToDoListFragment)fm.findFragmentById(R.id.TodoListFragment);
     
    // Create the array list of to do items
    todoItems = new ArrayList<ToDoItem>();
     
    // Create the array adapter to bind the array to the ListView
    int resID = R.layout.todolist_item;
    aa = new ToDoItemAdapter(this, resID, todoItems);
     
    // Bind the array adapter to the ListView.
    todoListFragment.setListAdapter(aa);

  }
  
  public void onNewItemAdded(String newItem) {
    ToDoItem newTodoItem = new ToDoItem(newItem);
    if (todoItems.size()==0)
      newTodoItem.setNum(1);
    else
        newTodoItem.setNum(todoItems.get(0).getNum()+1);
    todoItems.add(0, newTodoItem);
    aa.notifyDataSetChanged();

  }
    @Override
    protected void onPause() {
        super.onPause();
        sharedPrefs = new SharedPrefs();
        Iterator<ToDoItem> iter = todoItems.iterator();
        String store="";
        while(iter.hasNext()){
            ToDoItem temp = iter.next();
            store+=temp.storeItem()+";";
        }
        sharedPrefs.save(this, store);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sharedPrefs = new SharedPrefs();
        String taskList = sharedPrefs.getValue(this);
        if(taskList !=null) {
            StringTokenizer tkn = new StringTokenizer(taskList, ";");
            todoItems.clear();
            while (tkn.hasMoreTokens()) {
                StringTokenizer temp = new StringTokenizer(tkn.nextToken(), "\t");
                int count = Integer.parseInt(temp.nextToken());
                String task = temp.nextToken();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                Date created;
                try {
                    created = sdf.parse(temp.nextToken());
                } catch (ParseException e) {
                    created = new Date(java.lang.System.currentTimeMillis());
                }
                ToDoItem newTodoItem = new ToDoItem(task, created);
                newTodoItem.setNum(count);
                todoItems.add(todoItems.size(), newTodoItem);
            }
            aa.notifyDataSetChanged();
        }
    }

    public void ExportButton(View view) {
        Boolean status = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (!status)
            Toast.makeText(this, "No SD card present", Toast.LENGTH_LONG).show();
        else{
            File listFile = new File(this.getExternalFilesDir(null),"SavedToDoList.txt");
            try {
                Iterator<ToDoItem> iter = todoItems.iterator();
                String result="";
                while(iter.hasNext()){
                    ToDoItem temp = iter.next();
                    result+=temp.storeItem()+"\n";
                }
                FileWriter fw =new FileWriter(listFile);
                fw.write(result);
                fw.close();
                Toast.makeText(this,"list was exported.",Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                Log.w("ExternalStorage", "Error writing " + listFile, e);
            }
        }
    }

    public void ImportButton(View view) {
        Boolean status = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (!status)
            Toast.makeText(this,"No SD card present",Toast.LENGTH_LONG).show();
        else{
            File listFile = new File(this.getExternalFilesDir(null),"SavedToDoList.txt");
            try {
                todoItems.clear();
                FileReader fr = new FileReader(listFile);
                BufferedReader br = new BufferedReader(fr);
                String line;
                while((line = br.readLine())!=null) {
                    StringTokenizer temp = new StringTokenizer(line, "\t");
                    int count = Integer.parseInt(temp.nextToken());
                    String task = temp.nextToken();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                    Date created;
                    try {
                        created = sdf.parse(temp.nextToken());
                    } catch (ParseException e) {
                        created = new Date(java.lang.System.currentTimeMillis());
                    }
                    ToDoItem newTodoItem = new ToDoItem(task, created);
                    newTodoItem.setNum(count);
                    todoItems.add(todoItems.size(), newTodoItem);
                }
                aa.notifyDataSetChanged();
                fr.close();
            } catch (IOException e) {
                Log.w("ExternalStorage", "Error reading " + listFile, e);
            }
        }
    }

    public void ResetButton(View view){
        todoItems.clear();
        aa.notifyDataSetChanged();
    }

}
package gift_card2.IPRO.com;

import android.app.ActionBar;
import android.app.Activity;
//import android.support.v7.app.ActionBar;
//import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class StoreList extends AppCompatActivity {
    public static String storeN = "", personN = "", priceN = "";
    private ArrayList<HashMap<String,Object>> cards = new ArrayList<HashMap<String, Object>>();
    private ListView myList;

    public static final String STORES_NAME = "store_name";
    public static final String CARDS_PRICE = "price";
    public static final String CARDS_NUM = "num";
    public static final String CARDS_PIN = "pin";
    public static final String PERSONS_NAME = "person_name";
    public static final String ID = "_index";
    public static final String ISEDIT = "is_edit";
    public static final int REG_REQ_CODE = 55;
    public static final int REG_RESULT_CODE = 56;
    public static final int ADD = 110;
    public static final int EDIT = 111;
    public static final int DELETE = 112;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_list);
        String storeType = MainActivity.s_name;
        TextView textView = (TextView) findViewById(R.id.store_type);
        textView.setText(storeType);
        myList = (ListView)findViewById(R.id.mylist);
        getAllFromDB();
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                        int position, long arg3){
                // TODO Auto-generated method stub
                String chosen = ""+myList.getItemAtPosition(position);
                int endname = chosen.indexOf(", n");
                storeN = chosen.substring(12, endname);
                int personstart = 12+chosen.indexOf("person_name=");
                int personend = chosen.indexOf(", _");
                personN = chosen.substring(personstart, personend);
                int pricestart = 3+chosen.indexOf("ce=");
                int priceend = chosen.indexOf(", pin");
                priceN = "$"+chosen.substring(pricestart, priceend);
                Toast.makeText(StoreList.this, storeN, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(StoreList.this, CardList.class);
                startActivity(intent);
            }
        });
        registerForContextMenu(myList);
    }


    private void getAllFromDB(){
        cards = new ArrayList<HashMap<String, Object>>();
        MyDB myDB = new MyDB(this);
        myDB.openDB();
        Cursor cursor = myDB.getAllUsers();
        cursor.moveToFirst();
        for(int i = 0; i < cursor.getCount(); i++){
            HashMap<String,Object> hm = new HashMap<String,Object>();
            hm.put(STORES_NAME,cursor.getString(cursor.getColumnIndex(MyDB.STORE_NAME)));
            hm.put(CARDS_PRICE,cursor.getInt(cursor.getColumnIndex(MyDB.CARD_PRICE)));
            hm.put(CARDS_NUM,cursor.getInt(cursor.getColumnIndex(MyDB.CARD_NUM)));
            hm.put(CARDS_PIN,cursor.getInt(cursor.getColumnIndex(MyDB.CARD_PIN)));
            hm.put(PERSONS_NAME,cursor.getString(cursor.getColumnIndex(MyDB.PERSON_NAME)));
            hm.put(ID,cursor.getLong(cursor.getColumnIndex(MyDB.STORE_ID)));
            cards.add(hm);
            cursor.moveToNext();
        }
        cursor.close();
        myDB.closeDB();
        SimpleAdapter adapter = new SimpleAdapter(this,
                cards,
                R.layout.listbox,
                new String[]{STORES_NAME,CARDS_PRICE},
                new int[]{R.id.storeholder,R.id.priceholder});
        myList.setAdapter(adapter);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        menu.setHeaderTitle("Do you want to ?");
        menu.add(Menu.NONE,EDIT,0,"Edit");
        menu.add(Menu.NONE,DELETE,0,"Delete");

        super.onCreateContextMenu(menu, v, menuInfo);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();

        switch (item.getItemId()){
            case EDIT:
                Intent intent = new Intent(this,RegisterActivity.class);
                intent.putExtra(ISEDIT,true);
                intent.putExtra(ID,(Long)cards.get(info.position).get(ID));

                startActivityForResult(intent, REG_REQ_CODE);
                break;
            case DELETE:
                MyDB myDB = new MyDB(this);
                myDB.openDB();
                myDB.deleteUser((Long)cards.get(info.position).get(ID));
                myDB.closeDB();
                getAllFromDB();

                break;
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_store_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home){
            finish();
        }
        if (id == R.id.action_add){
            Intent intent = new Intent(this,RegisterActivity.class);
            intent.putExtra(ISEDIT,false);
            startActivityForResult(intent, REG_REQ_CODE);
        }
        if(id == R.id.action_edit){
            Toast.makeText(this, "Please long press the card you wish to edit.", Toast.LENGTH_LONG).show();
        }
        if(id == R.id.action_delete){
            Toast.makeText(this, "Please long press the card you wish to delete.", Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REG_REQ_CODE){
            if (resultCode == REG_RESULT_CODE){

                getAllFromDB();

            }
        }

    }
}

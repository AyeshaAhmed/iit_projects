package gift_card2.IPRO.com;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class PersonList extends AppCompatActivity {

    public static String mName, mRate, mPref, mLoc, mPrice;
    private ArrayList<HashMap<String,String>> personArray = new ArrayList<HashMap<String, String>>();
    ListView personList;
    String rangeTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_list);
        rangeTitle = CardList.r_name;
        TextView title = (TextView) findViewById(R.id.range_title);
        title.setText(rangeTitle);
        personList = (ListView) findViewById(R.id.myRangelist);
        initList();
        personList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String chosen = "" + personList.getItemAtPosition(position);
                int endprice = chosen.indexOf(", r");
                mPrice = chosen.substring(7, endprice);
                int startrate = 2+(chosen.indexOf("g="));
                int endrate = chosen.indexOf(", l");
                mRate = chosen.substring(startrate, endrate);
                int startloc = 2+(chosen.indexOf("n="));
                int endloc = chosen.indexOf(", p");
                mLoc = chosen.substring(startloc, endloc);
                int startname = 12+(chosen.indexOf("person_name="));
                int endname = chosen.indexOf(", s");
                mName = chosen.substring(startname, endname);
                int startpref = 2+(chosen.indexOf("f="));
                int endpref = chosen.indexOf("}");
                mPref = chosen.substring(startpref, endpref);
                Toast.makeText(PersonList.this, mName, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(PersonList.this, PersonInfo.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_person_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void initList(){
        String n = "person_name", r = "rating", p = "price",
                s = "swap_pref", l = "location";

        personArray = new ArrayList<HashMap<String, String>>();

        HashMap<String, String> hm = new HashMap<String, String>();
        hm.put(n, "Vishal");
        hm.put(r, "4 Stars");
        hm.put(p, "$15");
        hm.put(s, "Swap");
        hm.put(l, "New York City, NY");
        personArray.add(hm);

        HashMap<String, String> hm1 = new HashMap<String, String>();
        hm1.put(n, "Joshua");
        hm1.put(r, "3 Stars");
        hm1.put(p, "$100");
        hm1.put(s, "Sell");
        hm1.put(l, "San Diego, CA");
        personArray.add(hm1);

        HashMap<String, String> hm2 = new HashMap<String, String>();
        hm2.put(n, "Han");
        hm2.put(r, "5 Stars");
        hm2.put(p, "$25");
        hm2.put(s, "Swap");
        hm2.put(l, "Chicago, IL");
        personArray.add(hm2);

        HashMap<String, String> hm3 = new HashMap<String, String>();
        hm3.put(n, "Duncan");
        hm3.put(r, "1 Stars");
        hm3.put(p, "$20");
        hm3.put(s, "Sell");
        hm3.put(l, "Mars");
        personArray.add(hm3);

        HashMap<String, String> hm4 = new HashMap<String, String>();
        hm4.put(n, "Ayesha");
        hm4.put(r, "4 Stars");
        hm4.put(p, "$50");
        hm4.put(s, "Swap");
        hm4.put(l, "Skokie, IL");
        personArray.add(hm4);

        HashMap<String, String> hm5 = new HashMap<String, String>();
        hm5.put(n, StoreList.personN);
        hm5.put(r, "5 Stars");
        hm5.put(p, StoreList.priceN);
        hm5.put(s, "Swap");
        hm5.put(l, "Chicago, IL");
        personArray.add(hm5);

        SimpleAdapter adapter = new SimpleAdapter(this,
                personArray,
                R.layout.person_listbox,
                new String[]{n,r,p},
                new int[]{R.id.nameholder,R.id.starholder,R.id.cardpriceholder});
        personList.setAdapter(adapter);
    }
}

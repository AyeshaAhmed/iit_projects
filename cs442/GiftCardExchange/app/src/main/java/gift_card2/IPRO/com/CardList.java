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

public class CardList extends AppCompatActivity {
    public static String r_name = "";

    private ArrayList<HashMap<String,String>> cardArray = new ArrayList<HashMap<String, String>>();
    ListView cardList;
    String storename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_list);
        storename = StoreList.storeN;
        TextView title = (TextView) findViewById(R.id.store_title);
        title.setText(storename);
        cardList = (ListView) findViewById(R.id.myCardlist);
        initList();
        cardList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String chosen = ""+cardList.getItemAtPosition(position);
                int endrange = chosen.indexOf(", s");
                String range = chosen.substring(7, endrange);
                String rangeTitle = storename+"\n"+range;
                Toast.makeText(CardList.this, rangeTitle, Toast.LENGTH_SHORT).show();
                r_name = rangeTitle;
                Intent intent = new Intent(CardList.this, PersonList.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_card_list, menu);
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
        String n = "storename", r = "range";

        cardArray = new ArrayList<HashMap<String, String>>();

        HashMap<String, String> hm = new HashMap<String, String>();
        hm.put(n, storename);
        hm.put(r, "$10<= -- >$25");
        cardArray.add(hm);

        HashMap<String, String> hm1 = new HashMap<String, String>();
        hm1.put(n, storename);
        hm1.put(r, "$25<= -- >$50");
        cardArray.add(hm1);

        HashMap<String, String> hm2 = new HashMap<String, String>();
        hm2.put(n, storename);
        hm2.put(r, "$50<= -- >$100");
        cardArray.add(hm2);

        HashMap<String, String> hm3 = new HashMap<String, String>();
        hm3.put(n, storename);
        hm3.put(r, "$100<= -- >$200");
        cardArray.add(hm3);

        HashMap<String, String> hm4 = new HashMap<String, String>();
        hm4.put(n, storename);
        hm4.put(r, "$200<=");
        cardArray.add(hm4);

        SimpleAdapter adapter = new SimpleAdapter(this,
                cardArray,
                R.layout.listbox,
                new String[]{n,r},
                new int[]{R.id.storeholder,R.id.priceholder});
        cardList.setAdapter(adapter);
    }
}

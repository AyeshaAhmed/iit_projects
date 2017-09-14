package aahmed31_a6.cs442.com;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import aahmed31_a6.cs442.com.dummy.DummyContent;

/**
 * An activity representing a single Item detail screen. This
 * activity is only used on handset devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link ItemListActivity}.
 * <p/>
 * This activity is mostly just a 'shell' activity containing nothing
 * more than a {@link ItemDetailFragment}.
 */
public class ItemDetailActivity extends AppCompatActivity {

    private DBController dbController;
    private static DummyContent.DummyItem mItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        dbController = new DBController(getApplicationContext());
        // Show the Up button in the action bar.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(ItemDetailFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(ItemDetailFragment.ARG_ITEM_ID));
            ItemDetailFragment fragment = new ItemDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.item_detail_container, fragment)
                    .commit();
            mItem = DummyContent.ITEM_MAP.get(arguments.getString(ItemDetailFragment.ARG_ITEM_ID));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        menu.removeItem(R.id.add);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, ItemListActivity.class));
            return true;
        }

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.new_item, (ViewGroup) findViewById(R.id.main));
        final EditText item_name = (EditText) view.findViewById(R.id.add_name_edit);
        final TextView detail_head = (TextView) view.findViewById(R.id.detail_head);
        final TextView price_head = (TextView) view.findViewById(R.id.add_price_head);
        final EditText item_price = (EditText) view.findViewById(R.id.add_price);
        final EditText item_desc = (EditText) view.findViewById(R.id.add_desc);
        final EditText item_ing = (EditText) view.findViewById(R.id.add_ingre);
        final EditText item_reci = (EditText) view.findViewById(R.id.add_recipie);
        final TextView add_name = (TextView) view.findViewById(R.id.add_name);
        final TextView add_des = (TextView) view.findViewById(R.id.add_desc_head);
        final TextView add_ing = (TextView) view.findViewById(R.id.add_ingre_head);
        final TextView add_res = (TextView) view.findViewById(R.id.add_recipie_head);

        //noinspection SimplifiableIfStatement
        try {
            dbController.open();
        } catch (Exception e) {
        }
        final Cursor c1 = dbController.fetch();
        Integer[] map_arr = new Integer[c1.getCount()];
        int i=0;
        for(c1.moveToFirst(); !c1.isAfterLast(); c1.moveToNext()) {
            map_arr[i] = c1.getInt(c1.getColumnIndex(MyDB.COLUMN_ID));
            i++;
        }
        final Cursor c = dbController.fetchById((map_arr[(Integer.parseInt(mItem.id)-1)]));

        if(id == R.id.update){

            detail_head.setText("Update First Item");
            item_name.setText(c.getString(c.getColumnIndex(MyDB.COLUMN_ITEM_NAME)));
            item_price.setText(c.getString(c.getColumnIndex(MyDB.COLUMN_ITEM_PRICE)));
            item_desc.setText(c.getString(c.getColumnIndex(MyDB.COLUMN_ITEM_DESC)));
            item_ing.setText(c.getString(c.getColumnIndex(MyDB.COLUMN_ITEM_ING)));
            item_reci.setText(c.getString(c.getColumnIndex(MyDB.COLUMN_ITEM_RECIPIE)));
            //add_des.setVisibility(View.GONE);
            //add_ing.setVisibility(View.GONE);
            //add_res.setVisibility(View.GONE);



            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(view)
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            final String name = item_name.getText().toString();
                            final String price = item_price.getText().toString();
                            final String desc =item_desc.getText().toString();
                            final String ing =item_ing.getText().toString();
                            final String rec = item_reci.getText().toString();
                            dbController.update(c.getInt(c.getColumnIndex(MyDB.COLUMN_ID)), name, price, desc, ing, rec);
                            dbController.close();
                            Intent i = getIntent();
                            finish();
                            startActivity(i);

                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dbController.close();
                            dialog.cancel();
                        }
                    })
                    .show();

            return true;
        }
        if(id == R.id.delete){
            detail_head.setText("Delete First Item");
            add_name.setText("Name: " + c.getString(c.getColumnIndex(MyDB.COLUMN_ITEM_NAME)));
            price_head.setText("Price: " + c.getString(c.getColumnIndex(MyDB.COLUMN_ITEM_PRICE)));
            item_desc.setVisibility(View.GONE);
            item_ing.setVisibility(View.GONE);
            item_reci.setVisibility(View.GONE);
            add_des.setVisibility(View.GONE);
            add_ing.setVisibility(View.GONE);
            add_res.setVisibility(View.GONE);
            item_price.setVisibility(View.GONE);
            item_name.setVisibility(View.GONE);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(view)
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            dbController.delete(c.getInt(c.getColumnIndex(MyDB.COLUMN_ID)));
                            dbController.close();
                            Intent i = new Intent(getApplicationContext(),ItemListActivity.class);
                            finish();
                            startActivity(i);

                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dbController.close();
                            dialog.cancel();
                        }
                    })
                    .show();
        }


        return super.onOptionsItemSelected(item);
    }
}

package aahmed31_a6.cs442.com;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p/>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link ItemListFragment} and the item details
 * (if present) is a {@link ItemDetailFragment}.
 * <p/>
 * This activity also implements the required
 * {@link ItemListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class ItemListActivity extends AppCompatActivity
        implements ItemListFragment.Callbacks {
    private DBController dbController;
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        dbController = new DBController(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            ((ItemListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.item_list))
                    .setActivateOnItemClick(true);
        }

        // TODO: If exposing deep links into your app, handle intents here.
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int id = item.getItemId();

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


        if (id == R.id.add) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(view).setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String nameString = item_name.getText().toString();

                    try {
                        dbController.open();
                        final String name = item_name.getText().toString();
                        final String price = item_price.getText().toString();
                        final String desc = item_desc.getText().toString();
                        final String ing = item_ing.getText().toString();
                        final String rec = item_reci.getText().toString();
                        dbController.insert(name, price, desc, ing, rec);
                        dbController.close();
                        Intent i = getIntent();
                        finish();
                        startActivity(i);

                    } catch (Exception e) {

                    }

                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            }).show();
        }
        if(id == R.id.update) {
            try {
                dbController.open();
            } catch (Exception e) {

            }
            final long _id = 1;
            final Cursor c = dbController.fetch();
            c.moveToFirst();
            detail_head.setText("Update Food Item");
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
                            final String desc = item_desc.getText().toString();
                            final String ing = item_ing.getText().toString();
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


        }
        if(id == R.id.delete) {
            try {
                dbController.open();
            } catch (Exception e) {

            }
            final Cursor c1 = dbController.fetch();
            c1.moveToFirst();
            detail_head.setText("Delete Food Item");
            add_name.setText("Name: "+c1.getString(c1.getColumnIndex(MyDB.COLUMN_ITEM_NAME)));
            price_head.setText("Price: "+c1.getString(c1.getColumnIndex(MyDB.COLUMN_ITEM_PRICE)));
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

                            dbController.delete(c1.getInt(c1.getColumnIndex(MyDB.COLUMN_ID)));
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



        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Callback method from {@link ItemListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(String id) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(ItemDetailFragment.ARG_ITEM_ID, id);
            ItemDetailFragment fragment = new ItemDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit();

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, ItemDetailActivity.class);
            detailIntent.putExtra(ItemDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
    }
}

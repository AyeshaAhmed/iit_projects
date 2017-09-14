package sdutta9_a6.cs442.com.foodItem;

import android.app.FragmentManager;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

import java.util.ArrayList;

public class FoodItemListActivity extends Activity
        implements FoodItemListFragment.Callbacks, LoaderManager.LoaderCallbacks<Cursor> {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    private static  String TAG = "FoodItemListActivity";
    private ArrayList<MenuItem> menuItems = new ArrayList<MenuItem>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get Food Menu from a file named Items.txt
        DataModel dataModel = new DataModel(this, "Items");

        //menuItems = dataModel.getItems();

        //Ensures a loader is initialized and active.
        getLoaderManager().initLoader(0, null, this);

        FragmentManager fm = getFragmentManager();
        setContentView(R.layout.activity_fooditem_list);

        if (findViewById(R.id.FoodItem_detail_container) != null && getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE) {
            mTwoPane = true;
            // Its a tablet, so create a new detail fragment if one does not already exist
            FoodItemDetailFragment df = (FoodItemDetailFragment) fm.findFragmentByTag("Detail");
            if (df == null) {
                // Initialize new detail fragment
                df = new FoodItemDetailFragment();
                Bundle args = new Bundle();
                MenuItem mi=new MenuItem(0);
                mi.name="Welcome to Food Menu App";
                args.putParcelable("item", mi);
                df.setArguments(args);
                fm.beginTransaction().replace(R.id.FoodItem_detail_container, df, "Detail").commit();
            }
        }

        // Initialize a new MenuItem list fragment, if one does not already exist
        FoodItemListFragment cf = (FoodItemListFragment) fm.findFragmentByTag("List");
        if ( cf == null) {
            cf = new FoodItemListFragment();
            Bundle arguments = new Bundle();
            arguments.putParcelableArrayList("Items", menuItems);
            cf.setArguments(arguments);
            fm.beginTransaction().replace(R.id.FoodItem_list, cf, "List").commit();

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Starts a new or restarts an existing Loader in this manager
        getLoaderManager().restartLoader(0, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mainlst, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_Add) {
            // starts a new Intent to add a FoodItem
            Log.v("Inside Add","-------Inside Add------");
            Intent foodItemEdit = new Intent(getBaseContext(), FoodItemEdit.class);
            Bundle bundle = new Bundle();
            bundle.putString("mode", "add");
            foodItemEdit.putExtras(bundle);
            startActivity(foodItemEdit);
        }
        if (id == R.id.action_Delete) {
            // starts a new Intent to add a FoodItem
            Log.v("Inside Delete","-------Inside Delete------");
            Intent foodItemEdit = new Intent(getBaseContext(), FoodItemEdit.class);
            Bundle bundle = new Bundle();
            bundle.putString("mode", "delete");
            bundle.putString("rowid",Integer.toString(menuItems.get(0).id));
            foodItemEdit.putExtras(bundle);
            startActivity(foodItemEdit);
        }

        return super.onOptionsItemSelected(item);
    }
    /**
     * Callback method from {@link FoodItemListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(MenuItem item) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            // Pass the selected MenuItem object to the DetailFragment
            arguments.putParcelable("item", item);
            FoodItemDetailFragment fragment = new FoodItemDetailFragment();
            fragment.setArguments(arguments);
            getFragmentManager().beginTransaction()
                    .replace(R.id.FoodItem_detail_container, fragment)
                    .commit();

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, FoodItemDetailActivity.class);
            // Pass the selected MenuItem object to the DetailActivity
            detailIntent.putExtra("item", item);
            startActivity(detailIntent);
        }
    }

    // This is called when a new Loader needs to be created.
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                MenuItemDB.KEY_ROWID,
                MenuItemDB.KEY_NAME,
                MenuItemDB.KEY_PRICE,
                MenuItemDB.KEY_DETAILS,
                MenuItemDB.KEY_METHOD,
                MenuItemDB.KEY_INGREDIENTS};
        CursorLoader cursorLoader = new CursorLoader(this,
                ExampleProvider.CONTENT_URI, projection, null, null, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Swap the new cursor in.  (The framework will take care of closing the
        // old cursor once we return.)
        //dataAdapter.swapCursor(data);
        menuItems.clear();
        if(data!= null) {
            data.moveToFirst();
            while (!data.isAfterLast()) {
                MenuItem gc = new MenuItem(data.getInt(data.getColumnIndex("_id")));
                gc.name = data.getString((data.getColumnIndex("name")));
                gc.price = data.getString((data.getColumnIndex("price")));
                gc.details = data.getString((data.getColumnIndex("details")));
                gc.method = data.getString((data.getColumnIndex("method")));
                gc.ingredients = data.getString((data.getColumnIndex("ingredients")));
                menuItems.add(gc);
                data.moveToNext();
            }
        }


    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // This is called when the last Cursor provided to onLoadFinished()
        // above is about to be closed.  We need to make sure we are no
        // longer using it.
        //dataAdapter.swapCursor(null);
        menuItems.clear();
    }
}

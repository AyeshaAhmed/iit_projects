package aahmed31_a3.cs442.com;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
@TargetApi(17)

/**
 * An activity representing a list of FoodItems. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link FoodDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p/>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link FoodListFragment} and the item details
 * (if present) is a {@link FoodDetailFragment}.
 * <p/>
 * This activity also implements the required
 * {@link FoodListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class FoodListActivity extends FragmentActivity
        implements FoodListFragment.Callbacks {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //boolean orient = getResources().getConfiguration().getLayoutDirection() == Configuration.ORIENTATION_LANDSCAPE);
        //if(orient){
        //    setContentView(R.layout.);
        //}
        setContentView(R.layout.activity_food_list);

        if (findViewById(R.id.food_detail_container) != null
                && getResources().getConfiguration().getLayoutDirection() == Configuration.ORIENTATION_LANDSCAPE) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            ((FoodListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.food_list))
                    .setActivateOnItemClick(true);
            //long click is also implemented
            ((FoodListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.food_list))
                    .setActivateOnItemLongClick(true);
            //Double click is also implemented
            ((FoodListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.food_list))
                    .setActivateOnItemDoubleClick(true);
        }

        // TODO: If exposing deep links into your app, handle intents here.
    }

    /**
     * Callback method from {@link FoodListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(String id) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(FoodDetailFragment.ARG_ITEM_ID, id);
            FoodDetailFragment fragment = new FoodDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.food_detail_container, fragment)
                    .commit();

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, FoodDetailActivity.class);
            detailIntent.putExtra(FoodDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
    }
}

package sdutta9_a6.cs442.com.foodItem;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A fragment representing a single MenuItem detail screen.
 * This fragment is either contained in a {@link FoodItemListActivity}
 * in two-pane mode (on tablets) or a {@link FoodItemDetailActivity}
 * on handsets.
 */
public class FoodItemDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FoodItemDetailFragment() {
    }

    private MenuItem item;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // If intent arguments have a item object, get it
        if (getArguments().containsKey("item")) {
            item = getArguments().getParcelable("item");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_fooditem_detail, container, false);

        if (item != null) {

            ((TextView) rootView.findViewById(R.id.FoodItem_title)).setText(String.format("%2d %-20s", item.id, item.name));
            ((TextView) rootView.findViewById(R.id.FoodItem_price)).setText(item.price+"  ");
            ((TextView)rootView.findViewById(R.id.FoodItem_detail)).setText(item.details);

            ImageView img = (ImageView)rootView.findViewById(R.id.imageView);
            int id = getResources().getIdentifier("sdutta9_a6.cs442.com.foodItem:mipmap/s"+Integer.toString(item.id),null,null);
            img.setImageResource(id);

            ((TextView)rootView.findViewById(R.id.FoodItem_method)).setText(item.method);

            ((TextView)rootView.findViewById(R.id.FoodItem_ingredients)).setText(item.ingredients);
        }
        else {
            ((TextView) rootView.findViewById(R.id.FoodItem_title)).setText("Welcome to Food Menu App");
        }

        return rootView;
    }

}
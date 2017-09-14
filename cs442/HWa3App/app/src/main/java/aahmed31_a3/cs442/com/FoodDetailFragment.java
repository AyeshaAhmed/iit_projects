package aahmed31_a3.cs442.com;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import aahmed31_a3.cs442.com.FoodModel;

/**
 * A fragment representing a single Food detail screen.
 * This fragment is either contained in a {@link FoodListActivity}
 * in two-pane mode (on tablets) or a {@link FoodDetailActivity}
 * on handsets.
 */
public class FoodDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private FoodModel.FoodItem mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FoodDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = FoodModel.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_food_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.Title)).setText(mItem.name);
            ((TextView) rootView.findViewById(R.id.Price)).setText(mItem.price);
            ((TextView) rootView.findViewById(R.id.Drescription)).setText(mItem.descrip);
            ((ImageView) rootView.findViewById(R.id.imageView)).setImageResource(mItem.imageID);
            ((TextView) rootView.findViewById(R.id.Ingredients)).setText(mItem.ingred);
            ((TextView) rootView.findViewById(R.id.Instructions)).setText(mItem.instruct);
        }

        return rootView;
    }
}

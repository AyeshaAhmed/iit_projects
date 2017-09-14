package aahmed31_a6.cs442.com;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import aahmed31_a6.cs442.com.dummy.DummyContent;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListActivity}
 * in two-pane mode (on tablets) or a {@link ItemDetailActivity}
 * on handsets.
 */
public class ItemDetailFragment extends Fragment {
    private DBController dbController;
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private DummyContent.DummyItem mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbController = new DBController(getActivity());

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_item_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            String n = "";
            try {
                dbController.open();
            } catch (Exception e) {

            }
            Cursor c1 = dbController.fetch();
            Integer[] map_arr = new Integer[c1.getCount()];
            int i=0;
            for(c1.moveToFirst(); !c1.isAfterLast(); c1.moveToNext()) {
                map_arr[i] = c1.getInt(c1.getColumnIndex(MyDB.COLUMN_ID));
                i++;
            }
            int m= (Integer.parseInt(mItem.id));
            Cursor c = dbController.fetchById((map_arr[m-1] ));
            ((TextView) rootView.findViewById(R.id.Name)).setText(c.getString(c.getColumnIndex(MyDB.COLUMN_ITEM_NAME)));
            ((TextView) rootView.findViewById(R.id.Price)).setText(c.getString(c.getColumnIndex(MyDB.COLUMN_ITEM_PRICE)));
            ((TextView) rootView.findViewById(R.id.item_detail)).setText(c.getString(c.getColumnIndex(MyDB.COLUMN_ITEM_DESC)));
            ((TextView) rootView.findViewById(R.id.ing)).setText(c.getString(c.getColumnIndex(MyDB.COLUMN_ITEM_ING)));
            ((TextView) rootView.findViewById(R.id.ins)).setText(c.getString(c.getColumnIndex(MyDB.COLUMN_ITEM_RECIPIE)));
            ImageView img = (ImageView) rootView.findViewById(R.id.imageView);
            int id = getResources().getIdentifier("aahmed31_a6.cs442.com:mipmap/p"+Integer.toString((m%11)),null,null);
            img.setImageResource(id);

        }

        return rootView;
    }
}

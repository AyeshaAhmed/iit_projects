package gift_card2.IPRO.com;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HomeFragment extends Fragment {
    //public final static String EXTRA_MESSAGE = "gift_card2.IPRO.com.MESSAGE";
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    /*public void localButton (View view){
        Intent intent = new Intent(getActivity(),StoreList.class);
        Log.d("TAG", "in home local button");
        //String storeType = "Local Stores \n(Chicago)";
        //intent.putExtra(EXTRA_MESSAGE, storeType);
        startActivity(intent);
    }
    public void chainButton (View view){
        Intent intent = new Intent(getActivity(),StoreList.class);
        Log.d("TAG", "in home chain button");
        //String storeType = "Chain Stores \n(United States)";
        //intent.putExtra(EXTRA_MESSAGE, storeType);
        startActivity(intent);
    }*/
}

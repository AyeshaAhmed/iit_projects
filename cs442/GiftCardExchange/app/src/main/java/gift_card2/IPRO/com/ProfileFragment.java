package gift_card2.IPRO.com;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;


public class ProfileFragment extends Fragment {

    private ArrayList<HashMap<String,String>> comments = new ArrayList<HashMap<String, String>>();
    ListView commentsList;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        commentsList = (ListView) view.findViewById(R.id.feedbackList);
        initList();

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void initList(){
        String c = "comment", n = "name";

        comments = new ArrayList<HashMap<String, String>>();

        HashMap<String, String> hm = new HashMap<String, String>();
        hm.put(c, "Very fast responder! 5 stars");
        hm.put(n, "Han");
        comments.add(hm);

        HashMap<String, String> hm1 = new HashMap<String, String>();
        hm1.put(c, "Prompt.");
        hm1.put(n, "Vishal");
        comments.add(hm1);

        HashMap<String, String> hm2 = new HashMap<String, String>();
        hm2.put(c, "Take my like sir!");
        hm2.put(n, "Ayesha");
        comments.add(hm2);

        HashMap<String, String> hm3 = new HashMap<String, String>();
        hm3.put(c, "No hassel.");
        hm3.put(n, "Joshua");
        comments.add(hm3);

        HashMap<String, String> hm4 = new HashMap<String, String>();
        hm4.put(c, "Good guy.");
        hm4.put(n, "Duncan");
        comments.add(hm4);

        SimpleAdapter adapter = new SimpleAdapter(getActivity(),
                comments,
                R.layout.comment_listbox,
                new String[]{c, n},
                new int[]{R.id.commentholder,R.id.commmenter_name_holder});
        commentsList.setAdapter(adapter);
    }

}

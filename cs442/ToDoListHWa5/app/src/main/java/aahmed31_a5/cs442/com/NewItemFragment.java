package aahmed31_a5.cs442.com;

import android.app.Activity;
import android.app.Fragment;
import android.view.KeyEvent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


public class NewItemFragment extends Fragment {

  private OnNewItemAddedListener onNewItemAddedListener;

   private int cnt;
  public interface OnNewItemAddedListener {
    public void onNewItemAdded(String newItem);
  }
  
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
    Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.new_item_fragment, container, false);
      cnt = 0;

    final EditText myEditText = 
      (EditText)view.findViewById(R.id.myEditText);

    myEditText.setOnKeyListener(new View.OnKeyListener() {
      public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN)
          if ((keyCode == KeyEvent.KEYCODE_DPAD_CENTER) ||
                    (keyCode == KeyEvent.KEYCODE_ENTER)) {
              String editStr = myEditText.getText().toString();
            //  String editStr = editStr1.replaceAll("\\s|\\r|\\n", "");
              editStr = editStr.trim();
              if(editStr.isEmpty()) {
                  myEditText.setText("");
                  return true;
              }
              cnt++;
              //StringBuilder aStr = new StringBuilder();
              //aStr.append("");
              //aStr.append(cnt);
              //aStr.append(".    ");
              //aStr.append(editStr);


              //String newItem = aStr.toString();
            onNewItemAddedListener.onNewItemAdded(editStr);
            myEditText.setText("");
            return true;
          }
        return false;
      }
    });

    return view;
  }
  

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
      
    try {
      onNewItemAddedListener = (OnNewItemAddedListener)activity;
    } catch (ClassCastException e) {
      throw new ClassCastException(activity.toString() + 
                " must implement OnNewItemAddedListener");
    }
  }


}
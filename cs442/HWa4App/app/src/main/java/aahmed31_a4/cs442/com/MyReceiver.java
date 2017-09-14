package aahmed31_a4.cs442.com;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Ayesha Ahmed on 10/3/2015.
 */
public class MyReceiver extends BroadcastReceiver { //This class implements and handles broadcast receiver objects
    @Override // Tell the compiler that you want to overwrite a certain function
    public void onReceive(Context context, Intent intent) {
        String message = "Power USB connection:  "    //The message we print in our toast to check that we received the broadcast
                + intent.getAction();   //Gets the action of the activity specified by the intent-filter

        Toast.makeText(context, message,
                Toast.LENGTH_LONG).show(); // A long toast with our message will be displayed to check if we received the broadcast.
    }
}

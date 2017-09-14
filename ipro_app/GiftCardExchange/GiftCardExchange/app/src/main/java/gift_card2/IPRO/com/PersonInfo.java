package gift_card2.IPRO.com;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PersonInfo extends AppCompatActivity {
    public String per_name, per_price, per_store, per_pref, per_loc, per_rate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);
        per_name = PersonList.mName;
        per_rate = PersonList.mRate;
        per_pref = PersonList.mPref;
        per_loc = PersonList.mLoc;
        per_price = PersonList.mPrice;
        per_store = StoreList.storeN;

        TextView title = (TextView) findViewById(R.id.person_info_name);
        title.setText(per_name);
        TextView rating = (TextView) findViewById(R.id.person_info_rate);
        rating.setText(per_rate);
        TextView prefers = (TextView) findViewById(R.id.person_info_pref);
        prefers.setText(per_pref);
        TextView locate = (TextView) findViewById(R.id.person_info_loc);
        locate.setText(per_loc);
        TextView price = (TextView) findViewById(R.id.person_info_price);
        price.setText(per_price);
        TextView mstore = (TextView) findViewById(R.id.person_info_store);
        mstore.setText(per_store);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_person_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void swapClicked(View v){
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_box, (ViewGroup) findViewById(R.id.dialog));
        TextView output = (TextView) view.findViewById(R.id.message);
        output.setText(per_name+" will been notified of your offer to swap cards. " +
                "\nWe will send you a notification as soon as we hear back from them. " +
                "\nDo want to proceed with this offer?");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view).setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).show();
    }

    public void buyClicked(View v){
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_box, (ViewGroup) findViewById(R.id.dialog));
        TextView output = (TextView) view.findViewById(R.id.message);
        output.setText(per_name+" will been notified of your offer to buy their card. " +
                "\nWe will send you a notification as soon as we hear back from them. " +
                "\nDo want to proceed with this offer?");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view).setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).show();
    }
}

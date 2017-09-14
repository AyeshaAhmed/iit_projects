package aahmed31_a2.cs442.com;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class HW2Activity extends AppCompatActivity {
    int inc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hw2);
        inc = 0;
        inc++;
        Log.d("Lifecycle_aahmed31", Integer.toString(inc)+": onCreate() called:");
    }

    @Override
    protected void onStart(){
        super.onStart();
        inc++;
        Log.d("Lifecycle_aahmed31", Integer.toString(inc)+": onStart() called:");
    }

    @Override
    protected void onStop(){
        super.onStop();
        inc++;
        Log.d("Lifecycle_aahmed31", Integer.toString(inc)+": onStop() called:");
    }

    @Override
    protected void onResume(){
        super.onResume();
        inc++;
        Log.d("Lifecycle_aahmed31", Integer.toString(inc)+": onResume() called:");
    }

    @Override
    protected void onPause(){
        super.onPause();
        inc++;
        Log.d("Lifecycle_aahmed31", Integer.toString(inc)+": onPause() called:");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        inc++;
        Log.d("Lifecycle_aahmed31", Integer.toString(inc)+": onDestroy() called:");
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        inc++;
        Log.d("Lifecycle_aahmed31", Integer.toString(inc)+": onRestart() called:");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_hw2, menu);
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

    public void myButtonPressed(View view){
        Log.d("Lifecycle_aahmed31", "My first button hitted!");
        finish();
    }
}

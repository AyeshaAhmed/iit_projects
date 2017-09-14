package gift_card2.IPRO.com;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    //public final static String EXTRA_MESSAGE = "gift_card2.IPRO.com.MESSAGE";
    public static String s_name = "";
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private DrawerLayout drawerLayout;
    private ListView navList;
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawerlayout);
        navList = (ListView)findViewById(R.id.navlist);
        ArrayList<String> navArray = new ArrayList<String>();
        navArray.add("Home");
        navArray.add("Account Info");
        navArray.add("Profile");
        navArray.add("My Wallet");
        navArray.add("Transaction History");
        navArray.add("Rewards");
        navArray.add("Logout");
        navList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_activated_1,navArray);
        navList.setAdapter(adapter);
        navList.setOnItemClickListener(this);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.opendrawer,R.string.closedrawer);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        fragmentManager = getSupportFragmentManager();

        loadSelection(0);
    }

    private void loadSelection(int i){
        navList.setItemChecked(i,true);

        switch (i){
            case 0:
                HomeFragment homeFragment = new HomeFragment();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentholder, homeFragment);
                fragmentTransaction.commit();
                break;
            case 1:
                AccountFragment accountFragment = new AccountFragment();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentholder,accountFragment);
                fragmentTransaction.commit();
                break;

            case 2:
                ProfileFragment profileFragment = new ProfileFragment();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentholder,profileFragment);
                fragmentTransaction.commit();
                break;
            case 3:
                WalletFragment walletFragment = new WalletFragment();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentholder,walletFragment);
                fragmentTransaction.commit();
                break;

            case 4:
                HistoryFragment historyFragment = new HistoryFragment();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentholder, historyFragment);
                fragmentTransaction.commit();
                break;

            case 5:
                RewardsFragment rewardsFragment = new RewardsFragment();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentholder, rewardsFragment);
                fragmentTransaction.commit();
                break;

            case 6:
                LogoutFragment logoutFragment = new LogoutFragment();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentholder, logoutFragment);
                fragmentTransaction.commit();
                break;
        }

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        }else if (id == android.R.id.home){
            if (drawerLayout.isDrawerOpen(navList)){
                drawerLayout.closeDrawer(navList);
            }else{
                drawerLayout.openDrawer(navList);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        loadSelection(position);

        drawerLayout.closeDrawer(navList);
    }

    @Override
    public void onBackPressed() {
        navList.setItemChecked(0,true);
        HomeFragment homeFragment = new HomeFragment();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentholder, homeFragment);
        fragmentTransaction.commit();
    }

    public void localButton (View view){
        Intent intent = new Intent(this,StoreList.class);
        String storeType = "Local Stores \n(Chicago)";
        s_name = storeType;
        startActivity(intent);
    }

    public void chainButton (View view){
        Intent intent = new Intent(this,StoreList.class);
        String storeType = "Chain Stores \n(United States)";
        s_name = storeType;
        startActivity(intent);
    }

    public void yesLogout (View v){
        Toast.makeText(this, "OK, logging out. \nGood Bye! Come back soon!", Toast.LENGTH_LONG).show();
        finish();
    }

    public void noLogout (View v){
        Toast.makeText(this, "OK, directing you back to the home page.", Toast.LENGTH_LONG).show();
        navList.setItemChecked(0,true);
        HomeFragment homeFragment = new HomeFragment();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentholder, homeFragment);
        fragmentTransaction.commit();
    }

}

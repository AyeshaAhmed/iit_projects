package sdutta9_a6.cs442.com.foodItem;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FoodItemEdit extends Activity implements OnClickListener {

    private Button save, delete;
    private String mode;
    private EditText item_price, item_name, item_details, item_method, item_ingredients;
    private String id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item_page);

        // get the values passed to the activity from the calling activity
        // determine the mode - add, update or delete
        if (this.getIntent().getExtras() != null){
            Bundle bundle = this.getIntent().getExtras();
            mode = bundle.getString("mode");
        }

        // get references to the buttons and attach listeners
        save = (Button) findViewById(R.id.save);
        save.setOnClickListener(this);
        delete = (Button) findViewById(R.id.delete);
        delete.setOnClickListener(this);

        item_name = (EditText) findViewById(R.id.item_name);
        item_price = (EditText) findViewById(R.id.item_price);
        item_details = (EditText) findViewById(R.id.item_details);
        item_method = (EditText) findViewById(R.id.item_method);
        item_ingredients = (EditText) findViewById(R.id.item_ingredients);


        /*// create a dropdown for users to select various continents
        continentList = (Spinner) findViewById(R.id.continentList);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.continent_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        continentList.setAdapter(adapter);*/

        // if in add mode disable the delete option
        if(mode.trim().equalsIgnoreCase("add")){
            delete.setEnabled(false);
        }
        // get the rowId for the specific country
        else{
            Bundle bundle = this.getIntent().getExtras();
            id = bundle.getString("rowId");
            loadCountryInfo();
        }
    }

    // based on the rowId get all information from the Content Provider
    // about that country
    private void loadCountryInfo(){

        String[] projection = {
                MenuItemDB.KEY_ROWID,
                MenuItemDB.KEY_NAME,
                MenuItemDB.KEY_PRICE,
                MenuItemDB.KEY_DETAILS,
                MenuItemDB.KEY_METHOD,
                MenuItemDB.KEY_INGREDIENTS};
        Uri uri = Uri.parse(ExampleProvider.CONTENT_URI + "/" + id);
        Cursor cursor = getContentResolver().query(uri, projection, null, null,
                null);
        if (cursor != null) {
            cursor.moveToFirst();
            String myName = cursor.getString(cursor.getColumnIndexOrThrow(MenuItemDB.KEY_NAME));
            String myPrice = cursor.getString(cursor.getColumnIndexOrThrow(MenuItemDB.KEY_PRICE));
            String myDetails = cursor.getString(cursor.getColumnIndexOrThrow(MenuItemDB.KEY_DETAILS));
            String mymethod = cursor.getString(cursor.getColumnIndexOrThrow(MenuItemDB.KEY_METHOD));
            String myingredients = cursor.getString(cursor.getColumnIndexOrThrow(MenuItemDB.KEY_INGREDIENTS));

            item_name.setText(myName);
            item_price.setText(myPrice);
            item_details.setText(myDetails);
            item_method.setText(mymethod);
            item_ingredients.setText(myingredients);
        }
    }
    public void onClick(View v) {

        // get values from the input text fields
        String myName = item_name.getText().toString();
        String myPrice = item_price.getText().toString();
        String myDetails = item_details.getText().toString();
        String myMethod = item_method.getText().toString();
        String myIngredients = item_ingredients.getText().toString();

        // check for blanks
        if(myName.trim().equalsIgnoreCase("")){
            Toast.makeText(getBaseContext(), "Please ENTER food item name", Toast.LENGTH_LONG).show();
            return;
        }
        // check for blanks
        if(myPrice.trim().equalsIgnoreCase("")){
            Toast.makeText(getBaseContext(), "Please ENTER price", Toast.LENGTH_LONG).show();
            return;
        }
        // check for blanks
        if(myDetails.trim().equalsIgnoreCase("")){
            Toast.makeText(getBaseContext(), "Please ENTER food item detail", Toast.LENGTH_LONG).show();
            return;
        }
        // check for blanks
        if(myMethod.trim().equalsIgnoreCase("")){
            Toast.makeText(getBaseContext(), "Please ENTER food item Method", Toast.LENGTH_LONG).show();
            return;
        }
        // check for blanks
        if(myIngredients.trim().equalsIgnoreCase("")){
            Toast.makeText(getBaseContext(), "Please ENTER food item Ingredients", Toast.LENGTH_LONG).show();
            return;
        }
        switch (v.getId()) {
            case R.id.save:
                ContentValues values = new ContentValues();
                values.put(MenuItemDB.KEY_NAME, myName);
                values.put(MenuItemDB.KEY_PRICE, myPrice);
                values.put(MenuItemDB.KEY_DETAILS, myDetails);
                values.put(MenuItemDB.KEY_METHOD, myMethod);
                values.put(MenuItemDB.KEY_INGREDIENTS,myIngredients);

                // insert a record
                if(mode.trim().equalsIgnoreCase("add")){
                    getContentResolver().insert(ExampleProvider.CONTENT_URI, values);
                }
                // update a record
                else {
                    Uri uri = Uri.parse(ExampleProvider.CONTENT_URI + "/" + id);
                    getContentResolver().update(uri, values, null, null);
                }

                //Toast.makeText(getBaseContext(), "Add button pressed", Toast.LENGTH_LONG).show();
                finish();

                break;

            case R.id.delete:
                // delete a record
                Uri uri = Uri.parse(ExampleProvider.CONTENT_URI + "/" + id);
                getContentResolver().delete(uri, null, null);


                Toast.makeText(getBaseContext(), "Delete button pressed", Toast.LENGTH_LONG).show();
                finish();

                break;

            // More buttons go here (if any) ...

        }
    }
}

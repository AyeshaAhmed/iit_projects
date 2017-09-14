package gift_card2.IPRO.com;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {
    private EditText cardStoreField,cardPriceField,cardNumField,cardPinField,cardPersonField;
    private Boolean isEdit = false;
    private long index = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Bundle bundle = getIntent().getExtras();
        cardStoreField = (EditText)findViewById(R.id.card_store);
        cardPriceField = (EditText)findViewById(R.id.card_price);
        cardNumField = (EditText)findViewById(R.id.card_num);
        cardPinField = (EditText)findViewById(R.id.card_pin);
        cardPersonField = (EditText)findViewById(R.id.card_person);
        Button save = (Button)findViewById(R.id.save_button);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDB myDB = new MyDB(RegisterActivity.this);
                myDB.openDB();
                Intent intent = new Intent();

                if (isEdit) {
                    //edit

                    myDB.updateUserById(index, cardStoreField.getText().toString(), Integer.parseInt(cardPriceField.getText().toString()),
                            Integer.parseInt(cardNumField.getText().toString()), Integer.parseInt(cardPinField.getText().toString()),
                            cardPersonField.getText().toString());
                    setResult(StoreList.REG_RESULT_CODE, intent);
                } else {
                    //add

                    myDB.addUser(cardStoreField.getText().toString(), Integer.parseInt(cardPriceField.getText().toString()),
                            Integer.parseInt(cardNumField.getText().toString()), Integer.parseInt(cardPinField.getText().toString()),
                            cardPersonField.getText().toString());
                    setResult(StoreList.REG_RESULT_CODE, intent);
                }

                myDB.closeDB();

                finish();
            }
        });
        //ActionBar actionBar = getSupportActionBar();
        //actionBar.setDisplayHomeAsUpEnabled(true);
        if (bundle != null){
            isEdit = bundle.getBoolean(StoreList.ISEDIT);
            if (isEdit){
                //for editing
                index = bundle.getLong(StoreList.ID);
                MyDB myDB = new MyDB(this);
                myDB.openDB();
                Cursor cursor = myDB.getUserById(index);
                cursor.moveToFirst();
                for (int i = 0; i < cursor.getCount();i++){
                    cardStoreField.setText(cursor.getString(cursor.getColumnIndex(MyDB.STORE_NAME)));
                    int cardPrice = cursor.getInt(cursor.getColumnIndex(MyDB.CARD_PRICE));
                    cardPriceField.setText(Integer.toString(cardPrice));
                    int cardNum = cursor.getInt(cursor.getColumnIndex(MyDB.CARD_NUM));
                    cardNumField.setText(Integer.toString(cardNum));
                    int cardPin = cursor.getInt(cursor.getColumnIndex(MyDB.CARD_PIN));
                    cardPinField.setText(Integer.toString(cardPin));
                    cardPersonField.setText(cursor.getString(cursor.getColumnIndex(MyDB.PERSON_NAME)));
                    cursor.moveToNext();
                }
                cursor.close();
                myDB.closeDB();

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
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
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}

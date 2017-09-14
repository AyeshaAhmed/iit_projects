package sdutta9_a6.cs442.com.foodItem;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MenuItemDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "FoodMenu";
    private static final int DATABASE_VERSION = 1;

    MenuItemDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        MenuItemDB.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        MenuItemDB.onUpgrade(db, oldVersion, newVersion);
    }

}
package sdutta9_a6.cs442.com.foodItem;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class MenuItemDB {
    public static final String KEY_ROWID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_PRICE = "price";
    public static final String KEY_DETAILS = "details";
    public static final String KEY_METHOD = "method";
    public static final String KEY_INGREDIENTS = "ingredients";

    private static final String LOG_TAG = "MenuItemDb";
    public static final String SQLITE_TABLE = "MenuItems";

    private static final String DATABASE_CREATE =
            "CREATE TABLE if not exists " + SQLITE_TABLE + " (" +
                    KEY_ROWID + " integer PRIMARY KEY autoincrement," +
                    KEY_NAME + "," +
                    KEY_PRICE + "," +
                    KEY_DETAILS + "," +
                    KEY_METHOD + "," +
                    KEY_INGREDIENTS + "," +
                    " UNIQUE (" + KEY_ROWID +"));";

    public static void onCreate(SQLiteDatabase db) {
        Log.w(LOG_TAG, DATABASE_CREATE);
        db.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(LOG_TAG, "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + SQLITE_TABLE);
        onCreate(db);
    }
}
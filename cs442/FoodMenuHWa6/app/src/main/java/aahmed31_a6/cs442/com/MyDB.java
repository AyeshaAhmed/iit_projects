package aahmed31_a6.cs442.com;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ayesha Ahmed on 11/14/2015.
 */
public class MyDB extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "foodtable";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ITEM_NAME = "name";
    public static final String COLUMN_ITEM_PRICE = "price";
    public static final String COLUMN_ITEM_DESC = "description";
    public static final String COLUMN_ITEM_ING = "ingridients";
    public static final String COLUMN_ITEM_RECIPIE = "recipie";
    private static final String DATABASE_NAME = "foodmenu.db";
    private static final int DATABASE_VERSION = 1;


    private static final String DATABASE_CREATE = "create table " + TABLE_NAME + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_ITEM_NAME + " TEXT NOT NULL , " +
            COLUMN_ITEM_PRICE + " TEXT NOT NULL, " +
            COLUMN_ITEM_DESC + " TEXT NULL," +
            COLUMN_ITEM_ING + " TEXT NULL," +
            COLUMN_ITEM_RECIPIE + " TEXT NULL);";

    public MyDB(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}

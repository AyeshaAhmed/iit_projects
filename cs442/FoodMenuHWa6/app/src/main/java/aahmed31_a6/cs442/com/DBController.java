package aahmed31_a6.cs442.com;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;

/**
 * Created by Ayesha Ahmed on 11/14/2015.
 */
public class DBController {

    private MyDB dbClass;
    private Context context;
    private SQLiteDatabase sqLiteDatabase;

    public DBController(Context c){
        context = c;
    }

    public DBController open() throws SQLException {
        dbClass = new MyDB(context);
        sqLiteDatabase = dbClass.getWritableDatabase();
        return this;
    }
    public void close(){
        dbClass.close();
    }

    public void insert(String name, String price, String description, String ingredients, String recipie){
        sqLiteDatabase = dbClass.getWritableDatabase();
        ContentValues contentValue = new ContentValues();
        contentValue.put(MyDB.COLUMN_ITEM_NAME, name);
        contentValue.put(MyDB.COLUMN_ITEM_PRICE, price);
        contentValue.put(MyDB.COLUMN_ITEM_DESC,description);
        contentValue.put(MyDB.COLUMN_ITEM_ING, ingredients);
        contentValue.put(MyDB.COLUMN_ITEM_RECIPIE, recipie);
        long i = sqLiteDatabase.insert(MyDB.TABLE_NAME, null, contentValue);
    }
    public Cursor fetch() {
        Cursor cursor = sqLiteDatabase.rawQuery("select * from '" + MyDB.TABLE_NAME+"'", null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchById(long id){
        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + MyDB.TABLE_NAME + " where " + MyDB.COLUMN_ID + " = " + id, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(long id , String name, String price, String description, String ingredients, String recipie){
        ContentValues contentValues = new ContentValues();
        contentValues.put(MyDB.COLUMN_ITEM_NAME,name);
        contentValues.put(MyDB.COLUMN_ITEM_PRICE,price);
        contentValues.put(MyDB.COLUMN_ITEM_DESC,description);
        contentValues.put(MyDB.COLUMN_ITEM_ING,ingredients);
        contentValues.put(MyDB.COLUMN_ITEM_RECIPIE,recipie);
        int u = sqLiteDatabase.update(MyDB.TABLE_NAME, contentValues, MyDB.COLUMN_ID + " = " + id, null);
        return u;
    }

    public void delete(long id) {
        sqLiteDatabase.delete(MyDB.TABLE_NAME, MyDB.COLUMN_ID + "=" + id, null);
    }
}

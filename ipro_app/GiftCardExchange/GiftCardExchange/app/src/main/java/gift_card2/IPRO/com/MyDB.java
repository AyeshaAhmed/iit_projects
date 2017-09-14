package gift_card2.IPRO.com;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ayesha Ahmed on 10/29/2015.
 */
public class MyDB {

    public static final String DB_NAME = "giftcarddb.db";
    public static final String TABLE_NAME = "cards_tbl";
    public static final String STORE_NAME = "store_name";
    public static final String CARD_PRICE = "card_price";
    public static final String CARD_NUM = "card_num";
    public static final String CARD_PIN = "card_pin";
    public static final String PERSON_NAME = "person_name";
    public static final String STORE_ID = "_id";

    //public static final String CREATE_TBL = "CREATE TABLE students_tbl (_id INTEGER PRIMARY KEY AUTOINCREMENT, student_name TEXT, student_roll_num INTEGER)";
    public static final String CREATE_TBL = "CREATE TABLE "+TABLE_NAME
            +" ("+STORE_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
            +STORE_NAME+" TEXT NOT NULL, "
            +CARD_PRICE+" INTEGER NOT NULL,"
            +CARD_NUM+" INTEGER NOT NULL,"
            +CARD_PIN+" INTEGER,"
            +PERSON_NAME+" TEXT NOT NULL " +")";
    public static final String DROP_TBL = "DROP TABLE IF EXISTS "+TABLE_NAME;

    public static final int VER = 1;

    private SQLiteDatabase db;
    private MyDBOpenHelper myDBOpenHelper;

    public MyDB(Context context){
        myDBOpenHelper = new MyDBOpenHelper(context);
    }

    public void openDB() throws SQLiteException {
        db = myDBOpenHelper.getWritableDatabase();
    }

    public void closeDB(){
        db.close();
    }

    public long addUser(String store_name,int price,int card_num, int card_pin, String person_name){
        ContentValues contentValues = new ContentValues();
        contentValues.put(STORE_NAME,store_name);
        contentValues.put(CARD_PRICE,price);
        contentValues.put(CARD_NUM,card_num);
        contentValues.put(CARD_PIN,card_pin);
        contentValues.put(PERSON_NAME,person_name);
        return db.insert(TABLE_NAME,null,contentValues);
    }

    public int updateUserById(long studId,String store_name,int price,int card_num, int card_pin, String person_name){
        //UPDATE SET
        ContentValues contentValues = new ContentValues();
        contentValues.put(STORE_NAME,store_name);
        contentValues.put(CARD_PRICE,price);
        contentValues.put(CARD_NUM,card_num);
        contentValues.put(CARD_PIN,card_pin);
        contentValues.put(PERSON_NAME,person_name);
        return db.update(TABLE_NAME,contentValues,STORE_ID+ "= ?",new String[]{Long.toString(studId)});
    }

    public Cursor getAllUsers(){
        //SELECT * FROM students_tbl;
        Cursor cursor = db.query(TABLE_NAME,new String[]{STORE_ID,STORE_NAME,CARD_PRICE,CARD_NUM,CARD_PIN,PERSON_NAME},null,null,null,null,null);
        cursor.moveToFirst();
        return cursor;
    }

    public Cursor getUserById(long stuId){
        //SELECT * FROM students_tbl WHERE _id = ?
        Cursor cursor = db.query(TABLE_NAME,new String[]{STORE_ID,STORE_NAME,
                CARD_PRICE,CARD_NUM,CARD_PIN,PERSON_NAME},STORE_ID+" = ?",
                new String[]{Long.toString(stuId)},null,null,null);
        cursor.moveToFirst();
        return cursor;
    }

    public int deleteUser(long stuId){
        return db.delete(TABLE_NAME,STORE_ID+" = ?",new String[]{Long.toString(stuId)});
    }

    private class MyDBOpenHelper extends SQLiteOpenHelper {

        public MyDBOpenHelper(Context context) {
            super(context, DB_NAME, null, VER);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TBL);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            if (newVersion > oldVersion){
                db.execSQL(DROP_TBL);
                onCreate(db);
            }
        }
    }
}

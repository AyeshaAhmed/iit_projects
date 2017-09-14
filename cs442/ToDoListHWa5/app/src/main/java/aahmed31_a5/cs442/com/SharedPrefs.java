package aahmed31_a5.cs442.com;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Ayesha Ahmed on 10/11/2015.
 */
public class SharedPrefs {
    public static final String NAME = "name_pref";
    public static final String KEY = "key_pref";

    public SharedPrefs(){
        super();
    }

    public void save(Context context, String text) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.putString(KEY, text);
        editor.commit();
    }

    public String getValue(Context context) {
        SharedPreferences settings;
        String text;
        settings = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        text = settings.getString(KEY, null);
        return text;
    }
}

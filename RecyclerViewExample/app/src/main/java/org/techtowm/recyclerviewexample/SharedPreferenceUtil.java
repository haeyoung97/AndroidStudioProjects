package org.techtowm.recyclerviewexample;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceUtil {
    public static String getPreference(Context context, String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences("diary", Context.MODE_PRIVATE);
        return sharedPreferences.getString(key,null);
    }

    public static void savePreferences(Context context, String key, String data){
        SharedPreferences sharedPreferences = context.getSharedPreferences("diary",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, data);
        editor.commit();
    }

    public static void removePreferences(Context context, String key){
        SharedPreferences pref = context.getSharedPreferences("diary",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(key);
        editor.commit();
    }
}

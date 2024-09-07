package com.data.atul_auto_sales.Utills;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {
    static SharedPreferences sharedPreferences;

    public PrefManager() {
        init();
    }

    public static void putString(String key, String val) {
        init();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, val);
        editor.apply();
    }

    public static String getString(String key, String defaultValue) {
        init();
        return sharedPreferences.getString(key, defaultValue);
    }


    public static void putInt(String key, int val) {
        init();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, val);
        editor.apply();
    }

    public static int getInt(String key, int defaultValue) {
        init();
        return sharedPreferences.getInt(key, defaultValue);
    }
    public static void putBoolean(String key, boolean val) {
        init();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, val);
        editor.apply();
    }

    public static boolean getBoolean(String key, boolean defaultToMetric) {
        init();
        return sharedPreferences.getBoolean(key, defaultToMetric);
    }

    public static boolean checkKey(String key) {
        sharedPreferences = new SalesApp().getApplicationContext().getSharedPreferences("antimatter", Context.MODE_PRIVATE);

        return sharedPreferences.contains(key);
    }
    private static void init() {
        sharedPreferences = SalesApp.appContext.getSharedPreferences("antimatter", Context.MODE_PRIVATE);
    }
    public static void clear(){
        init();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

}

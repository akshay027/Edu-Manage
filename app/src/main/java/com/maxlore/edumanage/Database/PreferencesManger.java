package com.maxlore.edumanage.Database;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Nikhil on 04-06-2016.
 */
public class PreferencesManger {
    public static final String PREFS_ID = "APP_PREFS";

    public static String getStringFields(Context context, String key) {
        SharedPreferences settings;
        String value;
        settings = context.getSharedPreferences(PREFS_ID, Context.MODE_PRIVATE);
        value = settings.getString(key, "");
        return value;
    }

    public static String getArraylistFields(Context context, ArrayList<String> key) {
        SharedPreferences settings;
        String value;
        settings = context.getSharedPreferences(PREFS_ID, Context.MODE_PRIVATE);
        value = settings.getString(String.valueOf(key), "");
        return value;
    }

    public static void addStringFields(Context context, String key, String value) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        Log.e("addStringFields", "value --" + value);
        settings = context.getSharedPreferences(PREFS_ID, Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void addArraylistFields(Context context, ArrayList<String> key, String value) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        Log.e("addStringFields", "value --" + value);
        settings = context.getSharedPreferences(PREFS_ID, Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.putString(String.valueOf(key), value);
        editor.commit();
    }

    public static boolean getBooleanFields(Context context, String key) {
        SharedPreferences settings;
        boolean value;
        settings = context.getSharedPreferences(PREFS_ID, Context.MODE_PRIVATE);
        value = settings.getBoolean(key, false);
        return value;
    }

    public static void addBooleanFields(Context context, String key, boolean value) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(PREFS_ID, Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }
//    public static int getIntFields(Context context, String key) {
//        SharedPreferences settings;
//        int value;
//        settings = context.getSharedPreferences(PREFS_ID, Context.MODE_PRIVATE);
//        value = settings.getInt(key, -1);
//        return value;
//    }
//    public static void addIntFields(Context context, String key, int value) {
//        SharedPreferences settings;
//        SharedPreferences.Editor editor;
//        settings = context.getSharedPreferences(PREFS_ID, Context.MODE_PRIVATE);
//        editor = settings.edit();
//        editor.putInt(key, value);
//        editor.commit();
//    }

    public static void clearPreferences(Context context) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(PREFS_ID, Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.clear();
        editor.commit();
    }
}

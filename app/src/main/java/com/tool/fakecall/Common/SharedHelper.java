package com.tool.fakecall.Common;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedHelper {
    private static final String PREF_FILE_NAME = "MyPrefs"; // Name of the preference file
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SharedHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    // Method to store a string value
    public void saveString(String key, String value) {
        editor.putString(key, value);
        editor.apply();
    }

    // Method to retrieve a string value
    public String getString(String key) {
        return sharedPreferences.getString(key, null);
    }

    // You can add similar methods for other types of data (int, boolean, etc.)
}

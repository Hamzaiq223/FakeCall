package com.tool.fakecall.Common;

import android.content.Context;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class JsonHelper {

    public static ArrayList<String> readJsonArrayFromAssets(Context context, String fileName) {
        ArrayList<String> dataArray = new ArrayList<>();
        try {
            // Read the JSON file from assets
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String jsonString = new String(buffer, "UTF-8");

            // Parse the JSON array
            JSONArray jsonArray = new JSONArray(jsonString);

            // Convert JSON array to ArrayList
            for (int i = 0; i < jsonArray.length(); i++) {
                String item = jsonArray.getString(i);
                dataArray.add(item);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return dataArray;
    }
}

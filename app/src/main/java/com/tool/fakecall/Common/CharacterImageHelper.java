package com.tool.fakecall.Common;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.tool.fakecall.Models.CharactersModel;
import com.tool.fakecall.R;

import java.util.HashMap;

public class CharacterImageHelper {

    // HashMap to store character names and their corresponding resource IDs
    private static HashMap<String, Integer> characterImagesMap;

    // Method to initialize the character images map
    private static void initializeCharacterImagesMap() {
        characterImagesMap = new HashMap<>();
        // Add character names and their corresponding resource IDs here
        characterImagesMap.put("c_ronaldo", R.drawable.c_ronaldo);
        characterImagesMap.put("harry_potter", R.drawable.harry_potter);
        characterImagesMap.put("Hashim Amla",R.drawable.hashim_amla);
        characterImagesMap.put("Messi",R.drawable.leo_messi);
        characterImagesMap.put("Spider Man",R.drawable.spider_man);
        // Add more mappings for other characters as needed
    }

    // Method to get character image by name
    public static Drawable getCharacterImage(Context context, String characterName) {
        // Initialize the character images map if not already initialized
        if (characterImagesMap == null) {
            initializeCharacterImagesMap();
        }

        // Default drawable
        Drawable drawable = null;

        // Retrieve drawable resource ID for the given character name
        Integer resourceId = characterImagesMap.get(characterName);

        // If resource ID is found, load the drawable
        if (resourceId != null) {
            drawable = context.getResources().getDrawable(resourceId);
        } else {
            // Handle case when character name is not found
            // You can set a default image or return null here based on your requirement
        }

        return drawable;
    }

    public static Integer getCharacterImageResourceId(Context context, String characterName) {
        // Initialize the character images map if not already initialized
        if (characterImagesMap == null) {
            initializeCharacterImagesMap();
        }

        // Retrieve drawable resource ID for the given character name
        return characterImagesMap != null ? characterImagesMap.get(characterName) : null;
    }
}

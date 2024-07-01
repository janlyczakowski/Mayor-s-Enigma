package com.tudresden.map;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;

public class SharedPreferencesHelper {

    private static final String PREF_LOCATIONS = "locations";
    private static final String PREF_MAP_RECREATED = "isMapRecreated";

    public static void saveLocations(Context context, ArrayList<PlaceModal> arrayList) {
        Gson gson = new Gson();
        String json = gson.toJson(arrayList);

        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_LOCATIONS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PREF_LOCATIONS, json);
        editor.apply();
    }

    public static ArrayList<PlaceModal> getLocations(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_LOCATIONS, Context.MODE_PRIVATE);
        String retrievedJson = sharedPreferences.getString(PREF_LOCATIONS, "");

        Gson gson = new Gson();
        return gson.fromJson(retrievedJson, new TypeToken<ArrayList<PlaceModal>>(){}.getType());
    }
        public static void saveIsMapRecreated(Context context, boolean isMapRecreated) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_MAP_RECREATED, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(PREF_MAP_RECREATED, isMapRecreated);
        editor.apply();
    }
    public static Boolean getIsMapRecreated(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_MAP_RECREATED, Context.MODE_PRIVATE);

        return sharedPreferences.getBoolean(PREF_MAP_RECREATED, false);
    }


}
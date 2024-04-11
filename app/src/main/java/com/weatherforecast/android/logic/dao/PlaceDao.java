package com.weatherforecast.android.logic.dao;

import android.content.Context;
import android.content.SharedPreferences;


import com.google.gson.Gson;
import com.weatherforecast.android.WeatherForecastApplication;
import com.weatherforecast.android.logic.model.Place;

public class PlaceDao {
    public static void savePlace(Place place) {
        SharedPreferences.Editor editor = sharedPreferences().edit();
        editor.putString("place", new Gson().toJson(place));
        editor.apply();
    }

    public static Place getSavedPlace() {
        String placeJson = sharedPreferences().getString("place", "");
        return new Gson().fromJson(placeJson, Place.class);
    }

    public static boolean isPlaceSaved() {
        return sharedPreferences().contains("place");
    }

    private static SharedPreferences sharedPreferences() {
        return WeatherForecastApplication.getContext().getSharedPreferences("weather", Context.MODE_PRIVATE);
    }
}

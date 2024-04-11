package com.weatherforecast.android.logic.network;

import com.weatherforecast.android.WeatherForecastApplication;
import com.weatherforecast.android.logic.model.PlaceResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PlaceService {
    @GET("v2/place?token=" + WeatherForecastApplication.TOKEN + "&lang=zh_CN")
    Call<PlaceResponse> searchPlaces(@Query("query") String query);
}
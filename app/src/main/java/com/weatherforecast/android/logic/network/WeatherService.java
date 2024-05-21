package com.weatherforecast.android.logic.network;

import com.weatherforecast.android.WeatherForecastApplication;
import com.weatherforecast.android.logic.model.weather.DailyResponse;
import com.weatherforecast.android.logic.model.weather.HourlyResponse;
import com.weatherforecast.android.logic.model.weather.RealtimeResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface WeatherService {
    @GET("v2.6/" + WeatherForecastApplication.TOKEN + "/{lng},{lat}/realtime")
    Call<RealtimeResponse> getRealtimeWeather(@Path("lng") String lng, @Path("lat") String lat);

    @GET("v2.6/" + WeatherForecastApplication.TOKEN + "/{lng},{lat}/hourly?hourlysteps=24")
    Call<HourlyResponse> getHourlyWeather(@Path("lng") String lng, @Path("lat") String lat);

    @GET("v2.6/" + WeatherForecastApplication.TOKEN + "/{lng},{lat}/daily?dailysteps=7")
    Call<DailyResponse> getDailyWeather(@Path("lng") String lng, @Path("lat") String lat);
}

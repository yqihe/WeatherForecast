package com.weatherforecast.android;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

public class WeatherForecastApplication extends Application {
    /*
        给该项目提供一种全局获取Context的方式
     */
    public static Context getContext() {
        return context;
    }
    @SuppressLint("StaticFieldLeak")
    private static Context context;

    public static final String TOKEN = "vrZU1uW5u6BVMxg9";

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
}

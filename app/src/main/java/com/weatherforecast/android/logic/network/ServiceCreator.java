package com.weatherforecast.android.logic.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceCreator {
    /*
        为了能够使用PlaceService接口，创建一个Retrofit构建器
     */
    private static final String BASE_URL = "https://api.caiyunapp.com/";

    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static <T> T create(Class<T> serviceClass) {
        return retrofit.create(serviceClass);
    }

}

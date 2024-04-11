package com.weatherforecast.android.logic.network

import com.weatherforecast.android.logic.model.DailyResponse
import com.weatherforecast.android.logic.model.HourlyResponse
import com.weatherforecast.android.logic.model.PlaceResponse
import com.weatherforecast.android.logic.model.RealtimeResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object WeatherForecastNetwork {
    private val placeService = ServiceCreator.create(PlaceService::class.java)

    private val weatherService = ServiceCreator.create(WeatherService::class.java)

    suspend fun searchPlaces(query: String): PlaceResponse = placeService.searchPlaces(query).await()

    suspend fun getDailyWeather(lng: String, lat: String): DailyResponse = weatherService.getDailyWeather(lng,lat).await()

    suspend fun getRealtimeWeather(lng: String, lat: String): RealtimeResponse = weatherService.getRealtimeWeather(lng,lat).await()

    suspend fun getHourlyWeather(lng: String, lat: String): HourlyResponse = weatherService.getHourlyWeather(lng,lat).await()

    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine {  continuation ->
            enqueue(object: Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) continuation.resume(body)
                    else continuation.resumeWithException(RuntimeException("response body is null"))
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }

            })
        }
    }
}
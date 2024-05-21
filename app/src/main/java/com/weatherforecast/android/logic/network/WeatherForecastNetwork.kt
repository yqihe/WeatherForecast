package com.weatherforecast.android.logic.network

import com.weatherforecast.android.logic.model.weather.DailyResponse
import com.weatherforecast.android.logic.model.weather.HourlyResponse
import com.weatherforecast.android.logic.model.weather.PlaceResponse
import com.weatherforecast.android.logic.model.weather.RealtimeResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object WeatherForecastNetwork {
    /*
        定义一个统一的网络数据源访问入口，对所有的网络请求的API进行封装
     */

    // 使用ServiceCreator创建了一个PlaceService接口的动态代理对象
    private val placeService = ServiceCreator.create(PlaceService::class.java)

    private val weatherService = ServiceCreator.create(WeatherService::class.java)

    // 发起搜索城市数据请求

    /*
        当外部调用searchPlaces()函数时，Retrofit立即发送网络请求，阻塞当前协程。
        直到服务器响应我们请求后，await()函数返回模型对象，恢复当前协程.
     */
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
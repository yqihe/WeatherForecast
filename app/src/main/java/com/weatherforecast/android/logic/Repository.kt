package com.weatherforecast.android.logic

import androidx.lifecycle.liveData
import com.weatherforecast.android.WeatherForecastApplication
import com.weatherforecast.android.logic.dao.PlaceDao
import com.weatherforecast.android.logic.model.Place
import com.weatherforecast.android.logic.model.PlaceManage
import com.weatherforecast.android.logic.model.Weather
import com.weatherforecast.android.logic.network.WeatherForecastNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import java.lang.RuntimeException
import kotlin.coroutines.CoroutineContext

object Repository {

    /*
        仓库层用来判断调用方请求的数据是应该从本地数据源中获取还是从网络数据源获取，并返回数据源给调用方。
     */
    private val placeManageDao = AppDatabase.getDatabase(WeatherForecastApplication.getContext()).placeManageDao()

    fun searchPlaces(query: String) = fire(Dispatchers.IO) {

        //Android不允许在主线程进行网络请求
        val placeResponse = WeatherForecastNetwork.searchPlaces(query)
        if (placeResponse.status == "ok") {
            val places = placeResponse.places
            Result.success(places)
        } else {
            Result.failure(RuntimeException("response status is ${placeResponse.status}"))
        }
    }

    fun refreshWeather(lng: String, lat: String) = fire(Dispatchers.IO) {
        coroutineScope {
            val deferredRealtime = async { WeatherForecastNetwork.getRealtimeWeather(lng, lat) }
            val deferredHourly = async { WeatherForecastNetwork.getHourlyWeather(lng, lat) }
            val deferredDaily = async { WeatherForecastNetwork.getDailyWeather(lng, lat) }
            val realtimeResponse = deferredRealtime.await()
            val hourlyResponse = deferredHourly.await()
            val dailyResponse = deferredDaily.await()
            if (realtimeResponse.status == "ok" && hourlyResponse.status == "ok" && dailyResponse.status == "ok") {
                val weather = Weather(
                    realtimeResponse.result.realtime,
                    hourlyResponse.result.hourly,
                    dailyResponse.result.daily
                )
                Result.success(weather)
            } else {
                Result.failure(
                    RuntimeException(
                        "realtime response status is ${realtimeResponse.status}" +
                                "hourly response status is ${hourlyResponse.status}" +
                                "daily response status is ${dailyResponse.status}"
                    )
                )
            }
        }
    }

    fun savePlace(place: Place) = PlaceDao.savePlace(place)


    fun getSavedPlace(): Place = PlaceDao.getSavedPlace()


    fun isPlaceSaved() = PlaceDao.isPlaceSaved()


    suspend fun addPlaceManage(placeManage: PlaceManage): Result<Int> {
        return try {
            withContext(Dispatchers.IO) {
                val queryPlaceManage = async {
                    placeManageDao.querySpecifyPlaceManage(
                        placeManage.lng,
                        placeManage.lat
                    )
                }.await()
                if (queryPlaceManage == null) {
                    async { placeManageDao.insertPlaceManage(placeManage) }.await()
                } else {
                    queryPlaceManage.realtimeTem = placeManage.realtimeTem
                    queryPlaceManage.skycon = placeManage.skycon
                    async { placeManageDao.updatePlaceManage(queryPlaceManage) }.await()
                }
                Result.success(1)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    suspend fun updatePlaceManage(placeManage: PlaceManage): Result<Int> {
        return try {
            withContext(Dispatchers.IO) {
                val queryPlaceManage = async {
                    placeManageDao.querySpecifyPlaceManage(
                        placeManage.lng,
                        placeManage.lat
                    )
                }.await()
                queryPlaceManage?.realtimeTem = placeManage.realtimeTem
                queryPlaceManage?.skycon = placeManage.skycon
                async { placeManageDao.updatePlaceManage(queryPlaceManage!!) }.await()
                Result.success(1)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deletePlaceManage(lng: String, lat: String): Result<Int> {
        return try {
            withContext(Dispatchers.IO) {
                val num = async { placeManageDao.deletePlaceManageByLngLat(lng, lat) }.await()
                Result.success(num)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun loadAllPlaceManages() = fire(Dispatchers.IO) {
        coroutineScope {
            val placeManageList = async { placeManageDao.loadAllPlaceManages() }.await()
            Result.success(placeManageList)
        }
    }

    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure(e)
            }
            emit(result)
        }
}
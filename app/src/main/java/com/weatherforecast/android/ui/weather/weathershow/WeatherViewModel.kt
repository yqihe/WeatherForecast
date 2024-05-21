package com.weatherforecast.android.ui.weather.weathershow

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.weatherforecast.android.logic.Repository
import com.weatherforecast.android.logic.model.weather.Location

class WeatherViewModel : ViewModel(){
    private val locationLiveData = MutableLiveData<Location>()

    var locationLng = ""

    var locationLat = ""

    var placeName = ""

    var placeAddress = ""

    var placeRealtimeTem = -100

    var placeSkycon = ""

    var isUpdatePlaceManage = false

    val weatherLiveData = locationLiveData.switchMap { location ->
        Repository.refreshWeather(location.lng,location.lat)
    }

    fun refreshWeather(lng: String, lat: String) {
        locationLiveData.value =
            Location(lng, lat)
    }
}
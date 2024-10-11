package com.weatherforecast.android.ui.weatherChat;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class WeatherViewModel extends AndroidViewModel {

    public WeatherViewModel(Application application) {
        super(application);
    }

    public LiveData<String> getWeatherInfo() {
        MutableLiveData<String> weatherData = new MutableLiveData<>();
        // 假设获取天气信息的逻辑
        weatherData.setValue("今天的天气是晴天，温度 25°C，适宜外出。");
        return weatherData;
    }
}
package com.weatherforecast.android.ui.weather;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class WeatherViewModel extends AndroidViewModel {

    public WeatherViewModel(Application application) {
        super(application);
    }

    public LiveData<String> getWeatherInfo() {
        MutableLiveData<String> weatherData = new MutableLiveData<>();
        // 假设获取天气信息的逻辑
        weatherData.setValue("今天的天气是晴天，温度 25°C，适宜外出。");
        return weatherData;
    }
}

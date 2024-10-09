package com.weatherforecast.android.ui.weatherChat;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.weatherforecast.android.R;

public class WeatherActivity extends AppCompatActivity {

    private WeatherViewModel weatherViewModel;
    private TextView weatherInfoText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        weatherInfoText = findViewById(R.id.weather_info_text);
        weatherViewModel = new ViewModelProvider(this).get(WeatherViewModel.class);

        loadWeatherInfo();
    }

    private void loadWeatherInfo() {
        weatherViewModel.getWeatherInfo().observe(this, weatherInfo -> {
            weatherInfoText.setText(weatherInfo);
        });
    }
}

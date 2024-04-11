package com.weatherforecast.android.logic.model;

public class Weather {
    private final RealtimeResponse.Realtime realtime;
    private final HourlyResponse.Hourly
            hourly;
    private final DailyResponse.Daily daily;

    public Weather(RealtimeResponse.Realtime realtime, HourlyResponse.Hourly hourly, DailyResponse.Daily daily) {
        this.realtime = realtime;
        this.hourly = hourly;
        this.daily = daily;
    }

    // Getters
    public RealtimeResponse.Realtime getRealtime() {
        return realtime;
    }

    public HourlyResponse.Hourly getHourly() {
        return hourly;
    }

    public DailyResponse.Daily getDaily() {
        return daily;
    }

}

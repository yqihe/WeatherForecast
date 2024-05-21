package com.weatherforecast.android.logic.model.weather;

import java.util.Date;

public class HourlyForecast {
    private final Float temVal;
    private final String skyVal;
    private final Date datetime;

    public HourlyForecast(float temVal, String skyVal, Date datetime) {
        this.temVal = temVal;
        this.skyVal = skyVal;
        this.datetime = datetime;
    }

    // Getters and possibly setters
    public Float getTemVal() {
        return temVal;
    }

    public String getSkyVal() {
        return skyVal;
    }

    public Date getDatetime() {
        return datetime;
    }
}

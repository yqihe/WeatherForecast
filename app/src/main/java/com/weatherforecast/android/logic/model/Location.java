package com.weatherforecast.android.logic.model;

public class Location {
    private final String lng;
    private final String lat;

    public Location(String lng, String lat) {
        this.lng = lng;
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public String getLat() {
        return lat;
    }
}

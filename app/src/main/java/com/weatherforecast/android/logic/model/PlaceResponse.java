package com.weatherforecast.android.logic.model;

import java.util.List;

public class PlaceResponse {
    private final String status;
    private final List<Place> places;

    public PlaceResponse(String status, List<Place> places) {
        this.status = status;
        this.places = places;
    }

    public String getStatus() {
        return status;
    }

    public List<Place> getPlaces() {
        return places;
    }
}

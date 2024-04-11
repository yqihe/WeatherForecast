package com.weatherforecast.android.logic.model;

import com.google.gson.annotations.SerializedName;

public class Place {
    private final String name;
    private final Location location;
    @SerializedName("formatted_address")
    private String address;

    public Place(String name, Location location, String address) {
        this.name = name;
        this.location = location;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public String getAddress() {
        return address;
    }
}

package com.weatherforecast.android.logic.model.weather;

import com.google.gson.annotations.SerializedName;

public class Place {
    private final String name;
    private final Location location;
    @SerializedName("formatted_address")
    // JSON中一些字段的命名可能与Java的命名规范不太一致
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

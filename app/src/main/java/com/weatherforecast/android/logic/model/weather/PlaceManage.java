package com.weatherforecast.android.logic.model.weather;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class PlaceManage {
    @PrimaryKey(autoGenerate = true)
    private long id = 0;

    private final String name;
    private final String lng;
    private final String lat;
    private final String address;
    private int realtimeTem;
    private String skycon;

    public PlaceManage(String name, String lng, String lat, String address, int realtimeTem, String skycon) {
        this.name = name;
        this.lng = lng;
        this.lat = lat;
        this.address = address;
        this.realtimeTem = realtimeTem;
        this.skycon = skycon;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getLng() {
        return lng;
    }

    public String getLat() {
        return lat;
    }

    public String getAddress() {
        return address;
    }

    public int getRealtimeTem() {
        return realtimeTem;
    }

    public void setRealtimeTem(int realtimeTem) {
        this.realtimeTem = realtimeTem;
    }

    public String getSkycon() {
        return skycon;
    }

    public void setSkycon(String skycon) {
        this.skycon = skycon;
    }

}

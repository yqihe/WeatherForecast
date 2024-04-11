package com.weatherforecast.android.logic.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Update;
import androidx.room.Query;

import com.weatherforecast.android.logic.model.PlaceManage;

import java.util.List;


@Dao
public interface PlaceManageDao {
    @Insert
    void insertPlaceManage(PlaceManage placeManage);

    @Update
    void updatePlaceManage(PlaceManage placeManage);

    @Query("SELECT * FROM PlaceManage")
    List<PlaceManage> loadAllPlaceManages();

    @Query("SELECT * FROM PlaceManage WHERE lng = :lng AND lat = :lat")
    PlaceManage querySpecifyPlaceManage(String lng, String lat);

    @Query("DELETE FROM PlaceManage WHERE lng = :lng AND lat = :lat")
    int deletePlaceManageByLngLat(String lng, String lat);
}

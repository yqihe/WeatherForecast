package com.weatherforecast.android.logic;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.weatherforecast.android.logic.dao.PlaceManageDao;
import com.weatherforecast.android.logic.model.weather.PlaceManage;

@Database(entities = {PlaceManage.class}, version = 1, exportSchema = false)
public abstract class PlaceManageAppDatabase extends RoomDatabase {
    public abstract PlaceManageDao placeManageDao();

    private static volatile PlaceManageAppDatabase INSTANCE;

    public static PlaceManageAppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (PlaceManageAppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    PlaceManageAppDatabase.class, "place_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

package com.weatherforecast.android.ui.calendarChat;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.weatherforecast.android.logic.model.Event;
import com.weatherforecast.android.logic.EventDatabase;

import java.util.List;

public class CalendarViewModel extends AndroidViewModel {

    private final EventDatabase database;

    public CalendarViewModel(Application application) {
        super(application);
        database = EventDatabase.getDatabase(application);
    }

    public void insertEvent(Event event) {
        database.insertEvent(event);
    }

    public LiveData<List<Event>> getEventsByDate(String date) {
        return database.eventDao().getEventsByDate(date);
    }
}

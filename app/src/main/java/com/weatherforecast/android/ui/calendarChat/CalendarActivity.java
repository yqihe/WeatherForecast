package com.weatherforecast.android.ui.calendarChat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.weatherforecast.android.R;
import com.weatherforecast.android.logic.model.Event;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalendarActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private RecyclerView eventRecyclerView;
    private CalendarAdapter calendarAdapter;
    private CalendarViewModel calendarViewModel;
    private Button addEventBtn;
    private String selectedDate;
    private List<Event> filteredEvents = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        calendarView = findViewById(R.id.calendarView);
        eventRecyclerView = findViewById(R.id.event_recycler_view);
        calendarViewModel = new ViewModelProvider(this).get(CalendarViewModel.class);
        addEventBtn = findViewById(R.id.addEventBtn);

        eventRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        calendarAdapter = new CalendarAdapter(this, filteredEvents);
        eventRecyclerView.setAdapter(calendarAdapter);

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            selectedDate = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth);
            loadEventsForDate(selectedDate);
        });

        loadEventsForToday();

        addEventBtn.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.edit_event_dialog, null);
            builder.setView(dialogView);

            EditText eventNameEdit = dialogView.findViewById(R.id.edit_event_name);
            EditText eventTimeEdit = dialogView.findViewById(R.id.edit_event_time);

            builder.setPositiveButton("确定", (dialog, which) -> {
                String eventName = eventNameEdit.getText().toString();
                String eventTime = eventTimeEdit.getText().toString();
                Event newEvent = new Event(selectedDate, eventName, eventTime);
                calendarViewModel.insertEvent(newEvent);
                loadEventsForDate(selectedDate);
            });

            builder.setNegativeButton("取消", (dialog, which) -> dialog.cancel());

            builder.show();
        });
    }

    private void loadEventsForDate(String date) {
        calendarViewModel.getEventsByDate(date).observe(this, events -> {
            filteredEvents.clear();
            filteredEvents.addAll(events);
            calendarAdapter.notifyDataSetChanged();
        });
    }

    private void loadEventsForToday() {
        Calendar calendar = Calendar.getInstance();
        String todayDate = String.format("%04d-%02d-%02d", calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
        loadEventsForDate(todayDate);
    }
}
package com.weatherforecast.android.ui.calendar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.weatherforecast.android.R;
import com.weatherforecast.android.logic.model.Event;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalendarActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private RecyclerView eventRecyclerView;
    private CalendarAdapter calendarAdapter;
    private CalendarViewModel calendarViewModel;
    private Button addEventBtn;
    private String selectedDate;
    private List<Event> filteredEvents = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        calendarView = findViewById(R.id.calendarView);
        eventRecyclerView = findViewById(R.id.event_recycler_view);
        calendarViewModel = new ViewModelProvider(this).get(CalendarViewModel.class);
        addEventBtn = findViewById(R.id.addEventBtn);

        eventRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        calendarAdapter = new CalendarAdapter(this, filteredEvents);
        eventRecyclerView.setAdapter(calendarAdapter);

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            selectedDate = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth);
            loadEventsForDate(selectedDate);
        });

        loadEventsForToday();

        addEventBtn.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.edit_event_dialog, null);
            builder.setView(dialogView);

            EditText eventNameEdit = dialogView.findViewById(R.id.edit_event_name);
            EditText eventTimeEdit = dialogView.findViewById(R.id.edit_event_time);

            builder.setPositiveButton("确定", (dialog, which) -> {
                String eventName = eventNameEdit.getText().toString();
                String eventTime = eventTimeEdit.getText().toString();
                Event newEvent = new Event(selectedDate, eventName, eventTime);
                calendarViewModel.insertEvent(newEvent);
                loadEventsForDate(selectedDate);
            });

            builder.setNegativeButton("取消", (dialog, which) -> dialog.cancel());

            builder.show();
        });
    }

    private void loadEventsForDate(String date) {
        calendarViewModel.getEventsByDate(date).observe(this, events -> {
            filteredEvents.clear();
            filteredEvents.addAll(events);
            calendarAdapter.notifyDataSetChanged();
        });
    }

    private void loadEventsForToday() {
        Calendar calendar = Calendar.getInstance();
        String todayDate = String.format("%04d-%02d-%02d", calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
        loadEventsForDate(todayDate);
    }
}

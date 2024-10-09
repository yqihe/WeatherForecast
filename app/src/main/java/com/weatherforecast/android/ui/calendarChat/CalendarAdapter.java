package com.weatherforecast.android.ui.calendarChat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.weatherforecast.android.R;
import com.weatherforecast.android.logic.model.Event;

import java.util.List;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.ViewHolder> {

    private final CalendarActivity calendarActivity;
    private final List<Event> events;

    public CalendarAdapter(CalendarActivity calendarActivity, List<Event> events) {
        this.calendarActivity = calendarActivity;
        this.events = events;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_event_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Event event = events.get(position);
        holder.eventNameText.setText(event.getName());
        holder.eventTimeText.setText(event.getTime());
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView eventNameText;
        TextView eventTimeText;

        public ViewHolder(View view) {
            super(view);
            eventNameText = view.findViewById(R.id.event_name);
            eventTimeText = view.findViewById(R.id.event_time);
        }
    }
}

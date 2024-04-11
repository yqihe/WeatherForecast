package com.weatherforecast.android.ui.placesearch;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.weatherforecast.android.R;
import com.weatherforecast.android.logic.model.Place;
import com.weatherforecast.android.ui.weather.WeatherActivity;

import java.util.List;

public class PlaceSearchAdapter extends RecyclerView.Adapter<PlaceSearchAdapter.ViewHolder>{
    private final PlaceSearchActivity placeSearchActivity;
    private final List<Place> placeList;

    public PlaceSearchAdapter(PlaceSearchActivity placeSearchActivity, List<Place> placeList) {
        this.placeSearchActivity = placeSearchActivity;
        this.placeList = placeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.place_search_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.itemView.setOnClickListener(v -> {
            int position = holder.getBindingAdapterPosition();
            Place place = placeList.get(position);
            Intent intent = new Intent(placeSearchActivity, WeatherActivity.class);
            intent.putExtra("location_lng", place.getLocation().getLng());
            intent.putExtra("location_lat", place.getLocation().getLat());
            intent.putExtra("place_name", place.getName());
            intent.putExtra("place_address", place.getAddress());
            placeSearchActivity.getViewModel().savePlace(place);
            placeSearchActivity.startActivity(intent);
            placeSearchActivity.finish();
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Place place = placeList.get(position);
        holder.placeName.setText(place.getName());
        holder.placeAddress.setText(place.getAddress());
    }

    @Override
    public int getItemCount() {
        return placeList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView placeName;
        TextView placeAddress;

        public ViewHolder(View view) {
            super(view);
            placeName = view.findViewById(R.id.placeName);
            placeAddress = view.findViewById(R.id.placeAddress);
        }
    }

}

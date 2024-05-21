package com.weatherforecast.android.ui.weather.placemanage;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.weatherforecast.android.R;
import com.weatherforecast.android.logic.model.weather.Location;
import com.weatherforecast.android.logic.model.weather.Place;
import com.weatherforecast.android.logic.model.weather.PlaceManage;
import com.weatherforecast.android.ui.weather.WeatherActivity;

import java.util.List;

public class PlaceManageAdapter extends RecyclerView.Adapter<PlaceManageAdapter.ViewHolder>{
    private final WeatherActivity weatherActivity;
    private final List<PlaceManage> placeManageList;

    private final PropertyValuesHolder scaleXDown = PropertyValuesHolder.ofFloat(View.SCALE_X, 0.9f);
    private final PropertyValuesHolder scaleYDown = PropertyValuesHolder.ofFloat(View.SCALE_Y, 0.9f);
    private final PropertyValuesHolder scaleXUp = PropertyValuesHolder.ofFloat(View.SCALE_X, 1.0f);
    private final PropertyValuesHolder scaleYUp = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1.0f);

    public PlaceManageAdapter(WeatherActivity weatherActivity, List<PlaceManage> placeManageList) {
        this.weatherActivity = weatherActivity;
        this.placeManageList = placeManageList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView placeManageName;
        TextView placeManageSkycon;
        TextView placeManageAddress;
        TextView placeManageRealtimeTem;

        public ViewHolder(View view) {
            super(view);
            placeManageName = view.findViewById(R.id.placeManageName);
            placeManageSkycon = view.findViewById(R.id.placeManageSkycon);
            placeManageAddress = view.findViewById(R.id.placeManageAddress);
            placeManageRealtimeTem = view.findViewById(R.id.placeManageRealtimeTem);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.place_manage_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.itemView.setOnClickListener(v -> {
            int position = holder.getBindingAdapterPosition();
            PlaceManage placeManage = placeManageList.get(position);
            weatherActivity.getDrawerLayout().closeDrawers();
            weatherActivity.getWeatherViewModel().setLocationLng(placeManage.getLng());
            weatherActivity.getWeatherViewModel().setLocationLat(placeManage.getLat());
            weatherActivity.getWeatherViewModel().setPlaceName(placeManage.getName());
            weatherActivity.getWeatherViewModel().setUpdatePlaceManage(true);
            weatherActivity.refreshWeather();
            Place place = new Place(placeManage.getName(), new Location(placeManage.getLng(), placeManage.getLat()), placeManage.getAddress());
            weatherActivity.getPlaceManageViewModel().savePlace(place);
        });
        holder.itemView.setOnLongClickListener(v -> {
            int position = holder.getBindingAdapterPosition();
            PlaceManage placeManage = placeManageList.get(position);
            ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(v, scaleXDown, scaleYDown);
            animator.setDuration(200);
            animator.start();
            new AlertDialog.Builder(parent.getContext())
                    .setTitle("删除地点")
                    .setMessage("是否删除地点：" + placeManage.getName() + "?")
                    .setCancelable(false)
                    .setPositiveButton("是", (dialog, which) -> {
                        weatherActivity.getPlaceManageViewModel().deletePlaceManage(placeManage.getLng(), placeManage.getLat());
                        dialog.dismiss();
                        v.setScaleX(1.0f);
                        v.setScaleY(1.0f);
                    })
                    .setNegativeButton("否", (dialog, which) -> {
                        dialog.dismiss();
                        ObjectAnimator deleteAnimator = ObjectAnimator.ofPropertyValuesHolder(v, scaleXUp, scaleYUp);
                        deleteAnimator.setDuration(200);
                        deleteAnimator.start();
                    })
                    .show();
            return true;
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PlaceManage placeManage = placeManageList.get(position);
        holder.placeManageName.setText(placeManage.getName());
        holder.placeManageAddress.setText(placeManage.getAddress());
        holder.placeManageSkycon.setText(placeManage.getSkycon());
        String realtimeTemInfo = placeManage.getRealtimeTem() + "°";
        holder.placeManageRealtimeTem.setText(realtimeTemInfo);
    }

    @Override
    public int getItemCount() {
        return placeManageList.size();
    }
}

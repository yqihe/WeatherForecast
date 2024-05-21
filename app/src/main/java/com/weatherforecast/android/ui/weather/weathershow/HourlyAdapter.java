package com.weatherforecast.android.ui.weather.weathershow;

import static com.weatherforecast.android.logic.model.weather.Sky.getSky;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.weatherforecast.android.R;
import com.weatherforecast.android.logic.model.weather.HourlyForecast;
import com.weatherforecast.android.logic.model.weather.Sky;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HourlyAdapter extends RecyclerView.Adapter<HourlyAdapter.ViewHolder>{
    private final List<HourlyForecast> hourlyForecastList;

    public HourlyAdapter(List<HourlyForecast> hourlyForecastList) {
        this.hourlyForecastList = hourlyForecastList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView hourlyTemperatureInfo;
        ImageView hourlySkyIcon;
        TextView hourlyDateInfo;

        public ViewHolder(View view) {
            super(view);
            hourlyTemperatureInfo = view.findViewById(R.id.hourlyTemperatureInfo);
            hourlySkyIcon = view.findViewById(R.id.hourlySkyIcon);
            hourlyDateInfo = view.findViewById(R.id.hourlyDateInfo);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forecast_hourly_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HourlyForecast hourlyForecast = hourlyForecastList.get(position);
        int hourlyTemperatureInfoText = (int) hourlyForecast.getTemVal().floatValue();
        holder.hourlyTemperatureInfo.setText(hourlyTemperatureInfoText + "°");

        Sky sky = getSky(hourlyForecast.getSkyVal());
        holder.hourlySkyIcon.setImageResource(sky.getIcon());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        Date date = hourlyForecast.getDatetime();
        if (date != null) {
            holder.hourlyDateInfo.setText(simpleDateFormat.format(date));
        } else {
            holder.hourlyDateInfo.setText("未知时间");
        }
    }



    @Override
    public int getItemCount() {
        return hourlyForecastList.size();
    }
}

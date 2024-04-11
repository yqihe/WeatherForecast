package com.weatherforecast.android.logic.model;

import com.google.gson.annotations.SerializedName;

public class RealtimeResponse {
    private final String status;
    private final Result result;

    public RealtimeResponse(String status, Result result) {
        this.status = status;
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public Result getResult() {
        return result;
    }

    public static class Result {

        private final Realtime realtime;

        public Result(Realtime realtime) {
            this.realtime = realtime;
        }

        public Realtime getRealtime() {
            return realtime;
        }
    }

    public static class Realtime {

        private final String skycon;
        private final float temperature;
        private final float humidity;
        private final Wind wind;
        @SerializedName("apparent_temperature")
        private float apparentTemperature;
        @SerializedName("air_quality")
        private AirQuality airQuality;

        public Realtime(String skycon, float temperature, float humidity, Wind wind, float apparentTemperature, AirQuality airQuality) {
            this.skycon = skycon;
            this.temperature = temperature;
            this.humidity = humidity;
            this.wind = wind;
            this.apparentTemperature = apparentTemperature;
            this.airQuality = airQuality;
        }

        public String getSkycon() {
            return skycon;
        }

        public float getTemperature() {
            return temperature;
        }

        public float getHumidity() {
            return humidity;
        }

        public Wind getWind() {
            return wind;
        }

        public float getApparentTemperature() {
            return apparentTemperature;
        }

        public AirQuality getAirQuality() {
            return airQuality;
        }
    }

    public static class AirQuality {

        private final AQI aqi;

        public AirQuality(AQI aqi) {
            this.aqi = aqi;
        }

        public AQI getAqi() {
            return aqi;
        }
    }

    public static class AQI {

        private final float chn;

        public AQI(float chn) {
            this.chn = chn;
        }

        public float getChn() {
            return chn;
        }
    }

    public static class Wind {

        private final float speed;
        private final float direction;

        public Wind(float speed, float direction) {
            this.speed = speed;
            this.direction = direction;
        }

        public float getSpeed() {
            return speed;
        }

        public float getDirection() {
            return direction;
        }
    }
}

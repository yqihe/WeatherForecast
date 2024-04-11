package com.weatherforecast.android.logic.model;

import java.util.Date;
import java.util.List;

public class HourlyResponse {
    private final String status;
    private final Result result;

    public HourlyResponse(String status, Result result) {
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
        private final Hourly hourly;

        public Result(Hourly hourly) {
            this.hourly = hourly;
        }

        public Hourly getHourly() {
            return hourly;
        }
    }

    public static class Hourly {
        private final List<Temperature> temperature;
        private final List<Skycon> skycon;

        public Hourly(List<Temperature> temperature, List<Skycon> skycon) {
            this.temperature = temperature;
            this.skycon = skycon;
        }

        public List<Temperature> getTemperature() {
            return temperature;
        }

        public List<Skycon> getSkycon() {
            return skycon;
        }
    }

    public static class Temperature {
        private final Float value;

        public Temperature(Float value) {
            this.value = value;
        }

        public Float getValue() {
            return value;
        }
    }

    public static class Skycon {
        private final String value;
        private final Date datetime;

        public Skycon(String value, Date datetime) {
            this.value = value;
            this.datetime = datetime;
        }

        public String getValue() {
            return value;
        }

        public Date getDatetime() {
            return datetime;
        }
    }
}


package com.example.myapplication.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class TodayWeather {
    public String temperature;
    public String pressure;
    public String wet;
    public String wind;

    private TodayWeather() {}

    public TodayWeather(String temperature, String pressure, String wet, String wind) {
        this.temperature = temperature;
        this.pressure = pressure;
        this.wet = wet;
        this.wind =wind;
    }

    public static TodayWeather getInstance() {
        return new TodayWeather();
    }
}
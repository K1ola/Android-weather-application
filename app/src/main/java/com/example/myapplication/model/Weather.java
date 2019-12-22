package com.example.myapplication.model;

public class Weather {
    public String temperature;
    public String pressure;
    public String wet;
    public String wind;
    public String time;
    public String summary;
    public String icon;
    public String temperatureMin;
    public String temperatureMax;

    public Weather(String temperature, String pressure, String wet, String wind, String time, String summary, String icon) {
        this.temperature = temperature;
        this.pressure = pressure;
        this.wet = wet;
        this.wind =wind;
        this.time = time;
        this.summary = summary;
        this.icon = icon;
        this.temperatureMin = temperatureMin;
        this.temperatureMax = temperatureMax;
    }
}
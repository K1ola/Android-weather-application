package com.example.myapplication.model;

public class Weather {
    private String temperature;
    private String pressure;
    private String wind;
    private String wet;

    public void init(String temperature, String pressure, String wind, String wet) {
        this.temperature = temperature;
        this.pressure = pressure;
        this.wind = wind;
        this.wet = wet;
    }
}

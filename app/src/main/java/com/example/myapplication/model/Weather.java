package com.example.myapplication.model;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = "time", unique = false)})
public class Weather {
//    @PrimaryKey
    public long id;

    public String temperature;
    public String pressure;
    public String wet;
    public String wind;
    @PrimaryKey
    @NonNull
    public String time;
    public String summary;
    public String icon;
    public String temperatureMin;
    public String temperatureMax;

    public String weatherType;

    public Weather(String temperature, String pressure, String wet, String wind, String time, String summary, String icon) {
        this.temperature = temperature;
        this.pressure = pressure;
        this.wet = wet;
        this.wind = wind;
        this.time = time;
        this.summary = summary;
        this.icon = icon;
    }

    @SuppressLint("DefaultLocale")
    public void toCelsius() {
        temperature = String.format("%.0f", (Double.parseDouble(temperature.replace(',', '.')) -32) * 5/9);
    }

    @SuppressLint("DefaultLocale")
    public void toFahrenheit() {
        temperature = String.format("%.0f", (Double.parseDouble(temperature.replace(',', '.')) * 9/5) + 32);
    }

    //TODO check
    @SuppressLint("DefaultLocale")
    public void toMmHg() {
        pressure = String.format("%.2f", Double.parseDouble(pressure.replace(',', '.')) / 1013.25);
    }

    @SuppressLint("DefaultLocale")
    public void toHPA() {
        pressure = String.format("%.2f", Double.parseDouble(pressure.replace(',', '.')) * 1013.25);
    }

    @SuppressLint("DefaultLocale")
    public void toMeters() {
        wind = String.format("%.0f", Double.parseDouble(wind.replace(',', '.')) / 3.6);
    }

    @SuppressLint("DefaultLocale")
    public void toHours() {
        wind = String.format("%.0f", Double.parseDouble(wind.replace(',', '.')) * 3.6);
    }
}
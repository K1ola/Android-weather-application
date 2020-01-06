package com.example.myapplication.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = "currentTemperatureMeasure", unique = false)})
public class Settings {
    @PrimaryKey(autoGenerate = true)
    public long id;
    public final static String CELSIUS = "°C";
    public final static String FAHRENHEIT = "°F";

    public final static String MM_HG = "мм рт.ст.";
    public final static String HPA = "гПа";

    public final static String METERS_PER_SECOND ="м/с";
    public final static String HOURS_PER_SECOND ="км/ч";

    public String currentTemperatureMeasure;
    public String currentPressureMeasure;
    public String currentWindMeasure;

    public boolean isCelsius;
    public boolean isHpa;
    public boolean isMeters;

    public Settings() {}

    public Settings(boolean temperature, boolean pressure, boolean wind) {
        isCelsius = temperature;
        isHpa = pressure;
        isMeters = wind;
    }

    public Settings(String temperature, String pressure, String wind) {
        this.currentTemperatureMeasure = temperature;
        this.currentPressureMeasure = pressure;
        this.currentWindMeasure = wind;
    }

    public static Settings getInstance() {
        return new Settings();
    }
}

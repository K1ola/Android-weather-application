package com.example.myapplication.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class Settings {
    public final static String CELSIUS = "°C";
    public final static String FAHRENHEIT = "°F";

    public final static String MM_HG = "мм рт.ст.";
    public final static String HPA = "гПа";

    public final static String METERS_PER_SECOND ="м/с";
    public final static String HOURS_PER_SECOND ="км/ч";

    public String currentTemperatureMeasure;
    public String currentPressureMeasure;
    public String currentWindMeasure;

    public static boolean isCelsius;
    public static boolean isHpa;
    public static boolean isMeters;

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

//    public MutableLiveData<Settings> getSettings() {
//        MutableLiveData<Settings> data = new MutableLiveData<>();
//        data.setValue(currentMeasure());
//        return data;
//    }

    public static Settings currentMeasure() {
        Settings settings = new Settings();
        settings.currentTemperatureMeasure = isCelsius ? CELSIUS : FAHRENHEIT;
        settings.currentPressureMeasure = isHpa ? HPA : MM_HG;
        settings.currentWindMeasure = isMeters ? METERS_PER_SECOND : HOURS_PER_SECOND;
        return settings;
    }
}

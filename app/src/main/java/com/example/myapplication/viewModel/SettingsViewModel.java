package com.example.myapplication.viewModel;

import androidx.lifecycle.ViewModel;


public class SettingsViewModel extends ViewModel {
    private String temp; //= "°C";
    private String pressure = "гПа";
    private String wind ="км/ч";
    private long id;

    public SettingsViewModel(long id, String temp/*, String pressure, String wind*/){
        this.id = id;
        this.temp = temp;
//        this.pressure = pressure;
//        this.wind = wind;
    }

    public void setTemp(String item) {
        temp = item;
    }

    public String getTemp() {
        return temp;
    }


    public void setPressure(String item) {
        pressure = item;
    }

    public String getPressure() {
        return pressure;
    }


    public void setWind(String item) {
        wind = item;
    }

    public String getWind() {
        return wind;
    }
}
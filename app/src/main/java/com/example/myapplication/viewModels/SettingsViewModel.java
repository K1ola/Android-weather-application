package com.example.myapplication.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SettingsViewModel extends ViewModel {
    private MutableLiveData<String> temp = new MutableLiveData<>();// = "°C";
    private MutableLiveData<String> pressure = new MutableLiveData<>();;//  = "гПа";
    private MutableLiveData<String> wind = new MutableLiveData<>();;// ="км/ч";

    public SettingsViewModel() {
        setTemp("°C");
        setPressure("гПа");
        setWind("км/ч");
    }

    public void setTemp(String item) {
        temp.setValue(item);
    }

    public LiveData<String> getTemp() {
        return temp;
    }


    public void setPressure(String item) {
        pressure.setValue(item);
    }

    public  LiveData<String> getPressure() {
        return pressure;
    }


    public void setWind(String item) {
        wind.setValue(item);
    }

    public  LiveData<String> getWind() {
        return wind;
    }
}
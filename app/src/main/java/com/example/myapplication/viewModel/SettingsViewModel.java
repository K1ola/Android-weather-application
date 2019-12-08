package com.example.myapplication.viewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.ViewModel;


public class SettingsViewModel extends ViewModel {

    public class TestViewModel extends BaseObservable{

        private String temp;

        @Nullable
        private TestViewModel mTestViewModel;

        private final LifecycleObserver mLifecycleObserver;

        public TestViewModel(LifecycleObserver lifecycleObserver) {
            mLifecycleObserver = lifecycleObserver;
        }

        // Чтобы указать, что значение может меняться и что эти изменения нужно отслеживать,
        // используется аннотация Bindable
        @Bindable
        @NonNull
        public String getTemp() {
            return temp;
        }
    }

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

//    public String getTemp() {
//        return temp;
//    }


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
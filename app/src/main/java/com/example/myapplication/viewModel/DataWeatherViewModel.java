package com.example.myapplication.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.model.DataWeather;

import java.util.List;

public class DataWeatherViewModel extends ViewModel {
    private DataWeather data;

    public LiveData<List<DataWeather>> getData() {
        return data.liveData;
    }

    public LiveData<List<DataWeather>> getDataDetails() {
        return data.liveDataDetails;
    }

}

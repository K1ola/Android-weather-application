package com.example.myapplication.model;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

public class DataWeather{
    public DataWeather(String day, String temp) {
        mDay = day;
        mTemp = temp;
    }

     List<DataWeather> CreateHardcodedData(String measure) {
        final List<DataWeather> data;
        data = new ArrayList<>();
        data.add(new DataWeather("Сегодня", "4 " + measure));
        data.add(new DataWeather("Завтра", "5 " + measure));
        data.add(new DataWeather("Среда", "6 " + measure));
        data.add(new DataWeather("Четверг", "7 " + measure));
        data.add(new DataWeather("Пятница", "8 " + measure));
        liveData.setValue(data);
        return data;
    }

     List<DataWeather> CreateHardcodedDetails(String measure) {
        final List<DataWeather> data;
        data = new ArrayList<>();
        data.add(new DataWeather("Утро", "4 " + measure));
        data.add(new DataWeather("День", "5 " + measure));
        data.add(new DataWeather("Вечер", "6 " + measure));
        data.add(new DataWeather("Ночь", "7 " + measure));
        liveDataDetails.setValue(data);
        return data;
    }

    public final String mDay;
    public final String mTemp;

    public MutableLiveData<List<DataWeather>> liveData = new MutableLiveData<>();
    public MutableLiveData<List<DataWeather>> liveDataDetails = new MutableLiveData<>();
}

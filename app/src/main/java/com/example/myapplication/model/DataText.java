package com.example.myapplication.model;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

public class DataText{
    public DataText(String top, String bottom) {
        mTopText.setValue(top);
        mBottomText.setValue(bottom);
    }

    public void setTopText(String text) {
        mTopText.setValue(text);
    }

    public void setBottomText(String text) {
        mBottomText.setValue(text);
    }

//     List<DataText> CreateHardcodedCalendar() {
//        final List<DataText> data;
//        data = new ArrayList<>();
//        data.add(new DataText("Сегодня", "4"));
//        data.add(new DataText("Завтра", "5"));
//        data.add(new DataText("Среда", "6"));
//        data.add(new DataText("Четверг", "7"));
//        data.add(new DataText("Пятница", "8"));
//        liveDataCalendar.setValue(data);
//        return data;
//    }
//
//     List<DataText> CreateHardcodedWind(String measure) {
//        final List<DataText> data;
//        data = new ArrayList<>();
//        data.add(new DataText("Утром", "5 " + measure));
//        data.add(new DataText("Днем", "5 " + measure));
//        data.add(new DataText("Вечером", "5 " + measure));
//        data.add(new DataText("Ночью", "5 " + measure));
//        liveDataWind.setValue(data);
//        return data;
//    }
//
//     List<DataText> CreateHardcodedPressure(String measure) {
//        final List<DataText> data;
//        data = new ArrayList<>();
//        data.add(new DataText("Утром", "743 " + measure));
//        data.add(new DataText("Днем", "743 " + measure));
//        data.add(new DataText("Вечером", "743 " + measure));
//        data.add(new DataText("Ночью", "743 " + measure));
//        liveDataPressure.setValue(data);
//        return data;
//    }
//
//     List<DataText> CreateHardcodedWet() {
//        final List<DataText> data;
//        data = new ArrayList<>();
//        data.add(new DataText("Утром", "80%"));
//        data.add(new DataText("Днем", "80%"));
//        data.add(new DataText("Вечером", "80%"));
//        data.add(new DataText("Ночью", "80%"));
//        liveDataWet.setValue(data);
//        return data;
//    }

    public MutableLiveData<String> mTopText;
    public MutableLiveData<String> mBottomText;
    public MutableLiveData<String> mBottomTextMeasure;

//    public MutableLiveData<List<DataText>> liveDataCalendar = new MutableLiveData<>();
//    public MutableLiveData<List<DataText>> liveDataWind = new MutableLiveData<>();
//    public MutableLiveData<List<DataText>> liveDataPressure = new MutableLiveData<>();
//    public MutableLiveData<List<DataText>> liveDataWet = new MutableLiveData<>();
}

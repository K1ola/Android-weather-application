package com.example.myapplication.presentation.common;

import java.util.ArrayList;
import java.util.List;

public class DataSource {


    private final List<DataWeather> mData;
    public DataSource(){
        mData = new ArrayList<>();
        mData.add(new DataWeather("Сегодня", "+10"));
        mData.add(new DataWeather("Завтра", "+100"));
        mData.add(new DataWeather("Среда", "+10"));
        mData.add(new DataWeather("Четверг", "+100"));
        mData.add(new DataWeather("Пятница", "+10"));
    }

    public List<DataWeather> getData(){
        return mData;
    }

    private static volatile DataSource sInstance = null;
    public static DataSource getInstance(){
        if (sInstance == null) {
            synchronized (DataSource.class) {
                if (sInstance == null)
                    sInstance = new DataSource();
            }
        }
        return sInstance;
    }

    public static class DataWeather{
        public DataWeather(String day, String temp) {
            mDay = day;
            mTemp = temp;
        }

        final String mDay;
        final String mTemp;
    }
}

package com.example.myapplication.presentation.common;

import java.util.ArrayList;
import java.util.List;

public class DataSource {

    private static DataSource sInstance;

    private final List<MyData> mData;
    public DataSource(){
        mData = new ArrayList<>();
        mData.add(new MyData("Сегодня", "+10"));
        mData.add(new MyData("Завтра", "+100"));
        mData.add(new MyData("Среда", "+10"));
        mData.add(new MyData("Четверг", "+100"));
        mData.add(new MyData("Пятница", "+10"));
    }

    public List<MyData> getData(){
        return mData;
    }

    public synchronized static DataSource getInstance(){
        if (sInstance == null) {
            sInstance = new DataSource();
        }
        return sInstance;
    }

    public static class MyData{
        public MyData(String day, String temp) {
            mDay = day;
            mTemp = temp;
        }

        String mDay;
        String mTemp;
    }
}

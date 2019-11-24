package com.example.myapplication.presentation.common;

import java.util.ArrayList;
import java.util.List;

public class DataSource {
    private final List<DataWeather> mData;
    private final List<DataWeather> mDataDetails;
    private final List<DataText> mCalendarData;
    private final List<DataText> mWindData;
    private final List<DataText> mPressureData;
    private final List<DataText> mWetData;

    public DataSource(){
        mData = new ArrayList<>();
        mData.add(new DataWeather("Сегодня", "+10"));
        mData.add(new DataWeather("Завтра", "+100"));
        mData.add(new DataWeather("Среда", "+10"));
        mData.add(new DataWeather("Четверг", "+100"));
        mData.add(new DataWeather("Пятница", "+10"));

        mDataDetails = new ArrayList<>();
        mDataDetails.add(new DataWeather("Утром", "+10"));
        mDataDetails.add(new DataWeather("Днем", "+100"));
        mDataDetails.add(new DataWeather("Вечером", "+10"));
        mDataDetails.add(new DataWeather("Ночью", "+100"));

        mCalendarData = DataText.CreateHardcodedCalendar();
        mWindData = DataText.CreateHardcodedWind();
        mPressureData = DataText.CreateHardcodedPressure();
        mWetData = DataText.CreateHardcodedWet();
    }

    public List<DataWeather> getData(){
        return mData;
    }

    public List<DataWeather> getDataDetails(){
        return mDataDetails;
    }

    public List<DataText> getDataCalendar(){
        return mCalendarData;
    }

    public List<DataText> getDataWind(){
        return mWindData;
    }

    public List<DataText> getDataPressure(){
        return mPressureData;
    }

    public List<DataText> getDataWet(){
        return mWetData;
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

    public static class DataText{
        public DataText(String top, String bottom) {
            mTopText = top;
            mBottomText = bottom;
        }

        static List<DataText> CreateHardcodedCalendar() {
            final List<DataText> data;
            data = new ArrayList<>();
            data.add(new DataText("Сегодня", "4"));
            data.add(new DataText("Завтра", "5"));
            data.add(new DataText("Среда", "6"));
            data.add(new DataText("Четверг", "7"));
            data.add(new DataText("Пятница", "8"));
            return data;
        }

        static List<DataText> CreateHardcodedWind() {
            final List<DataText> data;
            data = new ArrayList<>();
            data.add(new DataText("Утром", "5 м/с"));
            data.add(new DataText("Днем", "5 м/с"));
            data.add(new DataText("Вечером", "5 м/с"));
            data.add(new DataText("Ночью", "5 м/с"));
            return data;
        }

        static List<DataText> CreateHardcodedPressure() {
            final List<DataText> data;
            data = new ArrayList<>();
            data.add(new DataText("Утром", "743 мм рт. ст."));
            data.add(new DataText("Днем", "743 мм рт. ст."));
            data.add(new DataText("Вечером", "743 мм рт. ст."));
            data.add(new DataText("Ночью", "743 мм рт. ст."));
            return data;
        }

        static List<DataText> CreateHardcodedWet() {
            final List<DataText> data;
            data = new ArrayList<>();
            data.add(new DataText("Утром", "80%"));
            data.add(new DataText("Днем", "80%"));
            data.add(new DataText("Вечером", "80%"));
            data.add(new DataText("Ночью", "80%"));
            return data;
        }

        final String mTopText;
        final String mBottomText;
    }

}

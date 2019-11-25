package com.example.myapplication.presentation.common;

import java.util.ArrayList;
import java.util.List;

public class DataSource {
    private  List<DataWeather> mData;
    private  List<DataWeather> mDataDetails;
    private final List<DataText> mCalendarData;
    private  List<DataText> mWindData;
    private  List<DataText> mPressureData;
    private final List<DataText> mWetData;

    private String temperature_measure = "°F";
    private String pressure_measure = "гПа";
    private String wind_measure = "км/ч";

    public DataSource(){
        mData = new ArrayList<>();
        mData.add(new DataWeather("Сегодня", "+10 " + temperature_measure));
        mData.add(new DataWeather("Завтра", "+100 " + temperature_measure));
        mData.add(new DataWeather("Среда", "+10 " + temperature_measure));
        mData.add(new DataWeather("Четверг", "+100 " + temperature_measure));
        mData.add(new DataWeather("Пятница", "+10 " + temperature_measure));

        mDataDetails = new ArrayList<>();
        mDataDetails.add(new DataWeather("Утром", "+10" + temperature_measure));
        mDataDetails.add(new DataWeather("Днем", "+100" + temperature_measure));
        mDataDetails.add(new DataWeather("Вечером", "+10" + temperature_measure));
        mDataDetails.add(new DataWeather("Ночью", "+100" + temperature_measure));

        mCalendarData = DataText.CreateHardcodedCalendar();
        mWindData = DataText.CreateHardcodedWind(wind_measure);
        mPressureData = DataText.CreateHardcodedPressure(pressure_measure);
        mWetData = DataText.CreateHardcodedWet();
    }

    public void setMeasures(String temperature, String pressure, String wind) {
        temperature_measure = temperature;
        pressure_measure = pressure;
        wind_measure = wind;
    }

    public List<DataWeather> getData(){
        mData = new ArrayList<>();
        mData.add(new DataWeather("Сегодня", "+10 " + temperature_measure));
        mData.add(new DataWeather("Завтра", "+100 " + temperature_measure));
        mData.add(new DataWeather("Среда", "+10 " + temperature_measure));
        mData.add(new DataWeather("Четверг", "+100 " + temperature_measure));
        mData.add(new DataWeather("Пятница", "+10 " + temperature_measure));
        return mData;
    }

    public List<DataWeather> getDataDetails(){
        mDataDetails = new ArrayList<>();
        mDataDetails.add(new DataWeather("Утром", "+10" + temperature_measure));
        mDataDetails.add(new DataWeather("Днем", "+100" + temperature_measure));
        mDataDetails.add(new DataWeather("Вечером", "+10" + temperature_measure));
        mDataDetails.add(new DataWeather("Ночью", "+100" + temperature_measure));
        return mDataDetails;
    }

    public List<DataText> getDataCalendar(){
        return mCalendarData;
    }

    public List<DataText> getDataWind(){
        mWindData = DataText.CreateHardcodedWind(wind_measure);
        return mWindData;
    }

    public List<DataText> getDataPressure(){
        mPressureData = DataText.CreateHardcodedPressure(pressure_measure);
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

        static List<DataText> CreateHardcodedWind(String measure) {
            final List<DataText> data;
            data = new ArrayList<>();
            data.add(new DataText("Утром", "5 " + measure));
            data.add(new DataText("Днем", "5 " + measure));
            data.add(new DataText("Вечером", "5 " + measure));
            data.add(new DataText("Ночью", "5 " + measure));
            return data;
        }

        static List<DataText> CreateHardcodedPressure(String measure) {
            final List<DataText> data;
            data = new ArrayList<>();
            data.add(new DataText("Утром", "743 " + measure));
            data.add(new DataText("Днем", "743 " + measure));
            data.add(new DataText("Вечером", "743 " + measure));
            data.add(new DataText("Ночью", "743 " + measure));
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

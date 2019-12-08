package com.example.myapplication.model;

import java.util.ArrayList;
import java.util.List;

public class DataSource {
    private List<DataWeather> mData;
    private List<DataWeather> mDataDetails;
    private final List<DataText> mCalendarData;
    private List<DataText> mWindData;
    private List<DataText> mPressureData;
    private final List<DataText> mWetData;

    private String default_temperature_measure = "°C";
    private String default_pressure_measure = "мм рт.ст.";
    private String default_wind_measure = "м/с";

    // попытка вытащить данные
    public String getTest(){
        return default_pressure_measure;
    }

    public DataSource(){
        mData = DataWeather.CreateHardcodedData(default_temperature_measure);
        mDataDetails = DataWeather.CreateHardcodedDetails(default_temperature_measure);
        mCalendarData = DataText.CreateHardcodedCalendar();
        mWindData = DataText.CreateHardcodedWind(default_wind_measure);
        mPressureData = DataText.CreateHardcodedPressure(default_pressure_measure);
        mWetData = DataText.CreateHardcodedWet();
    }

    public void setMeasures(String temperature, String pressure, String wind) {
        default_temperature_measure = temperature;
        default_pressure_measure = pressure;
        default_wind_measure = wind;
    }

    public List<DataWeather> getData(){
        mData = DataWeather.CreateHardcodedData(default_temperature_measure);
        return mData;
    }

    public List<DataWeather> getDataDetails(){
        mDataDetails = DataWeather.CreateHardcodedDetails(default_temperature_measure);
        return mDataDetails;
    }

    public List<DataText> getDataCalendar(){
        return mCalendarData;
    }

    public List<DataText> getDataWind(){
        mWindData = DataText.CreateHardcodedWind(default_wind_measure);
        return mWindData;
    }

    public List<DataText> getDataPressure(){
        mPressureData = DataText.CreateHardcodedPressure(default_pressure_measure);
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

        static List<DataWeather> CreateHardcodedData(String measure) {
            final List<DataWeather> data;
            data = new ArrayList<>();
            data.add(new DataWeather("Сегодня", "4 " + measure));
            data.add(new DataWeather("Завтра", "5 " + measure));
            data.add(new DataWeather("Среда", "6 " + measure));
            data.add(new DataWeather("Четверг", "7 " + measure));
            data.add(new DataWeather("Пятница", "8 " + measure));
            return data;
        }

        static List<DataWeather> CreateHardcodedDetails(String measure) {
            final List<DataWeather> data;
            data = new ArrayList<>();
            data.add(new DataWeather("Утро", "4 " + measure));
            data.add(new DataWeather("День", "5 " + measure));
            data.add(new DataWeather("Вечер", "6 " + measure));
            data.add(new DataWeather("Ночь", "7 " + measure));
            return data;
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

package com.example.myapplication.model;

import java.util.List;

//public class DataSource {
//    private List<DataWeather> mData;
//    private List<DataWeather> mDataDetails;
//    private final List<DataText> mCalendarData;
//    private List<DataText> mWindData;
//    private List<DataText> mPressureData;
//    private final List<DataText> mWetData;
//
//    private String default_temperature_measure = "°C";
//    private String default_pressure_measure = "мм рт.ст.";
//    private String default_wind_measure = "м/с";
//
//    public DataSource(){
////        mData = DataWeather.CreateHardcodedData(default_temperature_measure);
////        mDataDetails = DataWeather.CreateHardcodedDetails(default_temperature_measure);
////        mCalendarData = DataText.CreateHardcodedCalendar();
////        mWindData = DataText.CreateHardcodedWind(default_wind_measure);
////        mPressureData = DataText.CreateHardcodedPressure(default_pressure_measure);
////        mWetData = DataText.CreateHardcodedWet();
//    }
//
//    public void setTemperatureMeasures(String temperature) {
//        default_temperature_measure = temperature;
//    }
//
//    public void setPressureMeasures(String pressure) {
//        default_pressure_measure = pressure;
//    }
//
//    public void setWindMeasures(String wind) {
//        default_wind_measure = wind;
//    }
//
//    public List<DataWeather> getData(){
//        mData = DataWeather.CreateHardcodedData(default_temperature_measure);
//        return mData;
//    }
//
//    public List<DataWeather> getDataDetails(){
//        mDataDetails = DataWeather.CreateHardcodedDetails(default_temperature_measure);
//        return mDataDetails;
//    }
//
//    public List<DataText> getDataCalendar(){
//        return mCalendarData;
//    }
//
//    public List<DataText> getDataWind(){
//        mWindData = DataText.CreateHardcodedWind(default_wind_measure);
//        return mWindData;
//    }
//
//    public List<DataText> getDataPressure(){
//        mPressureData = DataText.CreateHardcodedPressure(default_pressure_measure);
//        return mPressureData;
//    }
//
//    public List<DataText> getDataWet(){
//        return mWetData;
//    }
//
//    private static volatile DataSource sInstance = null;
//    public static DataSource getInstance(){
//        if (sInstance == null) {
//            synchronized (DataSource.class) {
//                if (sInstance == null)
//                    sInstance = new DataSource();
//            }
//        }
//        return sInstance;
//    }
//}
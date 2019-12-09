package com.example.myapplication.model;

import android.media.Image;

import java.util.Arrays;
import java.util.List;

public class HolderItem {
    public Settings settings;
    public TodayWeather todayWeather;

    public String topData;
    public String bottomData;
    public Image image;

    public HolderItem() {}

//    public HolderItem setHolderItemData(String topData, String bottomData, Image image) {
//        HolderItem result = new HolderItem();
//        result.topData = topData;
//        result.bottomData = bottomData;
//        result.image = image;
//        return result;
//
//    }
//
//    public HolderItem setHolderItem(Settings settings, TodayWeather todayWeather) {
//        HolderItem result = new HolderItem();
//        result.settings = settings;
//        result.todayWeather = todayWeather;
//        return result;
//    }

    public HolderItem(String topData, String bottomData, Image image) {
        this.topData = topData;
        this.bottomData = bottomData;
        this.image = image;

    }

    public HolderItem(Settings settings, TodayWeather todayWeather) {
        this.settings = settings;
        this.todayWeather = todayWeather;
    }



    public static HolderItem getInstance() {
        return new HolderItem();
    }

    public static List<HolderItem> fetchList() {
        Settings s = new Settings(Settings.CELSIUS, Settings.HPA, Settings.METERS_PER_SECOND);
        TodayWeather t = new TodayWeather("some temp","some press",
                "some wet",
                "some wind");
        String topData, bottomData;
        topData = s.currentTemperatureMeasure;
        bottomData = t.wet;
        HolderItem[] array = new HolderItem[] {
                new HolderItem(topData, bottomData, null),
                new HolderItem(topData, bottomData, null),
                new HolderItem(topData, bottomData, null),
                new HolderItem(topData, bottomData, null),
                new HolderItem(topData, bottomData, null),
        };

        List<HolderItem> list = Arrays.asList(array);
        return list;
    }
}

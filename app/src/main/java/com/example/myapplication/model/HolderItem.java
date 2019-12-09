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

    public HolderItem(Settings settings, TodayWeather todayWeather) {
        this.settings = settings;
        this.todayWeather = todayWeather;
    }

    public HolderItem(String topData, String bottomData, Image image) {
        this.topData = topData;
        this.bottomData = bottomData;
        this.image = image;
    }

    public static HolderItem getInstance() {
        return new HolderItem();
    }

    public static List<HolderItem> fetchList() {
        Settings s = new Settings(Settings.CELSIUS, Settings.HPA, Settings.METERS_PER_SECOND);
        TodayWeather t = new TodayWeather("some temp","some press",
                "some wet",
                "some wind");
        HolderItem[] array = new HolderItem[] {
                new HolderItem(s, t),
                new HolderItem(s, t),
                new HolderItem(s, t),
                new HolderItem(s, t),
                new HolderItem(s, t),
        };

        List<HolderItem> list = Arrays.asList(array);
        return list;
    }
}

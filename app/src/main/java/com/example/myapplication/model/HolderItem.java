package com.example.myapplication.model;

import android.media.Image;

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
}

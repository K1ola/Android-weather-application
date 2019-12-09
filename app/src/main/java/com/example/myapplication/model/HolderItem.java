package com.example.myapplication.model;

import android.media.Image;

import com.example.myapplication.viewModel.DataViewModel;

import java.util.Arrays;
import java.util.List;

public class HolderItem {
    public Settings settings;
    public TodayWeather todayWeather;

    public String topData;
    public String bottomData;
    public Image image;

    public HolderItem() {}

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
        Settings s = DataViewModel.currentMeasure();
        TodayWeather t = new TodayWeather("10 ","10 ",
                "10 ",
                "10 ");
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

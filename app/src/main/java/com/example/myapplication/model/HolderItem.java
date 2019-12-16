package com.example.myapplication.model;

import android.graphics.Color;
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

    public int color;

    public HolderItem() {}

    public HolderItem(String topData, String bottomData, Image image, int color) {
        this.topData = topData;
        this.bottomData = bottomData;
        this.image = image;
        this.color = color;

    }

    public HolderItem(Settings settings, TodayWeather todayWeather) {
        this.settings = settings;
        this.todayWeather = todayWeather;
    }

    public void setColor(int color) {
        this.color = color;
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
                new HolderItem(topData, bottomData, null, Color.GRAY),
                new HolderItem(topData, bottomData, null, Color.WHITE),
                new HolderItem(topData, bottomData, null, Color.WHITE),
                new HolderItem(topData, bottomData, null, Color.WHITE),
                new HolderItem(topData, bottomData, null, Color.WHITE),
        };

        List<HolderItem> list = Arrays.asList(array);
        return list;
    }
}

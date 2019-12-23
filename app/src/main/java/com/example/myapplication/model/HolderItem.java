package com.example.myapplication.model;

import android.graphics.Color;
import android.media.Image;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HolderItem {
    public Settings settings;
    public Weather weather;

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

    public HolderItem(Settings settings, Weather weather) {
        this.settings = settings;
        this.weather = weather;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public static HolderItem getInstance() {
        return new HolderItem();
    }

    // EXAMPLE
    public static List<HolderItem> getDataList(Settings s, Weather w) {
        String topData, bottomData;
        topData = s.currentTemperatureMeasure;
        bottomData = w.wet;
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

    public static List<HolderItem> getHourlyDataList(Settings s, List<Weather> w) {
        List<String> topData = new ArrayList<String>();
        List<String> bottomData = new ArrayList<String>();
        HolderItem[] array = new HolderItem[5];

        for (int i = 0; i < w.size(); i++) {
            topData.add(w.get(i).time);
            bottomData.add(w.get(i).temperature + s.currentTemperatureMeasure);
            array[i] = new HolderItem(topData.get(i), bottomData.get(i), null, Color.WHITE);
        }

        List<HolderItem> list = Arrays.asList(array);
        return list;
    }

    public static List<HolderItem> get5DaysDataList(Settings s, List<Weather> w) {
        List<String> topData = new ArrayList<String>();
        List<String> bottomData = new ArrayList<String>();
        HolderItem[] array = new HolderItem[5];

        for (int i = 0; i < w.size(); i++) {
            topData.add(w.get(i).time);
            bottomData.add(w.get(i).temperature + s.currentTemperatureMeasure);
            array[i] = new HolderItem(topData.get(i), bottomData.get(i), null, Color.WHITE);
        }

        List<HolderItem> list = Arrays.asList(array);
        return list;
    }
}

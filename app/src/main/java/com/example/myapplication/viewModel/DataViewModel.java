package com.example.myapplication.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.R;
import com.example.myapplication.model.HolderItem;
import com.example.myapplication.model.Settings;
import com.example.myapplication.model.TodayWeather;
import com.example.myapplication.view.adapter.WeatherAdapter;

import java.util.Arrays;
import java.util.List;

public class DataViewModel extends AndroidViewModel {
    private final MutableLiveData<Settings> settingsObservable;
    public ObservableField<Settings> settings = new ObservableField<>();

    private final MutableLiveData<TodayWeather> todayWeatherObservable;
    public ObservableField<TodayWeather> todayWeather = new ObservableField<>();

    private final MutableLiveData<List<HolderItem>> holderItemObservable;
    public ObservableField<List<HolderItem>> holderItems = new ObservableField<>();
    private WeatherAdapter weatherAdapter;

    public DataViewModel(@NonNull Application application) {
        super(application);

        settingsObservable = getSettings();
        todayWeatherObservable = getTodayWeather();
        holderItemObservable = getHolderItem();

        weatherAdapter = new WeatherAdapter(R.layout.holder_item, this);
    }

    public WeatherAdapter getWeatherAdapter() {
        return weatherAdapter;
    }

    public void setWeatherAdapter(List<HolderItem> items) {
        this.weatherAdapter.setHolderItems(items);
        this.weatherAdapter.notifyDataSetChanged();
    }

    public LiveData<Settings> getSettingsObservable() {
        return settingsObservable;
    }
    public LiveData<TodayWeather> getTodayWeatherObservable() {
        return todayWeatherObservable;
    }
    public LiveData<List<HolderItem>> getHolderItemObservable() {
        return holderItemObservable;
    }

    public void setSettings(Settings settings) {
//        settingsObservable.setValue(settings);
        this.settings.set(settings);
    }

    public MutableLiveData<Settings> getSettings() {
        MutableLiveData<Settings> data = new MutableLiveData<>();
        data.setValue(Settings.currentMeasure());
        return data;
    }

    public void setTodayWeather(TodayWeather todayWeather) {
//        todayWeatherObservable.setValue(todayWeather);
        this.todayWeather.set(todayWeather);
    }

    public MutableLiveData<TodayWeather> getTodayWeather() {
        MutableLiveData<TodayWeather> data = new MutableLiveData<>();
        data.setValue(new TodayWeather("some temp","some press",
                "some wet",
                "some wind"));
        return data;
    }

    public void setHolderItem(List<HolderItem> holderItem) {
//        todayWeatherObservable.setValue(todayWeather);
        this.holderItems.set(holderItem);
    }

    public MutableLiveData<List<HolderItem>> getHolderItem() {
        MutableLiveData<List<HolderItem>> data = new MutableLiveData<>();

        Settings s = new Settings();
        TodayWeather t = new TodayWeather("some temp","some press",
                "some wet",
                "some wind");
        HolderItem[] array = new HolderItem[] {
                new HolderItem(s, t),
                new HolderItem(s, todayWeather.get()),
                new HolderItem(s, todayWeather.get()),
                new HolderItem(s, todayWeather.get()),
                new HolderItem(s, todayWeather.get())
        };

        List<HolderItem> list = Arrays.asList(array);
        data.setValue(list);
        return data;
    }


    public HolderItem getHolderItemAt(Integer index) {
        if (holderItems.get().get(index) != null &&
                index != null &&
                holderItems.get().size() > index) {
            return holderItems.get().get(index);
        }
        return null;
    }

    public void setHolderItemsInAdapter(List<HolderItem> holderItems) {

        Settings s = new Settings();
        TodayWeather t = new TodayWeather("some temp","some press",
                "some wet",
                "some wind");
        HolderItem[] array = new HolderItem[] {
                new HolderItem(s, t),
                new HolderItem(s, todayWeather.get()),
                new HolderItem(s, todayWeather.get()),
                new HolderItem(s, todayWeather.get()),
                new HolderItem(s, todayWeather.get())
        };

        List<HolderItem> list = Arrays.asList(array);

        this.weatherAdapter.setHolderItems(list);
        this.weatherAdapter.notifyDataSetChanged();
    }

}

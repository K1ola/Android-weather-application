package com.example.myapplication.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.R;
import com.example.myapplication.model.HolderItem;
import com.example.myapplication.model.Settings;
import com.example.myapplication.model.Weather;
import com.example.myapplication.repository.API;
import com.example.myapplication.view.adapter.WeatherAdapter;
import com.example.myapplication.view.callback.ItemClickCallback;
import com.example.myapplication.view.details.DetailsFragment;

import java.util.List;

public class DataViewModel extends AndroidViewModel {
    private API api;

    private final MutableLiveData<Settings> settingsObservable;
    public ObservableField<Settings> settings = new ObservableField<>();

    private final MutableLiveData<Weather> weatherObservable;
    public ObservableField<Weather> weather = new ObservableField<>();

    private final MutableLiveData<List<HolderItem>> holderItemObservable;
    public ObservableField<List<HolderItem>> holderItems = new ObservableField<>();
    private WeatherAdapter weatherAdapter;

    public DataViewModel(@NonNull Application application) {
        super(application);
        api = new API(application.getApplicationContext());

        setSettings(new Settings(Settings.FAHRENHEIT, Settings.HPA, Settings.HOURS_PER_SECOND));
        setWeather(api.getCurrentWeather("Moscow"));

        weatherAdapter = new WeatherAdapter(R.layout.holder_item, this, null);

        settingsObservable = getSettings();
        weatherObservable = getWeather();
        holderItemObservable = getHolderItem();
    }

    public WeatherAdapter getWeatherAdapter() {
        return weatherAdapter;
    }

//    public void setWeatherAdapter(List<HolderItem> items) {
//        this.weatherAdapter.setHolderItems(items);
//        this.weatherAdapter.notifyDataSetChanged();
//    }

    public LiveData<Settings> getSettingsObservable() {
        return settingsObservable;
    }
    public LiveData<Weather> getTodayWeatherObservable() {
        return weatherObservable;
    }
    public LiveData<List<HolderItem>> getHolderItemObservable() {
        return holderItemObservable;
    }

    public void setSettings(Settings settings) {
        this.settings.set(settings);
    }

    public MutableLiveData<Settings> getSettings() {
        MutableLiveData<Settings> data = new MutableLiveData<>();
        data.setValue(currentMeasure());
        return data;
    }

    public void setWeather(Weather weather) {
        this.weather.set(weather);
    }

    public MutableLiveData<Weather> getWeather() {
        MutableLiveData<Weather> data = new MutableLiveData<>();
        //TODO remove hardcode
        this.weather.set(api.getCurrentWeather("Moscow"));
        currentMeasure();
        data.setValue(weather.get());
        return data;
    }

    public void setHolderItem(List<HolderItem> holderItem) {
        this.holderItems.set(holderItem);
    }

    private MutableLiveData<List<HolderItem>> getHolderItem() {
        MutableLiveData<List<HolderItem>> data = new MutableLiveData<>();
        data.setValue(HolderItem.getDataList(settings.get(), weather.get()));
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
        this.weatherAdapter.setHolderItems(holderItems);
        this.weatherAdapter.notifyDataSetChanged();
    }

    public void getConstDataList() {
        setHolderItem(HolderItem.getDataList(settings.get(), weather.get()));
    }

    public Settings currentMeasure() {
        if (Settings.isCelsius) {
            this.settings.get().currentTemperatureMeasure = Settings.CELSIUS;
            this.weather.get().toCelsius();
        } else {
            this.settings.get().currentTemperatureMeasure = Settings.FAHRENHEIT;
            this.weather.get().toFahrenheit();
        }

        if (Settings.isHpa) {
            this.settings.get().currentPressureMeasure = Settings.HPA;
            this.weather.get().toHPA();
        } else {
            this.settings.get().currentPressureMeasure = Settings.MM_HG;
            this.weather.get().toMmHg();
        }

        if (Settings.isMeters) {
            this.settings.get().currentWindMeasure = Settings.METERS_PER_SECOND;
            this.weather.get().toMeters();
        } else {
            this.settings.get().currentWindMeasure = Settings.HOURS_PER_SECOND;
            this.weather.get().toHours();
        }

        setWeather(this.weather.get());

//        settings.get().currentTemperatureMeasure = Settings.isCelsius ? Settings.CELSIUS : Settings.FAHRENHEIT;
//        settings.get().currentPressureMeasure = Settings.isHpa ? Settings.HPA : Settings.MM_HG;
//        settings.get().currentWindMeasure = Settings.isMeters ? Settings.METERS_PER_SECOND : Settings.HOURS_PER_SECOND;
        return this.settings.get();
    }

    public void setOnClickItemListener(final FragmentActivity fragmentActivity) {
        weatherAdapter.setItemClickCallback(new ItemClickCallback() {
            @Override
            public void onClick(HolderItem holderItem) {
                DetailsFragment detailsFragment = new DetailsFragment();

                fragmentActivity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, detailsFragment, "detailsFragment")
                        .addToBackStack("detailsFragment")
                        .commit();
            }
        });
    }

    public void SetTextColor(int color) {

    }
}

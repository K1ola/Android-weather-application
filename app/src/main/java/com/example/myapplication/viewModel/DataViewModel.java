package com.example.myapplication.viewModel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.myapplication.R;
import com.example.myapplication.model.HolderItem;
import com.example.myapplication.model.Settings;
import com.example.myapplication.model.Weather;
import com.example.myapplication.repository.API;
import com.example.myapplication.repository.AppDatabase;
import com.example.myapplication.repository.SettingsDao;
import com.example.myapplication.repository.WeatherDao;
import com.example.myapplication.view.adapter.WeatherAdapter;
import com.example.myapplication.view.adapter.WeatherAdapter2;
import com.example.myapplication.view.callback.ItemClickCallback;
import com.example.myapplication.view.details.DetailsFragment;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class DataViewModel extends AndroidViewModel {
    private static AppDatabase appDatabase;
    private API api;

    private final MutableLiveData<Settings> settingsObservable;
    public ObservableField<Settings> settings = new ObservableField<>();

    private final MutableLiveData<Weather> weatherObservable;
    public ObservableField<Weather> weather = new ObservableField<>();

//    private final MutableLiveData<List<HolderItem>> holderItemObservable;
    public ObservableField<List<HolderItem>> holderItems1 = new ObservableField<>();
    public ObservableField<List<HolderItem>> holderItems2 = new ObservableField<>();
    public WeatherAdapter weatherAdapter1;
    public WeatherAdapter2 weatherAdapter2;

    public DataViewModel(@NonNull Application application) {
        super(application);
        if (appDatabase == null) appDatabase = Room.databaseBuilder(application.getApplicationContext(),
                AppDatabase.class, "database").build();
        api = new API(application.getApplicationContext());

        setSettings(new Settings(Settings.FAHRENHEIT, Settings.HPA, Settings.HOURS_PER_SECOND));
        Weather w = api.getCurrentWeather("Moscow");
        //TODO 1st value default, not from db;
        if (w == null) w = new Weather("10", "10", "10", "10", "10", "10", "10");
        setWeather(w);
        WeatherDao wd = appDatabase.weatherDao();
        new insertAsyncTaskWeather(wd).execute(this.weather.get());

        weatherAdapter1 = new WeatherAdapter(R.layout.holder_item, this, null);
        weatherAdapter2 = new WeatherAdapter2(R.layout.holder_item2, this, null);

        settingsObservable = getSettings();
        weatherObservable = getWeather();
//        holderItemObservable = getHolderItem();
    }

//    public WeatherAdapter getWeatherAdapter() {
//        return weatherAdapter;
//    }
//
//    public WeatherAdapter getWeatherAdapter(List<HolderItem> items) {
//        weatherAdapter = new WeatherAdapter(R.layout.holder_item, this, null);
//        this.weatherAdapter.setHolderItems(items);
//        this.weatherAdapter.notifyDataSetChanged();
//        return this.weatherAdapter;
//    }
//
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

    public LiveData<List<HolderItem>> getHolderItemObservable1() {
        MutableLiveData<List<HolderItem>> data = new MutableLiveData<>();
        data.setValue(get5DaysDataList());
        return data;
    }
    public LiveData<List<HolderItem>> getHolderItemObservable2() {
        MutableLiveData<List<HolderItem>> data = new MutableLiveData<>();
        data.setValue(getHourlyDataList());
        return data;
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
        Weather w = api.getCurrentWeather("Moscow");
        if (w == null) {
            WeatherDao wd = appDatabase.weatherDao();
            try {
                w = new getLastAsyncTaskWeather(wd).execute().get();
            } catch (ExecutionException | NullPointerException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.weather.set(w);
        WeatherDao wd = appDatabase.weatherDao();
        new updateAsyncTaskWeather(wd).execute(this.weather.get());
        currentMeasure();
        data.setValue(weather.get());
        return data;
    }

    public void setHolderItem1(List<HolderItem> holderItem) {
        this.holderItems1.set(holderItem);
    }

    public void setHolderItem2(List<HolderItem> holderItem) {
        this.holderItems2.set(holderItem);
    }

    private MutableLiveData<List<HolderItem>> getHolderItem() {
        MutableLiveData<List<HolderItem>> data = new MutableLiveData<>();
//        setWeatherAdapter(data.getValue());
        data.setValue(HolderItem.getDataList(settings.get(), weather.get()));
        return data;
    }


    public HolderItem getHolderItemAt1(Integer index) {
        if (holderItems1.get().get(index) != null &&
                index != null &&
                holderItems1.get().size() > index) {
            return holderItems1.get().get(index);
        }
        return null;
    }

    public HolderItem getHolderItemAt2(Integer index) {
        if (holderItems2.get().get(index) != null &&
                index != null &&
                holderItems2.get().size() > index) {
            return holderItems2.get().get(index);
        }
        return null;
    }

    public void setHolderItemsInAdapter1(List<HolderItem> holderItems) {
        this.weatherAdapter1.setHolderItems(holderItems);
        this.weatherAdapter1.notifyDataSetChanged();
    }

    public void setHolderItemsInAdapter2(List<HolderItem> holderItems) {
        this.weatherAdapter2.setHolderItems(holderItems);
        this.weatherAdapter2.notifyDataSetChanged();
    }

    public void getConstDataList() {
        setHolderItem1(HolderItem.getDataList(settings.get(), weather.get()));
    }

    public List<HolderItem> getHourlyDataList() {
        List<Weather> w = api.getHourlyWeather("Moscow");
        setHolderItem1(HolderItem.getHourlyDataList(settings.get(), w));
        return HolderItem.getHourlyDataList(settings.get(), w);
    }

    public List<HolderItem> get5DaysDataList() {
        List<Weather> w = api.get5DaysWeather("Moscow");
        setHolderItem2(HolderItem.get5DaysDataList(settings.get(), w));
        return HolderItem.get5DaysDataList(settings.get(), w);
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
        SettingsDao s = appDatabase.settingsDao();
        new updateAsyncTaskSettings(s).execute(this.settings.get());

        return this.settings.get();
    }

    public void setOnClickItemListener(final FragmentActivity fragmentActivity) {
        weatherAdapter2.setItemClickCallback(new ItemClickCallback() {
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

    private static class getLastAsyncTaskWeather extends AsyncTask<Weather, Void, Weather> {
        private WeatherDao mAsyncTaskDao;
        getLastAsyncTaskWeather(WeatherDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Weather doInBackground(final Weather... params) {
            return mAsyncTaskDao.getLast();
        }

        @Override
        protected void onPostExecute(Weather result) {}
    }

    private static class insertAsyncTaskWeather extends AsyncTask<Weather, Void, Void> {
        private WeatherDao mAsyncTaskDao;
        insertAsyncTaskWeather(WeatherDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Weather... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class updateAsyncTaskWeather extends AsyncTask<Weather, Void, Void> {
        private WeatherDao mAsyncTaskDao;
        updateAsyncTaskWeather(WeatherDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Weather... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }

    private static class updateAsyncTaskSettings extends AsyncTask<Settings, Void, Void> {
        private SettingsDao mAsyncTaskDao;
        updateAsyncTaskSettings(SettingsDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Settings... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }
}

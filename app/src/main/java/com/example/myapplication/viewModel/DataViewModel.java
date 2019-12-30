package com.example.myapplication.viewModel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.myapplication.model.Settings;
import com.example.myapplication.model.Weather;
import com.example.myapplication.repository.API;
import com.example.myapplication.repository.AppDatabase;
import com.example.myapplication.repository.SettingsDao;
import com.example.myapplication.repository.WeatherDao;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class DataViewModel extends AndroidViewModel {
    private static AppDatabase appDatabase;
    private API api;

    public ObservableField<Settings> settings = new ObservableField<>();
    public ObservableField<Weather> weather = new ObservableField<>();

    private final ObservableField<List<Weather>> weatherList = new ObservableField<>();

    public DataViewModel(@NonNull Application application) {
        super(application);
        if (appDatabase == null) appDatabase = Room.databaseBuilder(application.getApplicationContext(),
                AppDatabase.class, "database").build();
        api = new API(application.getApplicationContext());

        setSettings(new Settings(Settings.FAHRENHEIT, Settings.HPA, Settings.HOURS_PER_SECOND));
        Weather w = api.getCurrentWeather("Moscow");
//        //TODO 1st value default, not from db;
        if (w == null) w = new Weather("10", "10", "10", "10", "10", "10", "10");
        setWeather(w);
//        //WeatherDao wd = appDatabase.weatherDao();
//        //new insertAsyncTaskWeather(wd).execute(this.weather.get());
//
//        List<Weather> www = new ArrayList<>();
//        for (int i =0; i<5; i++) {
//            www.add(new Weather("10", "10", "10", "10", "10", "10", "10"));
//        }
//        setWeathersObservable(www);
//        setWeathers(www);
    }

//    public void setWeathersObservable(List<Weather> we) {
//        MutableLiveData<List<Weather>> data = new MutableLiveData<>();
////        List<Weather> w = null;
////        for (int i =0; i<5; i++) {
////            w.add(getHourlyDataList().get(i).weather);
////        }
//        List<Weather> www = new ArrayList<>();
//        for (int i =0; i<5; i++) {
//            www.add(new Weather("10", "10", "10", "10", "10", "10", "10"));
//        }
//        data.setValue(www);
//        this.weatherList.set(www);
//    }

    public void setWeathers(List<Weather> w) {
        this.weatherList.set(w);
    }
    public ObservableField<List<Weather>> getWeathers() {
        setWeathers(api.get5DaysWeather("Moscow"));
        return this.weatherList;
    }


    public void setSettings(Settings settings) {
        this.settings.set(settings);
    }
    public ObservableField<Settings> getSettings() {
        setSettings(currentMeasure());
        return this.settings;
    }


    public void setWeather(Weather weather) {
        this.weather.set(weather);
    }
    public ObservableField<Weather> getWeather() {
        setWeather(api.getCurrentWeather("Moscow"));
        return this.weather;
    }

//    public List<HolderItem> getHourlyDataList() {
//        List<Weather> w = api.getHourlyWeather("Moscow");
//        setHolderItem(HolderItem.getHourlyDataList(settings.get(), w));
//        return HolderItem.getHourlyDataList(settings.get(), w);
//    }
//
//    public List<HolderItem> get5DaysDataList() {
//        List<Weather> w = api.get5DaysWeather("Moscow");
//        setHolderItem2(HolderItem.get5DaysDataList(settings.get(), w));
//        return HolderItem.get5DaysDataList(settings.get(), w);
//    }

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

//    public void setOnClickItemListener(final FragmentActivity fragmentActivity) {
//        weatherAdapter2.setItemClickCallback(new ItemClickCallback() {
//            @Override
//            public void onClick(HolderItem holderItem) {
//                DetailsFragment detailsFragment = new DetailsFragment();
//
//                fragmentActivity.getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.container, detailsFragment, "detailsFragment")
//                        .addToBackStack("detailsFragment")
//                        .commit();
//            }
//        });
//    }

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

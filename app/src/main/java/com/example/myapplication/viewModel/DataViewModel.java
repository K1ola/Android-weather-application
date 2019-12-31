package com.example.myapplication.viewModel;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.room.Room;

import com.example.myapplication.R;
import com.example.myapplication.model.Settings;
import com.example.myapplication.model.Weather;
import com.example.myapplication.repository.API;
import com.example.myapplication.repository.AppDatabase;
import com.example.myapplication.repository.SettingsDao;
import com.example.myapplication.repository.WeatherDao;
import com.example.myapplication.view.details.DetailsFragment;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class DataViewModel extends AndroidViewModel {
    private static AppDatabase appDatabase;
    private API api;
    private Context context;

    public static int currentDay;
    public static boolean internet;

    public ObservableField<Settings> settings = new ObservableField<>();
    public ObservableField<Weather> weather = new ObservableField<>();

    private final ObservableField<List<Weather>> weatherListDaily = new ObservableField<>();
    private final ObservableField<List<Weather>> weatherListHourly = new ObservableField<>();

    public DataViewModel(@NonNull Application application) {
        super(application);
        if (appDatabase == null) {
            appDatabase = Room.databaseBuilder(application.getApplicationContext(),
                    AppDatabase.class, "database").build();
        }
        api = new API(application.getApplicationContext());

        Weather w = api.getCurrentWeather("Moscow");
        internet = w != null;
//        internet = false;
    }

    public void setContext(Context c) {
        this.context = c;
    }

    public void setWeathersDaily(List<Weather> w) {
        try {
            if (w == null)
                w = new getLastDailyAsyncTaskWeather(appDatabase.weatherDao()).execute().get();
        } catch (ExecutionException | NullPointerException | InterruptedException e) {
            e.printStackTrace();
        }
        this.weatherListDaily.set(w);
    }
    public ObservableField<List<Weather>> getWeathersDaily() {
        setWeathersDaily(api.get5DaysWeather("Moscow"));
        currentMeasure();
        new insertListAsyncTaskWeather(appDatabase.weatherDao()).execute(this.weatherListDaily.get());
        return this.weatherListDaily;
    }

    public void setWeathersHourly(List<Weather> w) {
        try {
            if (w == null)
                w = new getLastHourlyAsyncTaskWeather(appDatabase.weatherDao()).execute().get();
        } catch (ExecutionException | NullPointerException | InterruptedException e) {
            e.printStackTrace();
        }
        this.weatherListHourly.set(w);
    }
    public ObservableField<List<Weather>> getWeathersHourly() {
        setWeathersHourly(api.getHourlyWeather("Moscow"));
        currentMeasure();
        new insertListAsyncTaskWeather(appDatabase.weatherDao()).execute(this.weatherListHourly.get());
        return this.weatherListHourly;
    }


    public void setSettings(Settings settings) {
//        try {
//            if (settings == null)
//                settings = new getLastAsyncTaskSettings(appDatabase.settingsDao()).execute().get();
//                if (settings == null) {
//                    //settings = new Settings(Settings.FAHRENHEIT, Settings.HPA, Settings.HOURS_PER_SECOND);
//                }
//        } catch (ExecutionException | NullPointerException | InterruptedException e) {
//            e.printStackTrace();
//        }
        this.settings.set(settings);
    }
    public ObservableField<Settings> getSettings() {
        setSettings(currentMeasure());
        //new insertAsyncTaskSettings(appDatabase.settingsDao()).execute(this.settings.get());
        return this.settings;
    }


    public void setWeather(Weather weather) {
        try {
            if (weather == null)
                weather = new getLastCurrentlyAsyncTaskWeather(appDatabase.weatherDao()).execute().get();
        } catch (ExecutionException | NullPointerException | InterruptedException e) {
            e.printStackTrace();
        }
        this.weather.set(weather);
    }
    public ObservableField<Weather> getWeather() {
        setWeather(api.getCurrentWeather("Moscow"));
        currentMeasure();
        new insertAsyncTaskWeather(appDatabase.weatherDao()).execute(this.weather.get());
        return this.weather;
    }

    public Settings currentMeasure() {
        try {
            if (this.settings.get() == null)
                this.settings.set(new getLastAsyncTaskSettings(appDatabase.settingsDao()).execute().get());
            if (this.settings.get() == null) {
                new insertAsyncTaskSettings(appDatabase.settingsDao()).execute(new Settings(Settings.FAHRENHEIT, Settings.HPA, Settings.HOURS_PER_SECOND));
                this.settings.set(new getLastAsyncTaskSettings(appDatabase.settingsDao()).execute().get());

//                this.settings.set(new Settings(Settings.FAHRENHEIT, Settings.HPA, Settings.HOURS_PER_SECOND));
            }
        } catch (ExecutionException | NullPointerException | InterruptedException e) {
            e.printStackTrace();
        }

        if (weather.get() == null || weatherListDaily.get() == null) return null;

        if (Settings.isCelsius) {
            this.settings.get().currentTemperatureMeasure = Settings.CELSIUS;
            this.weather.get().toCelsius();

            if (weatherListDaily.get() != null)
                for (int i=0; i<5; i++) {
                    this.weatherListDaily.get().get(i).toCelsius();
                }
            if (weatherListHourly.get() != null)
                for (int i=0; i<5; i++) {
                    this.weatherListHourly.get().get(i).toCelsius();
                }
        } else {
            this.settings.get().currentTemperatureMeasure = Settings.FAHRENHEIT;
            this.weather.get().toFahrenheit();

            if (weatherListDaily.get() != null)
                for (int i=0; i<5; i++) {
                    this.weatherListDaily.get().get(i).toFahrenheit();
                }
            if (weatherListHourly.get() != null)
                for (int i=0; i<5; i++) {
                    this.weatherListHourly.get().get(i).toFahrenheit();
                }
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

        return this.settings.get();
    }

    public int onDayClick(int index) {
        final DetailsFragment detailsFragment = new DetailsFragment();

        ((FragmentActivity)context).getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, detailsFragment)
                .addToBackStack(null)
                .commit();
        this.currentDay = index;
        return index;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////


    private static class getLastCurrentlyAsyncTaskWeather extends AsyncTask<Weather, Void, Weather> {
        private WeatherDao mAsyncTaskDao;
        getLastCurrentlyAsyncTaskWeather(WeatherDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Weather doInBackground(final Weather... params) {
            return mAsyncTaskDao.getLastCurrent();
        }

        @Override
        protected void onPostExecute(Weather result) {}
    }

    private static class getLastDailyAsyncTaskWeather extends AsyncTask<List<Weather>, Void, List<Weather>> {
        private WeatherDao mAsyncTaskDao;
        getLastDailyAsyncTaskWeather(WeatherDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected List<Weather> doInBackground(final List<Weather>... params) {
            return mAsyncTaskDao.getLastDaily();
        }

        @Override
        protected void onPostExecute(List<Weather> result) {}
    }

    private static class getLastHourlyAsyncTaskWeather extends AsyncTask<List<Weather>, Void, List<Weather>> {
        private WeatherDao mAsyncTaskDao;
        getLastHourlyAsyncTaskWeather(WeatherDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected List<Weather> doInBackground(final List<Weather>... params) {
            return mAsyncTaskDao.getLastHourly();
        }

        @Override
        protected void onPostExecute(List<Weather> result) {}
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

    private static class getLastAsyncTaskSettings extends AsyncTask<Settings, Void, Settings> {
        private SettingsDao mAsyncTaskDao;
        getLastAsyncTaskSettings(SettingsDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Settings doInBackground(final Settings... params) {
            return mAsyncTaskDao.getLast();
        }

        @Override
        protected void onPostExecute(Settings result) {}
    }

    private static class insertAsyncTaskSettings extends AsyncTask<Settings, Void, Void> {
        private SettingsDao mAsyncTaskDao;
        insertAsyncTaskSettings(SettingsDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Settings... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class insertListAsyncTaskWeather extends AsyncTask<List<Weather>, Void, Void> {
        private WeatherDao mAsyncTaskDao;
        insertListAsyncTaskWeather(WeatherDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final List<Weather>... params) {
            mAsyncTaskDao.insertList(params[0]);
            return null;
        }
    }
}

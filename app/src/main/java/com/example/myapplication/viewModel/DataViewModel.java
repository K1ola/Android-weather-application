package com.example.myapplication.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
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

        settingsObservable = getSettings();
        weatherObservable = getTodayWeather();
        holderItemObservable = getHolderItem();

        weatherAdapter = new WeatherAdapter(R.layout.holder_item, this, null);
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

    public void setTodayWeather(Weather weather) {
        this.weather.set(weather);
    }

    public MutableLiveData<Weather> getTodayWeather() {
        MutableLiveData<Weather> data = new MutableLiveData<>();
        //TODO remove hardcode
        Weather weather = api.getCurrentWeather("Moscow");
        data.setValue(weather);
        return data;
    }

    public void setHolderItem(List<HolderItem> holderItem) {
        this.holderItems.set(holderItem);
    }

    private MutableLiveData<List<HolderItem>> getHolderItem() {
        MutableLiveData<List<HolderItem>> data = new MutableLiveData<>();
        data.setValue(HolderItem.getConstDataList());
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
        setHolderItem(HolderItem.getConstDataList());
    }

    public static Settings currentMeasure() {
        Settings settings = new Settings();
        settings.currentTemperatureMeasure = Settings.isCelsius ? Settings.CELSIUS : Settings.FAHRENHEIT;
        settings.currentPressureMeasure = Settings.isHpa ? Settings.HPA : Settings.MM_HG;
        settings.currentWindMeasure = Settings.isMeters ? Settings.METERS_PER_SECOND : Settings.HOURS_PER_SECOND;
        return settings;
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

//    private void getCity(Context context, String location) {
//        if (Geocoder.isPresent()) {
//            try {
////                String location = "Moscow";
//                Geocoder gc = new Geocoder(context);
//                List<Address> addresses = gc.getFromLocationName(location, 5); // get the found Address Objects
//
//                //                List<LatLng> ll = new ArrayList<LatLng>(addresses.size()); // A list to save the coordinates if they are available
//                //                for(Address a : addresses){
//                //                    if(a.hasLatitude() && a.hasLongitude()){
//                //                        ll.add(new LatLng(a.getLatitude(), a.getLongitude()));
//                //                    }
//                //                }
//
//                Logger LOGGER = Logger.getGlobal();
//                LOGGER.info(addresses.toString());
//            } catch (IOException e) {
//                // handle the exception
//            }
//        }
//    }
}

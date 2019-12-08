package com.example.myapplication.view.main;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleObserver;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.viewModel.SettingsViewModel;
import com.example.myapplication.model.AdapterWeather;
import com.example.myapplication.model.AdapterWithText;
import com.example.myapplication.model.DataSource;
import com.example.myapplication.view.settings.SettingsFragment;
import com.example.myapplication.databinding.MainFragmentBinding;

public class MainFragment extends Fragment implements AdapterWeather.OnItemClickListener  {
    private AdapterWeather mAdapter;
    private AdapterWithText mAdapterWithText;
    private DataSource mDataSource = DataSource.getInstance();
    private DrawerLayout mDrawerLayout;
  
    private static final String TEMPERATURE = "+29 ";
    private static final String[] WEATHER_STATUS = {"Пасмурно", "Небольшая облачность", "Сильный дождь", "Туман", "Дождь", "Снег", "Облачно", "Безоблачно", "Гроза"};
    private static final String WET = "Влажность: 29%";
    private static final String PRESSURE = "Давление: 27 ";
    private static final String WIND = "Сила ветра: 10 ";

    private final static String TEMPERATURE_KEY = "Temperature";
    private final static String PRESSURE_KEY = "Pressure";
    private final static String WIND_KEY = "Wind";

    private String temperature;
    private String pressure;
    private String wind;


    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        LifecycleObserver observer = Lif
        SettingsViewModel settingsViewModel = new SettingsViewModel(1,"°C");
        //..................      Binding
        MainFragmentBinding mainFragmentBinding = MainFragmentBinding.inflate(inflater,
                container,
                false);

        mainFragmentBinding.setSettingsViewModel(settingsViewModel);
        View view = mainFragmentBinding.getRoot();

//        final View view = inflater.inflate(R.layout.main_fragment, container, false);
//        SettingsViewModel model = ViewModelProviders.of(getActivity()).get(SettingsViewModel.class);

        if (savedInstanceState != null) {
            temperature = savedInstanceState.getString(TEMPERATURE_KEY);
            pressure = savedInstanceState.getString(PRESSURE_KEY);
            wind = savedInstanceState.getString(WIND_KEY);
        } else {
            temperature = "1";//model.getTemp();
            pressure = "1"; //model.getPressure();
            wind = "1";//model.getWind();
        }

        mDataSource.setMeasures(temperature, pressure, wind);
        final RecyclerView recyclerView = view.findViewById(R.id.list);

        mAdapter = new AdapterWeather(mDataSource.getData(), this, Color.WHITE);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);

        // Toolbar
        Toolbar mainToolBar = view.findViewById(R.id.main_toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(mainToolBar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        // Draweer
        mDrawerLayout = view.findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(activity, mDrawerLayout, mainToolBar, R.string.Open, R.string.Close);
        toggle.getDrawerArrowDrawable().setColor(Color.WHITE);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
      
        ImageView settings_icon = view.findViewById(R.id.main_setting);
        settings_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenSettingsFragment();
            }
        });

        final RecyclerView recyclerViewText = view.findViewById(R.id.text_list);
        final LinearLayoutManager layoutManagerText = new LinearLayoutManager(getContext());

        mAdapterWithText = new AdapterWithText(mDataSource.getDataWet(), Color.WHITE);
        layoutManagerText.setOrientation(RecyclerView.HORIZONTAL);
        recyclerViewText.setLayoutManager(layoutManagerText);
        recyclerViewText.setAdapter(mAdapterWithText);

        return view;
    }

    private void OpenSettingsFragment() {
        SettingsFragment settingsFragment = new SettingsFragment();

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, settingsFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setViewText(view, R.id.temperature, TEMPERATURE);
        setViewText(view, R.id.status, WEATHER_STATUS[0]);
        setViewText(view, R.id.wet, WET);
        setViewText(view, R.id.pressure, PRESSURE);
        setViewText(view, R.id.wind, WIND);

        final ImageView imageView = view.findViewById(R.id.imageView);
        imageView.setColorFilter(Color.WHITE);
        imageView.setImageResource(R.drawable.cold_snow_snowflake);

//        final SettingsViewModel model = ViewModelProviders.of(getActivity()).get(SettingsViewModel.class);

        setViewText(view, R.id.temperature_measure, "1"/*model.getTemp()*/);
        setViewText(view, R.id.pressure_measure, "1"/*model.getPressure()*/);
        setViewText(view, R.id.wind_measure, "1"/*model.getWind()*/);
    }


    private void setViewText(@NonNull View view, int viewId, String value) {
        final TextView textView = view.findViewById(viewId);
        textView.setText(value);
    }

    @Override
    public void onItemClick() {
        if (getActivity() == null || !(getActivity() instanceof AdapterWeather.OnItemClickListener)) {
            return;
        }
        ((AdapterWeather.OnItemClickListener) getActivity()).onItemClick();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(TEMPERATURE_KEY, temperature);
        outState.putString(PRESSURE_KEY, pressure);
        outState.putString(WIND_KEY, wind);
    }
}

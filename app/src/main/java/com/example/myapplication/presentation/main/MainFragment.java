package com.example.myapplication.presentation.main;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.interactor.SettingsViewModel;
import com.example.myapplication.presentation.common.AdapterWeather;
import com.example.myapplication.presentation.common.AdapterWithText;
import com.example.myapplication.presentation.common.DataSource;
import com.example.myapplication.presentation.settings.SettingsFragment;

public class MainFragment extends Fragment implements AdapterWeather.OnItemClickListener  {
    private AdapterWeather mAdapter;
    private AdapterWithText mAdapterWithText;
    private DataSource mDataSource = DataSource.getInstance();
  
    private static final String TEMPERATURE = "+29 ";
    private static final String[] WEATHER_STATUS = {"Пасмурно", "Небольшая облачность", "Сильный дождь", "Туман", "Дождь", "Снег", "Облачно", "Безоблачно", "Гроза"};
    private static final String WET = "Влажность: 29%";
    private static final String PRESSURE = "Давление: 27 ";
    private static final String WIND = "Сила ветра: 10 ";

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.main_fragment, container, false);
        SettingsViewModel model = ViewModelProviders.of(getActivity()).get(SettingsViewModel.class);

        mDataSource.setMeasures(model.getTemp(), model.getPressure(), model.getWind());
        final RecyclerView recyclerView = view.findViewById(R.id.list);

        mAdapter = new AdapterWeather(mDataSource.getData(), this);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);

        Toolbar mainToolBar = view.findViewById(R.id.main_toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(mainToolBar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        ImageView settings_icon = view.findViewById(R.id.main_setting);
        settings_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenSettingsFragment();
            }
        });


      
        final RecyclerView recyclerViewText = view.findViewById(R.id.text_list);
        final LinearLayoutManager layoutManagerText = new LinearLayoutManager(getContext());

        mAdapterWithText = new AdapterWithText(mDataSource.getDataWet());
        layoutManagerText.setOrientation(RecyclerView.HORIZONTAL);
        recyclerViewText.setLayoutManager(layoutManagerText);
        recyclerViewText.setAdapter(mAdapterWithText);

        return view;
    }

    private void OpenSettingsFragment(){
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

        SettingsViewModel model = ViewModelProviders.of(getActivity()).get(SettingsViewModel.class);

        setViewText(view, R.id.temperature_measure, model.getTemp());
        setViewText(view, R.id.pressure_measure, model.getPressure());
        setViewText(view, R.id.wind_measure, model.getWind());
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
}

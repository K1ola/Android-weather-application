package com.example.myapplication.presentation.main;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.presentation.common.AdapterText;
import com.example.myapplication.presentation.common.AdapterWeather;
import com.example.myapplication.presentation.common.DataSource;

public class MainFragment extends Fragment {
    private AdapterWeather mAdapter;
    private AdapterText mAdapterText;
    private DataSource mDataSource = DataSource.getInstance();
  
    private static final String TEMPERATURE = "+29°C";
    private static final String[] WEATHER_STATUS = {"Пасмурно", "Небольшая облачность", "Сильный дождь", "Туман", "Дождь", "Снег", "Облачно", "Безоблачно", "Гроза"};
    private static final String WET = "Влажность: 29%";
    private static final String PRESSURE = "Давление: 27 мм. рт. ст.";
    private static final String WIND = "Сила ветра: 10 м/с";

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.main_fragment, container, false);
        final RecyclerView recyclerView = view.findViewById(R.id.list);

        mAdapter = new AdapterWeather(mDataSource.getData());

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);

        final RecyclerView recyclerViewText = view.findViewById(R.id.text_list);
        final LinearLayoutManager layoutManagerText = new LinearLayoutManager(getContext());

        mAdapterText = new AdapterText(mDataSource.getData());
        layoutManagerText.setOrientation(RecyclerView.HORIZONTAL);
        recyclerViewText.setLayoutManager(layoutManagerText);
        recyclerViewText.setAdapter(mAdapterText);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setViewText(view, R.id.temperature, TEMPERATURE);
        setViewText(view, R.id.status, WEATHER_STATUS[0]);
        setViewText(view, R.id.wet, WET);
        setViewText(view, R.id.pressure, PRESSURE);
        setViewText(view, R.id.wind, WIND);

        final ImageView imageView = view.findViewById(R.id.imageView);
        imageView.setColorFilter(Color.WHITE);
        imageView.setImageResource(R.drawable.cold_snow_snowflake);

    }

    private void setViewText(@NonNull View view, int viewId, String value) {
        final TextView textView = view.findViewById(viewId);
        textView.setText(value);
    }
}

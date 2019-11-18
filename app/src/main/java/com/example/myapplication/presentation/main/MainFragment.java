package com.example.myapplication.presentation.main;

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
import com.example.myapplication.presentation.common.AdapterWeather;
import com.example.myapplication.presentation.common.DataSource;

public class MainFragment extends Fragment {
    private AdapterWeather mAdapter;
    private DataSource mDataSource = DataSource.getInstance();
  
    private static final String TEMPERATURE = "+29°C";
    private static final String WEATHER_STATUS = "Солнечно";
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

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setViewText(view, R.id.temperature, TEMPERATURE);
        setViewText(view, R.id.status, WEATHER_STATUS);
        setViewText(view, R.id.wet, WET);
        setViewText(view, R.id.pressure, PRESSURE);
        setViewText(view, R.id.wind, WIND);

        final ImageView imageView = view.findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.cold_snow_snowflake);

    }

    private void setViewText(@NonNull View view, int viewId, String value) {
        final TextView textView = view.findViewById(viewId);
        textView.setText(value);
    }
}

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

import com.example.myapplication.R;

public class MainFragment extends Fragment {
    private final String temperature = "+29°C";
    private final String weatherStatus = "Солнечно";
    private final String wet = "Влажность: 29%";
    private final String pressure = "Давление: 27 мм. рт. ст.";
    private final String wind = "Сила ветра: 10 м/с";

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setViewText(view, R.id.temperature, temperature);
        setViewText(view, R.id.status, weatherStatus);
        setViewText(view, R.id.wet, wet);
        setViewText(view, R.id.pressure, pressure);
        setViewText(view, R.id.wind, wind);

        ImageView imageView = view.findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.cold_snow_snowflake);

    }

    private void setViewText(@NonNull View view, int viewId, String value) {
        TextView textView = view.findViewById(viewId);
        textView.setText(value);
    }
}

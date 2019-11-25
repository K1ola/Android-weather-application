package com.example.myapplication.presentation.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.interactor.SettingsViewModel;
import com.example.myapplication.presentation.common.AdapterWeather;
import com.example.myapplication.presentation.common.AdapterWithText;
import com.example.myapplication.presentation.common.DataSource;

public class DetailsFragment extends Fragment {
    private AdapterWeather mWeatherAdapter;
    private AdapterWithText mCalendarAdapterWithText;
    private AdapterWithText mPressureAdapterWithText;
    private AdapterWithText mWindAdapterWithText;
    private AdapterWithText mWetAdapterWithText;
    private DataSource mDataSource = DataSource.getInstance();

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.details_fragment, container, false);

        SettingsViewModel model = ViewModelProviders.of(getActivity()).get(SettingsViewModel.class);

        mDataSource.setMeasures(model.getTemp(), model.getPressure(), model.getWind());

        final RecyclerView recyclerViewTextCalendar = view.findViewById(R.id.calendar);
        final LinearLayoutManager layoutManagerTextCalendar = new LinearLayoutManager(getContext());
        mCalendarAdapterWithText = new AdapterWithText(mDataSource.getDataCalendar());
        layoutManagerTextCalendar.setOrientation(RecyclerView.HORIZONTAL);
        recyclerViewTextCalendar.setLayoutManager(layoutManagerTextCalendar);
        recyclerViewTextCalendar.setAdapter(mCalendarAdapterWithText);

        final RecyclerView recyclerViewTextPressure = view.findViewById(R.id.pressure);
        final LinearLayoutManager layoutManagerTextPressure = new LinearLayoutManager(getContext());
        mPressureAdapterWithText = new AdapterWithText(mDataSource.getDataPressure());
        layoutManagerTextPressure.setOrientation(RecyclerView.HORIZONTAL);
        recyclerViewTextPressure.setLayoutManager(layoutManagerTextPressure);
        recyclerViewTextPressure.setAdapter(mPressureAdapterWithText);

        final RecyclerView recyclerViewTextWind = view.findViewById(R.id.wind);
        final LinearLayoutManager layoutManagerTextWind = new LinearLayoutManager(getContext());
        mWindAdapterWithText = new AdapterWithText(mDataSource.getDataWind());
        layoutManagerTextWind.setOrientation(RecyclerView.HORIZONTAL);
        recyclerViewTextWind.setLayoutManager(layoutManagerTextWind);
        recyclerViewTextWind.setAdapter(mWindAdapterWithText);

        final RecyclerView recyclerViewTextWet = view.findViewById(R.id.wet);
        final LinearLayoutManager layoutManagerText = new LinearLayoutManager(getContext());
        mWetAdapterWithText = new AdapterWithText(mDataSource.getDataWet());
        layoutManagerText.setOrientation(RecyclerView.HORIZONTAL);
        recyclerViewTextWet.setLayoutManager(layoutManagerText);
        recyclerViewTextWet.setAdapter(mWetAdapterWithText);

        final RecyclerView recyclerView = view.findViewById(R.id.daily_weather);
        mWeatherAdapter = new AdapterWeather(mDataSource.getDataDetails(), null);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mWeatherAdapter);

        return view;
    }

    private void setViewText(@NonNull View view, int viewId, String value) {
        final TextView textView = view.findViewById(viewId);
        textView.setText(value);
    }

}

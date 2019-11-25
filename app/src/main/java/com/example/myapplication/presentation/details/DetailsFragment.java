package com.example.myapplication.presentation.details;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    private DataSource mDataSource = DataSource.getInstance();

    private AdapterWeather mWeatherAdapter = new AdapterWeather(mDataSource.getDataDetails(), null);
    private AdapterWithText mCalendarAdapterWithText = new AdapterWithText(mDataSource.getDataCalendar());
    private AdapterWithText mPressureAdapterWithText = new AdapterWithText(mDataSource.getDataPressure());
    private AdapterWithText mWindAdapterWithText = new AdapterWithText(mDataSource.getDataWind());
    private AdapterWithText mWetAdapterWithText = new AdapterWithText(mDataSource.getDataWet());

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.details_fragment, container, false);

        SettingsViewModel model = ViewModelProviders.of(getActivity()).get(SettingsViewModel.class);

        mDataSource.setMeasures(model.getTemp(), model.getPressure(), model.getWind());

        createRecycler(view, R.id.calendar, null, mCalendarAdapterWithText);
        createRecycler(view, R.id.pressure, null, mPressureAdapterWithText);
        createRecycler(view, R.id.wind, null, mWindAdapterWithText);
        createRecycler(view, R.id.wet, null, mWetAdapterWithText);

        createRecycler(view, R.id.daily_weather, mWeatherAdapter, null);

        return view;
    }

    private void createRecycler(View view, int recyclerId, AdapterWeather adapterWeather, AdapterWithText adapterWithText) {
        final RecyclerView recyclerView = view.findViewById(recyclerId);
        if (adapterWeather != null) {
            adapterWeather.setDataColor(Color.BLACK);
            final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            layoutManager.setOrientation(RecyclerView.HORIZONTAL);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapterWeather);
        } else {
            adapterWithText.setDataColor(Color.BLACK);
            final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            layoutManager.setOrientation(RecyclerView.HORIZONTAL);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapterWithText);
        }

    }

}

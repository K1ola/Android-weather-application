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

    private AdapterWeather mWeatherAdapter = new AdapterWeather(mDataSource.getDataDetails(), null, Color.BLACK);
    private AdapterWithText mCalendarAdapterWithText = new AdapterWithText(mDataSource.getDataCalendar(), Color.BLACK);
    private AdapterWithText mPressureAdapterWithText = new AdapterWithText(mDataSource.getDataPressure(), Color.BLACK);
    private AdapterWithText mWindAdapterWithText = new AdapterWithText(mDataSource.getDataWind(), Color.BLACK);
    private AdapterWithText mWetAdapterWithText = new AdapterWithText(mDataSource.getDataWet(), Color.BLACK);

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.details_fragment, container, false);

        SettingsViewModel model = ViewModelProviders.of(getActivity()).get(SettingsViewModel.class);

        mDataSource.setMeasures(model.getTemp(), model.getPressure(), model.getWind());

        createRecycler(view, R.id.calendar, mCalendarAdapterWithText);
        createRecycler(view, R.id.pressure, mPressureAdapterWithText);
        createRecycler(view, R.id.wind,  mWindAdapterWithText);
        createRecycler(view, R.id.wet,  mWetAdapterWithText);
        createRecycler(view, R.id.daily_weather, mWeatherAdapter);

        return view;
    }

    private void createRecycler(@NonNull View view, int recyclerId, @Nullable RecyclerView.Adapter adapter) {
        final RecyclerView recyclerView = view.findViewById(recyclerId);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

}

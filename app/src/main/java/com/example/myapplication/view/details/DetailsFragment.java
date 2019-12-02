package com.example.myapplication.view.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.viewModel.SettingsViewModel;
import com.example.myapplication.adapter.AdapterWeather;
import com.example.myapplication.adapter.AdapterWithText;
//import com.example.myapplication.model.DataSource;
import com.example.myapplication.view.main.MainFragment;

public class DetailsFragment extends Fragment {
//    private DataSource mDataSource = DataSource.getInstance();

    private AdapterWeather mWeatherAdapter;// = new AdapterWeather(mDataSource.getDataDetails(), null, Color.BLACK);
    private AdapterWithText mCalendarAdapterWithText;// = new AdapterWithText(mDataSource.getDataCalendar(), Color.BLACK);
    private AdapterWithText mPressureAdapterWithText;// = new AdapterWithText(mDataSource.getDataPressure(), Color.BLACK);
    private AdapterWithText mWindAdapterWithText;// = new AdapterWithText(mDataSource.getDataWind(), Color.BLACK);
    private AdapterWithText mWetAdapterWithText;// = new AdapterWithText(mDataSource.getDataWet(), Color.BLACK);

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.details_fragment, container, false);

        SettingsViewModel model = ViewModelProviders.of(getActivity()).get(SettingsViewModel.class);


        model.getTemp().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String value) {
//                mDataSource.setTemperatureMeasures(value);
            }
        });

        model.getPressure().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String value) {
//                mDataSource.setPressureMeasures(value);
            }
        });

        model.getWind().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String value) {
//                mDataSource.setWindMeasures(value);
            }
        });

        createRecycler(view, R.id.calendar, mCalendarAdapterWithText);
        createRecycler(view, R.id.pressure, mPressureAdapterWithText);
        createRecycler(view, R.id.wind,  mWindAdapterWithText);
        createRecycler(view, R.id.wet,  mWetAdapterWithText);
        createRecycler(view, R.id.daily_weather, mWeatherAdapter);

        ImageButton arrow_back = view.findViewById(R.id.arrow_back_details);
        arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenMainFragment();
            }
        });

        return view;
    }

    private void OpenMainFragment(){

        MainFragment mainFragment = new MainFragment();
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, mainFragment)
                .commit();
    }

    private void createRecycler(@NonNull View view, int recyclerId, @Nullable RecyclerView.Adapter adapter) {
        final RecyclerView recyclerView = view.findViewById(recyclerId);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

}
package com.example.myapplication.view.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.SearchEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.HolderItem;
import com.example.myapplication.model.Settings;
import com.example.myapplication.model.Weather;
import com.example.myapplication.view.search.SearchFragment;
import com.example.myapplication.view.settings.SettingsFragment;
import com.example.myapplication.viewModel.DataViewModel;

import java.util.List;

public class MainFragment extends Fragment implements LifecycleOwner {
    private DataViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //mainFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false);
        //mainFragmentBinding.getRoot();
        final View view = inflater.inflate(R.layout.main_fragment, container, false);
        viewModel = ViewModelProviders.of(getActivity()).get(DataViewModel.class);
        final RecyclerView recyclerViewText = view.findViewById(R.id.daily_list);
        final LinearLayoutManager layoutManagerText = new LinearLayoutManager(getContext());



        layoutManagerText.setOrientation(RecyclerView.HORIZONTAL);
        recyclerViewText.setLayoutManager(layoutManagerText);
        recyclerViewText.setAdapter(viewModel.weatherAdapter1);


        final RecyclerView recyclerViewHour = view.findViewById(R.id.hourly_list);
        final LinearLayoutManager layoutManagerHour = new LinearLayoutManager(getContext());
        layoutManagerHour.setOrientation(RecyclerView.HORIZONTAL);
        recyclerViewHour.setLayoutManager(layoutManagerHour);
        recyclerViewHour.setAdapter(viewModel.weatherAdapter2);

        //viewModel.setOnClickItemListener(getActivity());

        //observeViewModel(viewModel);

        return view;
    }
/*
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(DataViewModel.class);


        final RecyclerView recyclerViewText = view.findViewById(R.id.daily_list);
        final LinearLayoutManager layoutManagerText = new LinearLayoutManager(getContext());



        layoutManagerText.setOrientation(RecyclerView.HORIZONTAL);
        recyclerViewText.setLayoutManager(layoutManagerText);
        recyclerViewText.setAdapter(viewModel.weatherAdapter1);


        final RecyclerView recyclerViewHour = view.findViewById(R.id.hourly_list);
        final LinearLayoutManager layoutManagerHour = new LinearLayoutManager(getContext());
        layoutManagerHour.setOrientation(RecyclerView.HORIZONTAL);
        recyclerViewHour.setLayoutManager(layoutManagerHour);
        recyclerViewHour.setAdapter(viewModel.weatherAdapter2);

        viewModel.setOnClickItemListener(getActivity());

        observeViewModel(viewModel);
    }

 */

    private void observeViewModel(final DataViewModel viewModel) {
        viewModel.getTodayWeatherObservable().observe(this, new Observer<Weather>() {
            @Override
            public void onChanged(@Nullable Weather weather) {
                if (weather != null) {
                    viewModel.setWeather(weather);
                }
            }
        });

        viewModel.getSettingsObservable().observe(this, new Observer<Settings>() {
            @Override
            public void onChanged(@Nullable Settings settings) {
                if (settings != null) {
                    viewModel.setSettings(settings);
                }
            }
        });

        //viewModel.get5DaysDataList();
        viewModel.getHolderItemObservable1().observe(this, new Observer<List<HolderItem>>() {
            @Override
            public void onChanged(List<HolderItem> holderItemList) {
                viewModel.setHolderItemsInAdapter1(holderItemList);
            }
        });

//        viewModel.getHourlyDataList();
        viewModel.getHolderItemObservable2().observe(this, new Observer<List<HolderItem>>() {
            @Override
            public void onChanged(List<HolderItem> holderItemList) {
                viewModel.setHolderItemsInAdapter2(holderItemList);
            }
        });
    }



    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        ImageView settingsIcon = view.findViewById(R.id.main_setting);
        settingsIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSettingsFragment();
            }
        });

        ImageButton searchButton = view.findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSearchFragment();
            }
        });

        viewModel = ViewModelProviders.of(getActivity()).get(DataViewModel.class);
        observeViewModel(viewModel);
    }

    private void openSettingsFragment() {
        final SettingsFragment settingsFragment = new SettingsFragment();

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, settingsFragment)
                .addToBackStack(null)
                .commit();
    }

    private void openSearchFragment(){
        final SearchFragment searchFragment = new SearchFragment();
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, searchFragment)
                .addToBackStack(null)
                .commit();
    }

}

package com.example.myapplication.view.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.databinding.MainFragmentBinding;
import com.example.myapplication.model.HolderItem;
import com.example.myapplication.model.Settings;
import com.example.myapplication.model.Weather;
import com.example.myapplication.view.settings.SettingsFragment;
import com.example.myapplication.viewModel.DataViewModel;

import java.util.List;

public class MainFragment extends Fragment implements LifecycleOwner {
    private MainFragmentBinding mainFragmentBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mainFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false);

        return mainFragmentBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final DataViewModel viewModel =
                ViewModelProviders.of(this).get(DataViewModel.class);
        mainFragmentBinding.setDataViewModel(viewModel);


        View view = mainFragmentBinding.getRoot();

        final RecyclerView recyclerViewText = view.findViewById(R.id.list);
        final LinearLayoutManager layoutManagerText = new LinearLayoutManager(getContext());

        viewModel.setOnClickItemListener(getActivity());

        layoutManagerText.setOrientation(RecyclerView.HORIZONTAL);
        recyclerViewText.setLayoutManager(layoutManagerText);
        recyclerViewText.setAdapter(viewModel.getWeatherAdapter());

        observeViewModel(viewModel);
    }

    private void observeViewModel(final DataViewModel viewModel) {
        viewModel.getTodayWeatherObservable().observe(this, new Observer<Weather>() {
            @Override
            public void onChanged(@Nullable Weather weather) {
                if (weather != null) {
                    viewModel.setTodayWeather(weather);
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


        viewModel.getConstDataList();
        viewModel.getHolderItemObservable().observe(this, new Observer<List<HolderItem>>() {
            @Override
            public void onChanged(List<HolderItem> holderItemList) {
                viewModel.setHolderItemsInAdapter(holderItemList);
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

        final DataViewModel viewModel =
                ViewModelProviders.of(this).get(DataViewModel.class);
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

}

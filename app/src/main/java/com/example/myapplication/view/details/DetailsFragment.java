package com.example.myapplication.view.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.databinding.DetailsFragmentBinding;
import com.example.myapplication.model.HolderItem;
import com.example.myapplication.model.Settings;
import com.example.myapplication.model.TodayWeather;
import com.example.myapplication.view.main.MainFragment;
import com.example.myapplication.viewModel.DataViewModel;

import java.util.List;

public class DetailsFragment extends Fragment {
    private DetailsFragmentBinding detailsFragmentBinding;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        detailsFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.details_fragment, container, false);

        final View view = detailsFragmentBinding.getRoot();

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

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final DataViewModel viewModel =
                ViewModelProviders.of(this).get(DataViewModel.class);
        detailsFragmentBinding.setDataViewModel(viewModel);


        View view = detailsFragmentBinding.getRoot();

        final RecyclerView recyclerViewText = view.findViewById(R.id.calendar);
        final LinearLayoutManager layoutManagerText = new LinearLayoutManager(getContext());

        viewModel.setOnClickItemListener(getActivity());

        layoutManagerText.setOrientation(RecyclerView.HORIZONTAL);
        recyclerViewText.setLayoutManager(layoutManagerText);
        recyclerViewText.setAdapter(viewModel.getWeatherAdapter());

        observeViewModel(viewModel);
    }

    private void observeViewModel(final DataViewModel viewModel) {
        viewModel.getTodayWeatherObservable().observe(this, new Observer<TodayWeather>() {
            @Override
            public void onChanged(@Nullable TodayWeather todayWeather) {
                if (todayWeather != null) {
                    viewModel.setTodayWeather(todayWeather);
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
        LiveData<List<HolderItem>> a = viewModel.getHolderItemObservable();
        a.observe(this, new Observer<List<HolderItem>>() {
            @Override
            public void onChanged(List<HolderItem> holderItemList) {
                viewModel.setHolderItemsInAdapter(holderItemList);
            }
        });
    }
}

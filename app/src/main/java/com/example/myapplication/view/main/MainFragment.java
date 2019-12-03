package com.example.myapplication.view.main;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
//import com.example.myapplication.model.DataSource;
import com.example.myapplication.databinding.MainFragmentBinding;
import com.example.myapplication.adapter.AdapterWeather;
import com.example.myapplication.adapter.AdapterWithText;
import com.example.myapplication.view.settings.SettingsFragment;
import com.example.myapplication.viewModel.DataTextViewModel;
import com.example.myapplication.viewModel.DataWeatherViewModel;
import com.example.myapplication.viewModel.SettingsViewModel;

public class MainFragment extends Fragment implements AdapterWeather.OnItemClickListener  {
//    private DrawerLayout mDrawerLayout;
    private DataTextViewModel dataTextViewModel;
    private DataWeatherViewModel dataWeatherViewModel;


//    private static final String TEMPERATURE = "+29 ";
//    private static final String[] WEATHER_STATUS = {"Пасмурно", "Небольшая облачность", "Сильный дождь", "Туман", "Дождь", "Снег", "Облачно", "Безоблачно", "Гроза"};
//    private static final String WET = "Влажность: 29%";
//    private static final String PRESSURE = "Давление: 27 ";
//    private static final String WIND = "Сила ветра: 10 ";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupBindings(savedInstanceState);
    }

    private void setupBindings(Bundle savedInstanceState) {
        MainFragmentBinding mainFragmentBinding = DataBindingUtil.setContentView(getActivity(), R.layout.main_fragment);
        dataTextViewModel = ViewModelProviders.of(this).get(DataTextViewModel.class);
        if (savedInstanceState == null) {
            dataTextViewModel.init();
        }
        mainFragmentBinding.setDataTextViewModel(dataTextViewModel);

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.main_fragment, container, false);

        MainFragmentBinding mainFragmentBinding = DataBindingUtil.inflate(
                inflater, R.layout.main_fragment, container, false);

        dataTextViewModel = ViewModelProviders.of(getActivity()).get(DataTextViewModel.class);
        dataWeatherViewModel = ViewModelProviders.of(getActivity()).get(DataWeatherViewModel.class);

        mainFragmentBinding.invalidateAll();


        final RecyclerView recyclerViewText = view.findViewById(R.id.text_list);
        final LinearLayoutManager layoutManagerText = new LinearLayoutManager(getContext());

        mAdapterWithText = new AdapterWithText(???, Color.WHITE);
        layoutManagerText.setOrientation(RecyclerView.HORIZONTAL);
        recyclerViewText.setLayoutManager(layoutManagerText);
        recyclerViewText.setAdapter(mAdapterWithText);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onItemClick() {
        if (getActivity() == null || !(getActivity() instanceof AdapterWeather.OnItemClickListener)) {
            return;
        }
        ((AdapterWeather.OnItemClickListener) getActivity()).onItemClick();
    }
}



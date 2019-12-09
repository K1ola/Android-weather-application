package com.example.myapplication.view.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.R;
import com.example.myapplication.databinding.SettingsFragmentBinding;
import com.example.myapplication.model.Settings;
import com.example.myapplication.view.main.MainFragment;
import com.example.myapplication.viewModel.DataViewModel;

public class SettingsFragment extends Fragment {
    private SettingsFragmentBinding settingsFragmentBinding;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        settingsFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.settings_fragment, container, false);
        final View view = settingsFragmentBinding.getRoot();
//        temperatureState = loadData(temperatureKey);
//        pressureState = loadData(pressureKey);
//        windState = loadData(windKey);
//        final View view = inflater.inflate(R.layout.settings_fragment, container, false);
//        model = ViewModelProviders.of(getActivity()).get(SettingsViewModel.class);
        setSwitchHandler(view, R.id.switch_temperature);
        setSwitchHandler(view, R.id.switch_pressure);
        setSwitchHandler(view, R.id.switch_wind);

        ImageButton arrow_back = view.findViewById(R.id.arrow_back);
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final DataViewModel viewModel =
                ViewModelProviders.of(getActivity()).get(DataViewModel.class);

        settingsFragmentBinding.setDataViewModel(viewModel);

        observeViewModel(viewModel);
    }

    private void observeViewModel(final DataViewModel viewModel) {
        viewModel.getSettingsObservable().observe(getActivity(), new Observer<Settings>() {
            @Override
            public void onChanged(@Nullable Settings settings) {
                if (settings != null) {
                    //…
                    viewModel.setSettings(settings);
                }
            }
        });
    }

    private void setSwitchHandler(View view, int idSwitch) {
        Switch s = view.findViewById(idSwitch);
//        s.setChecked(state);
        if (s != null) {
            s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    String switchElem = getResources().getResourceEntryName(buttonView.getId());

                    Boolean currentTemperatureMeasure = false;
                    Boolean currentPressureMeasure = false;
                    Boolean currentWindMeasure = false;

                    if (switchElem.equals("switch_temperature")) {
                        currentTemperatureMeasure = isChecked;
                    }
                    if (switchElem.equals("switch_pressure")) {
                        currentPressureMeasure = isChecked;
                    }
                    if (switchElem.equals("switch_wind")) {
                        currentWindMeasure = isChecked;
                    }


                    final DataViewModel viewModel =
                            ViewModelProviders.of(getActivity()).get(DataViewModel.class);
                    final Boolean finalCurrentTemperatureMeasure = currentTemperatureMeasure;
                    final Boolean finalCurrentPressureMeasure = currentPressureMeasure;
                    final Boolean finalCurrentWindMeasure = currentWindMeasure;
                    viewModel.getSettingsObservable().observe(getActivity(), new Observer<Settings>() {
                        @Override
                        public void onChanged(@Nullable Settings settings) {
                            if (settings != null) {
                                settings.isCelsius = finalCurrentTemperatureMeasure;
                                settings.isHpa = finalCurrentPressureMeasure;
                                settings.isMeters = finalCurrentWindMeasure;

                                viewModel.setSettings(settings);
                            }
                        }
                    });
                }
            });
        }
    }

    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//        String switchElem = getResources().getResourceEntryName(buttonView.getId());
//
//        Boolean currentTemperatureMeasure = false;
//        Boolean currentPressureMeasure = false;
//        Boolean currentWindMeasure = false;
//
//        switch (switchElem) {
//            case "switch_temperature":
////                temperatureState = isChecked;
//                currentTemperatureMeasure = isChecked;
////
////                saveData(temperatureState, temperatureKey);
//            case "switch_pressure":
////                pressureState = isChecked;
//
//                currentPressureMeasure = isChecked;
////
////                saveData(pressureState, pressureKey);
//            case "switch_wind":
//                currentWindMeasure = isChecked;
////                windState = isChecked;
////
////                saveData(windState, windKey);
//                break;
//            default:
//                throw new IllegalStateException("Unexpected value: " + switchElem);
//        }
//
//        final DataViewModel viewModel =
//                ViewModelProviders.of(this).get(DataViewModel.class);
//        final Boolean finalCurrentTemperatureMeasure = currentTemperatureMeasure;
//        final Boolean finalCurrentPressureMeasure = currentPressureMeasure;
//        final Boolean finalCurrentWindMeasure = currentWindMeasure;
//        viewModel.getSettingsObservable().observe(this, new Observer<Settings>() {
//            @Override
//            public void onChanged(@Nullable Settings settings) {
//                if (settings != null) {
//                    settings.isCelsius = finalCurrentTemperatureMeasure;
//                    settings.isHpa = finalCurrentPressureMeasure;
//                    settings.isMeters = finalCurrentWindMeasure;
//
//                    viewModel.setSettings(settings);
//                }
//            }
//        });
    }

    private void saveData(boolean flag, String key) {
//        sPref = getActivity().getPreferences(MODE_PRIVATE);
//        SharedPreferences.Editor ed = sPref.edit();
//        ed.putBoolean(key, flag);
//        ed.apply();
    }

//    private boolean loadData(String key) {
//        sPref = getActivity().getPreferences(MODE_PRIVATE);
//        return sPref.getBoolean(key, false);
//    }

}

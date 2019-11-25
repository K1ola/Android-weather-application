package com.example.myapplication.presentation.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.R;
import com.example.myapplication.viewModels.SettingsViewModel;

import static android.content.Context.MODE_PRIVATE;

public class SettingsFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {
    private SettingsViewModel model;
    private boolean temperatureState = false;
    private boolean pressureState = false;
    private boolean windState = false;
    private SharedPreferences sPref;

    private final String CELSIUS = "°C";
    private final String FAHRENHEIT = "°F";

    private final String MM_HG = "мм рт.ст.";
    private final String HPA = "гПа";

    private final String METERS_PER_SECOND ="м/с";
    private final String HOURS_PER_SECOND ="км/ч";

    private final String temperatureKey = "Temperature";
    private final String pressureKey = "Pressure";
    private final String windKey = "Wind";

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        temperatureState = loadData(temperatureKey);
        pressureState = loadData(pressureKey);
        windState = loadData(windKey);
        final View view = inflater.inflate(R.layout.settings_fragment, container, false);
        model = ViewModelProviders.of(getActivity()).get(SettingsViewModel.class);
        setSwitchHandler(view, R.id.switch_temperature, temperatureState);
        setSwitchHandler(view, R.id.switch_pressure, pressureState);
        setSwitchHandler(view, R.id.switch_wind, windState);

        return view;
    }

    private void setSwitchHandler(View view, int idSwitch, boolean state) {
        Switch s = (Switch) view.findViewById(idSwitch);
        s.setChecked(state);
        if (s != null) {
            s.setOnCheckedChangeListener(this);
        }
    }

    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        String switchElem = getResources().getResourceEntryName(buttonView.getId());
        switch (switchElem) {
            case "switch_temperature":
                temperatureState = isChecked;
                if(isChecked) {
                    model.setTemp(FAHRENHEIT);
                } else {
                    model.setTemp(CELSIUS);
                }

                saveData(temperatureState, temperatureKey);
            case "switch_pressure":
                pressureState = isChecked;
                if(isChecked) {
                    model.setPressure(MM_HG);
                } else {
                    model.setPressure(HPA);
                }

                saveData(pressureState, pressureKey);
            case "switch_wind":
                windState = isChecked;
                if(isChecked) {
                    model.setWind(METERS_PER_SECOND);
                } else {
                    model.setWind(HOURS_PER_SECOND);
                }

                saveData(windState, windKey);
        }
    }

    private void saveData(boolean flag, String key) {
        sPref = getActivity().getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putBoolean(key, flag);
        ed.apply();
    }

    private boolean loadData(String key) {
        sPref = getActivity().getPreferences(MODE_PRIVATE);
        return sPref.getBoolean(key, false);
    }

}
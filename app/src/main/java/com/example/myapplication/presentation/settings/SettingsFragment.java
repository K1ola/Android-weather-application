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
import com.example.myapplication.interactor.SettingsViewModel;

import static android.content.Context.MODE_PRIVATE;

public class SettingsFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {
    private SettingsViewModel model;
    private boolean temperatureState = false;
    private boolean pressureState = false;
    private boolean windState = false;
    SharedPreferences sPref;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        temperatureState = loadData("Temperature");
        pressureState = loadData("Pressure");
        windState = loadData("Wind");
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
                    model.setTemp("°C");
                } else {
                    model.setTemp("°F");
                }

                saveData(temperatureState, "Temperature");
            case "switch_pressure":
                pressureState = isChecked;
                if(isChecked) {
                    model.setPressure("мм рт.ст.");
                } else {
                    model.setPressure("гПа");
                }

                saveData(pressureState, "Pressure");
            case "switch_wind":
                windState = isChecked;
                if(isChecked) {
                    model.setWind("м/с");
                } else {
                    model.setWind("км/с");
                }

                saveData(windState, "Wind");
        }
    }

    void saveData(boolean flag, String key) {
        sPref = getActivity().getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putBoolean(key, flag);
        ed.commit();
    }

    boolean loadData(String key) {
        sPref = getActivity().getPreferences(MODE_PRIVATE);
        boolean savedFlag = sPref.getBoolean(key, false);
        return savedFlag;
    }

}

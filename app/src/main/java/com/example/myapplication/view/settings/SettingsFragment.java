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
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.R;
import com.example.myapplication.databinding.SettingsFragmentBinding;
import com.example.myapplication.model.Settings;
import com.example.myapplication.view.main.MainFragment;
import com.example.myapplication.viewModel.DataViewModel;

public class SettingsFragment extends Fragment {
    private SettingsFragmentBinding settingsFragmentBinding;
    private DataViewModel viewModel;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        settingsFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.settings_fragment, container, false);
        final View view = settingsFragmentBinding.getRoot();

        ImageButton arrowBack = view.findViewById(R.id.arrow_back);
        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMainFragment();
            }
        });

        setSwitchHandler(view, R.id.switch_temperature);
        setSwitchHandler(view, R.id.switch_pressure);
        setSwitchHandler(view, R.id.switch_wind);

        return view;
    }

    private void openMainFragment(){
        final MainFragment mainFragment = new MainFragment();
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, mainFragment)
                .commit();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(DataViewModel.class);
        settingsFragmentBinding.setDataViewModel(viewModel);
    }

    private void setSwitchHandler(View view, int idSwitch) {
        viewModel = ViewModelProviders.of(getActivity()).get(DataViewModel.class);
        Switch s = view.findViewById(idSwitch);
        if (s != null) {
            final Settings currentSettings = viewModel.getSettings().get();
            s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    String switchElemName = getResources().getResourceEntryName(buttonView.getId());

//                    Boolean currentTemperatureMeasure = false;
//                    Boolean currentPressureMeasure = false;
//                    Boolean currentWindMeasure = false;

                    if (switchElemName.equals("switch_temperature")) {
//                        currentTemperatureMeasure = isChecked;
                        currentSettings.isCelsius = isChecked;
                    }
                    if (switchElemName.equals("switch_pressure")) {
//                        currentPressureMeasure = isChecked;
                        currentSettings.isHpa = isChecked;
                    }
                    if (switchElemName.equals("switch_wind")) {
//                        currentWindMeasure = isChecked;
                        currentSettings.isMeters = isChecked;
                    }

                    viewModel.settings.set(currentSettings);

                }
            });
        }
    }
}

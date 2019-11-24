package com.example.myapplication.presentation.settings;

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

public class SettingsFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {
    private SettingsViewModel model;
    private boolean flag = false;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            flag = savedInstanceState.getBoolean("bool");
        }
        final View view = inflater.inflate(R.layout.settings_fragment, container, false);
        model = ViewModelProviders.of(getActivity()).get(SettingsViewModel.class);
        Switch s = (Switch) view.findViewById(R.id.switch1);
        s.setChecked(flag);

        if (s != null) {
            s.setOnCheckedChangeListener(this);
        }


        return view;
    }

    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        flag = isChecked;
        if(isChecked) {
            model.select( "C");
        } else {
            model.select( "F");
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle state) {
        super.onSaveInstanceState(state);
        state.putBoolean("bool", flag);
    }

//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//
//    }

}

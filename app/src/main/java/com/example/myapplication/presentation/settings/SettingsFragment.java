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
    private boolean flag = false;
    SharedPreferences sPref;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        flag = loadData();
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

        saveData(flag);
    }

    void saveData(boolean flag) {
        sPref = getActivity().getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putBoolean("SAVED_TEXT", flag);
        ed.commit();
    }

    boolean loadData() {
        sPref = getActivity().getPreferences(MODE_PRIVATE);
        boolean savedFlag = sPref.getBoolean("SAVED_TEXT", false);
        return savedFlag;
    }

//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//
//    }

}

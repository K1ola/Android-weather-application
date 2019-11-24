package com.example.myapplication.interactor;

import androidx.lifecycle.ViewModel;

public class SettingsViewModel extends ViewModel {
    private String selected = "";

    public void select(String item) {
        selected = item;
    }

    public String getSelected() {
        return selected;
    }
}
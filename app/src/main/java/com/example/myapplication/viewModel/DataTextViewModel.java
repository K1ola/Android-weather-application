package com.example.myapplication.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.R;
import com.example.myapplication.model.DataText;
import com.example.myapplication.adapter.AdapterWithText;

public class DataTextViewModel extends ViewModel {
    private DataText data;
    private AdapterWithText adapterWithText;

    public void init() {
        data = new DataText("Hello", "World");
        adapterWithText = new AdapterWithText(R.id.text_list, this);
    }

    public LiveData<String> getTopData() {
        return data.mTopText;
    }

    public LiveData<String> getBottomData() {
        return data.mBottomText;
    }

    public LiveData<String> getBottomDataMeasure() {
        return data.mBottomTextMeasure;
    }
}

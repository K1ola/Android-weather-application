package com.example.myapplication;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.presentation.common.AdapterWeather;
import com.example.myapplication.presentation.details.DetailsFragment;
import com.example.myapplication.presentation.main.MainFragment;

public class MainActivity extends AppCompatActivity implements AdapterWeather.OnItemClickListener  {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        if (savedInstanceState == null) {
//            SettingsFragment mainFragment = new SettingsFragment();
//
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .add(R.id.container, mainFragment)
//                    .commit();
//        }

        if (savedInstanceState == null) {
            MainFragment mainFragment = new MainFragment();

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, mainFragment)
                    .commit();
        }
    }

    @Override
    public void onItemClick() {
        DetailsFragment detailsFragment = new DetailsFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, detailsFragment)
                .addToBackStack(null)
                .commit();
    }
}
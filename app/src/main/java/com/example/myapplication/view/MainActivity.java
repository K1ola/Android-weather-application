package com.example.myapplication.view;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.view.main.MainFragment;
import com.facebook.stetho.Stetho;

public class MainActivity extends AppCompatActivity  {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Stetho.initializeWithDefaults(this);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            MainFragment mainFragment = new MainFragment();

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, mainFragment)
                    .commit();
        }
    }
}


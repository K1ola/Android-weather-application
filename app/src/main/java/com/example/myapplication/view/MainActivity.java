package com.example.myapplication.view;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
//import com.example.myapplication.model.AdapterWeather;
//import com.example.myapplication.view.details.DetailsFragment;
import com.example.myapplication.view.main.MainFragment;

public class MainActivity extends AppCompatActivity {// implements AdapterWeather.OnItemClickListener  {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            MainFragment mainFragment = new MainFragment();

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, mainFragment)
                    .commit();
        }
    }

//    @Override
//    public void onItemClick() {
//        DetailsFragment detailsFragment = new DetailsFragment();
//
//        getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.container, detailsFragment, "detailsFragment")
//                .addToBackStack("detailsFragment")
//                .commit();
//    }
}
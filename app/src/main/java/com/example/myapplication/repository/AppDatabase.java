package com.example.myapplication.repository;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.myapplication.model.Settings;
import com.example.myapplication.model.Weather;

@Database(entities = {Weather.class, Settings.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract WeatherDao weatherDao();
    public abstract SettingsDao settingsDao();
}

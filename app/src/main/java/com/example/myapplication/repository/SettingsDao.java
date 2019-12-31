package com.example.myapplication.repository;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.myapplication.model.Settings;

@Dao
public interface SettingsDao {
    @Query("SELECT * FROM settings ORDER BY id ASC LIMIT 1")
    Settings getLast();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Settings settings);
}

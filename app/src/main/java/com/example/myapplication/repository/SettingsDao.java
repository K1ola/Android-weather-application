package com.example.myapplication.repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.model.Settings;

import java.util.List;

@Dao
public interface SettingsDao {
    @Query("SELECT * FROM settings")
    LiveData<List<Settings>> getAll();

    @Query("SELECT * FROM settings WHERE id = :id")
    LiveData<Settings> getById(long id);

    @Insert
    void insert(Settings settings);

    @Update
    void update(Settings settings);

    @Delete
    void delete(Settings settings);

}

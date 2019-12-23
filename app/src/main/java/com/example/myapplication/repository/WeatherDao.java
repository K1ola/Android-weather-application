package com.example.myapplication.repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.model.Weather;

import java.util.List;

@Dao
public interface WeatherDao {
    @Query("SELECT * FROM weather")
    LiveData<List<Weather>> getAll();

    @Query("SELECT * FROM weather WHERE id = :id")
    LiveData<Weather> getById(long id);

    @Insert
    void insert(Weather weather);

    @Update
    void update(Weather weather);

    @Delete
    void delete(Weather weather);
}

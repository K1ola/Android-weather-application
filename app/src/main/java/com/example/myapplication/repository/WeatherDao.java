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
    List<Weather> getAll();

    @Query("SELECT * FROM weather WHERE id = :id")
    Weather getById(long id);

    @Query("SELECT * FROM weather ORDER BY ID DESC LIMIT 1")
    Weather getLast();

    @Insert
    void insert(Weather weather);

    @Update
    void update(Weather weather);

    @Delete
    void delete(Weather weather);
}

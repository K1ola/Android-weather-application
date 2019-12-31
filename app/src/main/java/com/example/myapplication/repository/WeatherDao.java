package com.example.myapplication.repository;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.model.Weather;

import java.util.List;

@Dao
public interface WeatherDao {
    @Query("SELECT * FROM weather WHERE weatherType='currently' ORDER BY time DESC LIMIT 1")
    Weather getLastCurrent();

    @Query("SELECT * FROM weather WHERE weatherType='daily' ORDER BY time DESC LIMIT 5")
    List<Weather> getLastDaily();

    @Query("SELECT * FROM weather WHERE weatherType='hourly' ORDER BY time DESC LIMIT 5")
    List<Weather> getLastHourly();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Weather weather);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertList(List<Weather> weather);

    @Delete
    void delete(Weather weather);
}

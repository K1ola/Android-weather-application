package com.example.myapplication.repository;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.example.myapplication.model.Weather;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class API {
    private Network network;
    private Context context;

    public API(Context context) {
        this.context = context;
    }

    @SuppressLint("DefaultLocale")
    public Weather getCurrentWeather(String location) {
        Weather weather = null;
        Coordinates coordinates = getCity(context, location);
        network = new Network(coordinates.latitude, coordinates.longitude);
        try {
            String result = network.execute().get();

            JSONObject root = new JSONObject(result);
            JSONObject currently;

            currently = root.getJSONObject("currently");

            double pressure = currently.getDouble("pressure") * 1;  //HPA
            double humidity = currently.getDouble("humidity") * 100;
            double windSpeed = currently.getDouble("windSpeed") * 1.609;  //HOURS_PER_SECOND
            Timestamp stamp = new Timestamp(currently.getLong("time"));
            Date date = new Date(stamp.getTime());

            weather = new Weather(
                    currently.getString("temperature"),
                    String.format("%.2f", pressure),
                    String.format ("%.0f", humidity),
                    String.format("%.0f", windSpeed),
                    String.valueOf(date),
                    currently.getString("summary"),
                    currently.getString("icon")
            );
        }
        catch (JSONException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
//        catch (TimeoutException e) {
//            e.printStackTrace();
//        }

        return weather;
    }

    private class Coordinates {
        // Location data
        public double latitude;
        public double longitude;
    }

    private Coordinates getCity(Context context, String location) {
        Coordinates coordinates = new Coordinates();
        if (Geocoder.isPresent()) {
            try {
                Geocoder gc = new Geocoder(context);
                Address address = gc.getFromLocationName(location, 1).get(0); // get the found Address Objects
                coordinates.latitude = address.getLatitude();
                coordinates.longitude = address.getLongitude();
            } catch (IOException e) {
                // handle the exception
            }
        }
        return coordinates;
    }
}

package com.example.myapplication.repository;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.example.myapplication.model.Weather;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class API {
    private Network network;
    private Context context;

    public API(Context context) {
        this.context = context;
    }

    public Weather getCurrentWeather(String location) {
        Weather weather = null;
        Coordinates coordinates = getCity(context, location);
        network = new Network(coordinates.latitude, coordinates.longitude);
        try {
            String result = network.execute().get();

            JSONObject root = new JSONObject(result);
            JSONObject currently;

            currently = root.getJSONObject("currently");

            weather = new Weather(
                    currently.getString("temperature"),
                    currently.getString("pressure"),
                    currently.getString("humidity"),
                    currently.getString("windSpeed"),
                    currently.getString("time"),
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

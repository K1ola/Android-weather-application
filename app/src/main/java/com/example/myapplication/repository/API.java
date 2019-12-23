package com.example.myapplication.repository;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.example.myapplication.model.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;

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
            weather.id = currently.getLong("time");
        }
        catch (JSONException |  NullPointerException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
//        catch (TimeoutException e) {
//            e.printStackTrace();
//        }

        return weather;
    }

    @SuppressLint("DefaultLocale")
    public List<Weather> getHourlyWeather(String location) {
        ArrayList<Weather> weeklyForecast = new ArrayList<Weather>();
        weeklyForecast.clear();
        Coordinates coordinates = getCity(context, location);
        network = new Network(coordinates.latitude, coordinates.longitude);
        try {
            String result = network.execute().get();

            JSONObject root = new JSONObject(result);
            JSONObject daily;

            daily = root.getJSONObject("hourly");
            JSONArray forecasts = daily.getJSONArray("data");

            for (int i = 0; i < 5; i++) {
                JSONObject dayObject = forecasts.getJSONObject(i);

                double pressure = dayObject.getDouble("pressure") * 1;  //HPA
                double humidity = dayObject.getDouble("humidity") * 100;
                double windSpeed = dayObject.getDouble("windSpeed") * 1.609;  //HOURS_PER_SECOND

//                Timestamp stamp = new Timestamp(dayObject.getLong("time"));
//                Date date = new Date(stamp.getTime());
                Date date = new Date(dayObject.getLong("time")*1000L);
                // format of the date
                SimpleDateFormat jdf = new SimpleDateFormat("H:mm");
                jdf.setTimeZone(TimeZone.getTimeZone("GMT-4"));
                String java_date = jdf.format(date);

                Weather w = new Weather(
                        dayObject.getString("temperature"),
                        String.format("%.2f", pressure),
                        String.format ("%.0f", humidity),
                        String.format("%.0f", windSpeed),
                        java_date,
                        dayObject.getString("summary"),
                        dayObject.getString("icon")
                );
                w.id = dayObject.getLong("time");

                weeklyForecast.add(w);
            }
        }
        catch (JSONException |  NullPointerException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return weeklyForecast;
    }

    @SuppressLint("DefaultLocale")
    public List<Weather> get5DaysWeather(String location) {
        ArrayList<Weather> weeklyForecast = new ArrayList<Weather>();
        weeklyForecast.clear();
        Coordinates coordinates = getCity(context, location);
        network = new Network(coordinates.latitude, coordinates.longitude);
        try {
            String result = network.execute().get();

            JSONObject root = new JSONObject(result);
            JSONObject daily;

            daily = root.getJSONObject("daily");
            JSONArray forecasts = daily.getJSONArray("data");

            for (int i = 0; i < 5; i++) {
                JSONObject dayObject = forecasts.getJSONObject(i);

                double pressure = dayObject.getDouble("pressure") * 1;  //HPA
                double humidity = dayObject.getDouble("humidity") * 100;
                double windSpeed = dayObject.getDouble("windSpeed") * 1.609;  //HOURS_PER_SECOND

//                Timestamp stamp = new Timestamp(dayObject.getLong("time"));
//                Date date = new Date(stamp.getTime());
                Date date = new Date(dayObject.getLong("time")*1000L);
                // format of the date
                SimpleDateFormat jdf = new SimpleDateFormat("dd MMMM", myDateFormatSymbols);
                jdf.setTimeZone(TimeZone.getTimeZone("GMT-4"));
                String java_date = jdf.format(date);

                Weather w = new Weather(
                        dayObject.getString("temperatureLow"),
                        String.format("%.2f", pressure),
                        String.format ("%.0f", humidity),
                        String.format("%.0f", windSpeed),
                        java_date,
                        dayObject.getString("summary"),
                        dayObject.getString("icon")
                );
                w.id = dayObject.getLong("time");

                weeklyForecast.add(w);
            }
        }
        catch (JSONException |  NullPointerException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return weeklyForecast;
    }

    private static DateFormatSymbols myDateFormatSymbols = new DateFormatSymbols(){

        @Override
        public String[] getMonths() {
            return new String[]{"января", "февраля", "марта", "апреля", "мая", "июня",
                    "июля", "августа", "сентября", "октября", "ноября", "декабря"};
        }

    };

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

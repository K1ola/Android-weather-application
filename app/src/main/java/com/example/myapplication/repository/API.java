package com.example.myapplication.repository;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.myapplication.model.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;
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
        try {
            Coordinates coordinates = getCity(context, location);
            network = new Network(coordinates.latitude, coordinates.longitude);
//            String result = network.execute().get();
            String result = getFileWeatherContent();

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
        catch (JSONException | NullPointerException e) {
            e.printStackTrace();
        }

        return weather;
    }

    @SuppressLint("DefaultLocale")
    public List<Weather> getHourlyWeather(String location) {
        ArrayList<Weather> weeklyForecast = new ArrayList<Weather>();
        weeklyForecast.clear();
        try {
            Coordinates coordinates = getCity(context, location);
            network = new Network(coordinates.latitude, coordinates.longitude);
//            String result = network.execute().get();
            String result = getFileWeatherContent();

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
        catch (JSONException |  NullPointerException e) {
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
//            String result = network.execute().get();
            String result = getFileWeatherContent();

            JSONObject root = new JSONObject(result);
            JSONObject daily;

            daily = root.getJSONObject("daily");
            JSONArray forecasts = daily.getJSONArray("data");

            for (int i = 0; i < 5; i++) {
                JSONObject dayObject = forecasts.getJSONObject(i);

                double pressure = dayObject.getDouble("pressure") * 1;  //HPA
                double humidity = dayObject.getDouble("humidity") * 100;
                double windSpeed = dayObject.getDouble("windSpeed") * 1.609;  //HOURS_PER_SECOND


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
        catch (JSONException |  NullPointerException e) {
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

    private String getFileWeatherContent() {
        String everything = "{  \n" +
                "   \"latitude\":42.3601,\n" +
                "   \"longitude\":-71.0589,\n" +
                "   \"timezone\":\"America/New_York\",\n" +
                "   \"currently\":{  \n" +
                "      \"time\":1528402806,\n" +
                "      \"summary\":\"Mostly Cloudy\",\n" +
                "      \"icon\":\"partly-cloudy-day\",\n" +
                "      \"nearestStormDistance\":2,\n" +
                "      \"nearestStormBearing\":164,\n" +
                "      \"precipIntensity\":0,\n" +
                "      \"precipProbability\":0,\n" +
                "      \"temperature\":70.54,\n" +
                "      \"apparentTemperature\":70.54,\n" +
                "      \"dewPoint\":52.59,\n" +
                "      \"humidity\":0.53,\n" +
                "      \"pressure\":1018.75,\n" +
                "      \"windSpeed\":8.7,\n" +
                "      \"windGust\":12.49,\n" +
                "      \"windBearing\":207,\n" +
                "      \"cloudCover\":0.91,\n" +
                "      \"uvIndex\":2,\n" +
                "      \"visibility\":10,\n" +
                "      \"ozone\":346.01\n" +
                "   },\n" +
                "   \"minutely\":{  \n" +
                "      \"summary\":\"Mostly cloudy for the hour.\",\n" +
                "      \"icon\":\"partly-cloudy-day\",\n" +
                "      \"data\":[  \n" +
                "         {  \n" +
                "            \"time\":1528402800,\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528402860,\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528402920,\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528402980,\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528403040,\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528403100,\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528403160,\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528403220,\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528403280,\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528403340,\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528403400,\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528403460,\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528403520,\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528403580,\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528403640,\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528403700,\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528403760,\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528403820,\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528403880,\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528403940,\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528404000,\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528404060,\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528404120,\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528404180,\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528404240,\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528404300,\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528404360,\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528404420,\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528404480,\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528404540,\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528404600,\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528404660,\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528404720,\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528404780,\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528404840,\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528404900,\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528404960,\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528405020,\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528405080,\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528405140,\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528405200,\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528405260,\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528405320,\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528405380,\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528405440,\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528405500,\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528405560,\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528405620,\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528405680,\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528405740,\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528405800,\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528405860,\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528405920,\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528405980,\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528406040,\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528406100,\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528406160,\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528406220,\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528406280,\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528406340,\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528406400,\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0\n" +
                "         }\n" +
                "      ]\n" +
                "   },\n" +
                "   \"hourly\":{  \n" +
                "      \"summary\":\"Partly cloudy until tomorrow morning.\",\n" +
                "      \"icon\":\"partly-cloudy-night\",\n" +
                "      \"data\":[  \n" +
                "         {  \n" +
                "            \"time\":1528401600,\n" +
                "            \"summary\":\"Overcast\",\n" +
                "            \"icon\":\"cloudy\",\n" +
                "            \"precipIntensity\":0.0012,\n" +
                "            \"precipProbability\":0.05,\n" +
                "            \"precipType\":\"rain\",\n" +
                "            \"temperature\":70.52,\n" +
                "            \"apparentTemperature\":70.52,\n" +
                "            \"dewPoint\":52.87,\n" +
                "            \"humidity\":0.54,\n" +
                "            \"pressure\":1018.76,\n" +
                "            \"windSpeed\":8.42,\n" +
                "            \"windGust\":11.71,\n" +
                "            \"windBearing\":208,\n" +
                "            \"cloudCover\":0.94,\n" +
                "            \"uvIndex\":2,\n" +
                "            \"visibility\":10,\n" +
                "            \"ozone\":345.71\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528405200,\n" +
                "            \"summary\":\"Mostly Cloudy\",\n" +
                "            \"icon\":\"partly-cloudy-day\",\n" +
                "            \"precipIntensity\":0.0003,\n" +
                "            \"precipProbability\":0.04,\n" +
                "            \"precipType\":\"rain\",\n" +
                "            \"temperature\":70.58,\n" +
                "            \"apparentTemperature\":70.58,\n" +
                "            \"dewPoint\":52.01,\n" +
                "            \"humidity\":0.52,\n" +
                "            \"pressure\":1018.73,\n" +
                "            \"windSpeed\":9.28,\n" +
                "            \"windGust\":14.04,\n" +
                "            \"windBearing\":204,\n" +
                "            \"cloudCover\":0.85,\n" +
                "            \"uvIndex\":1,\n" +
                "            \"visibility\":10,\n" +
                "            \"ozone\":346.61\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528408800,\n" +
                "            \"summary\":\"Mostly Cloudy\",\n" +
                "            \"icon\":\"partly-cloudy-day\",\n" +
                "            \"precipIntensity\":0.0002,\n" +
                "            \"precipProbability\":0.02,\n" +
                "            \"precipType\":\"rain\",\n" +
                "            \"temperature\":69.55,\n" +
                "            \"apparentTemperature\":69.55,\n" +
                "            \"dewPoint\":51.04,\n" +
                "            \"humidity\":0.52,\n" +
                "            \"pressure\":1018.82,\n" +
                "            \"windSpeed\":10.03,\n" +
                "            \"windGust\":16.03,\n" +
                "            \"windBearing\":203,\n" +
                "            \"cloudCover\":0.76,\n" +
                "            \"uvIndex\":1,\n" +
                "            \"visibility\":10,\n" +
                "            \"ozone\":348.47\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528412400,\n" +
                "            \"summary\":\"Partly Cloudy\",\n" +
                "            \"icon\":\"partly-cloudy-day\",\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0,\n" +
                "            \"temperature\":67.79,\n" +
                "            \"apparentTemperature\":67.79,\n" +
                "            \"dewPoint\":50.56,\n" +
                "            \"humidity\":0.54,\n" +
                "            \"pressure\":1019.02,\n" +
                "            \"windSpeed\":10.06,\n" +
                "            \"windGust\":17.8,\n" +
                "            \"windBearing\":203,\n" +
                "            \"cloudCover\":0.56,\n" +
                "            \"uvIndex\":0,\n" +
                "            \"visibility\":10,\n" +
                "            \"ozone\":350.61\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528416000,\n" +
                "            \"summary\":\"Partly Cloudy\",\n" +
                "            \"icon\":\"partly-cloudy-day\",\n" +
                "            \"precipIntensity\":0.0004,\n" +
                "            \"precipProbability\":0.01,\n" +
                "            \"precipType\":\"rain\",\n" +
                "            \"temperature\":65.39,\n" +
                "            \"apparentTemperature\":65.39,\n" +
                "            \"dewPoint\":50.3,\n" +
                "            \"humidity\":0.58,\n" +
                "            \"pressure\":1019.43,\n" +
                "            \"windSpeed\":9.6,\n" +
                "            \"windGust\":17.31,\n" +
                "            \"windBearing\":199,\n" +
                "            \"cloudCover\":0.49,\n" +
                "            \"uvIndex\":0,\n" +
                "            \"visibility\":10,\n" +
                "            \"ozone\":351.83\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528419600,\n" +
                "            \"summary\":\"Partly Cloudy\",\n" +
                "            \"icon\":\"partly-cloudy-night\",\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0,\n" +
                "            \"temperature\":62.96,\n" +
                "            \"apparentTemperature\":62.96,\n" +
                "            \"dewPoint\":50.05,\n" +
                "            \"humidity\":0.63,\n" +
                "            \"pressure\":1019.75,\n" +
                "            \"windSpeed\":9.23,\n" +
                "            \"windGust\":18.5,\n" +
                "            \"windBearing\":200,\n" +
                "            \"cloudCover\":0.39,\n" +
                "            \"uvIndex\":0,\n" +
                "            \"visibility\":10,\n" +
                "            \"ozone\":351.53\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528423200,\n" +
                "            \"summary\":\"Partly Cloudy\",\n" +
                "            \"icon\":\"partly-cloudy-night\",\n" +
                "            \"precipIntensity\":0.001,\n" +
                "            \"precipProbability\":0.02,\n" +
                "            \"precipType\":\"rain\",\n" +
                "            \"temperature\":61.1,\n" +
                "            \"apparentTemperature\":61.1,\n" +
                "            \"dewPoint\":50.02,\n" +
                "            \"humidity\":0.67,\n" +
                "            \"pressure\":1020.26,\n" +
                "            \"windSpeed\":8.84,\n" +
                "            \"windGust\":18.77,\n" +
                "            \"windBearing\":204,\n" +
                "            \"cloudCover\":0.3,\n" +
                "            \"uvIndex\":0,\n" +
                "            \"visibility\":10,\n" +
                "            \"ozone\":350.33\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528426800,\n" +
                "            \"summary\":\"Partly Cloudy\",\n" +
                "            \"icon\":\"partly-cloudy-night\",\n" +
                "            \"precipIntensity\":0.0004,\n" +
                "            \"precipProbability\":0.02,\n" +
                "            \"precipType\":\"rain\",\n" +
                "            \"temperature\":59.77,\n" +
                "            \"apparentTemperature\":59.77,\n" +
                "            \"dewPoint\":50.14,\n" +
                "            \"humidity\":0.7,\n" +
                "            \"pressure\":1020.61,\n" +
                "            \"windSpeed\":8.42,\n" +
                "            \"windGust\":18.63,\n" +
                "            \"windBearing\":208,\n" +
                "            \"cloudCover\":0.25,\n" +
                "            \"uvIndex\":0,\n" +
                "            \"visibility\":10,\n" +
                "            \"ozone\":348.74\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528430400,\n" +
                "            \"summary\":\"Clear\",\n" +
                "            \"icon\":\"clear-night\",\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0,\n" +
                "            \"temperature\":58.79,\n" +
                "            \"apparentTemperature\":58.79,\n" +
                "            \"dewPoint\":50.36,\n" +
                "            \"humidity\":0.74,\n" +
                "            \"pressure\":1020.66,\n" +
                "            \"windSpeed\":8.13,\n" +
                "            \"windGust\":18.2,\n" +
                "            \"windBearing\":211,\n" +
                "            \"cloudCover\":0.24,\n" +
                "            \"uvIndex\":0,\n" +
                "            \"visibility\":10,\n" +
                "            \"ozone\":346.63\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528434000,\n" +
                "            \"summary\":\"Clear\",\n" +
                "            \"icon\":\"clear-night\",\n" +
                "            \"precipIntensity\":0.0011,\n" +
                "            \"precipProbability\":0.02,\n" +
                "            \"precipType\":\"rain\",\n" +
                "            \"temperature\":58.05,\n" +
                "            \"apparentTemperature\":58.05,\n" +
                "            \"dewPoint\":50.52,\n" +
                "            \"humidity\":0.76,\n" +
                "            \"pressure\":1020.35,\n" +
                "            \"windSpeed\":7.9,\n" +
                "            \"windGust\":17.75,\n" +
                "            \"windBearing\":215,\n" +
                "            \"cloudCover\":0.24,\n" +
                "            \"uvIndex\":0,\n" +
                "            \"visibility\":10,\n" +
                "            \"ozone\":344.19\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528437600,\n" +
                "            \"summary\":\"Partly Cloudy\",\n" +
                "            \"icon\":\"partly-cloudy-night\",\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0,\n" +
                "            \"temperature\":57.52,\n" +
                "            \"apparentTemperature\":57.52,\n" +
                "            \"dewPoint\":50.58,\n" +
                "            \"humidity\":0.78,\n" +
                "            \"pressure\":1020.07,\n" +
                "            \"windSpeed\":7.73,\n" +
                "            \"windGust\":17.29,\n" +
                "            \"windBearing\":216,\n" +
                "            \"cloudCover\":0.32,\n" +
                "            \"uvIndex\":0,\n" +
                "            \"visibility\":10,\n" +
                "            \"ozone\":342.58\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528441200,\n" +
                "            \"summary\":\"Partly Cloudy\",\n" +
                "            \"icon\":\"partly-cloudy-night\",\n" +
                "            \"precipIntensity\":0.0014,\n" +
                "            \"precipProbability\":0.02,\n" +
                "            \"precipType\":\"rain\",\n" +
                "            \"temperature\":57.07,\n" +
                "            \"apparentTemperature\":57.07,\n" +
                "            \"dewPoint\":50.52,\n" +
                "            \"humidity\":0.79,\n" +
                "            \"pressure\":1020.05,\n" +
                "            \"windSpeed\":7.68,\n" +
                "            \"windGust\":17.41,\n" +
                "            \"windBearing\":220,\n" +
                "            \"cloudCover\":0.32,\n" +
                "            \"uvIndex\":0,\n" +
                "            \"visibility\":10,\n" +
                "            \"ozone\":342.54\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528444800,\n" +
                "            \"summary\":\"Partly Cloudy\",\n" +
                "            \"icon\":\"partly-cloudy-night\",\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0,\n" +
                "            \"temperature\":56.7,\n" +
                "            \"apparentTemperature\":56.7,\n" +
                "            \"dewPoint\":50.37,\n" +
                "            \"humidity\":0.79,\n" +
                "            \"pressure\":1020.1,\n" +
                "            \"windSpeed\":7.55,\n" +
                "            \"windGust\":17.5,\n" +
                "            \"windBearing\":226,\n" +
                "            \"cloudCover\":0.3,\n" +
                "            \"uvIndex\":0,\n" +
                "            \"visibility\":10,\n" +
                "            \"ozone\":343.33\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528448400,\n" +
                "            \"summary\":\"Partly Cloudy\",\n" +
                "            \"icon\":\"partly-cloudy-night\",\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0,\n" +
                "            \"temperature\":56.68,\n" +
                "            \"apparentTemperature\":56.68,\n" +
                "            \"dewPoint\":50.44,\n" +
                "            \"humidity\":0.8,\n" +
                "            \"pressure\":1020.22,\n" +
                "            \"windSpeed\":7.36,\n" +
                "            \"windGust\":17.23,\n" +
                "            \"windBearing\":230,\n" +
                "            \"cloudCover\":0.26,\n" +
                "            \"uvIndex\":0,\n" +
                "            \"visibility\":10,\n" +
                "            \"ozone\":343.9\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528452000,\n" +
                "            \"summary\":\"Clear\",\n" +
                "            \"icon\":\"clear-day\",\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0,\n" +
                "            \"temperature\":57.35,\n" +
                "            \"apparentTemperature\":57.35,\n" +
                "            \"dewPoint\":52,\n" +
                "            \"humidity\":0.82,\n" +
                "            \"pressure\":1020.39,\n" +
                "            \"windSpeed\":7.17,\n" +
                "            \"windGust\":16.92,\n" +
                "            \"windBearing\":237,\n" +
                "            \"cloudCover\":0.14,\n" +
                "            \"uvIndex\":0,\n" +
                "            \"visibility\":10,\n" +
                "            \"ozone\":343.94\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528455600,\n" +
                "            \"summary\":\"Clear\",\n" +
                "            \"icon\":\"clear-day\",\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0,\n" +
                "            \"temperature\":59.98,\n" +
                "            \"apparentTemperature\":59.98,\n" +
                "            \"dewPoint\":52.93,\n" +
                "            \"humidity\":0.78,\n" +
                "            \"pressure\":1020.59,\n" +
                "            \"windSpeed\":6.62,\n" +
                "            \"windGust\":13.62,\n" +
                "            \"windBearing\":259,\n" +
                "            \"cloudCover\":0.16,\n" +
                "            \"uvIndex\":1,\n" +
                "            \"visibility\":10,\n" +
                "            \"ozone\":343.71\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528459200,\n" +
                "            \"summary\":\"Clear\",\n" +
                "            \"icon\":\"clear-day\",\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0,\n" +
                "            \"temperature\":63.06,\n" +
                "            \"apparentTemperature\":63.06,\n" +
                "            \"dewPoint\":53.65,\n" +
                "            \"humidity\":0.71,\n" +
                "            \"pressure\":1020.71,\n" +
                "            \"windSpeed\":6.15,\n" +
                "            \"windGust\":11.08,\n" +
                "            \"windBearing\":252,\n" +
                "            \"cloudCover\":0.1,\n" +
                "            \"uvIndex\":1,\n" +
                "            \"visibility\":10,\n" +
                "            \"ozone\":343.2\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528462800,\n" +
                "            \"summary\":\"Clear\",\n" +
                "            \"icon\":\"clear-day\",\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0,\n" +
                "            \"temperature\":66.96,\n" +
                "            \"apparentTemperature\":66.96,\n" +
                "            \"dewPoint\":53.92,\n" +
                "            \"humidity\":0.63,\n" +
                "            \"pressure\":1020.64,\n" +
                "            \"windSpeed\":5.56,\n" +
                "            \"windGust\":9.85,\n" +
                "            \"windBearing\":221,\n" +
                "            \"cloudCover\":0.09,\n" +
                "            \"uvIndex\":3,\n" +
                "            \"visibility\":10,\n" +
                "            \"ozone\":342.19\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528466400,\n" +
                "            \"summary\":\"Clear\",\n" +
                "            \"icon\":\"clear-day\",\n" +
                "            \"precipIntensity\":0.0002,\n" +
                "            \"precipProbability\":0.01,\n" +
                "            \"precipType\":\"rain\",\n" +
                "            \"temperature\":70.7,\n" +
                "            \"apparentTemperature\":70.7,\n" +
                "            \"dewPoint\":53.91,\n" +
                "            \"humidity\":0.55,\n" +
                "            \"pressure\":1020.46,\n" +
                "            \"windSpeed\":4.94,\n" +
                "            \"windGust\":9.27,\n" +
                "            \"windBearing\":307,\n" +
                "            \"cloudCover\":0.09,\n" +
                "            \"uvIndex\":4,\n" +
                "            \"visibility\":10,\n" +
                "            \"ozone\":340.92\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528470000,\n" +
                "            \"summary\":\"Clear\",\n" +
                "            \"icon\":\"clear-day\",\n" +
                "            \"precipIntensity\":0.0002,\n" +
                "            \"precipProbability\":0.01,\n" +
                "            \"precipType\":\"rain\",\n" +
                "            \"temperature\":74.04,\n" +
                "            \"apparentTemperature\":74.04,\n" +
                "            \"dewPoint\":53.87,\n" +
                "            \"humidity\":0.49,\n" +
                "            \"pressure\":1020.11,\n" +
                "            \"windSpeed\":5.13,\n" +
                "            \"windGust\":9.31,\n" +
                "            \"windBearing\":278,\n" +
                "            \"cloudCover\":0.24,\n" +
                "            \"uvIndex\":6,\n" +
                "            \"visibility\":10,\n" +
                "            \"ozone\":339.79\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528473600,\n" +
                "            \"summary\":\"Partly Cloudy\",\n" +
                "            \"icon\":\"partly-cloudy-day\",\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0,\n" +
                "            \"temperature\":76.23,\n" +
                "            \"apparentTemperature\":76.23,\n" +
                "            \"dewPoint\":53.98,\n" +
                "            \"humidity\":0.46,\n" +
                "            \"pressure\":1019.72,\n" +
                "            \"windSpeed\":5.39,\n" +
                "            \"windGust\":10.49,\n" +
                "            \"windBearing\":277,\n" +
                "            \"cloudCover\":0.26,\n" +
                "            \"uvIndex\":8,\n" +
                "            \"visibility\":10,\n" +
                "            \"ozone\":338.91\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528477200,\n" +
                "            \"summary\":\"Partly Cloudy\",\n" +
                "            \"icon\":\"partly-cloudy-day\",\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0,\n" +
                "            \"temperature\":77.81,\n" +
                "            \"apparentTemperature\":77.81,\n" +
                "            \"dewPoint\":54.16,\n" +
                "            \"humidity\":0.44,\n" +
                "            \"pressure\":1019.18,\n" +
                "            \"windSpeed\":5.74,\n" +
                "            \"windGust\":12.38,\n" +
                "            \"windBearing\":281,\n" +
                "            \"cloudCover\":0.33,\n" +
                "            \"uvIndex\":8,\n" +
                "            \"visibility\":10,\n" +
                "            \"ozone\":338.27\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528480800,\n" +
                "            \"summary\":\"Partly Cloudy\",\n" +
                "            \"icon\":\"partly-cloudy-day\",\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0,\n" +
                "            \"temperature\":79.17,\n" +
                "            \"apparentTemperature\":79.17,\n" +
                "            \"dewPoint\":54.35,\n" +
                "            \"humidity\":0.42,\n" +
                "            \"pressure\":1018.69,\n" +
                "            \"windSpeed\":5.91,\n" +
                "            \"windGust\":13.48,\n" +
                "            \"windBearing\":275,\n" +
                "            \"cloudCover\":0.32,\n" +
                "            \"uvIndex\":7,\n" +
                "            \"visibility\":10,\n" +
                "            \"ozone\":338.22\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528484400,\n" +
                "            \"summary\":\"Partly Cloudy\",\n" +
                "            \"icon\":\"partly-cloudy-day\",\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0,\n" +
                "            \"temperature\":80.2,\n" +
                "            \"apparentTemperature\":80.2,\n" +
                "            \"dewPoint\":54.54,\n" +
                "            \"humidity\":0.41,\n" +
                "            \"pressure\":1018.17,\n" +
                "            \"windSpeed\":5.79,\n" +
                "            \"windGust\":13.04,\n" +
                "            \"windBearing\":260,\n" +
                "            \"cloudCover\":0.54,\n" +
                "            \"uvIndex\":4,\n" +
                "            \"visibility\":10,\n" +
                "            \"ozone\":339.46\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528488000,\n" +
                "            \"summary\":\"Mostly Cloudy\",\n" +
                "            \"icon\":\"partly-cloudy-day\",\n" +
                "            \"precipIntensity\":0.0011,\n" +
                "            \"precipProbability\":0.01,\n" +
                "            \"precipType\":\"rain\",\n" +
                "            \"temperature\":80.43,\n" +
                "            \"apparentTemperature\":80.43,\n" +
                "            \"dewPoint\":54.75,\n" +
                "            \"humidity\":0.41,\n" +
                "            \"pressure\":1017.79,\n" +
                "            \"windSpeed\":5.62,\n" +
                "            \"windGust\":11.89,\n" +
                "            \"windBearing\":313,\n" +
                "            \"cloudCover\":0.61,\n" +
                "            \"uvIndex\":2,\n" +
                "            \"visibility\":10,\n" +
                "            \"ozone\":341.31\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528491600,\n" +
                "            \"summary\":\"Partly Cloudy\",\n" +
                "            \"icon\":\"partly-cloudy-day\",\n" +
                "            \"precipIntensity\":0.0026,\n" +
                "            \"precipProbability\":0.01,\n" +
                "            \"precipType\":\"rain\",\n" +
                "            \"temperature\":79.7,\n" +
                "            \"apparentTemperature\":79.7,\n" +
                "            \"dewPoint\":54.92,\n" +
                "            \"humidity\":0.43,\n" +
                "            \"pressure\":1017.61,\n" +
                "            \"windSpeed\":5.4,\n" +
                "            \"windGust\":10.72,\n" +
                "            \"windBearing\":293,\n" +
                "            \"cloudCover\":0.58,\n" +
                "            \"uvIndex\":1,\n" +
                "            \"visibility\":10,\n" +
                "            \"ozone\":342.33\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528495200,\n" +
                "            \"summary\":\"Partly Cloudy\",\n" +
                "            \"icon\":\"partly-cloudy-day\",\n" +
                "            \"precipIntensity\":0.0047,\n" +
                "            \"precipProbability\":0.01,\n" +
                "            \"precipType\":\"rain\",\n" +
                "            \"temperature\":78.22,\n" +
                "            \"apparentTemperature\":78.22,\n" +
                "            \"dewPoint\":55.18,\n" +
                "            \"humidity\":0.45,\n" +
                "            \"pressure\":1017.62,\n" +
                "            \"windSpeed\":4.8,\n" +
                "            \"windGust\":9.57,\n" +
                "            \"windBearing\":298,\n" +
                "            \"cloudCover\":0.55,\n" +
                "            \"uvIndex\":1,\n" +
                "            \"visibility\":10,\n" +
                "            \"ozone\":341.73\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528498800,\n" +
                "            \"summary\":\"Partly Cloudy\",\n" +
                "            \"icon\":\"partly-cloudy-day\",\n" +
                "            \"precipIntensity\":0.0079,\n" +
                "            \"precipProbability\":0.02,\n" +
                "            \"precipType\":\"rain\",\n" +
                "            \"temperature\":76.28,\n" +
                "            \"apparentTemperature\":76.28,\n" +
                "            \"dewPoint\":55.41,\n" +
                "            \"humidity\":0.48,\n" +
                "            \"pressure\":1017.81,\n" +
                "            \"windSpeed\":4.13,\n" +
                "            \"windGust\":8.43,\n" +
                "            \"windBearing\":312,\n" +
                "            \"cloudCover\":0.52,\n" +
                "            \"uvIndex\":0,\n" +
                "            \"visibility\":10,\n" +
                "            \"ozone\":340.32\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528502400,\n" +
                "            \"summary\":\"Partly Cloudy\",\n" +
                "            \"icon\":\"partly-cloudy-day\",\n" +
                "            \"precipIntensity\":0.0091,\n" +
                "            \"precipProbability\":0.01,\n" +
                "            \"precipType\":\"rain\",\n" +
                "            \"temperature\":73.8,\n" +
                "            \"apparentTemperature\":73.8,\n" +
                "            \"dewPoint\":55.25,\n" +
                "            \"humidity\":0.52,\n" +
                "            \"pressure\":1018.04,\n" +
                "            \"windSpeed\":3.75,\n" +
                "            \"windGust\":8.07,\n" +
                "            \"windBearing\":331,\n" +
                "            \"cloudCover\":0.46,\n" +
                "            \"uvIndex\":0,\n" +
                "            \"visibility\":10,\n" +
                "            \"ozone\":339.02\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528506000,\n" +
                "            \"summary\":\"Partly Cloudy\",\n" +
                "            \"icon\":\"partly-cloudy-night\",\n" +
                "            \"precipIntensity\":0.0053,\n" +
                "            \"precipProbability\":0.01,\n" +
                "            \"precipType\":\"rain\",\n" +
                "            \"temperature\":71.42,\n" +
                "            \"apparentTemperature\":71.42,\n" +
                "            \"dewPoint\":54.46,\n" +
                "            \"humidity\":0.55,\n" +
                "            \"pressure\":1018.43,\n" +
                "            \"windSpeed\":3.82,\n" +
                "            \"windGust\":9.06,\n" +
                "            \"windBearing\":343,\n" +
                "            \"cloudCover\":0.34,\n" +
                "            \"uvIndex\":0,\n" +
                "            \"visibility\":10,\n" +
                "            \"ozone\":338.18\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528509600,\n" +
                "            \"summary\":\"Partly Cloudy\",\n" +
                "            \"icon\":\"partly-cloudy-night\",\n" +
                "            \"precipIntensity\":0.0015,\n" +
                "            \"precipProbability\":0.01,\n" +
                "            \"precipType\":\"rain\",\n" +
                "            \"temperature\":69.38,\n" +
                "            \"apparentTemperature\":69.38,\n" +
                "            \"dewPoint\":53.36,\n" +
                "            \"humidity\":0.57,\n" +
                "            \"pressure\":1018.84,\n" +
                "            \"windSpeed\":4.3,\n" +
                "            \"windGust\":10.55,\n" +
                "            \"windBearing\":338,\n" +
                "            \"cloudCover\":0.28,\n" +
                "            \"uvIndex\":0,\n" +
                "            \"visibility\":10,\n" +
                "            \"ozone\":337.52\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528513200,\n" +
                "            \"summary\":\"Clear\",\n" +
                "            \"icon\":\"clear-night\",\n" +
                "            \"precipIntensity\":0.0007,\n" +
                "            \"precipProbability\":0.01,\n" +
                "            \"precipType\":\"rain\",\n" +
                "            \"temperature\":67.56,\n" +
                "            \"apparentTemperature\":67.56,\n" +
                "            \"dewPoint\":52.3,\n" +
                "            \"humidity\":0.58,\n" +
                "            \"pressure\":1019.12,\n" +
                "            \"windSpeed\":4.42,\n" +
                "            \"windGust\":11.13,\n" +
                "            \"windBearing\":337,\n" +
                "            \"cloudCover\":0.24,\n" +
                "            \"uvIndex\":0,\n" +
                "            \"visibility\":10,\n" +
                "            \"ozone\":337.21\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528516800,\n" +
                "            \"summary\":\"Clear\",\n" +
                "            \"icon\":\"clear-night\",\n" +
                "            \"precipIntensity\":0.0005,\n" +
                "            \"precipProbability\":0.01,\n" +
                "            \"precipType\":\"rain\",\n" +
                "            \"temperature\":65.9,\n" +
                "            \"apparentTemperature\":65.9,\n" +
                "            \"dewPoint\":51.23,\n" +
                "            \"humidity\":0.59,\n" +
                "            \"pressure\":1019.19,\n" +
                "            \"windSpeed\":4.04,\n" +
                "            \"windGust\":10.23,\n" +
                "            \"windBearing\":340,\n" +
                "            \"cloudCover\":0.23,\n" +
                "            \"uvIndex\":0,\n" +
                "            \"visibility\":10,\n" +
                "            \"ozone\":337.34\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528520400,\n" +
                "            \"summary\":\"Clear\",\n" +
                "            \"icon\":\"clear-night\",\n" +
                "            \"precipIntensity\":0.0003,\n" +
                "            \"precipProbability\":0.01,\n" +
                "            \"precipType\":\"rain\",\n" +
                "            \"temperature\":64.35,\n" +
                "            \"apparentTemperature\":64.35,\n" +
                "            \"dewPoint\":50.11,\n" +
                "            \"humidity\":0.6,\n" +
                "            \"pressure\":1019.14,\n" +
                "            \"windSpeed\":3.53,\n" +
                "            \"windGust\":8.62,\n" +
                "            \"windBearing\":332,\n" +
                "            \"cloudCover\":0.24,\n" +
                "            \"uvIndex\":0,\n" +
                "            \"visibility\":10,\n" +
                "            \"ozone\":337.84\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528524000,\n" +
                "            \"summary\":\"Clear\",\n" +
                "            \"icon\":\"clear-night\",\n" +
                "            \"precipIntensity\":0.0002,\n" +
                "            \"precipProbability\":0.01,\n" +
                "            \"precipType\":\"rain\",\n" +
                "            \"temperature\":62.91,\n" +
                "            \"apparentTemperature\":62.91,\n" +
                "            \"dewPoint\":49.26,\n" +
                "            \"humidity\":0.61,\n" +
                "            \"pressure\":1019.15,\n" +
                "            \"windSpeed\":3.39,\n" +
                "            \"windGust\":7.13,\n" +
                "            \"windBearing\":318,\n" +
                "            \"cloudCover\":0.24,\n" +
                "            \"uvIndex\":0,\n" +
                "            \"visibility\":10,\n" +
                "            \"ozone\":338.68\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528527600,\n" +
                "            \"summary\":\"Clear\",\n" +
                "            \"icon\":\"clear-night\",\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0,\n" +
                "            \"temperature\":61.36,\n" +
                "            \"apparentTemperature\":61.36,\n" +
                "            \"dewPoint\":48.85,\n" +
                "            \"humidity\":0.63,\n" +
                "            \"pressure\":1019.08,\n" +
                "            \"windSpeed\":3.28,\n" +
                "            \"windGust\":5.97,\n" +
                "            \"windBearing\":341,\n" +
                "            \"cloudCover\":0.23,\n" +
                "            \"uvIndex\":0,\n" +
                "            \"visibility\":10,\n" +
                "            \"ozone\":340.48\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528531200,\n" +
                "            \"summary\":\"Partly Cloudy\",\n" +
                "            \"icon\":\"partly-cloudy-night\",\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0,\n" +
                "            \"temperature\":60.11,\n" +
                "            \"apparentTemperature\":60.11,\n" +
                "            \"dewPoint\":48.69,\n" +
                "            \"humidity\":0.66,\n" +
                "            \"pressure\":1019.02,\n" +
                "            \"windSpeed\":3.16,\n" +
                "            \"windGust\":4.96,\n" +
                "            \"windBearing\":325,\n" +
                "            \"cloudCover\":0.35,\n" +
                "            \"uvIndex\":0,\n" +
                "            \"visibility\":10,\n" +
                "            \"ozone\":342.67\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528534800,\n" +
                "            \"summary\":\"Clear\",\n" +
                "            \"icon\":\"clear-night\",\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0,\n" +
                "            \"temperature\":59.44,\n" +
                "            \"apparentTemperature\":59.44,\n" +
                "            \"dewPoint\":48.57,\n" +
                "            \"humidity\":0.67,\n" +
                "            \"pressure\":1019.07,\n" +
                "            \"windSpeed\":3.61,\n" +
                "            \"windGust\":4.5,\n" +
                "            \"windBearing\":305,\n" +
                "            \"cloudCover\":0.21,\n" +
                "            \"uvIndex\":0,\n" +
                "            \"visibility\":10,\n" +
                "            \"ozone\":343.74\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528538400,\n" +
                "            \"summary\":\"Clear\",\n" +
                "            \"icon\":\"clear-day\",\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0,\n" +
                "            \"temperature\":60.55,\n" +
                "            \"apparentTemperature\":60.55,\n" +
                "            \"dewPoint\":48.5,\n" +
                "            \"humidity\":0.64,\n" +
                "            \"pressure\":1019.18,\n" +
                "            \"windSpeed\":3.56,\n" +
                "            \"windGust\":4.79,\n" +
                "            \"windBearing\":300,\n" +
                "            \"cloudCover\":0.18,\n" +
                "            \"uvIndex\":0,\n" +
                "            \"visibility\":10,\n" +
                "            \"ozone\":342.85\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528542000,\n" +
                "            \"summary\":\"Partly Cloudy\",\n" +
                "            \"icon\":\"partly-cloudy-day\",\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0,\n" +
                "            \"temperature\":63.26,\n" +
                "            \"apparentTemperature\":63.26,\n" +
                "            \"dewPoint\":48.51,\n" +
                "            \"humidity\":0.59,\n" +
                "            \"pressure\":1019.39,\n" +
                "            \"windSpeed\":3.49,\n" +
                "            \"windGust\":5.62,\n" +
                "            \"windBearing\":295,\n" +
                "            \"cloudCover\":0.38,\n" +
                "            \"uvIndex\":1,\n" +
                "            \"visibility\":10,\n" +
                "            \"ozone\":340.85\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528545600,\n" +
                "            \"summary\":\"Partly Cloudy\",\n" +
                "            \"icon\":\"partly-cloudy-day\",\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0,\n" +
                "            \"temperature\":66.24,\n" +
                "            \"apparentTemperature\":66.24,\n" +
                "            \"dewPoint\":48.39,\n" +
                "            \"humidity\":0.53,\n" +
                "            \"pressure\":1019.34,\n" +
                "            \"windSpeed\":3.63,\n" +
                "            \"windGust\":6.5,\n" +
                "            \"windBearing\":289,\n" +
                "            \"cloudCover\":0.46,\n" +
                "            \"uvIndex\":1,\n" +
                "            \"visibility\":10,\n" +
                "            \"ozone\":339.03\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528549200,\n" +
                "            \"summary\":\"Partly Cloudy\",\n" +
                "            \"icon\":\"partly-cloudy-day\",\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0,\n" +
                "            \"temperature\":68.74,\n" +
                "            \"apparentTemperature\":68.74,\n" +
                "            \"dewPoint\":48.08,\n" +
                "            \"humidity\":0.48,\n" +
                "            \"pressure\":1019.23,\n" +
                "            \"windSpeed\":4.48,\n" +
                "            \"windGust\":7.19,\n" +
                "            \"windBearing\":291,\n" +
                "            \"cloudCover\":0.5,\n" +
                "            \"uvIndex\":2,\n" +
                "            \"visibility\":10,\n" +
                "            \"ozone\":338.05\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528552800,\n" +
                "            \"summary\":\"Partly Cloudy\",\n" +
                "            \"icon\":\"partly-cloudy-day\",\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0,\n" +
                "            \"temperature\":71.58,\n" +
                "            \"apparentTemperature\":71.58,\n" +
                "            \"dewPoint\":47.63,\n" +
                "            \"humidity\":0.43,\n" +
                "            \"pressure\":1018.87,\n" +
                "            \"windSpeed\":5.37,\n" +
                "            \"windGust\":7.9,\n" +
                "            \"windBearing\":295,\n" +
                "            \"cloudCover\":0.51,\n" +
                "            \"uvIndex\":4,\n" +
                "            \"visibility\":10,\n" +
                "            \"ozone\":337.36\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528556400,\n" +
                "            \"summary\":\"Mostly Cloudy\",\n" +
                "            \"icon\":\"partly-cloudy-day\",\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0,\n" +
                "            \"temperature\":74.16,\n" +
                "            \"apparentTemperature\":74.16,\n" +
                "            \"dewPoint\":47.19,\n" +
                "            \"humidity\":0.38,\n" +
                "            \"pressure\":1018.47,\n" +
                "            \"windSpeed\":6,\n" +
                "            \"windGust\":8.86,\n" +
                "            \"windBearing\":297,\n" +
                "            \"cloudCover\":0.6,\n" +
                "            \"uvIndex\":5,\n" +
                "            \"visibility\":10,\n" +
                "            \"ozone\":336.09\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528560000,\n" +
                "            \"summary\":\"Mostly Cloudy\",\n" +
                "            \"icon\":\"partly-cloudy-day\",\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0,\n" +
                "            \"temperature\":75.98,\n" +
                "            \"apparentTemperature\":75.98,\n" +
                "            \"dewPoint\":46.84,\n" +
                "            \"humidity\":0.36,\n" +
                "            \"pressure\":1018.06,\n" +
                "            \"windSpeed\":6.42,\n" +
                "            \"windGust\":10.53,\n" +
                "            \"windBearing\":293,\n" +
                "            \"cloudCover\":0.63,\n" +
                "            \"uvIndex\":6,\n" +
                "            \"visibility\":10,\n" +
                "            \"ozone\":333.59\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528563600,\n" +
                "            \"summary\":\"Mostly Cloudy\",\n" +
                "            \"icon\":\"partly-cloudy-day\",\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0,\n" +
                "            \"temperature\":77.48,\n" +
                "            \"apparentTemperature\":77.48,\n" +
                "            \"dewPoint\":46.53,\n" +
                "            \"humidity\":0.34,\n" +
                "            \"pressure\":1017.54,\n" +
                "            \"windSpeed\":7.07,\n" +
                "            \"windGust\":12.55,\n" +
                "            \"windBearing\":287,\n" +
                "            \"cloudCover\":0.65,\n" +
                "            \"uvIndex\":7,\n" +
                "            \"visibility\":10,\n" +
                "            \"ozone\":330.57\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528567200,\n" +
                "            \"summary\":\"Mostly Cloudy\",\n" +
                "            \"icon\":\"partly-cloudy-day\",\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0,\n" +
                "            \"temperature\":78.58,\n" +
                "            \"apparentTemperature\":78.58,\n" +
                "            \"dewPoint\":46.33,\n" +
                "            \"humidity\":0.32,\n" +
                "            \"pressure\":1017.05,\n" +
                "            \"windSpeed\":7.59,\n" +
                "            \"windGust\":13.68,\n" +
                "            \"windBearing\":286,\n" +
                "            \"cloudCover\":0.64,\n" +
                "            \"uvIndex\":6,\n" +
                "            \"visibility\":10,\n" +
                "            \"ozone\":328.37\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528570800,\n" +
                "            \"summary\":\"Mostly Cloudy\",\n" +
                "            \"icon\":\"partly-cloudy-day\",\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0,\n" +
                "            \"temperature\":79.21,\n" +
                "            \"apparentTemperature\":79.21,\n" +
                "            \"dewPoint\":46.07,\n" +
                "            \"humidity\":0.31,\n" +
                "            \"pressure\":1016.5,\n" +
                "            \"windSpeed\":7.61,\n" +
                "            \"windGust\":13.09,\n" +
                "            \"windBearing\":294,\n" +
                "            \"cloudCover\":0.65,\n" +
                "            \"uvIndex\":4,\n" +
                "            \"visibility\":10,\n" +
                "            \"ozone\":327.56\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528574400,\n" +
                "            \"summary\":\"Mostly Cloudy\",\n" +
                "            \"icon\":\"partly-cloudy-day\",\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipProbability\":0,\n" +
                "            \"temperature\":79.22,\n" +
                "            \"apparentTemperature\":79.22,\n" +
                "            \"dewPoint\":45.82,\n" +
                "            \"humidity\":0.31,\n" +
                "            \"pressure\":1016.03,\n" +
                "            \"windSpeed\":7.13,\n" +
                "            \"windGust\":11.61,\n" +
                "            \"windBearing\":306,\n" +
                "            \"cloudCover\":0.65,\n" +
                "            \"uvIndex\":2,\n" +
                "            \"visibility\":10,\n" +
                "            \"ozone\":327.54\n" +
                "         }\n" +
                "      ]\n" +
                "   },\n" +
                "   \"daily\":{  \n" +
                "      \"summary\":\"No precipitation throughout the week, with high temperatures rising to 82°F next Thursday.\",\n" +
                "      \"icon\":\"clear-day\",\n" +
                "      \"data\":[  \n" +
                "         {  \n" +
                "            \"time\":1528344000,\n" +
                "            \"summary\":\"Mostly cloudy throughout the day.\",\n" +
                "            \"icon\":\"partly-cloudy-day\",\n" +
                "            \"sunriseTime\":1528362552,\n" +
                "            \"sunsetTime\":1528417203,\n" +
                "            \"moonPhase\":0.78,\n" +
                "            \"precipIntensity\":0.0002,\n" +
                "            \"precipIntensityMax\":0.0016,\n" +
                "            \"precipIntensityMaxTime\":1528398000,\n" +
                "            \"precipProbability\":0.05,\n" +
                "            \"precipType\":\"rain\",\n" +
                "            \"temperatureHigh\":70.58,\n" +
                "            \"temperatureHighTime\":1528405200,\n" +
                "            \"temperatureLow\":56.68,\n" +
                "            \"temperatureLowTime\":1528448400,\n" +
                "            \"apparentTemperatureHigh\":70.58,\n" +
                "            \"apparentTemperatureHighTime\":1528405200,\n" +
                "            \"apparentTemperatureLow\":56.68,\n" +
                "            \"apparentTemperatureLowTime\":1528448400,\n" +
                "            \"dewPoint\":52.08,\n" +
                "            \"humidity\":0.7,\n" +
                "            \"pressure\":1018.79,\n" +
                "            \"windSpeed\":6.39,\n" +
                "            \"windGust\":18.77,\n" +
                "            \"windGustTime\":1528423200,\n" +
                "            \"windBearing\":205,\n" +
                "            \"cloudCover\":0.75,\n" +
                "            \"uvIndex\":7,\n" +
                "            \"uvIndexTime\":1528387200,\n" +
                "            \"visibility\":10,\n" +
                "            \"ozone\":342,\n" +
                "            \"temperatureMin\":54.46,\n" +
                "            \"temperatureMinTime\":1528362000,\n" +
                "            \"temperatureMax\":70.58,\n" +
                "            \"temperatureMaxTime\":1528405200,\n" +
                "            \"apparentTemperatureMin\":54.46,\n" +
                "            \"apparentTemperatureMinTime\":1528362000,\n" +
                "            \"apparentTemperatureMax\":70.58,\n" +
                "            \"apparentTemperatureMaxTime\":1528405200\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528430400,\n" +
                "            \"summary\":\"Partly cloudy starting in the afternoon.\",\n" +
                "            \"icon\":\"partly-cloudy-day\",\n" +
                "            \"sunriseTime\":1528448938,\n" +
                "            \"sunsetTime\":1528503639,\n" +
                "            \"moonPhase\":0.81,\n" +
                "            \"precipIntensity\":0.0015,\n" +
                "            \"precipIntensityMax\":0.0091,\n" +
                "            \"precipIntensityMaxTime\":1528502400,\n" +
                "            \"precipProbability\":0.05,\n" +
                "            \"precipType\":\"rain\",\n" +
                "            \"temperatureHigh\":80.43,\n" +
                "            \"temperatureHighTime\":1528488000,\n" +
                "            \"temperatureLow\":59.44,\n" +
                "            \"temperatureLowTime\":1528534800,\n" +
                "            \"apparentTemperatureHigh\":80.43,\n" +
                "            \"apparentTemperatureHighTime\":1528488000,\n" +
                "            \"apparentTemperatureLow\":59.44,\n" +
                "            \"apparentTemperatureLowTime\":1528534800,\n" +
                "            \"dewPoint\":53.16,\n" +
                "            \"humidity\":0.6,\n" +
                "            \"pressure\":1019.39,\n" +
                "            \"windSpeed\":4.45,\n" +
                "            \"windGust\":18.2,\n" +
                "            \"windGustTime\":1528430400,\n" +
                "            \"windBearing\":262,\n" +
                "            \"cloudCover\":0.31,\n" +
                "            \"uvIndex\":8,\n" +
                "            \"uvIndexTime\":1528473600,\n" +
                "            \"visibility\":10,\n" +
                "            \"ozone\":341.22,\n" +
                "            \"temperatureMin\":56.68,\n" +
                "            \"temperatureMinTime\":1528448400,\n" +
                "            \"temperatureMax\":80.43,\n" +
                "            \"temperatureMaxTime\":1528488000,\n" +
                "            \"apparentTemperatureMin\":56.68,\n" +
                "            \"apparentTemperatureMinTime\":1528448400,\n" +
                "            \"apparentTemperatureMax\":80.43,\n" +
                "            \"apparentTemperatureMaxTime\":1528488000\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528516800,\n" +
                "            \"summary\":\"Mostly cloudy throughout the day.\",\n" +
                "            \"icon\":\"partly-cloudy-day\",\n" +
                "            \"sunriseTime\":1528535325,\n" +
                "            \"sunsetTime\":1528590074,\n" +
                "            \"moonPhase\":0.85,\n" +
                "            \"precipIntensity\":0.0001,\n" +
                "            \"precipIntensityMax\":0.0005,\n" +
                "            \"precipIntensityMaxTime\":1528516800,\n" +
                "            \"precipProbability\":0.01,\n" +
                "            \"precipType\":\"rain\",\n" +
                "            \"temperatureHigh\":79.22,\n" +
                "            \"temperatureHighTime\":1528574400,\n" +
                "            \"temperatureLow\":56.94,\n" +
                "            \"temperatureLowTime\":1528621200,\n" +
                "            \"apparentTemperatureHigh\":79.22,\n" +
                "            \"apparentTemperatureHighTime\":1528574400,\n" +
                "            \"apparentTemperatureLow\":56.94,\n" +
                "            \"apparentTemperatureLowTime\":1528621200,\n" +
                "            \"dewPoint\":47.68,\n" +
                "            \"humidity\":0.47,\n" +
                "            \"pressure\":1017.9,\n" +
                "            \"windSpeed\":4.7,\n" +
                "            \"windGust\":13.68,\n" +
                "            \"windGustTime\":1528567200,\n" +
                "            \"windBearing\":315,\n" +
                "            \"cloudCover\":0.53,\n" +
                "            \"uvIndex\":7,\n" +
                "            \"uvIndexTime\":1528563600,\n" +
                "            \"visibility\":10,\n" +
                "            \"ozone\":334.63,\n" +
                "            \"temperatureMin\":59.44,\n" +
                "            \"temperatureMinTime\":1528534800,\n" +
                "            \"temperatureMax\":79.22,\n" +
                "            \"temperatureMaxTime\":1528574400,\n" +
                "            \"apparentTemperatureMin\":59.44,\n" +
                "            \"apparentTemperatureMinTime\":1528534800,\n" +
                "            \"apparentTemperatureMax\":79.22,\n" +
                "            \"apparentTemperatureMaxTime\":1528574400\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528603200,\n" +
                "            \"summary\":\"Partly cloudy until afternoon.\",\n" +
                "            \"icon\":\"partly-cloudy-day\",\n" +
                "            \"sunriseTime\":1528621714,\n" +
                "            \"sunsetTime\":1528676508,\n" +
                "            \"moonPhase\":0.88,\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipIntensityMax\":0,\n" +
                "            \"precipProbability\":0,\n" +
                "            \"temperatureHigh\":72.69,\n" +
                "            \"temperatureHighTime\":1528657200,\n" +
                "            \"temperatureLow\":53.15,\n" +
                "            \"temperatureLowTime\":1528704000,\n" +
                "            \"apparentTemperatureHigh\":72.69,\n" +
                "            \"apparentTemperatureHighTime\":1528657200,\n" +
                "            \"apparentTemperatureLow\":53.15,\n" +
                "            \"apparentTemperatureLowTime\":1528704000,\n" +
                "            \"dewPoint\":39.93,\n" +
                "            \"humidity\":0.41,\n" +
                "            \"pressure\":1017.8,\n" +
                "            \"windSpeed\":3.88,\n" +
                "            \"windGust\":21.08,\n" +
                "            \"windGustTime\":1528614000,\n" +
                "            \"windBearing\":23,\n" +
                "            \"cloudCover\":0.3,\n" +
                "            \"uvIndex\":8,\n" +
                "            \"uvIndexTime\":1528650000,\n" +
                "            \"visibility\":10,\n" +
                "            \"ozone\":345.11,\n" +
                "            \"temperatureMin\":56.94,\n" +
                "            \"temperatureMinTime\":1528621200,\n" +
                "            \"temperatureMax\":72.69,\n" +
                "            \"temperatureMaxTime\":1528657200,\n" +
                "            \"apparentTemperatureMin\":56.94,\n" +
                "            \"apparentTemperatureMinTime\":1528621200,\n" +
                "            \"apparentTemperatureMax\":72.69,\n" +
                "            \"apparentTemperatureMaxTime\":1528657200\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528689600,\n" +
                "            \"summary\":\"Clear throughout the day.\",\n" +
                "            \"icon\":\"clear-day\",\n" +
                "            \"sunriseTime\":1528708106,\n" +
                "            \"sunsetTime\":1528762939,\n" +
                "            \"moonPhase\":0.92,\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipIntensityMax\":0,\n" +
                "            \"precipProbability\":0,\n" +
                "            \"temperatureHigh\":78.96,\n" +
                "            \"temperatureHighTime\":1528747200,\n" +
                "            \"temperatureLow\":56.95,\n" +
                "            \"temperatureLowTime\":1528794000,\n" +
                "            \"apparentTemperatureHigh\":78.96,\n" +
                "            \"apparentTemperatureHighTime\":1528747200,\n" +
                "            \"apparentTemperatureLow\":56.95,\n" +
                "            \"apparentTemperatureLowTime\":1528794000,\n" +
                "            \"dewPoint\":40.61,\n" +
                "            \"humidity\":0.42,\n" +
                "            \"pressure\":1019.19,\n" +
                "            \"windSpeed\":3.45,\n" +
                "            \"windGust\":28.67,\n" +
                "            \"windGustTime\":1528772400,\n" +
                "            \"windBearing\":272,\n" +
                "            \"cloudCover\":0.01,\n" +
                "            \"uvIndex\":9,\n" +
                "            \"uvIndexTime\":1528732800,\n" +
                "            \"visibility\":10,\n" +
                "            \"ozone\":345.23,\n" +
                "            \"temperatureMin\":53.15,\n" +
                "            \"temperatureMinTime\":1528704000,\n" +
                "            \"temperatureMax\":78.96,\n" +
                "            \"temperatureMaxTime\":1528747200,\n" +
                "            \"apparentTemperatureMin\":53.15,\n" +
                "            \"apparentTemperatureMinTime\":1528704000,\n" +
                "            \"apparentTemperatureMax\":78.96,\n" +
                "            \"apparentTemperatureMaxTime\":1528747200\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528776000,\n" +
                "            \"summary\":\"Mostly cloudy throughout the day.\",\n" +
                "            \"icon\":\"partly-cloudy-day\",\n" +
                "            \"sunriseTime\":1528794499,\n" +
                "            \"sunsetTime\":1528849369,\n" +
                "            \"moonPhase\":0.95,\n" +
                "            \"precipIntensity\":0,\n" +
                "            \"precipIntensityMax\":0.0002,\n" +
                "            \"precipIntensityMaxTime\":1528794000,\n" +
                "            \"precipProbability\":0,\n" +
                "            \"temperatureHigh\":78.52,\n" +
                "            \"temperatureHighTime\":1528830000,\n" +
                "            \"temperatureLow\":59.54,\n" +
                "            \"temperatureLowTime\":1528876800,\n" +
                "            \"apparentTemperatureHigh\":78.52,\n" +
                "            \"apparentTemperatureHighTime\":1528830000,\n" +
                "            \"apparentTemperatureLow\":59.54,\n" +
                "            \"apparentTemperatureLowTime\":1528876800,\n" +
                "            \"dewPoint\":41.49,\n" +
                "            \"humidity\":0.4,\n" +
                "            \"pressure\":1016.77,\n" +
                "            \"windSpeed\":6.62,\n" +
                "            \"windGust\":29.02,\n" +
                "            \"windGustTime\":1528779600,\n" +
                "            \"windBearing\":211,\n" +
                "            \"cloudCover\":0.51,\n" +
                "            \"uvIndex\":7,\n" +
                "            \"uvIndexTime\":1528819200,\n" +
                "            \"visibility\":10,\n" +
                "            \"ozone\":320.53,\n" +
                "            \"temperatureMin\":56.95,\n" +
                "            \"temperatureMinTime\":1528794000,\n" +
                "            \"temperatureMax\":78.52,\n" +
                "            \"temperatureMaxTime\":1528830000,\n" +
                "            \"apparentTemperatureMin\":56.95,\n" +
                "            \"apparentTemperatureMinTime\":1528794000,\n" +
                "            \"apparentTemperatureMax\":78.52,\n" +
                "            \"apparentTemperatureMaxTime\":1528830000\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528862400,\n" +
                "            \"summary\":\"Mostly cloudy throughout the day.\",\n" +
                "            \"icon\":\"partly-cloudy-day\",\n" +
                "            \"sunriseTime\":1528880895,\n" +
                "            \"sunsetTime\":1528935797,\n" +
                "            \"moonPhase\":0.99,\n" +
                "            \"precipIntensity\":0.0039,\n" +
                "            \"precipIntensityMax\":0.0279,\n" +
                "            \"precipIntensityMaxTime\":1528923600,\n" +
                "            \"precipProbability\":0.45,\n" +
                "            \"precipType\":\"rain\",\n" +
                "            \"temperatureHigh\":81.23,\n" +
                "            \"temperatureHighTime\":1528912800,\n" +
                "            \"temperatureLow\":62.86,\n" +
                "            \"temperatureLowTime\":1528966800,\n" +
                "            \"apparentTemperatureHigh\":81.51,\n" +
                "            \"apparentTemperatureHighTime\":1528912800,\n" +
                "            \"apparentTemperatureLow\":62.86,\n" +
                "            \"apparentTemperatureLowTime\":1528966800,\n" +
                "            \"dewPoint\":57.32,\n" +
                "            \"humidity\":0.66,\n" +
                "            \"pressure\":1015.1,\n" +
                "            \"windSpeed\":8.01,\n" +
                "            \"windGust\":24.4,\n" +
                "            \"windGustTime\":1528862400,\n" +
                "            \"windBearing\":208,\n" +
                "            \"cloudCover\":0.91,\n" +
                "            \"uvIndex\":6,\n" +
                "            \"uvIndexTime\":1528909200,\n" +
                "            \"visibility\":10,\n" +
                "            \"ozone\":305.69,\n" +
                "            \"temperatureMin\":59.54,\n" +
                "            \"temperatureMinTime\":1528876800,\n" +
                "            \"temperatureMax\":81.23,\n" +
                "            \"temperatureMaxTime\":1528912800,\n" +
                "            \"apparentTemperatureMin\":59.54,\n" +
                "            \"apparentTemperatureMinTime\":1528876800,\n" +
                "            \"apparentTemperatureMax\":81.51,\n" +
                "            \"apparentTemperatureMaxTime\":1528912800\n" +
                "         },\n" +
                "         {  \n" +
                "            \"time\":1528948800,\n" +
                "            \"summary\":\"Mostly cloudy until afternoon.\",\n" +
                "            \"icon\":\"partly-cloudy-day\",\n" +
                "            \"sunriseTime\":1528967292,\n" +
                "            \"sunsetTime\":1529022224,\n" +
                "            \"moonPhase\":0.03,\n" +
                "            \"precipIntensity\":0.0048,\n" +
                "            \"precipIntensityMax\":0.0262,\n" +
                "            \"precipIntensityMaxTime\":1529010000,\n" +
                "            \"precipProbability\":0.09,\n" +
                "            \"precipType\":\"rain\",\n" +
                "            \"temperatureHigh\":82.11,\n" +
                "            \"temperatureHighTime\":1528999200,\n" +
                "            \"temperatureLow\":63.7,\n" +
                "            \"temperatureLowTime\":1529053200,\n" +
                "            \"apparentTemperatureHigh\":82.11,\n" +
                "            \"apparentTemperatureHighTime\":1528999200,\n" +
                "            \"apparentTemperatureLow\":63.7,\n" +
                "            \"apparentTemperatureLowTime\":1529053200,\n" +
                "            \"dewPoint\":51.63,\n" +
                "            \"humidity\":0.5,\n" +
                "            \"pressure\":1011.65,\n" +
                "            \"windSpeed\":5.61,\n" +
                "            \"windGust\":25.5,\n" +
                "            \"windGustTime\":1528956000,\n" +
                "            \"windBearing\":241,\n" +
                "            \"cloudCover\":0.61,\n" +
                "            \"uvIndex\":6,\n" +
                "            \"uvIndexTime\":1528995600,\n" +
                "            \"visibility\":10,\n" +
                "            \"ozone\":318.91,\n" +
                "            \"temperatureMin\":62.86,\n" +
                "            \"temperatureMinTime\":1528966800,\n" +
                "            \"temperatureMax\":82.11,\n" +
                "            \"temperatureMaxTime\":1528999200,\n" +
                "            \"apparentTemperatureMin\":62.86,\n" +
                "            \"apparentTemperatureMinTime\":1528966800,\n" +
                "            \"apparentTemperatureMax\":82.11,\n" +
                "            \"apparentTemperatureMaxTime\":1528999200\n" +
                "         }\n" +
                "      ]\n" +
                "   },\n" +
                "   \"flags\":{  \n" +
                "      \"sources\":[  \n" +
                "         \"nearest-precip\",\n" +
                "         \"nwspa\",\n" +
                "         \"cmc\",\n" +
                "         \"gfs\",\n" +
                "         \"hrrr\",\n" +
                "         \"icon\",\n" +
                "         \"isd\",\n" +
                "         \"madis\",\n" +
                "         \"nam\",\n" +
                "         \"sref\",\n" +
                "         \"darksky\"\n" +
                "      ],\n" +
                "      \"units\":\"us\"\n" +
                "   },\n" +
                "   \"offset\":-4\n" +
                "}\n";
        BufferedReader br = null;
        String filename = "/api_response.txt";
//        try {
//            File file = new File(filename);
//            if(file.exists()) br = new BufferedReader(new FileReader(filename));
//            StringBuilder sb = new StringBuilder();
//            String line = br.readLine();
//
//            while (line != null) {
//                sb.append(line);
//                sb.append(System.lineSeparator());
//                line = br.readLine();
//            }
//            everything = sb.toString();
//
//            br.close();
//        } catch (IOException  e) {
//            e.printStackTrace();
//        }
        return everything;
    }
}

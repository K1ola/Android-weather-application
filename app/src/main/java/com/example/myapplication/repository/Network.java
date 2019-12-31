package com.example.myapplication.repository;

import android.os.AsyncTask;

import com.androdocs.httprequest.HttpRequest;

// https://api.forecast.io/forecast/40ed0c90cb518cef245856b4588dd08a/28.854167,128.041667?units=uk&exclude=hourly%2Calerts%2Cflags

public class Network extends AsyncTask<String, Void, String> {
    private static final String BASE_URL = "https://api.forecast.io/forecast/";
//    private static final String API_KEY = "40ed0c90cb518cef245856b4588dd08a";
    private static final String API_KEY = "9a3ca1940406cec981fa1740cc76bd2b";

    // Location data
    private double latitude;
    private double longitude;

    public Network(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    protected String doInBackground(String... args) {
        String response = HttpRequest.excuteGet(BASE_URL + API_KEY + "/" + latitude + "," + longitude + "?lang=ru");
        return response;
    }

    @Override
    protected void onPostExecute(String result) {}
}


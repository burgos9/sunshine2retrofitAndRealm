package com.example.android.sunshine.app.utilities;

import com.example.android.sunshine.app.controler.RestClient;
import com.example.android.sunshine.app.controler.WeatherService;

/**
 * Created by Administrador on 25/01/2017.
 */

public class ApiUtils {

    private static final String API_BASE_URL = "http://api.openweathermap.org/";


    public static WeatherService getWeatherService(){
        return RestClient.getRestClient(API_BASE_URL).create(WeatherService.class);
    }
}

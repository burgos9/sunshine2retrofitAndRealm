package com.example.android.sunshine.app.controler;

import com.example.android.sunshine.app.model.rest.ApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrador on 25/01/2017.
 */

public interface WeatherService {

    @GET("data/2.5/forecast/daily")
    Call<ApiResponse> getWeather(@Query("q") String cityName, @Query("mode") String dataTransferMode, @Query("units") String tempUnits, @Query("cnt") int numDays, @Query("APPID") String APP_ID);
}

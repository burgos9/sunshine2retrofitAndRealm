package com.example.android.sunshine.app.model.rest;

import com.example.android.sunshine.app.model.apimodels.City;
import com.example.android.sunshine.app.model.apimodels.WeatherInfo;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * Created by Administrador on 05/01/2017.
 */

@Parcel
public class ApiResponse {

    @SerializedName("city")
    private City city;

    @SerializedName("cod")
    private int responseCode;

    @SerializedName("cnt")
    private int numberOfDays;

    @SerializedName("list")
    private ArrayList<WeatherInfo> weatherInfo;


    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public int getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(int numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public ArrayList<WeatherInfo> getWeatherInfo() {
        return weatherInfo;
    }

    public void setWeatherInfo(ArrayList<WeatherInfo> weatherInfo) {
        this.weatherInfo = weatherInfo;
    }
}

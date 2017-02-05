package com.example.android.sunshine.app.model.apimodels;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by Administrador on 05/01/2017.
 */

@Parcel
public class City {


    @SerializedName("name")
    private String cityName;


    @SerializedName("coord")
    private Coord cord;


    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Coord getCord() {
        return cord;
    }

    public void setCord(Coord cord) {
        this.cord = cord;
    }
}

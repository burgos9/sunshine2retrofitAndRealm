package com.example.android.sunshine.app.model.apimodels;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by Administrador on 25/01/2017.
 */
@Parcel
public class Weather {

    @SerializedName("id")
    private Integer weatherId;

    @SerializedName("main")
    private String weatherDesc;

    public Integer getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(Integer weatherId) {
        this.weatherId = weatherId;
    }

    public String getWeatherDesc() {
        return weatherDesc;
    }

    public void setWeatherDesc(String weatherDesc) {
        this.weatherDesc = weatherDesc;
    }
}

package com.example.android.sunshine.app.model.apimodels;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by Administrador on 05/01/2017.
 */
@Parcel
public class Coord {

    @SerializedName("lon")
    private Double longitude;

    @SerializedName("lat")
    private Double latitude;

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
}

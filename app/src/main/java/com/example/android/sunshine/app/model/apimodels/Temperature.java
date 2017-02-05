package com.example.android.sunshine.app.model.apimodels;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by Administrador on 25/01/2017.
 */
@Parcel
public class Temperature {

    @SerializedName("max")
    private Double maxTemp;

    @SerializedName("min")
    private Double minTemp;

    public Double getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(Double maxTemp) {
        this.maxTemp = maxTemp;
    }

    public Double getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(Double minTemp) {
        this.minTemp = minTemp;
    }
}

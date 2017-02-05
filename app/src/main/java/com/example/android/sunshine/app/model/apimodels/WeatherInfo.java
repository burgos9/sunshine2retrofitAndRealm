package com.example.android.sunshine.app.model.apimodels;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by Administrador on 25/01/2017.
 */

@Parcel
public class WeatherInfo {

    @SerializedName("temp")
    private Temperature temperature;

    @SerializedName("pressure")
    private Double pressure;

    @SerializedName("humidity")
    private Integer humidity;

    @SerializedName("speed")
    private Double windSpeed;

    @SerializedName("deg")
    private Integer windDirection;

    @SerializedName("weather")
    private Weather weather;


    public Temperature getTemperature() {
        return temperature;
    }

    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public Integer getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(Integer windDirection) {
        this.windDirection = windDirection;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Double getPressure() {
        return pressure;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }
}

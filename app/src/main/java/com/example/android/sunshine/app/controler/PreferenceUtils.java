package com.example.android.sunshine.app.controler;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.android.sunshine.app.model.constant.PreferenceValue;

/**
 * Created by Administrador on 09/04/2016.
 */
public class PreferenceUtils {

    public static final String PREF_NAME = "weather_preferences";
    private static SharedPreferences prefs;

    public static PreferenceUtils getInstance(Context context){
        PreferenceUtils model = new PreferenceUtils();
        model.setSharedPreferences(context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE));
        return model;
    }

    public static String getStringPreference(String key){
        return prefs.getString(key, PreferenceValue.PREF_DEFAULT_STRING);
    }
    /**
     * Function to set shared preferences at instance
     *
     * @param prefs
     */
    private void setSharedPreferences(SharedPreferences prefs){
        this.prefs = prefs;
    }

}

package com.example.android.sunshine.app.model.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.android.sunshine.app.controler.PreferenceUtils;


/**
 * Created by Administrador on 09/04/2016.
 */
public  abstract  class BaseActivity extends AppCompatActivity {

    private static PreferenceUtils prefs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        setUpStorageOptions();
    }

    /**
     * Abstract function to implement in child activities
     *
     * @return The reference of the layout (R.layout.name)
     */
    public abstract int getLayoutResource();

    private void setUpStorageOptions(){
        prefs =  PreferenceUtils.getInstance(this);
    }

    public PreferenceUtils getPrefs(){
        return prefs;
    }

}

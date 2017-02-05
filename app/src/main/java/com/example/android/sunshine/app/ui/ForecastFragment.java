/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.sunshine.app.ui;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.android.sunshine.app.BuildConfig;
import com.example.android.sunshine.app.R;
import com.example.android.sunshine.app.controler.FetchWeatherTask;
import com.example.android.sunshine.app.controler.PreferenceUtils;
import com.example.android.sunshine.app.model.constant.PreferenceKey;
import com.example.android.sunshine.app.model.data.WeatherContract;
import com.example.android.sunshine.app.model.rest.ApiResponse;
import com.example.android.sunshine.app.utilities.ApiUtils;
import com.example.android.sunshine.app.utilities.FetchWeatherUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Encapsulates fetching the forecast and displaying it as a {@link ListView} layout.
 */
public class ForecastFragment extends Fragment {

    private static final String TAG = "ForecastFragment";

    private ShareActionProvider mShareActionProvider;

    private ArrayAdapter<String> mForecastAdapter;

    private Vector<ContentValues> cVVector;

    public ForecastFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.forecastfragment, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            updateWeather();
            return true;
        } else if (id == R.id.action_settings) {
            startActivity(new Intent(getActivity(), SettingsActivity.class));
            Log.i(TAG, "onOptionsItemSelected: you have clicked on " + R.id.action_settings);
        } else if (id == R.id.action_map) {
            openPreferredLocationInMap();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        List<String> weekForecast = new ArrayList<String>();

        // Now that we have some dummy forecast data, create an ArrayAdapter.
        // The ArrayAdapter will take data from a source (like our dummy forecast) and
        // use it to populate the ListView it's attached to.
        mForecastAdapter =
                new ArrayAdapter<String>(
                        getActivity(), // The current context (this activity)
                        R.layout.list_item_forecast, // The name of the layout ID.
                        R.id.list_item_forecast_textview, // The ID of the textview to populate.
                        weekForecast);

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        // Get a reference to the ListView, and attach this adapter to it.
        ListView listView = (ListView) rootView.findViewById(R.id.listview_forecast);
        listView.setAdapter(mForecastAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String forecast = mForecastAdapter.getItem(position);
                Bundle b = new Bundle();
                b.putString("forecast", forecast);
                Intent intentDetail = new Intent(getActivity(), DetailActivity.class);
                intentDetail.putExtras(b);
                startActivity(intentDetail);
            }
        });

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateWeather();
    }

    public void updateWeather() {
        ApiUtils.getWeatherService().getWeather(WeatherContract.PATH_WEATHER, FetchWeatherTask.FORMAT, FetchWeatherTask.UNITS, "1", BuildConfig.OPEN_WEATHER_MAP_API_KEY).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                Log.d(TAG, "onResponse: OUR JSON IS ->  " + response.body());
                //TODO fix this shit
                //getWeatherFromJson(response);
                //TODO add the retrieved data to the adapter
//                if(call != null && mForecastAdapter != null){
//
//                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t.getCause());
            }
        });

    }

    private String[] getWeatherFromJson(Response<ApiResponse> response) {

        cVVector.addAll((Collection<? extends ContentValues>) response.body());

        // add to database
        if (cVVector.size() > 0) {
            // Student: call bulkInsert to add the weatherEntries to the database here
            ContentValues[] cvArray = new ContentValues[cVVector.size()];
            cVVector.toArray(cvArray);
            getContext().getContentResolver().bulkInsert(WeatherContract.WeatherEntry.CONTENT_URI, cvArray);
        }

        String locationSetting = PreferenceUtils.getInstance(getActivity()).getStringPreference(PreferenceKey.PREF_LOCATION_KEY);

        // Sort order:  Ascending, by date.
        String sortOrder = WeatherContract.WeatherEntry.COLUMN_DATE + " ASC";
        Uri weatherForLocationUri = WeatherContract.WeatherEntry.buildWeatherLocationWithStartDate(
                locationSetting, System.currentTimeMillis());

        // Students: Uncomment the next lines to display what what you stored in the bulkInsert
        Cursor cur = getContext().getContentResolver().query(weatherForLocationUri,
                null, null, null, sortOrder);

        cVVector = new Vector<ContentValues>(cur.getCount());
        if (cur.moveToFirst()) {
            do {
                ContentValues cv = new ContentValues();
                DatabaseUtils.cursorRowToContentValues(cur, cv);
                cVVector.add(cv);
            } while (cur.moveToNext());
        }

        String[] result = FetchWeatherUtils.convertContentValuesToUXFormat(getContext(), cVVector);
        return result;
    }

    private void openPreferredLocationInMap() {
        String location = PreferenceUtils.getInstance(getActivity()).getStringPreference(PreferenceKey.PREF_LOCATION_KEY);

        Uri geoLocation = Uri.parse("geo:0,0?").buildUpon()
                .appendQueryParameter("q", location)
                .build();

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);

        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Log.i(TAG, "Couldn´t call " + location + "not found");
        }

    }

}

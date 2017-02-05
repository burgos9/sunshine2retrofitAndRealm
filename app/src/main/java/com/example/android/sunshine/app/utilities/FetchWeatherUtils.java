package com.example.android.sunshine.app.utilities;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.example.android.sunshine.app.R;
import com.example.android.sunshine.app.controler.PreferenceUtils;
import com.example.android.sunshine.app.model.constant.PreferenceKey;
import com.example.android.sunshine.app.model.data.WeatherContract;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

/**
 * Created by Administrador on 26/01/2017.
 */

public class FetchWeatherUtils {



    private static final String LOG_TAG = FetchWeatherUtils.class.getSimpleName();

    /**
     * The date/time conversion code is going to be moved outside the asynctask later,
     * so for convenience we're breaking it out into its own method now.
     */
    private static String getReadableDateString(long time) {
        // Because the API returns a unix timestamp (measured in seconds),
        // it must be converted to milliseconds in order to be converted to valid date.
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("E, MMM d");
        return format.format(date).toString();
    }


    /**
     * Prepare the weather high/lows for presentation.
     */
    private static String formatHighLows(Context mContext, double high, double low) {
        // Data is fetched in Celsius by default.
        // If user prefers to see in Fahrenheit, convert the values here.
        // We do this rather than fetching in Fahrenheit so that the user can
        // change this option without us having to re-fetch the data once
        // we start storing the values in a database.
        PreferenceUtils prefs = PreferenceUtils.getInstance(mContext);
        String unitType = prefs.getStringPreference(PreferenceKey.PREF_UNITS_KEY);

        if (unitType.equals(mContext.getString(R.string.pref_units_imperial))) {
            high = (high + 1.8) + 32;
            low = (low + 1.8) + 32;
        } else if (!unitType.equals(mContext.getString(R.string.pref_units_metric))) {
            Log.i(LOG_TAG, "formatHighLows: UNIT TYPE NOT FOUND" + unitType);
        }

        long roundedHigh = Math.round(high);
        long roundedLow = Math.round(low);

        String highLowStr = roundedHigh + "/" + roundedLow;
        return highLowStr;
    }

    public static long addLocation(Context mContext, String locationSetting, String cityName, double lat, double lon) {
        long locationId;

        // Students: First, check if the location with this city name exists in the db
        Cursor mCursor = mContext.getContentResolver().query(
                WeatherContract.LocationEntry.CONTENT_URI,
                new String[]{WeatherContract.LocationEntry._ID},
                WeatherContract.LocationEntry.COLUMN_LOCATION_SETTING + " = ?",
                new String[]{locationSetting},
                null);
        // If it exists, return the current ID
        if (mCursor.moveToFirst()) {
            int locationIdIndex = mCursor.getColumnIndex(WeatherContract.LocationEntry._ID);
            locationId = mCursor.getLong(locationIdIndex);
        }// Otherwise, insert it using the content resolver and the base URI
        else {
            // Now that the content provider is set up, inserting rows of data is pretty simple
            // First create a ContentValues object to hold the data you want to insert.
            ContentValues locationValues = new ContentValues();

            // Then add the data, along with the corresponding name of the data type,
            // so the content provider knows what kind of value is being inserted.
            locationValues.put(WeatherContract.LocationEntry.COLUMN_CITY_NAME, cityName);
            locationValues.put(WeatherContract.LocationEntry.COLUMN_COORD_LAT, lat);
            locationValues.put(WeatherContract.LocationEntry.COLUMN_COORD_LONG, lon);
            locationValues.put(WeatherContract.LocationEntry.COLUMN_LOCATION_SETTING, locationSetting);

            Uri insertedUri = mContext.getContentResolver().insert(WeatherContract.LocationEntry.CONTENT_URI, locationValues);

            locationId = ContentUris.parseId(insertedUri);
        }

        return locationId;
    }

    /*
      Students: This code will allow the FetchWeatherTask to continue to return the strings that
      the UX expects so that we can continue to test the application even once we begin using
      the database.
   */
    public static String[] convertContentValuesToUXFormat(Context mContext, Vector<ContentValues> cvv) {
        // return strings to keep UI functional for now
        String[] resultStrs = new String[cvv.size()];
        for (int i = 0; i < cvv.size(); i++) {
            ContentValues weatherValues = cvv.elementAt(i);
            String highAndLow = formatHighLows(mContext,
                    weatherValues.getAsDouble(WeatherContract.WeatherEntry.COLUMN_MAX_TEMP),
                    weatherValues.getAsDouble(WeatherContract.WeatherEntry.COLUMN_MIN_TEMP));
            resultStrs[i] = getReadableDateString(
                    weatherValues.getAsLong(WeatherContract.WeatherEntry.COLUMN_DATE)) +
                    " - " + weatherValues.getAsString(WeatherContract.WeatherEntry.COLUMN_SHORT_DESC) +
                    " - " + highAndLow;
        }
        return resultStrs;
    }


}

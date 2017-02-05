package com.example.android.sunshine.app.ui;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import com.example.android.sunshine.app.R;
import com.example.android.sunshine.app.controler.PreferenceUtils;
import com.example.android.sunshine.app.model.constant.PreferenceKey;
import com.example.android.sunshine.app.model.constant.PreferenceValue;

/**
 * Created by Administrador on 07/04/2016.
 */
public class SettingsActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener {

    private PreferenceUtils prefs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add 'general' preferences, defined in the XML file
        getPreferenceManager().setSharedPreferencesName(PreferenceUtils.PREF_NAME);
        addPreferencesFromResource(R.xml.preferences);
        prefs = PreferenceUtils.getInstance(this);

        // For all preferences, attach an OnPreferenceChangeListener so the UI summary can be
        // updated when the preference changes.
        bindPreferenceSummaryToValue(findPreference(PreferenceKey.PREF_LOCATION_KEY));
        bindPreferenceSummaryToValue(findPreference(PreferenceKey.PREF_UNITS_KEY));
    }

    /**
     * Attaches a listener so the summary is always updated with the preference value.
     * Also fires the listener once, to initialize the summary (so it shows up before the value
     * is changed.)
     */
    private void bindPreferenceSummaryToValue(Preference preference) {
        // Set the listener to watch for value changes.
        preference.setOnPreferenceChangeListener(this);

        // Trigger the listener immediately with the preference's
        // current value.
        onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), PreferenceValue.PREF_DEFAULT_STRING));
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object value) {
        String stringValue = value.toString();

        /*if (preference instanceof ListPreference) {
            // For list preferences, look up the correct display value in
            // the preference's 'entries' list (since they have separate labels/values).
            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(stringValue);
            if (prefIndex >= 0) {
                preference.setSummary(listPreference.getEntries()[prefIndex]);
            }
        } else {
            // For other preferences, set the summary to the value's simple string representation.
            preference.setSummary(stringValue);
        }*/
        return true;
    }
}

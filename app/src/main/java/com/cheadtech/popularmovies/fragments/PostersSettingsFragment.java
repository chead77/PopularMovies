package com.cheadtech.popularmovies.fragments;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.preference.PreferenceScreen;

import com.cheadtech.popularmovies.R;

public class PostersSettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {
    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.pref_posters);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(requireContext());
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        for (int i = 0; i < preferenceScreen.getPreferenceCount(); i++) {
            Preference preference = preferenceScreen.getPreference(i);
            if (!(preference instanceof CheckBoxPreference))
                setPreferenceSummary(preference, prefs.getString(preference.getKey(), ""));
        }
        prefs.registerOnSharedPreferenceChangeListener(this);
    }

    private void setPreferenceSummary(Preference pref, String value) {
        if (pref instanceof ListPreference) {
            ListPreference listPreference = (ListPreference) pref;
            int prefIndex = listPreference.findIndexOfValue(value);
            if (prefIndex >= 0)
                listPreference.setSummary(listPreference.getEntries()[prefIndex]);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(requireContext()).unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference pref = findPreference(key);
        if (pref != null)
            if (!(pref instanceof CheckBoxPreference))
                setPreferenceSummary(pref, sharedPreferences.getString(pref.getKey(), ""));
    }
}

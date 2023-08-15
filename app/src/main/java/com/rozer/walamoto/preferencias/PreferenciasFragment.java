package com.rozer.walamoto.preferencias;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.rozer.walamoto.R;


public class PreferenciasFragment extends PreferenceFragmentCompat{
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferencias,rootKey);
    }
}

package com.example.apiconnect.fragments;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.apiconnect.R;
import com.example.apiconnect.activities.mainwindow.MainActivity;

public class PreferenceFragment extends PreferenceFragmentCompat  {

    MainActivity mainActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainActivity = (MainActivity) getActivity();

        addPreferencesFromResource(R.xml.main_preferences);

    }

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {







    }

}

package com.example.apiconnect.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.AsyncLayoutInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.apiconnect.R;



/**
 * A simple {@link Fragment} subclass.
 */
public class ActivityTwoFragment extends Fragment {


    public ActivityTwoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View root = inflater.inflate(R.layout.fragment_activity_two, container, false);

        AsyncLayoutInflater asyncLayoutInflater = new AsyncLayoutInflater(getContext());

        try {
            asyncLayoutInflater.inflate(R.layout.test, container, new AsyncLayoutInflater.OnInflateFinishedListener() {
                @Override
                public void onInflateFinished(@NonNull View view, int resid, @Nullable ViewGroup parent) {
                    parent.addView(view);
                    onViewCreated(view, savedInstanceState);

                    Log.i("Asyn","true");

                    //cover = view.findViewById(R.id.cover);
                    //SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
                    //mapFragment.getMapAsync(fragThree);
                }
            });
        }
        catch (Exception e){
            Log.i("Asyn","false");
        }

        return root;


    }


}

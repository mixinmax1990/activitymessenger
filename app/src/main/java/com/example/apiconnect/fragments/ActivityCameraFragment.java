package com.example.apiconnect.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.apiconnect.R;
import com.example.apiconnect.activities.camerawindow.ShowCamera;

public class ActivityCameraFragment extends Fragment {

    Camera camera;
    FrameLayout cameraview;
    ShowCamera showCamera;

    public ActivityCameraFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_activity_camera, container, false);

        cameraview = root.findViewById(R.id.cameraview);
        //Open Camera
        startCamera();





        return root;
    }

    public static boolean cameraIsRunning = false;

    public void startCamera(){
        if(!cameraIsRunning) {
            camera = Camera.open();
            showCamera = new ShowCamera(this.getContext(), camera);
            cameraview.addView(showCamera);
            cameraIsRunning = true;
        }
    }

    public void stopCamera(){
        if(cameraIsRunning){
            camera.stopPreview();
            camera.release();
            cameraIsRunning = false;
        }


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onResume() {
        //startCamera();
        super.onResume();
    }

    @Override
    public void onPause() {
        stopCamera();
        super.onPause();
    }

}


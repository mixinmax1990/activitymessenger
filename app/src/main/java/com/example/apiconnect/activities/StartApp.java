package com.example.apiconnect.activities;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;


import com.example.apiconnect.activities.mainwindow.MainActivity;
import com.example.apiconnect.R;
import com.example.apiconnect.jabber.connect.APIConnectionService;
import com.example.apiconnect.ui.login.LoginActivity;

public class StartApp extends AppCompatActivity {

    private boolean accountExists;
    private SharedPreferences prefs;
    private BroadcastReceiver mBroadcastReceiver;
    //StartUp Activity To Check if User is new
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_jabber_initial_connection);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        accountExists = prefs.contains("xmpp_jid");
        //deleteAccount();
        if(accountExists){
            // Connect to Jabber and start open Contact Lists
            Log.d("Tag", "User has already created an account");

            startApplication();
        }
        else{
            //Open up Login Page
            Log.d("Tag", "The user will have to create an account");
            registerAccount();
        }


    }


    private void registerAccount(){

        Intent i1 = new Intent(this, LoginActivity.class);
        startActivity(i1);

    }

    private void startApplication(){
        //Start the service
        Intent i1 = new Intent(this, APIConnectionService.class);
        i1.putExtra("Action", "Connect");
        startService(i1);

        Intent i2 = new Intent(this, MainActivity.class);
        startActivity(i2);
        finish();

    }

    private boolean deleteAccount(){

        SharedPreferences.Editor editor = prefs.edit();
        editor.remove("xmpp_jid");
        editor.remove("xmpp_password");
        editor.remove("xmpp_logged_in");
        editor.commit();

        return true;
    }

}

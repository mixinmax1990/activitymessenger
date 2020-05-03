package com.example.apiconnect.jabber.connect;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.Log;

import android.os.Handler;

import com.example.apiconnect.jabber.connect.JabberConnection;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;

import java.io.IOException;

public class APIConnectionService extends Service {


    private static final String TAG ="APIService";
    public static final String UI_AUTHENTICATED = "com.example.apiconnect.uiauthenticated";

    public static final String UI_REGISTERED = "com.example.apiconnect.uiregistered";

    public static final String SEND_MESSAGE = "com.blikoon.rooster.sendmessage";
    public static final String BUNDLE_MESSAGE_BODY = "b_body";
    public static final String BUNDLE_TO = "b_to";

    public static final String NEW_MESSAGE = "com.example.apiconnect.newmessage";
    public static final String BUNDLE_FROM_JID = "b_from";

    private boolean mActive;//Stores whether or not the thread is active
    private Thread mThread;
    private Handler mTHandler;//We use this handler to post messages to

    private String intentAction;
    //the background thread.

    public static JabberConnection.ConnectionState sConnectionState;
    public static JabberConnection.LoggedInState sLoggedInState;

    private JabberConnection mConnection;
    public APIConnectionService() {

    }

    public static JabberConnection.ConnectionState getState()
    {
        if (sConnectionState == null)
        {
            return JabberConnection.ConnectionState.DISCONNECTED;
        }
        return sConnectionState;
    }

    public static JabberConnection.LoggedInState getLoggedInState()
    {
        if (sLoggedInState == null)
        {
            return JabberConnection.LoggedInState.LOGGED_OUT;
        }
        return sLoggedInState;
    }

    private void initConnection()
    {

        Log.d(TAG,"initConnection()");

        if( mConnection == null)
        {
            mConnection = new JabberConnection(this, intentAction);
        }
        try
        {
            mConnection.connect();

        }catch (IOException | SmackException | XMPPException e )
        {
            Log.d(TAG,"Something went wrong while connecting ,make sure the credentials are right and try again");
            e.printStackTrace();
            //Stop the service all together.
            stopSelf();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"onCreate()");


    }


    public void start()
    {
        Log.d(TAG," Service Start() function called.");
        if(!mActive)
        {
            mActive = true;
            if( mThread ==null || !mThread.isAlive())
            {
                mThread = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        Looper.prepare();
                        mTHandler = new Handler();
                        initConnection();
                        //initConnection();
                        //THE CODE HERE RUNS IN A BACKGROUND THREAD.
                        Looper.loop();

                    }
                });
                mThread.start();
            }


        }


    }

    public void stop()
    {
        Log.d(TAG,"stop()");
        mActive = false;
        if( mTHandler !=null)
        {
            mTHandler.post(new Runnable() {
                @Override
                public void run() {

                    if( mConnection != null);
                    //CODE HERE IS MEANT TO SHUT DOWN OUR CONNECTION TO THE SERVER.
                    mConnection.disconnect();
                }
            });

        }


    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG,"onStartCommand()");

        intentAction = (String) intent.getExtras().get("Action");
        start();
        return Service.START_STICKY;
        //RETURNING START_STICKY CAUSES OUR CODE TO STICK AROUND WHEN THE APP ACTIVITY HAS DIED.
    }

    @Override
    public void onDestroy() {
        Log.d(TAG,"onDestroy()");
        super.onDestroy();
        stop();
    }
}

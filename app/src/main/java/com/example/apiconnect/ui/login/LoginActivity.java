package com.example.apiconnect.ui.login;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.example.apiconnect.jabber.connect.APIConnectionService;
import com.example.apiconnect.activities.mainwindow.MainActivity;
import com.example.apiconnect.R;
import com.example.apiconnect.data.database.local.controller.DatabaseController;
import com.example.apiconnect.data.database.local.models.ModelUser;

import java.util.Random;


public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private boolean cancel = false;
    private EditText usernameEditText;
    private EditText passwordEditText;
    public String Host = "ec2-3-124-124-253.eu-central-1.compute.amazonaws.com";
    private View mProgressView;
    private View mLoginFormView;

    private BroadcastReceiver mBroadcastReceiver;
    private Context mContext;

    //Define Database
    DatabaseController myDB;

    ModelUser user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = ViewModelProviders.of(this, (ViewModelProvider.Factory) new LoginViewModelFactory())
                .get(LoginViewModel.class);

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

        myDB = new DatabaseController(this);



        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                finish();
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                //loginViewModel.login(usernameEditText.getText().toString(),
              //          passwordEditText.getText().toString());
                Log.d("TAG","Jid and password are valid ,proceeding with login.");
                attemptLogin();




            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.unregisterReceiver(mBroadcastReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                String action = intent.getAction();
                switch (action)
                {
                    case APIConnectionService.UI_AUTHENTICATED:
                        Log.d("TAG","Got a broadcast to show the main app window");
                        //Show the main app window
                        //showProgress(false);
                        Intent i2 = new Intent(context,MainActivity.class);
                        startActivity(i2);

                        //Database Connection Store User Data
                        storeUserData();
                        finish();
                        break;
                    case APIConnectionService.UI_REGISTERED:
                        Log.d("TAG","User has been Registered, now Authenticate");
                        attemptLogin();
                        break;
                }

            }
        };
        IntentFilter filter = new IntentFilter(APIConnectionService.UI_AUTHENTICATED);
        this.registerReceiver(mBroadcastReceiver, filter);
    }

    private void storeUserData() {
        user = new ModelUser();
        user.setNewUser(usernameEditText.getText().toString()+"@"+Host, "01521436772", usernameEditText.getText().toString(), "timestamp", "Something", "online", "true");
        myDB.user.createNewUser(user);
    }


    private void attemptLogin() {

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            //focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            //showProgress(true);<<---FOR NOW WE DON'T WANT TO SEE THIS PROGRESS THING.
            //This is where the login login is fired up.
            Log.d("TAG","Jid and password are valid ,proceeding with login.");
            //startActivity(new Intent(this, MainActivity.class));

        }

        saveCredentialsAndLogin();
    }

    private void verifyPhoneNumber(){

        //Use this method to run a verification process of Entered Phone number
    }

    private String randomPassword(){
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(10);
        char tempChar;
        for (int i = 0; i < randomLength; i++){
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();

        startActivity(new Intent(this, MainActivity.class));
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }


    private void saveCredentialsAndLogin()
    {
        String password = randomPassword();
        Log.d("Password",password);
        Log.d("TAG","saveCredentialsAndLogin() called.");
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.edit()
                .putString("xmpp_jid", usernameEditText.getText().toString() + "@" + Host)
                .putString("xmpp_password", "seether1990")
                .putBoolean("xmpp_logged_in",true)
                .commit();

        //Start the service
        Intent i1 = new Intent(this, APIConnectionService.class);
        i1.putExtra("Action", "Register");
        startService(i1);

    }
}

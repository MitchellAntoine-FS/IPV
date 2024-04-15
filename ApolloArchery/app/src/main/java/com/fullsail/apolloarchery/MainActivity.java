package com.fullsail.apolloarchery;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.fullsail.apolloarchery.fragments.MainFragment;
import com.fullsail.apolloarchery.utils.AuthCallbackImpl;
import com.fullsail.apolloarchery.utils.AuthenticationManager;
import com.fullsail.apolloarchery.utils.HistoryRoundDatabase;

public class MainActivity extends AppCompatActivity implements AuthenticationManager.AuthenticationListener {
    private static final String TAG = "MainActivity";
    public static HistoryRoundDatabase historyRoundDatabase;
    private AuthenticationManager authManager;
    public boolean loggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the AuthenticationManager
        authManager = new AuthenticationManager();

        if (savedInstanceState == null) {

        }
        // Setup History round database
        historyRoundDatabase = Room.databaseBuilder(this, HistoryRoundDatabase.class, "HistoryRounds")
                .allowMainThreadQueries().build();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void loginUser(String email, String password) {
        AuthCallbackImpl authCallback = new AuthCallbackImpl();
        authManager.login(email, password, authCallback);
    }


    @Override
    public void onSuccess() {
        // User has been successfully logged in
        loggedIn = true;

        // Navigate to the MainFragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, MainFragment.newInstance(), MainFragment.TAG)
                .commit();
    }

    @Override
    public void onFailure(Exception e) {

    }
}


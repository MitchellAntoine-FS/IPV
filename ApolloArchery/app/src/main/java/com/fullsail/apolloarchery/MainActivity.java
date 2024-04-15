package com.fullsail.apolloarchery;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.fullsail.apolloarchery.authentication.AuthenticationCallback;
import com.fullsail.apolloarchery.authentication.AuthenticationManager;
import com.fullsail.apolloarchery.fragments.MainFragment;
import com.fullsail.apolloarchery.utils.HistoryRoundDatabase;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements AuthenticationCallback {
    private static final String TAG = "MainActivity";
    public static HistoryRoundDatabase historyRoundDatabase;
    private AuthenticationManager authManager;
    public boolean loggedIn;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the AuthenticationManager
        authManager = new AuthenticationManager();
        // Check if the user is already logged in
        FirebaseUser currentUser = authManager.getCurrentUser();


        if (currentUser == null) {
            // User is not logged in, navigate to LoginActivity
            loggedIn = false;
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }else {
            // User is not logged in
            loggedIn = true;

            // Navigate to the MainFragment
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, MainFragment.newInstance(), MainFragment.TAG)
                    .commit();
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
        authManager.login(email, password, (AuthenticationManager.AuthenticationCallback) this);
    }

    @Override
    public void onSuccess(FirebaseUser user) {
        // User has been successfully logged in
        loggedIn = true;

        // Navigate to the MainFragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, MainFragment.newInstance(), MainFragment.TAG)
                .commit();
    }

    @Override
    public void onFailure(Exception e) {
        // Display an error message
        Toast.makeText(context, "Login failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
}

}


package com.fullsail.apolloarchery;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.fullsail.apolloarchery.fragments.MainFragment;
import com.fullsail.apolloarchery.util.HistoryRoundDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public static HistoryRoundDatabase historyRoundDatabase;
    private FirebaseAuth mAuth;
    private static boolean loggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        Intent loggedIn_Intent = getIntent();
        loggedIn = loggedIn_Intent.getBooleanExtra(Intent.EXTRA_TEXT, false);

        if (savedInstanceState == null) {
            if (!loggedIn) {
                Intent logInIntent = new Intent(this, LoginActivity.class);
                startActivity(logInIntent);
            }else {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_container, MainFragment.newInstance(), MainFragment.TAG)
                        .commit();
            }
        }
        // Setup History round database
        historyRoundDatabase = Room.databaseBuilder(this, HistoryRoundDatabase.class, "HistoryRounds")
                .allowMainThreadQueries().build();
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Check to see if current user is logged in
        FirebaseUser currentUser = mAuth.getCurrentUser();
        Log.i(TAG, "onStart: "+ currentUser);

        if (currentUser != null) {
            currentUser.reload();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (loggedIn) {

            // Check to see if current user is logged in
            FirebaseUser currentUser = mAuth.getCurrentUser();
            Log.i(TAG, "onStart: "+ currentUser);

            if (currentUser != null) {
                loggedIn = true;
                currentUser.reload();
            }else {
                loggedIn = false;
            }
        }
    }

}
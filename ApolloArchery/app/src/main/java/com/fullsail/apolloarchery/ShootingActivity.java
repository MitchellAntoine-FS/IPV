package com.fullsail.apolloarchery;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.fullsail.apolloarchery.fragments.ShootingFragment;
import com.fullsail.apolloarchery.object.HistoryRounds;
import com.fullsail.apolloarchery.object.Round;
import com.fullsail.apolloarchery.object.ShootingListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

public class ShootingActivity extends AppCompatActivity implements ShootingListener {
    private static final String TAG = "ShootingActivity";

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    Round round;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shooting);

        Intent round_intent = getIntent();
        round = (Round) round_intent.getParcelableExtra("round");

        sharedPreferences = getSharedPreferences("Scoring", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        boolean roundInProgress = sharedPreferences.getBoolean("roundInProgress", false);
        // Using Gson to save round object
        final Gson gson = new Gson();

        // Check if a round is in progress
        if(!roundInProgress) {
            editor.putBoolean("roundInProgress", true);
            String jsonSave = gson.toJson(round);
            editor.putString("round", jsonSave);
            editor.apply();
        }

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.shooting_container, ShootingFragment.newInstance(), ShootingFragment.TAG)
                    .commit();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public Round getRound() {
        return round;
    }

    @Override
    public void nextRound(HistoryRounds roundScore, int score) {




    }

}
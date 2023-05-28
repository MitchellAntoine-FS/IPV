package com.fullsail.apolloarchery;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.fullsail.apolloarchery.fragments.ShootingFragment;
import com.fullsail.apolloarchery.object.Distance;
import com.fullsail.apolloarchery.object.ShootingListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ShootingActivity extends AppCompatActivity implements ShootingListener {
    private static final String TAG = "ShootingActivity";


    ArrayList<Distance> distances;
    private List<String> arrowsScoreList;

    Distance distance;
    int totalArrows = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shooting);

        arrowsScoreList = new ArrayList<>();
        distances = new ArrayList<>();

        Intent distance_intent = getIntent();

        distance = (Distance) distance_intent.getParcelableExtra("round");

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.shooting_container, ShootingFragment.newInstance(), ShootingFragment.TAG)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_shooting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.profile_btn) {
            Intent profileIntent = new Intent(this, ProfileActivity.class);
            startActivity(profileIntent);
        }

        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Store array as a string with Gson
        Gson gson = new Gson();
        String savedArrowScoreList = gson.toJson(arrowsScoreList);

        // Setting up shared preference to store arrow values
        SharedPreferences sharedPreferences = this.getSharedPreferences("Scoring", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        // We to save this with reference to the distance so we use that as the name for shared pref
        editor.putString(distance.getDistance(), savedArrowScoreList);
        editor.apply();

        // Updating arrow counter
        SharedPreferences sharedPreferencesArrowCounter = this.getSharedPreferences("ArrowCounter", Context.MODE_PRIVATE);
        int currentCounterValue = sharedPreferencesArrowCounter.getInt("counter", 0);
        int counter = currentCounterValue + totalArrows;
        // To deal with rare case where counter ends up negative
        if (counter < 0) {
            counter = 0;
        }
        sharedPreferencesArrowCounter.edit().putInt("counter", counter).apply();

    }

    @Override
    public Distance getDistance() {
        return distance;
    }


    @Override
    public void nextRound(int totalArrowsShot, List<String> arrowsScoreList) {
        this.arrowsScoreList = arrowsScoreList;
        this.totalArrows = totalArrowsShot;

        finish();
    }

}
package com.fullsail.apolloarchery;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.fullsail.apolloarchery.fragments.RoundSelectionFragment;
import com.fullsail.apolloarchery.object.Round;
import com.fullsail.apolloarchery.object.RoundSelectionListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RoundSelectionActivity extends AppCompatActivity implements RoundSelectionListener {
    private static final String TAG = "RoundSelectionActivity";

    TextView roundNameTextView, totalScoreTextView;
    private List<Integer> distanceValues = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_selection);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.round_selection_container,
                            RoundSelectionFragment.newInstance(), RoundSelectionFragment.TAG)
                    .commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setup();
    }

    @Override
    public void onPause() {
        super.onPause();
        Gson gson = new Gson();
        String savedDistanceValues = gson.toJson(distanceValues);
        SharedPreferences sharedPreferences = this.getSharedPreferences("Scoring", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("distanceValues", savedDistanceValues);
        editor.apply();
    }

    @Override
    public Round getSelectedRound() {
        return null;
    }

    private void setup() {

        Intent roundList_intent = getIntent();
        Round round = roundList_intent.getParcelableExtra("round");

        SharedPreferences sharedPreferences = this.getSharedPreferences("Scoring", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        // Check if a round is in progress
        boolean roundInProgress = sharedPreferences.getBoolean("roundInProgress", false);
        // Using GSON to save round object
        final Gson gson = new Gson();

        if(!roundInProgress) {
            editor.putBoolean("roundInProgress", true);
            String jsonSave = gson.toJson(round);
            editor.putString("round", jsonSave);
            editor.apply();
        }

        String roundName = "";
        int scoringType;
        List<String> arrowsDistance;
        Round roundLoad = null;
        int totalArrowsShot = 0;

        List<String> distances;

        if (roundInProgress) {
            // Setting the values using shared preferences if the round is already in progress
            String jsonLoad = sharedPreferences.getString("round", "");
            roundLoad = gson.fromJson(jsonLoad, Round.class);

            arrowsDistance = roundLoad.getArrowsDistances();
            scoringType = roundLoad.getScoringType();
            distances = roundLoad.getDistances();

            String savedDistanceValues = sharedPreferences.getString("distanceValues", null);
            // Needs to look at the unpacked version NOT the string from sharedPref
            // Getting the data from the gson file
            Type distanceValuesType = new TypeToken<List<Integer>>(){}.getType();
            distanceValues = gson.fromJson(savedDistanceValues, distanceValuesType);
        }else {
            // Setting values as the round has been started from the round selection activity
            assert round != null;
            roundName = round.getRoundName();
            arrowsDistance = round.getArrowsDistances();
            scoringType = round.getScoringType();
            distances = round.getDistances();

            /* Creating the distanceValues array which is full of zeroes to be updated, this
               handles the case when a distance hasn't been shot yet hence the loop exits on the
               first if condition */
            for (int i = 0; i < distances.size(); i++) {
                assert distanceValues != null;
                distanceValues.add(0);

                Log.i("Scoring", "Distance Value array of 0's Size: " + distanceValues.size());
            }
        }

        roundNameTextView = findViewById(R.id.selected_round_name);
        totalScoreTextView = findViewById(R.id.total_score_textView);

        assert roundName != null;
        roundNameTextView.setText(roundName);

        for (int i = 0; i < distances.size(); i++) {
            String arrowValuesString = sharedPreferences.getString(distances.get(i), null);
            Type arrowValueType = new TypeToken<List<String>>(){}.getType();

            if (arrowValuesString != null) {

                List<String> savedScoreList = gson.fromJson(arrowValuesString, arrowValueType);

                int current = 0;
                for (int j = 0; j < savedScoreList.size(); j++) {
                    if (savedScoreList.contains("M")) {
                        current += 0;
                    } else if (savedScoreList.contains("X")) {
                        current += 10;
                    } else {
                        current += Integer.parseInt(savedScoreList.get(i));
                    }

                    if (!savedScoreList.contains(" ")) {
                        totalArrowsShot += 1;

                        Log.i("Scoring", "Total arrows shot: " + totalArrowsShot);
                    }
                }
                assert distanceValues != null;
                distanceValues.set(i, current);
                Log.i("Scoring", "Distance Value Size: " + distanceValues.size());
            }
        }

        int maxArrowVal;

        if (scoringType == 0 || scoringType == 2 || scoringType == 3) {
            maxArrowVal = 10;
        }
        else if (scoringType == 1) {
            maxArrowVal = 9;
        }
        else if (scoringType == 4){
            maxArrowVal = 5;
        }
        else {
            maxArrowVal = 0;
            Log.d("Scoring", "Error loading from shared preferences");

        }

        int totalArrows = 0;
        for (int i = 0; i < arrowsDistance.size(); i++) {
            totalArrows += Integer.parseInt(arrowsDistance.get(i));
        }
        int totalScore = totalArrows * maxArrowVal;

        // Calculating round total score
        int currentScore = 0;

        if (distanceValues != null) {
            for (int i = 0; i < distanceValues.size(); i++) {
                currentScore += distanceValues.get(i);
            }
        }
        String totalScoreString = currentScore + "/" + totalScore;
        totalScoreTextView.setText(totalScoreString);

        final Round roundSend;
        if (roundInProgress) {

            roundSend = roundLoad;
        }
        else {
            roundSend = round;
        }

        ListView roundListView = findViewById(R.id.round_selection_list);
        // Need set the inputs for the adapter depending on if the activity was started
        roundListView.setAdapter(new RoundSelectionFragment.RoundSelectionAdapter(roundSend, maxArrowVal, distanceValues));

        // Save button
        FloatingActionButton saveFAB = findViewById(R.id.save_fAB);

        // The save button only appears when the round has been completed
        if (totalArrowsShot == totalArrows) {

            saveFAB.setVisibility(View.VISIBLE);
        }
        else {
            saveFAB.setVisibility(View.VISIBLE);
        }

        final int finalCurrentScore = currentScore;

        saveFAB.setOnClickListener(v -> {

            // Getting the current date
            String date = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MMM-yy"));
            }
            // Using gson to serialise the round object
            String serialisedRound = gson.toJson(roundSend);
            // Using gson to serialise list of arrow values
            String serialisedArrowValues = gson.toJson(distanceValues);
            // Adding completed round to database
            Long id = MainActivity.historyRoundDatabase.HistoryRoundDoa().saveCompletedRound(date, roundSend.getRoundName(),
                    serialisedRound, serialisedArrowValues, finalCurrentScore, "", "");

            // Resetting roundInProgress information
            editor.clear();
            editor.putBoolean("roundInProgress", false);
            editor.apply();

            Intent intentSave = new Intent(this, ScoreCardsActivity.class);
            intentSave.putExtra("id", id);
            this.startActivity(intentSave);

        });
    }

}
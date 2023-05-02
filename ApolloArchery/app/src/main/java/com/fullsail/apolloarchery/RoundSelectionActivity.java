package com.fullsail.apolloarchery;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.fullsail.apolloarchery.fragments.RoundSelectionFragment;
import com.fullsail.apolloarchery.object.Round;
import com.fullsail.apolloarchery.object.RoundSelectionListener;

public class RoundSelectionActivity extends AppCompatActivity implements RoundSelectionListener {
    private static final String TAG = "RoundSelectionActivity";

    Round round;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_selection);


        Intent roundList_intent = getIntent();
        round = roundList_intent.getParcelableExtra("round");

        Log.i(TAG, "onCreate: " + round.getRoundName());

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.round_selection_container,
                            RoundSelectionFragment.newInstance(), RoundSelectionFragment.TAG)
                    .commit();
        }
    }

    @Override
    public Round getRoundList() {
        return round;
    }

}
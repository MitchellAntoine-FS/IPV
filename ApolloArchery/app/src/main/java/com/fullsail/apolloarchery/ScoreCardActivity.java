package com.fullsail.apolloarchery;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.fullsail.apolloarchery.fragments.ScoreCardFragment;
import com.fullsail.apolloarchery.object.HistoryRound;
import com.fullsail.apolloarchery.object.ScoreCardListener;

public class ScoreCardActivity extends AppCompatActivity implements ScoreCardListener {

    HistoryRound historyRound;

    int position;  // int position used to delete file

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_card);

        Intent history_intent = getIntent();
        historyRound = history_intent.getParcelableExtra("history");
        position = history_intent.getIntExtra("position", 0);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.scorecard_container, ScoreCardFragment.newInstance(), ScoreCardFragment.TAG)
                    .commit();
        }
    }

    @Override
    public HistoryRound getRoundScore() {
        return historyRound;
    }
}
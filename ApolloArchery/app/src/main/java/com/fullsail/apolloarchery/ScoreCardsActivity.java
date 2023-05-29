package com.fullsail.apolloarchery;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.fullsail.apolloarchery.fragments.ScoreCardFragment;
import com.fullsail.apolloarchery.object.HistoryRounds;
import com.fullsail.apolloarchery.object.ScoreCardListener;

public class ScoreCardsActivity extends AppCompatActivity implements ScoreCardListener {

    HistoryRounds historyRound;

    long id;  // int position used to delete file

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_card);

        Intent history_intent = getIntent();
        historyRound = (HistoryRounds) history_intent.getSerializableExtra("id");
        id = history_intent.getLongExtra("id", 0);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.scorecard_container, ScoreCardFragment.newInstance(), ScoreCardFragment.TAG)
                    .commit();
        }
    }

    @Override
    public HistoryRounds getRoundScore() {
        return historyRound;
    }

    @Override
    public long getID() {
        return id;
    }
}
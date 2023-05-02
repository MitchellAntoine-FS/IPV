package com.fullsail.apolloarchery;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.fullsail.apolloarchery.fragments.ShootingFragment;
import com.fullsail.apolloarchery.object.HistoryRound;
import com.fullsail.apolloarchery.object.Round;
import com.fullsail.apolloarchery.object.ShootingListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class ShootingActivity extends AppCompatActivity implements ShootingListener {
    private static final String TAG = "ShootingActivity";

    Round round;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shooting);

        Intent round_intent = getIntent();
        round = (Round) round_intent.getParcelableExtra("round");

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.shooting_container, ShootingFragment.newInstance(), ShootingFragment.TAG)
                    .commit();
        }
    }

    @Override
    public Round getRound() {
        return round;
    }

    @Override
    public void nextRound(HistoryRound roundScore, int score) {

        HistoryRound fireStoreSave = new HistoryRound(roundScore.getDate(), roundScore.getRoundName(),
                roundScore.getRound(), roundScore.getTotalScore(), roundScore.getArrowValues());
        db.collection("history").document("portsmouth-id").set(fireStoreSave);

        finish();
    }

}
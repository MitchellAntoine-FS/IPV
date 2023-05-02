package com.fullsail.apolloarchery;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.fullsail.apolloarchery.fragments.ProfileFragment;
import com.fullsail.apolloarchery.object.HistoryListener;
import com.fullsail.apolloarchery.object.HistoryRound;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity implements HistoryListener {

    ArrayList<HistoryRound> historyRound;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        historyRound = new ArrayList<>();


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.profile_container, ProfileFragment.newInstance(), ProfileFragment.TAG)
                    .commit();
        }
    }

    @Override
    public ArrayList<HistoryRound> getHistory() {

        DocumentReference docRef = db.collection("history").document("portsmouth-id");
        docRef.get().addOnSuccessListener(documentSnapshot -> {
          HistoryRound h = documentSnapshot.toObject(HistoryRound.class);

            if (h != null) {
                historyRound.add(new HistoryRound(h.getDate(), h.getRoundName(),
                        h.getRound(), h.getTotalScore(), h.getArrowValues()));
            }
        });

        return historyRound;
    }

    @Override
    public void getHistoricalData(HistoryRound historyRound, int position) {

        Intent intent = new Intent(this, ScoreCardActivity.class);
        intent.putExtra("history", historyRound);
        intent.putExtra("position", position);
        startActivity(intent);
    }


}
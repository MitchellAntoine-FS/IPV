package com.fullsail.apolloarchery;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.fullsail.apolloarchery.fragments.ProfileFragment;
import com.fullsail.apolloarchery.object.HistoryListener;
import com.fullsail.apolloarchery.object.HistoryRounds;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity implements HistoryListener {

    ArrayList<HistoryRounds> historyRounds;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        historyRounds = new ArrayList<>();


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.profile_container, ProfileFragment.newInstance(), ProfileFragment.TAG)
                    .commit();
        }
    }

    @Override
    public ArrayList<HistoryRounds> getHistory() {

        DocumentReference docRef = db.collection("history").document("portsmouth-id");
        docRef.get().addOnSuccessListener(documentSnapshot -> {
          HistoryRounds h = documentSnapshot.toObject(HistoryRounds.class);

            if (h != null) {

            }
        });

        return historyRounds;
    }

    @Override
    public void getHistoricalData(HistoryRounds historyRounds, int position) {

        Intent intent = new Intent(this, ScoreCardActivity.class);
//        intent.putExtra("history", historyRounds);
//        intent.putExtra("position", position);
        startActivity(intent);
    }


}
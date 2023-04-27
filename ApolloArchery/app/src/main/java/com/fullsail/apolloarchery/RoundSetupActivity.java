package com.fullsail.apolloarchery;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.fullsail.apolloarchery.fragments.RoundSetupFragment;
import com.fullsail.apolloarchery.object.Round;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class RoundSetupActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_setup);

        // Add Rounds to Firestore for use's round setup
        addNewRoundsFirestore();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.round_setup_container, RoundSetupFragment.newInstance(), RoundSetupFragment.TAG)
                    .commit();
        }
    }

    private void addNewRoundsFirestore() {

        Round round = new Round("Portsmouth", 2,
                new ArrayList<String>() {{ add("First half"); add("Second half"); }},
                new ArrayList<String>() {{ add("30"); add("30");  }},
                3);

        db.collection("rounds").document("portsmouth-id").set(round);

    }
}
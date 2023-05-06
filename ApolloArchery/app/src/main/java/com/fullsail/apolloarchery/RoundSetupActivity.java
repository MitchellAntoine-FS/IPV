package com.fullsail.apolloarchery;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.fullsail.apolloarchery.fragments.RoundSetupFragment;
import com.fullsail.apolloarchery.object.Round;
import com.fullsail.apolloarchery.object.RoundSetupListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class RoundSetupActivity extends AppCompatActivity implements RoundSetupListener {

    ArrayList<Round> roundsNameList;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_setup);

        roundsNameList = new ArrayList<>();
        // Add Rounds to Firestore for use's round setup
        addNewRoundsFirestore();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.round_setup_container, RoundSetupFragment.newInstance(), RoundSetupFragment.TAG)
                    .commit();
        }
    }

    private void addNewRoundsFirestore() {

        Round round1 = new Round("Portsmouth", 2,
                new ArrayList<String>() {{ add("First half"); add("Second half"); }},
                new ArrayList<String>() {{ add("30"); add("30");  }},
                3);
        db.collection("rounds").document("portsmouth-id").set(round1);
        roundsNameList.add(round1);

        Round round2 = new Round("WA1440 (Gents)", 0,
                new ArrayList<String>() {{ add("90m"); add("70m"); add("50m"); add("30m"); }},
                new ArrayList<String>() {{ add("36"); add("36"); add("36"); add("36"); }},
                6);
        db.collection("rounds").document("wa1440_gents_id").set(round2);
        roundsNameList.add(round2);

        Round round3 = new Round("WA1440 (Ladies)", 0,
                        new ArrayList<String>() {{ add("70m"); add("60m"); add("50m"); add("30m"); }},
                        new ArrayList<String>() {{ add("36"); add("36"); add("36"); add("36"); }},
                        6);
        db.collection("rounds").document("wa1440_ladies_id").set(round3);
        roundsNameList.add(round3);

        Round round4 = new Round("WA 70", 0,
                        new ArrayList<String>() {{ add("First half"); add("Second half"); }},
                        new ArrayList<String>() {{ add("36"); add("36"); }},
                        6);
        db.collection("rounds").document("wa70_id").set(round4);
        roundsNameList.add(round4);

        Round round5 = new Round("WA18 (Full Face)", 2,
                new ArrayList<String>() {{ add("First half"); add("Second half"); }},
                new ArrayList<String>() {{ add("30"); add("30");  }},
                3);
        db.collection("rounds").document("wa18_id").set(round5);
        roundsNameList.add(round5);


    }

    @Override
    public ArrayList<Round> getRoundList() {
        return roundsNameList;
    }
}
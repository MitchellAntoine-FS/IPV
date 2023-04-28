package com.fullsail.apolloarchery;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.fullsail.apolloarchery.fragments.RoundSelectionFragment;
import com.fullsail.apolloarchery.object.Round;
import com.fullsail.apolloarchery.object.RoundSelectionListener;

import java.util.ArrayList;

public class RoundSelectionActivity extends AppCompatActivity implements RoundSelectionListener {

    ArrayList<Round> roundList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_selection);

        roundList = new ArrayList<>();
        Intent roundList_intent = getIntent();
        roundList = roundList_intent.getParcelableArrayListExtra("roundList");

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.round_selection_container, RoundSelectionFragment.newInstance(), RoundSelectionFragment.TAG)
                .commit();
    }

    @Override
    public ArrayList<Round> getRoundList() {


        return roundList;
    }
}
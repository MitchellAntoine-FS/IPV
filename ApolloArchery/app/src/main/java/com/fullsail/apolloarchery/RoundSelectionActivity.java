package com.fullsail.apolloarchery;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.fullsail.apolloarchery.fragments.RoundSelectionFragment;

public class RoundSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_selection);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.round_selection_container, RoundSelectionFragment.newInstance(), RoundSelectionFragment.TAG)
                .commit();
    }
}
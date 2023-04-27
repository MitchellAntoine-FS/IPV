package com.fullsail.apolloarchery;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.fullsail.apolloarchery.fragments.RoundSetupFragment;

public class RoundSetupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_setup);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.round_setup_container, RoundSetupFragment.newInstance(), RoundSetupFragment.TAG)
                .commit();
    }
}
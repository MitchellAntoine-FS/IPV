package com.fullsail.apolloarchery;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.fullsail.apolloarchery.fragments.ArrowCounterFragment;
import com.fullsail.apolloarchery.object.ArrowCounterListener;

public class ArrowCounterActivity extends AppCompatActivity implements ArrowCounterListener {

    private int arrowCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arrow_counter);


        Intent intent = getIntent();
        arrowCount = intent.getIntExtra("count", 0);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.arrow_counter_container, ArrowCounterFragment.newInstance(), ArrowCounterFragment.TAG)
                    .commit();
        }

    }

    @Override
    public int getCount() {
        return arrowCount;
    }
}
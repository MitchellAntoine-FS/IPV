package com.fullsail.apolloarchery;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.fullsail.apolloarchery.fragments.SightMarksFragment;

public class SightMarksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sight_marks);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.sight_marks_container, SightMarksFragment.newInstance(), SightMarksFragment.TAG)
                    .commit();
        }
    }
}
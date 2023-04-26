package com.fullsail.apolloarchery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.fullsail.apolloarchery.fragments.CreateAccountFragment;
import com.fullsail.apolloarchery.object.CreateAccountListener;

public class CreateAccountActivity extends AppCompatActivity implements CreateAccountListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.create_account_container,
                        CreateAccountFragment.newInstance(), CreateAccountFragment.TAG)
                .commit();
    }

    @Override
    public void closeSignup() {
        boolean loggedIn = true;
        Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
        mainIntent.putExtra(Intent.EXTRA_TEXT, loggedIn);
        startActivity(mainIntent);
        finish();
    }
}
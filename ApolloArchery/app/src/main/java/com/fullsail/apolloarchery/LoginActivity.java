package com.fullsail.apolloarchery;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.fullsail.apolloarchery.fragments.LoginFragment;
import com.fullsail.apolloarchery.object.LogInListener;

public class LoginActivity extends AppCompatActivity implements LogInListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.login_container, LoginFragment.newInstance(), LoginFragment.TAG)
                    .commit();
        }

    }

    @Override
    public void closeLogIn() {
        boolean loggedIn = true;
        Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
        mainIntent.putExtra(Intent.EXTRA_TEXT, loggedIn);
        startActivity(mainIntent);
        finish();
    }
}
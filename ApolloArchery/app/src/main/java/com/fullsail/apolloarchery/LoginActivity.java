package com.fullsail.apolloarchery;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.fullsail.apolloarchery.fragments.LoginFragment;

public class LoginActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private final View view;

    public LoginActivity(View view) {
        this.view = view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressBar = view.findViewById(R.id.login_progress_bar);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.login_container, LoginFragment.newInstance(), LoginFragment.TAG)
                    .commit();
        }
    }

    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

}
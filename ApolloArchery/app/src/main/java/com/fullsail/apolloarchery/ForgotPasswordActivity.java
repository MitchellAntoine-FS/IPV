package com.fullsail.apolloarchery;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.fullsail.apolloarchery.fragments.ForgotPassWordFragment;

public class ForgotPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_passwrod);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.forgot_password_container, ForgotPassWordFragment.newInstance(), ForgotPassWordFragment.TAG)
                    .commit();
        }
    }
}
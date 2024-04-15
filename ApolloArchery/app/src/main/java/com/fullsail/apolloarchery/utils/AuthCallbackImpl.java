package com.fullsail.apolloarchery.utils;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.widget.Toast;

import com.fullsail.apolloarchery.LoginActivity;
import com.fullsail.apolloarchery.MainActivity;
import com.google.firebase.auth.FirebaseUser;

public class AuthCallbackImpl implements AuthenticationManager.AuthenticationCallback {

    @Override
    public void onSuccess(FirebaseUser user) {
        // Navigate to the main activity or update the UI
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onFailure(Exception e) {
        // Display an error message or take appropriate action
        Toast.makeText(LoginActivity.this, "Authentication failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
    }
}

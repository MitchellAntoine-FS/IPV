package com.fullsail.apolloarchery;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.fullsail.apolloarchery.authentication.AuthenticationManager;
import com.google.firebase.auth.FirebaseUser;

public class LauncherActivity extends AppCompatActivity {
private AuthenticationManager authManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_launcher);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        authManager = new AuthenticationManager();

        FirebaseUser currentUser = authManager.getCurrentUser();
        if (currentUser != null) {
            // User is logged in, navigate to MainActivity
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {
            // User is not logged in, navigate to LoginActivity
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }
}
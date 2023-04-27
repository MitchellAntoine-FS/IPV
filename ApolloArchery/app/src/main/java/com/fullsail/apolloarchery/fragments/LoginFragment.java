package com.fullsail.apolloarchery.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.fullsail.apolloarchery.CreateAccountActivity;
import com.fullsail.apolloarchery.ForgotPasswordActivity;
import com.fullsail.apolloarchery.R;
import com.fullsail.apolloarchery.object.LogInListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class LoginFragment extends Fragment {
    public static final String TAG = "LoginFragment.TAG";
    private FirebaseAuth mAuth;
    EditText etEmail, etPassword;
    Button logInBtn;
    TextView createAccountBtn, forgotPWordBtn;
    LogInListener mListener;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof LogInListener){
            mListener = (LogInListener) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Instantiate objects
        mAuth = FirebaseAuth.getInstance();

        // If user has no account, send to create an account.
        createAccountBtn = view.findViewById(R.id.create_account_btn);
        createAccountBtn.setOnClickListener(v -> {
            Intent createAccountIntent = new Intent(requireContext(), CreateAccountActivity.class);
            startActivity(createAccountIntent);
        });

        // Forgot Password
        forgotPWordBtn = view.findViewById(R.id.forgot_password_btn);
        forgotPWordBtn.setOnClickListener(v -> {
            Intent forgotPWordIntent = new Intent(requireContext(), ForgotPasswordActivity.class);
            startActivity(forgotPWordIntent);
        });

        logInBtn = view.findViewById(R.id.login_btn);
        logInBtn.setOnClickListener(v -> {
            // Get User email entry
            etEmail = view.findViewById(R.id.login_email_entry);
            String email = etEmail.getText().toString();

            if (TextUtils.isEmpty(email)) {
                etEmail.setError("Required.");
            }else {
                etEmail.setError(null);
            }

            // Get user password entry
            etPassword = view.findViewById(R.id.login_password_entry);
            String password = etPassword.getText().toString();

            if (TextUtils.isEmpty(password)) {
                etPassword.setError("Required.");
            }else {
                etPassword.setError(null);
            }

            if (!(email.trim().length() == 0) || !(password.trim().length() == 0)) {
                // Sign in with email and password
                signInWithEmailPassword(email, password);
            }
        });
    }

    private void signInWithEmailPassword(String email, String password) {
        Log.i(TAG, "signInWithEmailPassword: " + email);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(requireActivity(),
                task -> {
                    if (task.isSuccessful()) {
                        // Sign in success
                        Log.d(TAG, "signInWithEmail: success");

                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                        Toast.makeText(getContext(),"Hello Again " +  Objects.requireNonNull(user).getDisplayName(),
                                Toast.LENGTH_SHORT).show();

                        mListener.closeLogIn();

                    }else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail: failure", task.getException());
                        Toast.makeText(getContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}

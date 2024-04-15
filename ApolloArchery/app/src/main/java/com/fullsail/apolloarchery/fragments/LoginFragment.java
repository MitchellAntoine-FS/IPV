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
import com.fullsail.apolloarchery.LoginActivity;
import com.fullsail.apolloarchery.MainActivity;
import com.fullsail.apolloarchery.R;
import com.fullsail.apolloarchery.authentication.AuthenticationCallback;
import com.fullsail.apolloarchery.authentication.AuthenticationManager;
import com.fullsail.apolloarchery.object.LogInListener;
import com.google.firebase.auth.FirebaseUser;

public class LoginFragment extends Fragment implements AuthenticationCallback {
    public static final String TAG = "LoginFragment.TAG";
    EditText etEmail, etPassword;
    Button logInBtn;
    TextView createAccountBtn, forgotPWordBtn;
    LogInListener mListener;
    private AuthenticationManager authManager;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authManager = new AuthenticationManager();
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

            if (!(email.trim().isEmpty()) || !(password.trim().isEmpty())) {
                // Sign in with email and password
                signInWithEmailPassword(email, password);
            }
        });
    }

    private void signInWithEmailPassword(String email, String password) {
        Log.i(TAG, "signInWithEmailPassword: " + email);

        handleLoginSubmit(email, password);

        mListener.closeLogIn();
    }

    private void handleLoginSubmit(String email, String password) {
        // Show the progress bar
        ((LoginActivity) getActivity()).showProgressBar();

        authManager.login(email, password, (AuthenticationManager.AuthenticationCallback) this);
    }

    @Override
    public void onSuccess(FirebaseUser user) {
        // Sign-in success
        // Hide the progress bar
        ((LoginActivity) getActivity()).hideProgressBar();

        // You can navigate to the MainActivity or update the UI here
        Log.d(TAG, "signInWithEmail: success");
        Toast.makeText(getContext(), "Hello Again " + user.getDisplayName(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        requireActivity().finish();
    }

    @Override
    public void onFailure(Exception e) {
        // Sign-in failure
        // Hide the progress bar
        ((LoginActivity) getActivity()).hideProgressBar();

        // Display an error message or take appropriate action
        Log.w(TAG, "signInWithEmail: failure", e);
        Toast.makeText(getContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
    }

}

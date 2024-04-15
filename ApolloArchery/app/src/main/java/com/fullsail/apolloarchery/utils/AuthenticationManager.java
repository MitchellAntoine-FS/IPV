package com.fullsail.apolloarchery.utils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthenticationManager {

    private final FirebaseAuth mAuth; // Instance of FirebaseAuth for authentication
    private boolean loggedIn; // Flag to track the user's login state

    // Getter for the loggedIn flag
    public boolean isLoggedIn() {
        return loggedIn;
    }

    // Setter for the loggedIn flag
    public void setLoggedIn(boolean isLoggedIn) {
        loggedIn = isLoggedIn;
    }

    // Constructor to initialize the FirebaseAuth instance
    public AuthenticationManager() {
        mAuth = FirebaseAuth.getInstance();
    }

    // Method to log out the current user
    public void logout() {
        mAuth.signOut();
        loggedIn = false;
    }

    // Interface for authentication success/failure callbacks
    public interface AuthenticationListener {
        void onSuccess();
        void onFailure(Exception e);
    }

    private AuthenticationListener authenticationListener; // Instance of AuthenticationListener

    // Method to set the AuthenticationListener instance
    public void setAuthenticationListener(AuthenticationListener listener) {
        this.authenticationListener = listener;
    }

    // Interface for authentication callbacks with user data
    public interface AuthenticationCallback {
        void onSuccess(FirebaseUser user);
        void onFailure(Exception e);
    }

    // Method to log in a user with email and password
    public void login(String email, String password, AuthenticationCallback callback) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    callback.onSuccess(user); // Call the onSuccess callback with the user data
                } else {
                    callback.onFailure(task.getException()); // Call the onFailure callback with the exception
                }
            })
            .addOnFailureListener(callback::onFailure); // Call the onFailure callback if an exception occurs
    }

    // Method to register a new user with email and password
    public void register(String email, String password, AuthenticationCallback callback) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    callback.onSuccess(user); // Call the onSuccess callback with the user data
                } else {
                    callback.onFailure(task.getException()); // Call the onFailure callback with the exception
                }
            })
            .addOnFailureListener(callback::onFailure); // Call the onFailure callback if an exception occurs
    }

    // Method to send a password reset email
    public void resetPassword(String email, AuthenticationListener listener) {
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        listener.onSuccess(); // Call the onSuccess callback
                    } else {
                        listener.onFailure(task.getException()); // Call the onFailure callback with the exception
                    }
                })
                .addOnFailureListener(listener::onFailure); // Call the onFailure callback if an exception occurs
    }

    // Method to get the current user
    public FirebaseUser getCurrentUser() {
        return mAuth.getCurrentUser();
    }
}

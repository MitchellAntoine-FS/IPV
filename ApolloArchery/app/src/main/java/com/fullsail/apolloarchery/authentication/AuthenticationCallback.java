package com.fullsail.apolloarchery.authentication;

import com.google.firebase.auth.FirebaseUser;

public interface AuthenticationCallback {
    void onSuccess(FirebaseUser user);
    void onFailure(Exception e);
}

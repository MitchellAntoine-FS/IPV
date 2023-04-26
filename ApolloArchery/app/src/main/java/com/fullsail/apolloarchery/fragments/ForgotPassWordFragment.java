package com.fullsail.apolloarchery.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fullsail.apolloarchery.R;

public class ForgotPassWordFragment extends Fragment {


    public ForgotPassWordFragment() {
        // Required empty public constructor
    }

    public static ForgotPassWordFragment newInstance() {
        return new ForgotPassWordFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forgot_password, container, false);
    }

}
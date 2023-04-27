package com.fullsail.apolloarchery.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.fullsail.apolloarchery.R;
import com.fullsail.apolloarchery.RoundSetupActivity;


public class MainFragment extends Fragment {
    public static final String TAG = "MainFragment.TAG";

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance() {

        return new MainFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView newRoundBtn = view.findViewById(R.id.new_round_btn);
        ImageView profileBtn = view.findViewById(R.id.profile_btn_main);
        ImageView arrowCounterBtn = view.findViewById(R.id.arrow_counter_btn);
        ImageView siteMarksBtn = view.findViewById(R.id.site_marks_btn);

        newRoundBtn.setOnClickListener(v -> {
            Intent roundSetupIntent = new Intent(requireContext(), RoundSetupActivity.class);
            startActivity(roundSetupIntent);
        });
    }
}
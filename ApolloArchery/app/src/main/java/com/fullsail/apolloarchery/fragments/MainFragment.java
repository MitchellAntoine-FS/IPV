package com.fullsail.apolloarchery.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.fullsail.apolloarchery.ArrowCounterActivity;
import com.fullsail.apolloarchery.ProfileActivity;
import com.fullsail.apolloarchery.R;
import com.fullsail.apolloarchery.RoundSelectionActivity;
import com.fullsail.apolloarchery.RoundSetupActivity;
import com.fullsail.apolloarchery.SightMarksActivity;

import java.time.LocalDate;


public class MainFragment extends Fragment {
    public static final String TAG = "MainFragment.TAG";

    private SharedPreferences sharedPreferencesArrowCounter;
    private SharedPreferences sharedPreferencesScoring;
    private SharedPreferences.Editor editor;
    TextView arrowDayTotals;
    ImageView newRoundBtn;
    TextView newRoundLabel;
    private boolean roundInProgress;
    private int dailyCount;


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

        newRoundBtn = view.findViewById(R.id.new_round_btn);
        ImageView profileBtn = view.findViewById(R.id.profile_btn_main);
        ImageView arrowCounterBtn = view.findViewById(R.id.arrow_counter_btn);
        ImageView siteMarksBtn = view.findViewById(R.id.site_marks_btn);
        arrowDayTotals = view.findViewById(R.id.arrow_count_display);
        newRoundLabel = view.findViewById(R.id.new_round_label);

        sharedPreferencesScoring = requireActivity().getSharedPreferences("Scoring", Context.MODE_PRIVATE);
        roundInProgress = sharedPreferencesScoring.getBoolean("roundInProgress", false);

        if (!roundInProgress) {
            newRoundBtn.setOnClickListener(v -> {
                newRoundLabel.setText(R.string.new_round);
                Intent roundSetupIntent = new Intent(requireContext(), RoundSetupActivity.class);
                startActivity(roundSetupIntent);
            });
        }else {
            newRoundBtn.setOnClickListener(v -> {
                newRoundLabel.setText(R.string.continue_round);
                Intent roundSetupIntent = new Intent(requireContext(), RoundSelectionActivity.class);
                startActivity(roundSetupIntent);
            });
        }

        profileBtn.setOnClickListener(v -> {
            Intent profileIntent = new Intent(getContext(), ProfileActivity.class);
            startActivity(profileIntent);
        });

        arrowCounterBtn.setOnClickListener(v -> {
            Intent counterIntent = new Intent(requireActivity(), ArrowCounterActivity.class);
            counterIntent.putExtra("ArrowCounter", dailyCount);
            startActivity(counterIntent);
        });

        siteMarksBtn.setOnClickListener(v -> {
            Intent sightMarkIntent = new Intent(requireActivity(), SightMarksActivity.class);
            startActivity(sightMarkIntent);
        });
    }

    @SuppressLint("DefaultLocale")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onResume() {
        super.onResume();
        // sharedPreferences set up
        sharedPreferencesArrowCounter = requireActivity().getSharedPreferences("ArrowCounter", Context.MODE_PRIVATE);
        editor = sharedPreferencesArrowCounter.edit();

        // Getting daily counter values and last reset day values from shared preferences
        dailyCount = sharedPreferencesArrowCounter.getInt("counter", 0);
        String lastResetDay = sharedPreferencesArrowCounter.getString("resetDay", null);

        // Get today's date as a string for easy comparison to lastResetDay
        String today = LocalDate.now().toString();

        // For first set up of the app
        if (lastResetDay == null) {

            lastResetDay = today;
            editor.putString("resetDay", lastResetDay);
            editor.apply();
        }

        // Comparing counter to see if it has been reset
        if (!lastResetDay.equals(today)) {

            dailyCount = 0;
            lastResetDay = today;
            editor.putString("resetDay", lastResetDay);
            editor.apply();
        }

        arrowDayTotals.setText(String.format("%03d", dailyCount));
        roundInProgress = sharedPreferencesScoring.getBoolean("roundInProgress", false);
        if (!roundInProgress) {
            newRoundBtn.setOnClickListener(v -> {
                newRoundLabel.setText(R.string.new_round);
                Intent roundSetupIntent = new Intent(requireContext(), RoundSetupActivity.class);
                startActivity(roundSetupIntent);
            });
        }else {
            newRoundBtn.setOnClickListener(v -> {
                newRoundLabel.setText(R.string.continue_round);
                Intent roundSetupIntent = new Intent(requireContext(), RoundSetupActivity.class);
                startActivity(roundSetupIntent);
            });
        }

    }

    @Override
    public void onPause() {
        super.onPause();

        // sharedPreferences set up
        sharedPreferencesArrowCounter = requireActivity().getSharedPreferences("ArrowCounter", Context.MODE_PRIVATE);
        editor = sharedPreferencesArrowCounter.edit();

        // Saving dailyCount
        editor.putInt("counter", dailyCount);
        editor.apply();
    }
}
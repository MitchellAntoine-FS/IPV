package com.fullsail.apolloarchery.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.fullsail.apolloarchery.MainActivity;
import com.fullsail.apolloarchery.R;
import com.fullsail.apolloarchery.ScoreCardActivity;
import com.fullsail.apolloarchery.ShootingActivity;
import com.fullsail.apolloarchery.object.Distance;
import com.fullsail.apolloarchery.object.Round;
import com.fullsail.apolloarchery.object.RoundSelectionListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RoundSelectionFragment extends Fragment {

    public static final String TAG = "RoundSelectionFragment";

    TextView roundNameTextView, totalScoreTextView;
    ListView roundListView;
   RoundSelectionListener mListener;
    private List<Integer> distanceValues = new ArrayList<>();

    Distance distance;

    public RoundSelectionFragment() {
        // Required empty public constructor
    }

    public static RoundSelectionFragment newInstance() {
        return new RoundSelectionFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof  RoundSelectionListener) {
            mListener = (RoundSelectionListener) context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_round_selection, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        roundNameTextView = view.findViewById(R.id.selected_round_name);
        totalScoreTextView = view.findViewById(R.id.total_score_textView);

        roundListView = view.findViewById(R.id.round_selection_list);

        roundListView.setOnItemClickListener((parent, view1, position, id) -> {

            distance = new Distance(mListener.getSelectedRound().getDistances().get(position),
                    mListener.getSelectedRound().getArrowsDistances().get(position),
                    mListener.getSelectedRound().getScoringType(),
                    mListener.getSelectedRound().getArrowsPerEnd(),
                    mListener.getSelectedRound().getDistanceValue());

            Intent roundIntent = new Intent(requireContext(), ShootingActivity.class);
            roundIntent.putExtra("round", distance);

            startActivity(roundIntent);
        });


    }

    @Override
    public void onResume() {
        super.onResume();

        setup();
    }

    public static class RoundSelectionAdapter extends BaseAdapter {

        private final int maxArrowValue;

        private final Round round;
        private final List<String> distancesList;
        public RoundSelectionAdapter(Round round, int maxArrowVal, List<Integer> distanceValues) {
            // Trying to pass the data set from the RoundActivity using a constructor
            distancesList = round.getDistances();

            maxArrowValue = maxArrowVal;
            this.round = round;

            List<Round> rounds = new ArrayList<>();

            rounds.add(new Round(ShootingFragment.finalCurrentScore));

        }

        @Override
        public int getCount() {
            return distancesList.size();
        }

        @Override
        public Object getItem(int position) {
            return distancesList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @SuppressLint("InflateParams")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_round_selection_list_view, null);
            }

            int arrowsPerDistance = Integer.parseInt(round.getArrowsDistances().get(position));
            int totalDistance = arrowsPerDistance * maxArrowValue;
            String totalDistanceString = Integer.toString(totalDistance);
            // 0 is a placeholder which filled with score for this distance once I get round to scoring
            int scoreValue = ShootingFragment.finalCurrentScore;
            String combinedString = scoreValue + "/" + totalDistanceString;

            TextView tv = convertView.findViewById(R.id.round_selection_name_textView);
            tv.setText(round.getDistances().get(position));
            TextView roundScore = convertView.findViewById(R.id.round_total_score_textView);
            roundScore.setText(combinedString);

            return convertView;
        }
    }

    private void setup() {

        Round round = mListener.getSelectedRound();

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("Scoring", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        // Check if a round is in progress
        boolean roundInProgress = sharedPreferences.getBoolean("roundInProgress", false);
        // Using GSON to save round object
        final Gson gson = new Gson();

        if(!roundInProgress) {
            editor.putBoolean("roundInProgress", true);
            String jsonSave = gson.toJson(mListener.getSelectedRound());
            editor.putString("round", jsonSave);
            editor.apply();
        }

        String roundName = "";
        int scoringType;
        List<String> arrowsDistance;
        Round roundLoad = null;
        int totalArrowsShot = 0;


        List<String> distances;

        if (roundInProgress) {
            // Setting the values using shared preferences if the round is already in progress
            String jsonLoad = sharedPreferences.getString("round", "");
            roundLoad = gson.fromJson(jsonLoad, Round.class);

            arrowsDistance = roundLoad.getArrowsDistances();
            scoringType = roundLoad.getScoringType();
            distances = roundLoad.getDistances();

            String savedDistanceValues = sharedPreferences.getString("distanceValues", null);
            // Needs to look at the unpacked version NOT the string from sharedPref
            // Getting the data from the gson file
            Type distanceValuesType = new TypeToken<List<Integer>>(){}.getType();
            distanceValues = gson.fromJson(savedDistanceValues, distanceValuesType);
        }else {
            // Setting values as the round has been started from the round selection activity
            assert round != null;
            roundName = round.getRoundName();
            arrowsDistance = round.getArrowsDistances();
            scoringType = round.getScoringType();
            distances = round.getDistances();

            /* Creating the distanceValues array which is full of zeroes to be updated, this
               handles the case when a distance hasn't been shot yet hence the loop exits on the
               first if condition */
            for (int i = 0; i < distances.size(); i++) {
                assert distanceValues != null;
                distanceValues.add(0);

                Log.i("Scoring", "Distance Value array of 0's Size: " + distanceValues.size());
            }
        }

        roundNameTextView.setText(roundName);

        for (int i = 0; i < distances.size(); i++) {
            String arrowValuesString = sharedPreferences.getString(distances.get(i), null);
            Type arrowValueType = new TypeToken<List<String>>(){}.getType();

            if (arrowValuesString != null) {

                List<String> savedScoreList = gson.fromJson(arrowValuesString, arrowValueType);

                int current = 0;
                for (int j = 0; j < savedScoreList.size(); j++) {
                    if (savedScoreList.contains("M")) {
                        current += 0;
                    } else if (savedScoreList.contains("X")) {
                        current += 10;
                    } else {
                        current += Integer.parseInt(savedScoreList.get(i));
                    }

                    if (!savedScoreList.contains(" ")) {
                        totalArrowsShot += 1;

                        Log.i("Scoring", "Total arrows shot: " + totalArrowsShot);
                    }
                }
                assert distanceValues != null;
                distanceValues.set(i, current);
                Log.i("Scoring", "Distance Value Size: " + distanceValues.size());
            }
        }

        int maxArrowVal;

        if (scoringType == 0 || scoringType == 2 || scoringType == 3) {
            maxArrowVal = 10;
        }
        else if (scoringType == 1) {
            maxArrowVal = 9;
        }
        else if (scoringType == 4){
            maxArrowVal = 5;
        }
        else {
            maxArrowVal = 0;
            Log.d("Scoring", "Error loading from shared preferences");

        }

        int totalArrows = 0;
        for (int i = 0; i < arrowsDistance.size(); i++) {
            totalArrows += Integer.parseInt(arrowsDistance.get(i));
        }
        int totalScore = totalArrows * maxArrowVal;

        // Calculating round total score
        int currentScore = 0;

        if (distanceValues != null) {
            for (int i = 0; i < distanceValues.size(); i++) {
                currentScore += distanceValues.get(i);
            }
        }
        String totalScoreString = currentScore + "/" + totalScore;
        totalScoreTextView.setText(totalScoreString);

        final Round roundSend;
        if (roundInProgress) {

            roundSend = roundLoad;
        }
        else {
            roundSend = round;
        }

        // Need set the inputs for the adapter depending on if the activity was started
        roundListView.setAdapter(new RoundSelectionAdapter(roundSend, maxArrowVal, distanceValues));

        // Save button
        FloatingActionButton saveFAB = requireView().findViewById(R.id.save_fAB);

        // The save button only appears when the round has been completed
        if (totalArrowsShot == totalArrows) {

            saveFAB.setVisibility(View.VISIBLE);
        }
        else {
            saveFAB.setVisibility(View.GONE);
        }

        final int finalCurrentScore = currentScore;

        saveFAB.setOnClickListener(v -> {

            // Getting the current date
            String date = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MMM-yy"));
            }
            // Using gson to serialise the round object
            String serialisedRound = gson.toJson(roundSend);
            // Using gson to serialise list of arrow values
            String serialisedArrowValues = gson.toJson(distanceValues);
            // Adding completed round to database
            Long id = MainActivity.historyRoundDatabase.HistoryRoundDoa().saveCompletedRound(date, roundSend.getRoundName(),
                    serialisedRound, serialisedArrowValues, finalCurrentScore, "", "");

            // Resetting roundInProgress information
            editor.clear();
            editor.putBoolean("roundInProgress", false);
            editor.apply();

            Context context = requireView().getContext();
            Intent intentSave = new Intent(context, ScoreCardActivity.class);
            intentSave.putExtra("id", id);
            context.startActivity(intentSave);

        });
    }

    @Override
    public void onPause() {
        super.onPause();
        Gson gson = new Gson();
        String savedDistanceValues = gson.toJson(distanceValues);
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("Scoring", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("distanceValues", savedDistanceValues);
        editor.apply();
    }
}
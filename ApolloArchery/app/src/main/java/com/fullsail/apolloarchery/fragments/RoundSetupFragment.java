package com.fullsail.apolloarchery.fragments;

import static android.R.layout.simple_spinner_item;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.fullsail.apolloarchery.R;
import com.fullsail.apolloarchery.RoundSelectionActivity;
import com.fullsail.apolloarchery.object.Round;
import com.fullsail.apolloarchery.util.RoundStorageUtil;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class RoundSetupFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    public static final String TAG = "RoundSetupFragment";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<Round> roundsList;
    Round round;
    Spinner bowSpinner, arrowSpinner, rulesSpinner;
    private String docPath = " ";

    public RoundSetupFragment() {
        // Required empty public constructor
    }

    public static RoundSetupFragment newInstance() {

        return new RoundSetupFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_round_setup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        roundsList = new ArrayList<>();
        bowSpinner = view.findViewById(R.id.bow_spinner);
        arrowSpinner = view.findViewById(R.id.arrow_spinner);
        rulesSpinner = view.findViewById(R.id.rules_selection_spinner);

        DocumentReference docRef = db.collection("rounds").document("portsmouth-id");
        docRef.get().addOnSuccessListener(documentSnapshot -> {
            round = documentSnapshot.toObject(Round.class);

            if (round != null) {

                RoundStorageUtil.saveRound(getActivity(), round);

                roundsList.add(new Round(round.getRoundName(), round.getScoringType(),
                        round.getDistances(), round.getArrowsDistance(), round.getArrowsPerEnd()));
                populateRulesSpinner();
                Log.i("onSuccess", "Round Name " + round.getRoundName() + ": List size " + roundsList.size());
            }
        });

        populateBowSpinner();
        populateArrowSpinner();

        // Start
        Button startRound = view.findViewById(R.id.start_round_button);
        startRound.setOnClickListener(v -> {
            Intent startRoundIntent = new Intent(requireContext(), RoundSelectionActivity.class);
            startRoundIntent.putExtra("round", round);
            startActivity(startRoundIntent);
        });

    }

    private void populateRulesSpinner() {

        ArrayAdapter<Round> rulesSpinnerList = new ArrayAdapter<>(requireContext(), simple_spinner_item,
                roundsList);

        Log.i("populateRulesSpinner", "rounds list: " + roundsList.size());

        rulesSpinner.setAdapter(rulesSpinnerList);
        rulesSpinner.setOnItemSelectedListener(this);
    }

    private void populateArrowSpinner() {
        ArrayAdapter<String> spinnerList = new ArrayAdapter<>(requireContext(), simple_spinner_item,
                getResources().getStringArray(R.array.arrowList));

        arrowSpinner.setAdapter(spinnerList);
        arrowSpinner.setOnItemSelectedListener(this);
    }

    private void populateBowSpinner() {
        ArrayAdapter<String> spinnerList = new ArrayAdapter<>(requireContext(), simple_spinner_item,
                getResources().getStringArray(R.array.bowList));

        bowSpinner.setAdapter(spinnerList);
        bowSpinner.setOnItemSelectedListener(this);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String selectedSpinnerItem = parent.getItemAtPosition(position).toString();

        if (selectedSpinnerItem.contains("Portsmouth")) {
            docPath = "portsmouth-id";
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
package com.fullsail.apolloarchery.fragments;

import static android.R.layout.simple_spinner_item;

import android.content.Context;
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
import com.fullsail.apolloarchery.object.RoundSetupListener;
import com.fullsail.apolloarchery.util.RoundStorageUtil;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class RoundSetupFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    public static final String TAG = "RoundSetupFragment";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Round round;
    Spinner rulesSpinner;
    RoundSetupListener mListener;

    private String docPath = " ";

    public RoundSetupFragment() {
        // Required empty public constructor
    }

    public static RoundSetupFragment newInstance() {

        return new RoundSetupFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof RoundSetupListener) {
            mListener = (RoundSetupListener) context;
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
        return inflater.inflate(R.layout.fragment_round_setup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rulesSpinner = view.findViewById(R.id.rules_selection_spinner);

        ArrayAdapter<Round> rulesSpinnerList = new ArrayAdapter<>(requireContext(), simple_spinner_item,
                mListener.getRoundList());

        Log.i("populateRulesSpinner", "rounds list: " + mListener.getRoundList().size());

        rulesSpinner.setAdapter(rulesSpinnerList);
        rulesSpinner.setOnItemSelectedListener(this);

        // Start
        Button startRound = view.findViewById(R.id.start_round_button);
        startRound.setOnClickListener(v -> {
            Intent startRoundIntent = new Intent(requireContext(), RoundSelectionActivity.class);
            startRoundIntent.putExtra("round", round);
            startActivity(startRoundIntent);

        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String selectedSpinnerItem = parent.getItemAtPosition(position).toString();

        if (selectedSpinnerItem.contains("Portsmouth")) {
            docPath = "portsmouth-id";
        }else if (selectedSpinnerItem.contains("WA1440 (Gents)")){
            docPath = "wa1440_gents_id";
        } else if (selectedSpinnerItem.contains("WA1440 (Ladies)")) {
            docPath = "wa1440_ladies_id";
        }else if (selectedSpinnerItem.contains("WA 70")) {
            docPath = "wa70_id";
        }else if (selectedSpinnerItem.contains("WA18 (Full Face)")) {
            docPath = "wa18_id";
        }

        Log.i("onViewCreated", "Document Path: " + docPath);

        DocumentReference docRef = db.collection("rounds").document(docPath);

        docRef.get().addOnSuccessListener(documentSnapshot -> {

            Round r = documentSnapshot.toObject(Round.class);

            if (r != null) {
                round = r;
                Log.i("onSuccess", "Round Setup Name " + round.getRoundName());
            }
        });

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
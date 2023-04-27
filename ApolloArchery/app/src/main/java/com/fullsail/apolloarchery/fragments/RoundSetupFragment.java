package com.fullsail.apolloarchery.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.fullsail.apolloarchery.R;
import com.fullsail.apolloarchery.object.Round;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class RoundSetupFragment extends Fragment {
    public static final String TAG = "RoundSetupFragment";
    FirebaseFirestore db = FirebaseFirestore.getInstance();

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

        Round round = new Round("Portsmouth", 2,
                new ArrayList<String>() {{ add("First half"); add("Second half"); }},
                new ArrayList<String>() {{ add("30"); add("30");  }},
                3);

        db.collection("rounds").document("new-round-id").set(round);



    }
}
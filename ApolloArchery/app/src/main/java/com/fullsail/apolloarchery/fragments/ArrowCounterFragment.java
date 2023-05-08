package com.fullsail.apolloarchery.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.fullsail.apolloarchery.R;
import com.fullsail.apolloarchery.object.ArrowCounterListener;

import java.util.Locale;

public class ArrowCounterFragment extends Fragment {
    public static final String TAG = "ArrowCounterFragment";

    private TextView arrowCounterTextView;
    private int arrowCount;

    ArrowCounterListener mListener;

    public ArrowCounterFragment() {
        // Required empty public constructor
    }

    public static ArrowCounterFragment newInstance() {
        return new ArrowCounterFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ArrowCounterListener) {
            mListener = (ArrowCounterListener) context;
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
        return inflater.inflate(R.layout.fragment_arrow_counter, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        arrowCounterTextView = view.findViewById(R.id.arrow_counter);
        arrowCount = mListener.getCount();
        arrowCounterTextView.setText(String.format(Locale.getDefault(),"%03d", arrowCount));

        Button arrowCountAddButton = view.findViewById(R.id.arrow_counter_add);
        arrowCountAddButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onClick(View view) {
                arrowCount += 1;
                arrowCounterTextView.setText(String.format("%03d", arrowCount));
            }
        });

        Button arrowCountMinusButton = view.findViewById(R.id.arrow_counter_minus);
        arrowCountMinusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (arrowCount != 0) {
                    arrowCount -= 1;
                    arrowCounterTextView.setText(String.format(Locale.getDefault(),"%03d", arrowCount));
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        // Sending arrow count to home
        Context context = requireContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences("ArrowCounter", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("counter", arrowCount);
        editor.apply();
    }
}
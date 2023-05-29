package com.fullsail.apolloarchery.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.fullsail.apolloarchery.R;
import com.fullsail.apolloarchery.adapters.HistoryAdapter;
import com.fullsail.apolloarchery.object.HistoryListener;
import com.fullsail.apolloarchery.scorecard.ScorecardActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class ProfileFragment extends Fragment implements AdapterView.OnItemClickListener {

    public static final String TAG = "ProfileFragment";
    HistoryListener mListener;
    ListView histListView;

    public ProfileFragment() {
        // Required empty public constructor
    }


    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof HistoryListener){
            mListener = (HistoryListener) context;
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
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView profileImage = view.findViewById(R.id.profile_image);

        profileImage.setImageResource(R.drawable.baseline_profile);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        TextView userName = view.findViewById(R.id.users_name);
        if (user != null) {

            userName.setText(user.getDisplayName());

            profileImage.setImageURI(user.getPhotoUrl());
        }

        refresh();
    }

    public void refresh() {

        HistoryAdapter adapter = new HistoryAdapter(mListener.getFirebaseRoundHistory(), getActivity());

        histListView = requireView().findViewById(R.id.history_listView);
        histListView.setAdapter(adapter);
        histListView.setOnItemClickListener(this);

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        long historyId = mListener.getFirebaseRoundHistory().get(position).getId();
        long historyRounds = mListener.getHistory().get(position).id;

        Intent intentSave = new Intent(requireContext(), ScorecardActivity.class);
        intentSave.putExtra("id", historyId);
        this.startActivity(intentSave);
    }
}
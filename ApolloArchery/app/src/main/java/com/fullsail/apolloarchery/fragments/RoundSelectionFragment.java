package com.fullsail.apolloarchery.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.fullsail.apolloarchery.R;
import com.fullsail.apolloarchery.ShootingActivity;
import com.fullsail.apolloarchery.object.Distance;
import com.fullsail.apolloarchery.object.Round;
import com.fullsail.apolloarchery.object.RoundSelectionListener;

import java.util.ArrayList;
import java.util.List;

public class RoundSelectionFragment extends Fragment implements AdapterView.OnItemClickListener {

    public static final String TAG = "RoundSelectionFragment";

    ListView roundListView;
   RoundSelectionListener round;

   public static Distance current;

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
            round = (RoundSelectionListener) context;
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

        roundListView = view.findViewById(R.id.round_selection_list);

        roundListView.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Distance distance = (Distance) parent.getItemAtPosition(position);
        Intent roundIntent = new Intent(requireContext(), ShootingActivity.class);
        roundIntent.putExtra("round", distance);

        startActivity(roundIntent);
    }

    public static class RoundSelectionAdapter extends BaseAdapter {

        private final int maxArrowValue;

        private final List<Distance> distances = new ArrayList<>();

        public RoundSelectionAdapter(Round round, int maxArrowVal, List<Integer> distanceValues) {
            // Trying to pass the data set from the RoundActivity using a constructor
            List<String> distancesList = round.getDistances();
            List<String> arrowsDistanceList = round.getArrowsDistances();
            maxArrowValue = maxArrowVal;

            for (int i = 0; i < distancesList.size(); i++) {
                distances.add(new Distance(distancesList.get(i), arrowsDistanceList.get(i),
                        round.getScoringType(), round.getArrowsPerEnd(), distanceValues.get(i)));
            }

            List<Round> rounds = new ArrayList<>();

            rounds.add(new Round(ShootingFragment.finalCurrentScore));

        }

        @Override
        public int getCount() {
            return distances.size();
        }

        @Override
        public Object getItem(int position) {
            return distances.get(position);
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

            current = distances.get(position);
            int arrowsPerDistance = Integer.parseInt(current.getArrowsAtDistance());
            int totalDistance = arrowsPerDistance * maxArrowValue;
            String totalDistanceString = Integer.toString(totalDistance);
            // 0 is a placeholder which filled with score for this distance once I get round to scoring
            String combinedString = current.getDistanceValue() + "/" + totalDistanceString;

            TextView tv = convertView.findViewById(R.id.round_selection_name_textView);
            tv.setText(current.getDistance());
            TextView roundScore = convertView.findViewById(R.id.round_total_score_textView);
            roundScore.setText(combinedString);

            return convertView;
        }
    }

}
package com.fullsail.apolloarchery.fragments;

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
import com.fullsail.apolloarchery.object.Round;
import com.fullsail.apolloarchery.object.RoundSelectionListener;

import java.util.List;

public class RoundSelectionFragment extends Fragment {

    public static final String TAG = "RoundSelectionFragment";

    TextView roundName, roundScoreTotal;
    ListView roundListView;
   RoundSelectionListener mListener;

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

        roundName = view.findViewById(R.id.selected_round_name);
        roundScoreTotal = view.findViewById(R.id.round_total_score);

        roundName.setText(mListener.getSelectedRound().getRoundName());

        roundListView = view.findViewById(R.id.round_selection_list);

        roundListView.setAdapter(new RoundSelectionAdapter(mListener.getSelectedRound().getDistances(), requireContext()));

        roundListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String dist = (String) parent.getAdapter().getItem(position);

                Intent roundIntent = new Intent(requireContext(), ShootingActivity.class);
                roundIntent.putExtra("round", mListener.getSelectedRound());
                startActivity(roundIntent);
            }
        });
    }

    public static class RoundSelectionAdapter extends BaseAdapter {

        private final List<String> mRound;
        private final Context mContext;

        public RoundSelectionAdapter(List<String> mRound, Context mContext) {
            this.mRound = mRound;
            this.mContext = mContext;
        }

        @Override
        public int getCount() {
            return mRound.size();
        }

        @Override
        public Object getItem(int position) {
            return mRound.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.fragment_round_selection_list_view, null);
            }

            String dist = (String) getItem(position);

            TextView tv = convertView.findViewById(R.id.round_selection_name);
            tv.setText(dist);

            return convertView;
        }

    }



}
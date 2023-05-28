package com.fullsail.apolloarchery.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.fullsail.apolloarchery.R;
import com.fullsail.apolloarchery.object.SightMark;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class SightMarksFragment extends Fragment {
    public static final String TAG = "SightMarksFragment";

    ListView listView;
    FloatingActionButton fAB;
    String distance;
    double sight_mark = 0.0;
    ArrayList<SightMark> sightMarks;

    public SightMarksFragment() {
        // Required empty public constructor
    }

    public static SightMarksFragment newInstance() {
        return new SightMarksFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sight_marks, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fAB = view.findViewById(R.id.sight_marks_fAB);
        listView = view.findViewById(R.id.sight_marks_list);
        sightMarks = new ArrayList<>();

        fAB.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
            Context context = builder.getContext();
            builder.setTitle("Distance");

            // Set up the input
            final EditText etDistance = new EditText(context);
            // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
            etDistance.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
            builder.setView(etDistance);

            builder.setTitle("Sight Mark");
            final EditText etSight_mark = new EditText(context);
            etSight_mark.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            builder.setView(etSight_mark);

            // Set up the buttons
            builder.setPositiveButton("OK", (dialog, which) -> {
                distance = String.valueOf(etDistance.getText());
                sight_mark = Double.parseDouble(etSight_mark.getText().toString());

                sightMarks.add(new SightMark(distance, sight_mark));
            });
            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

            builder.show();
        });

        refresh();
    }

    public void refresh() {
        listView.setAdapter(new SightMarkAdapter(requireActivity(), 0, sightMarks));
    }

    static class SightMarkAdapter extends ArrayAdapter<SightMark> {

        private final Context mContext;
        public SightMarkAdapter(@NonNull Context context, int resource, @NonNull List<SightMark> objects) {
            super(context, resource, objects);
            mContext =context;
        }

        @SuppressLint("InflateParams")
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            if (convertView == null) {

                convertView = LayoutInflater.from(mContext).inflate(R.layout.fragment_sight_marks_view, null);
            }

            SightMark sightMark = (SightMark) getItem(position);

            TextView tvDist = convertView.findViewById(R.id.sight_mark_dist_textView);
            tvDist.setText(sightMark.getDistance());

            TextView tvSight = convertView.findViewById(R.id.sights_mark_textView);
            tvSight.setText(String.valueOf(sightMark.getSight_mark()));

            return convertView;
        }
    }
}
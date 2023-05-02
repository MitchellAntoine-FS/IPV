package com.fullsail.apolloarchery.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.fullsail.apolloarchery.R;
import com.fullsail.apolloarchery.object.HistoryRound;
import com.fullsail.apolloarchery.object.Round;
import com.fullsail.apolloarchery.object.ScoreCardListener;
import com.fullsail.apolloarchery.util.RoundStorageUtil;

import java.util.List;

public class ScoreCardFragment extends Fragment {
    public static final String TAG = "ScoreCardFragment";

    HistoryRound current;

    ScoreCardListener mListener;

    public ScoreCardFragment() {
        // Required empty public constructor
    }

    public static ScoreCardFragment newInstance() {
        return new ScoreCardFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ScoreCardListener) {
            mListener = (ScoreCardListener) context;
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
        return inflater.inflate(R.layout.fragment_score_card, container, false);
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        current = mListener.getRoundScore();

        // Getting all the fields
        String date = current.getDate();
        String roundName = current.getRoundName();

        // getting the round serialised object
        Round round = RoundStorageUtil.loadRounds(getActivity()).get(0);

        LinearLayout linearLayoutDistanceTables = view.findViewById(R.id.scorecard_layout_for_distance_tables);
        EditText archerSig = view.findViewById(R.id.scorecard_archer_sig);
        EditText scorerSig = view.findViewById(R.id.scorecard_scorer_sig);
        TextView roundNameTextView = view.findViewById(R.id.scorecard_name);
        TextView roundDateTextView = view.findViewById(R.id.scorecard_date);
        roundNameTextView.setText(roundName);
        roundDateTextView.setText(date);
        archerSig.setText(current.getArcherString());
        scorerSig.setText(current.getScorerString());

        // List of distances
        List<String> distances = round.getDistances();
        // List of number of arrows at each distance
        List<String> arrowsDistance = round.getArrowsDistance();
        int arrowsEnd = round.getArrowsPerEnd();
        // Variable to store ET values
        int ETVal;
        // Variable for storing previous ends RT
        int prevRTVal = 0;
        // Array for storing number of hits at each arrow value where 0 = M and 11 = X
        int[] hitsAtValue = new int[12];
        // Variable for calculating number of hits
        int hits = 0;

        // Layout parameters for different views that will be added programmatically
        TableRow.LayoutParams lpArrows = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);
        lpArrows.weight = 1;
        TableRow.LayoutParams headerLp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);
        headerLp.span = arrowsEnd;
        TableRow.LayoutParams totalsLp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);
        totalsLp.weight = (float) 1.5;
        TableLayout.LayoutParams lpTable = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        lpTable.setMargins(0, 0, 0, 20);


        for (int i = 0; i < distances.size(); i++) {
            TextView distanceHeader = new TextView(getActivity());
            distanceHeader.setText(distances.get(i));
            distanceHeader.setTextSize(15);
            distanceHeader.setTypeface(Typeface.DEFAULT_BOLD);
            linearLayoutDistanceTables.addView(distanceHeader);
            // Get arrow values for this distance

            List<List<String>> distanceArrowValues = current.getArrowValues();
            // Get the number of ends at this distance
            int ends = Integer.parseInt(arrowsDistance.get(i)) / arrowsEnd;

            // Create table view for this distance
            TableLayout tableLayout = new TableLayout(getActivity());
            tableLayout.setStretchAllColumns(true);
            tableLayout.setBackgroundResource(R.drawable.cell_shape_thick);
            tableLayout.setLayoutParams(lpTable);
            // Iterate through ends with 1 added for the header row
            for (int j = 0; j < ends + 1; j++) {
                // Create table row for this row
                TableRow tableRow = new TableRow(getActivity());

                /* Setting up the columns at the end of the scoring table, note we don't add these
                   to the row until after the other elements have been added */
                TextView ET = new TextView(getActivity());
                ET.setLayoutParams(totalsLp);
                ET.setTextSize(15);
                ET.setBackgroundResource(R.drawable.cell_shape_thick_sides);
                ET.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                ET.setTypeface(Typeface.DEFAULT_BOLD);

                TextView RT = new TextView(getActivity());
                RT.setLayoutParams(totalsLp);
                RT.setTextSize(15);
                RT.setBackgroundResource(R.drawable.cell_shape);
                RT.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                RT.setTypeface(Typeface.DEFAULT_BOLD);

                // Handle header row vs regular rows
                if (j == 0) {
                    // Setting up the arrows header
                    TextView arrowsHeader = new TextView(getActivity());
                    arrowsHeader.setLayoutParams(headerLp);
                    arrowsHeader.setTextSize(15);
                    arrowsHeader.setTypeface(Typeface.DEFAULT_BOLD);
                    arrowsHeader.setBackgroundResource(R.drawable.cell_shape_thick_bottom);
                    arrowsHeader.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    arrowsHeader.setText(R.string.arrows);
                    tableRow.addView(arrowsHeader);

                    // Setting up ET and RT header
                    ET.setText(R.string.end_total);
                    ET.setBackgroundResource(R.drawable.cell_shape_thick_bottom_sides);
                    tableRow.addView(ET);
                    RT.setText(R.string.running_total);
                    RT.setBackgroundResource(R.drawable.cell_shape_thick_bottom);
                    tableRow.addView(RT);

                    // prevRTVal is zero since no previous ends
                    prevRTVal = 0;
                } else {
                    // Counter for this ends ET
                    ETVal = 0;
                    // Iterate through number of arrows each end
                    for (int k = 0; k < arrowsEnd; k++) {
                        TextView arrow = new TextView(getActivity());
                        arrow.setLayoutParams(lpArrows);
                        arrow.setTextSize(15);
                        arrow.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        // Setting borders for cells
                        arrow.setBackgroundResource(R.drawable.cell_shape);
                        String arrowValue = distanceArrowValues.get(j - 1).get(k);
                        // Inputting arrow values
                        arrow.setText(arrowValue);
                        // Deal with different arrow values and adding them to ET
                        if (arrowValue.equals("X")) {
                            ETVal += 10;
                            hits += 1;
                            hitsAtValue[11] += 1;
                        } else if (arrowValue.equals("M") || arrowValue.equals("")) {
                            ETVal += 0;
                            hitsAtValue[0] += 1;
                        } else {
                            int value = Integer.parseInt(arrowValue);
                            ETVal += value;
                            hits += 1;
                            hitsAtValue[value] += 1;
                        }
                        tableRow.addView(arrow);
                    }
                    // Fill in ET and RT columns with correct numbers
                    ET.setText(Integer.toString(ETVal));
                    tableRow.addView(ET);
                    int RTVal = prevRTVal + ETVal;
                    RT.setText(Integer.toString(RTVal));
                    tableRow.addView(RT);

                    // Update prevRTVal
                    prevRTVal = RTVal;

                }
                // Adding the row to the tableLayout
                tableLayout.addView(tableRow);
            }
            // Adding the table to the linearLayout
            linearLayoutDistanceTables.addView(tableLayout);
        }

        // Summary table

        TableLayout summaryTable = view.findViewById(R.id.scorecard_table_for_summary);
        summaryTable.setBackgroundResource(R.drawable.cell_shape_thick);

        // Calculating the values we need
        int totalArrows = 0;
        for (String i : round.getArrowsDistance()) {
            totalArrows += Integer.parseInt(i);
        }
        float average = (float) current.getTotalScore() / totalArrows;

        int scoringType = round.getScoringType();

        TableRow firstRow = view.findViewById(R.id.scorecard_summary_upper);
        TableRow secondRow = view.findViewById(R.id.scorecard_summary_lower);

        if (scoringType == 2) {
            // Indoors full
            // Arrow values
            for (int i = 0; i < 11; i++) {
                // Setting up TextView formats
                TextView arrowValue = new TextView(getActivity());
                TextView hitsValue = new TextView(getActivity());
                arrowValue.setLayoutParams(lpArrows);
                hitsValue.setLayoutParams(lpArrows);
                arrowValue.setBackgroundResource(R.drawable.cell_shape_thick_bottom);
                hitsValue.setBackgroundResource(R.drawable.cell_shape);
                arrowValue.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                hitsValue.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                arrowValue.setTypeface(Typeface.DEFAULT_BOLD);
                arrowValue.setTextSize(15);
                hitsValue.setTextSize(15);

                // Calculating which arrow value we are working with
                int arrowScore = 10 - i;
                hitsValue.setText(Integer.toString(hitsAtValue[arrowScore]));
                if (arrowScore == 0) {
                    arrowValue.setText("M");
                } else {
                    arrowValue.setText(Integer.toString(arrowScore));
                }
                firstRow.addView(arrowValue);
                secondRow.addView(hitsValue);
            }
        }

        // hits and average
        // Names for first row
        TextView hitsNameTextView = new TextView(getActivity());
        TextView averageNameTextView = new TextView(getActivity());
        hitsNameTextView.setLayoutParams(totalsLp);
        averageNameTextView.setLayoutParams(totalsLp);
        hitsNameTextView.setBackgroundResource(R.drawable.cell_shape_thick_bottom_sides);
        averageNameTextView.setBackgroundResource(R.drawable.cell_shape_thick_bottom);
        hitsNameTextView.setTextSize(15);
        averageNameTextView.setTextSize(15);
        hitsNameTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        averageNameTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        hitsNameTextView.setTypeface(Typeface.DEFAULT_BOLD);
        averageNameTextView.setTypeface(Typeface.DEFAULT_BOLD);
        hitsNameTextView.setText("Hits");
        averageNameTextView.setText("Average");
        firstRow.addView(hitsNameTextView);
        firstRow.addView(averageNameTextView);

        // Values for second row
        TextView hitsTextView = new TextView(getActivity());
        TextView averageTextView = new TextView(getActivity());
        hitsTextView.setLayoutParams(totalsLp);
        averageTextView.setLayoutParams(totalsLp);
        hitsTextView.setBackgroundResource(R.drawable.cell_shape_thick_sides);
        averageTextView.setBackgroundResource(R.drawable.cell_shape);
        hitsTextView.setTextSize(15);
        averageTextView.setTextSize(15);
        hitsTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        averageTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        String hitsContent = hits + "/" + totalArrows;
        hitsTextView.setText(hitsContent);
        averageTextView.setText(String.format("%.2f", average));
        secondRow.addView(hitsTextView);
        secondRow.addView(averageTextView);

    }

}
package com.fullsail.apolloarchery.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.fullsail.apolloarchery.R;
import com.fullsail.apolloarchery.object.HistoryRound;
import com.fullsail.apolloarchery.object.Round;
import com.fullsail.apolloarchery.object.ShootingListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

public class ShootingFragment extends Fragment implements View.OnClickListener {
    public static final String TAG = "ShootingFragment";


    View view;
    private int arrowsPerEnd;
    private int scoringType;

    private Round round;
    private int shotCount;
    private int keyValue;
    private String shotString = "";
    private DateFormat date;
    private final List<List<String>> roundScoreList = new ArrayList<List<String>>();
    private List<String> endScoreList;
    private final List<Integer> distanceValues = new ArrayList<>();
    private int totalArrowsShot = 0;
    String mEndNum = "End";
    int endNum = 1;
    int totalArrows = 0;
    Button nextBtn, unDoBtn;
    TextView endNumber, distDisplay, endScore_roundScore, tvShotOne, tvShotTwo, tvShotThree;
    List<String> arrowsDistance;
    ShootingListener mListener;
    int bgColor;

    HistoryRound historyRound;

    public ShootingFragment() {
        // Required empty public constructor
    }


    public static ShootingFragment newInstance() {
        return new ShootingFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ShootingListener){
            mListener = (ShootingListener) context;
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
        return inflater.inflate(R.layout.fragment_shooting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view = v;

        date = DateFormat.getDateInstance();
        endNumber = v.findViewById(R.id.shooting_end_num);
        String endNumString = String.format(mEndNum + endNum);
        endNumber.setText(endNumString);
        distDisplay = v.findViewById(R.id.shooting_end_dist);
        endScore_roundScore = v.findViewById(R.id.shooting_total_score);
        tvShotOne = v.findViewById(R.id.shot_one_textView);
        tvShotTwo = v.findViewById(R.id.shot_two_textView);
        tvShotThree = v.findViewById(R.id.shot_three_textView);
        nextBtn = v.findViewById(R.id.next_btn);
        nextBtn.setEnabled(false);
        unDoBtn = v.findViewById(R.id.undo_btn);
        unDoBtn.setEnabled(false);
        endScoreList = new ArrayList<>();


        v.findViewById(R.id.x_bn).setOnClickListener(this);
        v.findViewById(R.id.one_bn).setOnClickListener(this);
        v.findViewById(R.id.two_bn).setOnClickListener(this);
        v.findViewById(R.id.three_bn).setOnClickListener(this);
        v.findViewById(R.id.four_bn).setOnClickListener(this);
        v.findViewById(R.id.five_bn).setOnClickListener(this);
        v.findViewById(R.id.six_bn).setOnClickListener(this);
        v.findViewById(R.id.seven_bn).setOnClickListener(this);
        v.findViewById(R.id.eight_bn).setOnClickListener(this);
        v.findViewById(R.id.nine_bn).setOnClickListener(this);
        v.findViewById(R.id.ten_bn).setOnClickListener(this);
        v.findViewById(R.id.miss_bn).setOnClickListener(this);

        round = mListener.getRound();
        arrowsPerEnd = round.getArrowsPerEnd();
        scoringType = round.getScoringType();
        arrowsDistance = round.getArrowsDistance();

        nextBtn.setOnClickListener(v12 -> {
            if (totalArrowsShot != totalArrows) {
                //Go to next End
                endNum ++;
                // Reset all score views
                tvShotOne.setText(null);
                tvShotOne.setBackgroundColor(Color.WHITE);
                tvShotOne.setBackgroundResource(R.drawable.cell_shape);
                tvShotOne.requestFocus();
                tvShotTwo.setText(null);
                tvShotTwo.setBackgroundColor(Color.WHITE);
                tvShotTwo.setBackgroundResource(R.drawable.cell_shape);
                tvShotThree.setText(null);
                tvShotThree.setBackgroundColor(Color.WHITE);
                tvShotThree.setBackgroundResource(R.drawable.cell_shape);
                nextBtn.setEnabled(false);
                unDoBtn.setEnabled(false);

                roundScoreList.add(endScoreList);
                endScoreList.clear();
            }
        });

        unDoBtn.setOnClickListener(v1 -> endScoreList.remove(shotString));

    }

    private void shooting() {

        String  shotOneString, shotTwoString, shotThreeString;
        shotOneString = tvShotOne.getText().toString();
        shotTwoString = tvShotTwo.getText().toString();
        shotThreeString = tvShotThree.getText().toString();

        int currentScore = 0;
        int maxArrowVal;

        if (scoringType == 0 || scoringType == 2 || scoringType == 3) {
            maxArrowVal = 10;
        }
        else if (scoringType == 1) {
            maxArrowVal = 9;
        }
        else {
            maxArrowVal = 0;
            Log.d("Scoring", "Error loading from shared preferences");
        }

        for (int i = 0; i < arrowsDistance.size(); i++) {
            totalArrows = Integer.parseInt(arrowsDistance.get(i));
        }
        int totalScore = totalArrows * maxArrowVal;

        if (totalArrowsShot != totalArrows || shotCount != arrowsPerEnd) {

            if (shotOneString.isBlank() && shotTwoString.isBlank() && shotThreeString.isBlank()) {
                tvShotOne.requestFocus();
                tvShotOne.setText(shotString);
                tvShotOne.setBackgroundColor(bgColor);
                endScoreList.add(shotString);
                distanceValues.add(keyValue);

                if (bgColor == Color.BLACK || bgColor == Color.BLUE) {
                    tvShotOne.setTextColor(Color.WHITE);
                } else {
                    tvShotOne.setTextColor(Color.BLACK);
                }
                unDoBtn.setEnabled(true);
            }
            else if (!shotOneString.isBlank() && shotTwoString.isBlank() && shotThreeString.isBlank()) {
                tvShotTwo.requestFocus();
                tvShotTwo.setText(shotString);
                tvShotTwo.setBackgroundColor(bgColor);
                endScoreList.add(shotString);
                distanceValues.add(keyValue);
                if (bgColor == Color.BLACK || bgColor == Color.BLUE) {
                    tvShotTwo.setTextColor(Color.WHITE);
                } else {
                    tvShotTwo.setTextColor(Color.BLACK);
                }
            } else if (!shotOneString.isBlank() && !shotTwoString.isBlank() && shotThreeString.isBlank()) {
                tvShotThree.requestFocus();
                tvShotThree.setText(shotString);
                tvShotThree.setBackgroundColor(bgColor);
                endScoreList.add(shotString);
                distanceValues.add(keyValue);
                if (bgColor == Color.BLACK || bgColor == Color.BLUE) {
                    tvShotThree.setTextColor(Color.WHITE);
                } else {
                    tvShotThree.setTextColor(Color.BLACK);
                }
            } else if (!shotOneString.isEmpty() && !shotTwoString.isEmpty() && !shotThreeString.isEmpty()) {
                    nextBtn.setEnabled(true);
            }

        }else {

            historyRound.setArrowValues(roundScoreList);

            historyRound = new HistoryRound(date.toString(), round.getRoundName(), round.toString(), totalScore);
            AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
            dialog.setTitle("Round Complete");
            dialog.setMessage("Go back and pick your next round.");

            int finalCurrentScore = currentScore;
            dialog.setPositiveButton("YES", (dialog1, which) -> {
                mListener.nextRound(historyRound, finalCurrentScore);
            });

            dialog.setNegativeButton("Cancel", (dialog12, which) -> {

            });
            dialog.show();
        }

        // Calculating round total score


        for (int i = 0; i < distanceValues.size(); i++) {
            currentScore += distanceValues.get(i);
        }
        String totalScoreString = currentScore + "/" + totalScore;
        endScore_roundScore.setText(totalScoreString);


    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.x_bn) {
            shotString = "X";
            shotCount += 1;
            keyValue = 10;
            bgColor = Color.YELLOW;
            shooting();
        }else if (v.getId() == R.id.one_bn) {
            shotString = "1";
            shotCount += 1;
            keyValue = 1;
            bgColor = Color.WHITE;
            shooting();
            totalArrowsShot += 1;
        }else if (v.getId() == R.id.two_bn) {
            shotString = "2";
            shotCount += 1;
            keyValue = 2;
            bgColor = Color.WHITE;
            shooting();
            totalArrowsShot += 1;
        }else if (v.getId() == R.id.three_bn) {
            shotString = "3";
            shotCount += 1;
            keyValue = 3;
            bgColor = Color.BLACK;
            shooting();
            totalArrowsShot += 1;
        }else if (v.getId() == R.id.four_bn) {
            shotString = "4";
            shotCount += 1;
            keyValue = 4;
            bgColor = Color.BLACK;
            shooting();
            totalArrowsShot += 1;
        }else if (v.getId() == R.id.five_bn) {
            shotString = "5";
            shotCount += 1;
            keyValue = 5;
            bgColor = Color.BLUE;
            shooting();
            totalArrowsShot += 1;
        }else if (v.getId() == R.id.six_bn) {
            shotString = "6";
            shotCount += 1;
            keyValue = 6;
            bgColor = Color.BLUE;
            shooting();
            totalArrowsShot += 1;
        }else if (v.getId() == R.id.seven_bn) {
            shotString = "7";
            shotCount += 1;
            keyValue = 7;
            bgColor = Color.RED;
            shooting();
            totalArrowsShot += 1;
        }else if (v.getId() == R.id.eight_bn) {
            shotString = "8";
            shotCount += 1;
            keyValue = 8;
            bgColor = Color.RED;
            shooting();
            totalArrowsShot += 1;
        }else if (v.getId() == R.id.nine_bn) {
            shotString = "9";
            shotCount += 1;
            keyValue = 9;
            bgColor = Color.YELLOW;
            shooting();
            totalArrowsShot += 1;
        }else if (v.getId() == R.id.ten_bn) {
            shotString = "10";
            shotCount += 1;
            keyValue = 10;
            bgColor = Color.YELLOW;
            shooting();
            totalArrowsShot += 1;
        }else if (v.getId() == R.id.miss_bn) {
            shotString = "M";
            shotCount += 1;
            keyValue = 0;
            bgColor = Color.GREEN;
            shooting();
            totalArrowsShot += 1;
        }

    }

    @Override
    public void onResume() {
        super.onResume();



    }
}
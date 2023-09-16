package com.fullsail.apolloarchery.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.fullsail.apolloarchery.R;
import com.fullsail.apolloarchery.object.Distance;
import com.fullsail.apolloarchery.object.ShootingListener;

import java.util.ArrayList;
import java.util.List;

public class ShootingFragment extends Fragment implements View.OnTouchListener {
    public static final String TAG = "ShootingFragment";


    View view;
    private int arrowsEnd;
    private int end;
    private int scoringStyle;
    Distance distance;
    // Number of shots made
    private int shotCount;
    private int keyValue;

    // String value of shot. ie.. (X,10,9,8,ext...)
    public static String shotString = "";
    // list of shot values in String format
    private List<String> arrowsScoreList;
    // list of shot values in Integer format
    private final List<Integer> distanceValues = new ArrayList<>();
    private int totalArrowsShot = 0;
    String mEndNum = "End";
    int endNum = 1;
    int totalArrows = 0;
    Button nextBtn, unDoBtn;
    TextView endNumber, distDisplay, endScore_roundScore, tvShotOne, tvShotTwo, tvShotThree;
    TextView shotOneTextView, shotTwoTextView, shotThreeTextView, shotFourTextView;
    TextView shotFiveTextView, shotSixTextView;

    String arrowsAtDistance;
    ShootingListener mListener;
    int bgColor;
    public static int finalCurrentScore = 0;
    LinearLayout threeShotLayout, sixShotLayout;

    String  shotOneString, shotTwoString, shotThreeString;

    String shotOne, shotTwo, shotThree, shotFour, shotFive, shotSix;
    private TextView[] shotTextViews;

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
        } else {
            throw new IllegalArgumentException("Context must implement ShootingListener");
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
        view = inflater.inflate(R.layout.fragment_shooting, container, false);
        return view;
    }

    @SuppressLint("ResourceType")
    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        view = v;

        // Initialize and assign views for the end number, distance display, and end score
        endNumber = v.findViewById(R.id.shooting_end_num);
        distDisplay = v.findViewById(R.id.shooting_end_dist);
        endScore_roundScore = v.findViewById(R.id.shooting_total_score);

        // Initialize and assign shot text views for three-shot layout
        tvShotOne = v.findViewById(R.id.shot_one_textView);
        tvShotTwo = v.findViewById(R.id.shot_two_textView);
        tvShotThree = v.findViewById(R.id.shot_three_textView);

        // Initialize and assign shot text views for six-shot layout
        shotTextViews = new TextView[] {
                v.findViewById(R.id.shot_one_text_view),
                v.findViewById(R.id.shot_two_text_view),
                v.findViewById(R.id.shot_three_text_view),
                v.findViewById(R.id.shot_four_text_view),
                v.findViewById(R.id.shot_five_text_view),
                v.findViewById(R.id.shot_six_text_view)
        };

        // Touch imageView to provide X for bullseye, 0 for a miss or number value of shot.
        v.findViewById(R.id.x_bn).setOnTouchListener(this);
        v.findViewById(R.id.one_bn).setOnTouchListener(this);
        v.findViewById(R.id.two_bn).setOnTouchListener(this);
        v.findViewById(R.id.three_bn).setOnTouchListener(this);
        v.findViewById(R.id.four_bn).setOnTouchListener(this);
        v.findViewById(R.id.five_bn).setOnTouchListener(this);
        v.findViewById(R.id.six_bn).setOnTouchListener(this);
        v.findViewById(R.id.seven_bn).setOnTouchListener(this);
        v.findViewById(R.id.eight_bn).setOnTouchListener(this);
        v.findViewById(R.id.nine_bn).setOnTouchListener(this);
        v.findViewById(R.id.ten_bn).setOnTouchListener(this);
        v.findViewById(R.id.miss_bn).setOnTouchListener(this);

        nextBtn = v.findViewById(R.id.next_btn);
        nextBtn.setEnabled(false);
        unDoBtn = v.findViewById(R.id.undo_btn);
        unDoBtn.setEnabled(false);

        arrowsScoreList = new ArrayList<>();

        threeShotLayout = v.findViewById(R.id.three_shot_layout);
        sixShotLayout = v.findViewById(R.id.six_shot_layout);

        distance = mListener.getDistance();
        arrowsEnd = distance.getArrowsEnd();
        scoringStyle = distance.getScoringStyle();
        arrowsAtDistance = distance.getArrowsAtDistance();

        if (arrowsEnd == 3) {
            threeShotLayout.setVisibility(View.VISIBLE);
            sixShotLayout.setVisibility(View.GONE);
        } else if (arrowsEnd == 6) {
            threeShotLayout.setVisibility(View.GONE);
            sixShotLayout.setVisibility(View.VISIBLE);
        }

        nextBtn.setOnClickListener(v12 -> {
            if (totalArrowsShot != totalArrows) {
                // Go to next end
                endNum += 1;
                // Reset all score views and values
                shotCount = 0;

                clearShotTextViews();

                nextBtn.setEnabled(false);
                unDoBtn.setEnabled(false);

                // Update End text view
                String endNumberString = String.format(mEndNum + endNum);
                endNumber.setText(endNumberString);
            } else {
                // Show dialog when the round is complete
                showRoundCompleteDialog();
            }
        });

        unDoBtn.setOnClickListener(v1 -> {
            if (arrowsEnd == 3) {
                for (int i = tvShotThree.getId(); i >= tvShotOne.getId(); i--) {
                    TextView shotTextView = view.findViewById(i);
                    shotTextView.setText(null);
                    shotTextView.setBackgroundColor(Color.WHITE);
                    shotTextView.setBackgroundResource(R.drawable.cell_shape);
                }
            } else if (arrowsEnd == 6) {
                for (TextView shotTextView : shotTextViews) {
                    shotTextView.setText(null);
                    shotTextView.setBackgroundColor(Color.WHITE);
                    shotTextView.setBackgroundResource(R.drawable.cell_shape);
                }
            }

            shotCount -= 1;
            totalArrowsShot -= 1;
            arrowsScoreList.remove(shotString);
            distanceValues.remove(distanceValues.size() - 1);

            if (arrowsEnd == 3 && tvShotOne.getText() == null) {
                nextBtn.setEnabled(false);
            }
        });

    }

//        @SuppressLint("ResourceType")
//        @Override
//        public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(v, savedInstanceState);
//        view = v;
//
//        // The numbered End of the round.
//        endNumber = v.findViewById(R.id.shooting_end_num);
//        String endNumString = String.format(mEndNum + endNum);
//        endNumber.setText(endNumString);
//
//        // Display the distance being shot at this end.
//        distDisplay = v.findViewById(R.id.shooting_end_dist);
//        // Total score for the round.
//        endScore_roundScore = v.findViewById(R.id.shooting_total_score);
//        // TextView for three shot layout
//        tvShotOne = v.findViewById(R.id.shot_one_textView);
//        tvShotTwo = v.findViewById(R.id.shot_two_textView);
//        tvShotThree = v.findViewById(R.id.shot_three_textView);
//
//        nextBtn = v.findViewById(R.id.next_btn);
//        nextBtn.setEnabled(false);
//        unDoBtn = v.findViewById(R.id.undo_btn);
//        unDoBtn.setEnabled(false);
//
//        arrowsScoreList = new ArrayList<>();
//        threeShotLayout = v.findViewById(R.id.three_shot_layout);
//        sixShotLayout = v.findViewById(R.id.six_shot_layout);
//
//        // TextView for six shot layout
//        shotOneTextView = v.findViewById(R.id.shot_one_text_view);
//        shotTwoTextView = v.findViewById(R.id.shot_two_text_view);
//        shotThreeTextView = v.findViewById(R.id.shot_three_text_view);
//        shotFourTextView = v.findViewById(R.id.shot_four_text_view);
//        shotFiveTextView = v.findViewById(R.id.shot_five_text_view);
//        shotSixTextView = v.findViewById(R.id.shot_six_text_view);
//
//        // Touch imageView to provide X for bullseye, 0 for a miss or number value of shot.
//        v.findViewById(R.id.x_bn).setOnTouchListener(this);
//        v.findViewById(R.id.one_bn).setOnTouchListener(this);
//        v.findViewById(R.id.two_bn).setOnTouchListener(this);
//        v.findViewById(R.id.three_bn).setOnTouchListener(this);
//        v.findViewById(R.id.four_bn).setOnTouchListener(this);
//        v.findViewById(R.id.five_bn).setOnTouchListener(this);
//        v.findViewById(R.id.six_bn).setOnTouchListener(this);
//        v.findViewById(R.id.seven_bn).setOnTouchListener(this);
//        v.findViewById(R.id.eight_bn).setOnTouchListener(this);
//        v.findViewById(R.id.nine_bn).setOnTouchListener(this);
//        v.findViewById(R.id.ten_bn).setOnTouchListener(this);
//        v.findViewById(R.id.miss_bn).setOnTouchListener(this);
//
//        // String value from textView
//        shotOne = shotOneTextView.getText().toString();
//        shotTwo = shotTwoTextView.getText().toString();
//        shotThree = shotThreeTextView.getText().toString();
//        shotFour = shotFourTextView.getText().toString();
//        shotFive = shotFiveTextView.getText().toString();
//        shotSix = shotSixTextView.getText().toString();
//
//        // Distance object from activity selected distance
//        distance = mListener.getDistance();
//
//        // Number of arrows per end
//        arrowsEnd = distance.getArrowsEnd();
//        /* Scoring style: 0 - metric outdoors, 1 - imperial outdoors, 2 - indoors full,
//       3 - indoors 3 spot,  */
//        scoringStyle = distance.getScoringStyle();
//        // Number of arrows shot at this distance
//        arrowsAtDistance = distance.getArrowsAtDistance();
//
//
//        if (arrowsEnd == 3) {
//            threeShotLayout.setVisibility(View.VISIBLE);
//            sixShotLayout.setVisibility(View.GONE);
//        }else if (arrowsEnd == 6) {
//            threeShotLayout.setVisibility(View.GONE);
//            sixShotLayout.setVisibility(View.VISIBLE);
//        }
//
//        nextBtn.setOnClickListener(v12 -> {
//
//            if (totalArrowsShot != totalArrows) {
//
//                if (arrowsEnd == 3) {
//
//                    //Go to next End
//                    endNum += 1;
//                    // Reset all score views and values
//                    shotCount = 0;
//
//                    tvShotOne.setText(null);
//                    tvShotOne.setBackgroundColor(Color.WHITE);
//                    tvShotOne.setBackgroundResource(R.drawable.cell_shape);
//                    tvShotOne.requestFocus();
//
//                    tvShotTwo.setText(null);
//                    tvShotTwo.setBackgroundColor(Color.WHITE);
//                    tvShotTwo.setBackgroundResource(R.drawable.cell_shape);
//
//                    tvShotThree.setText(null);
//                    tvShotThree.setBackgroundColor(Color.WHITE);
//                    tvShotThree.setBackgroundResource(R.drawable.cell_shape);
//
//                    nextBtn.setEnabled(false);
//                    unDoBtn.setEnabled(false);
//
//                }
//                else if (arrowsEnd == 6) {
//
//                    //Go to next End
//                    endNum += 1;
//                    // Reset all score views and values
//                    shotCount = 0;
//
//                    shotOneTextView.setText(null);
//                    shotOneTextView.setBackgroundColor(Color.WHITE);
//                    shotOneTextView.setBackgroundResource(R.drawable.cell_shape);
//                    shotOneTextView.requestFocus();
//
//                    shotTwoTextView.setText(null);
//                    shotTwoTextView.setBackgroundColor(Color.WHITE);
//                    shotTwoTextView.setBackgroundResource(R.drawable.cell_shape);
//
//                    shotThreeTextView.setText(null);
//                    shotThreeTextView.setBackgroundColor(Color.WHITE);
//                    shotThreeTextView.setBackgroundResource(R.drawable.cell_shape);
//
//                    shotFourTextView.setText(null);
//                    shotFourTextView.setBackgroundColor(Color.WHITE);
//                    shotFourTextView.setBackgroundResource(R.drawable.cell_shape);
//
//                    shotFiveTextView.setText(null);
//                    shotFiveTextView.setBackgroundColor(Color.WHITE);
//                    shotFiveTextView.setBackgroundResource(R.drawable.cell_shape);
//
//                    shotSixTextView.setText(null);
//                    shotSixTextView.setBackgroundColor(Color.WHITE);
//                    shotSixTextView.setBackgroundResource(R.drawable.cell_shape);
//
//                    nextBtn.setEnabled(false);
//                    unDoBtn.setEnabled(false);
//                }
//
//                // Update End text view
//                String endNumberString = String.format(mEndNum + endNum);
//                endNumber.setText(endNumberString);
//
//            }else {
//
//
//                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
//                dialog.setTitle("Round Complete");
//                dialog.setMessage("Go back and pick your next round.");
//
//                dialog.setPositiveButton("YES", (dialog1, which) -> {
//                    mListener.nextRound(totalArrowsShot, arrowsScoreList);
//                });
//
//                dialog.setNegativeButton("Cancel", (dialog12, which) -> {
//
//                });
//                dialog.show();
//
//            }
//        });
//
//        unDoBtn.setOnClickListener(v1 -> {
//
//                if (arrowsEnd == 3) {
//                    for (int i = tvShotThree.getId(); i >= tvShotOne.getId(); i--) {
//                        TextView shotTextView = view.findViewById(i);
//                        shotTextView.setText(null);
//                        shotTextView.setBackgroundColor(Color.WHITE);
//                        shotTextView.setBackgroundResource(R.drawable.cell_shape);
//                    }
//                } else if (arrowsEnd == 6) {
//                    for (TextView shotTextView : shotTextViews) {
//                        shotTextView.setText(null);
//                        shotTextView.setBackgroundColor(Color.WHITE);
//                        shotTextView.setBackgroundResource(R.drawable.cell_shape);
//                    }
//                }
//
//                shotCount -= 1;
//                totalArrowsShot -= 1;
//                arrowsScoreList.remove(shotString);
//                distanceValues.remove(distanceValues.size() - 1);
//
//                if (arrowsEnd == 3 && tvShotOne.getText() == null) {
//                    nextBtn.setEnabled(false);
//                }
//
////            if (arrowsEnd == 3) {
////                if (tvShotThree.getText() != null) {
////                    tvShotThree.setText(null);
////                    tvShotThree.setBackgroundColor(Color.WHITE);
////                    tvShotThree.setBackgroundResource(R.drawable.cell_shape);
////                    tvShotTwo.requestFocus();
////                } else if (tvShotTwo.getText() != null) {
////                    tvShotTwo.setText(null);
////                    tvShotTwo.setBackgroundColor(Color.WHITE);
////                    tvShotTwo.setBackgroundResource(R.drawable.cell_shape);
////                    tvShotOne.requestFocus();
////                } else if (tvShotOne.getText() != null) {
////                    tvShotOne.setText(null);
////                    tvShotOne.setBackgroundColor(Color.WHITE);
////                    tvShotOne.setBackgroundResource(R.drawable.cell_shape);
////                }
////            } else if (arrowsEnd == 6) {
////                if (shotSixTextView.getText() != null) {
////                    shotSixTextView.setText(null);
////                    shotSixTextView.setBackgroundColor(Color.WHITE);
////                    shotSixTextView.setBackgroundResource(R.drawable.cell_shape);
////                    shotFiveTextView.requestFocus();
////                } else if (shotFiveTextView.getText() != null) {
////                    shotFiveTextView.setText(null);
////                    shotFiveTextView.setBackgroundColor(Color.WHITE);
////                    shotFiveTextView.setBackgroundResource(R.drawable.cell_shape);
////                    shotFourTextView.requestFocus();
////                } else if (shotFourTextView.getText() != null) {
////                    shotFourTextView.setText(null);
////                    shotFourTextView.setBackgroundColor(Color.WHITE);
////                    shotFourTextView.setBackgroundResource(R.drawable.cell_shape);
////                    shotThreeTextView.requestFocus();
////                } else if (shotThreeTextView.getText() != null) {
////                    shotThreeTextView.setText(null);
////                    shotThreeTextView.setBackgroundColor(Color.WHITE);
////                    shotThreeTextView.setBackgroundResource(R.drawable.cell_shape);
////                    shotTwoTextView.requestFocus();
////                } else if (shotTwoTextView.getText() != null) {
////                    shotTwoTextView.setText(null);
////                    shotTwoTextView.setBackgroundColor(Color.WHITE);
////                    shotTwoTextView.setBackgroundResource(R.drawable.cell_shape);
////                    shotOneTextView.requestFocus();
////                } else if (shotOneTextView.getText() != null) {
////                    shotOneTextView.setText(null);
////                    shotOneTextView.setBackgroundColor(Color.WHITE);
////                    shotOneTextView.setBackgroundResource(R.drawable.cell_shape);
////                }
////            }
////
////            shotCount -= 1;
////            totalArrowsShot -= 1;
////            arrowsScoreList.remove(shotString);
////            distanceValues.remove(distanceValues.size() - 1);
////
////            if (arrowsEnd == 3 && tvShotOne.getText() == null) {
////                nextBtn.setEnabled(false);
////            }
//        });
//    }

    private void clearShotTextViews() {
        if (arrowsEnd == 3) {
            tvShotOne.setText(null);
            tvShotOne.setBackgroundColor(Color.WHITE);
            tvShotOne.setBackgroundResource(R.drawable.cell_shape);

            tvShotTwo.setText(null);
            tvShotTwo.setBackgroundColor(Color.WHITE);
            tvShotTwo.setBackgroundResource(R.drawable.cell_shape);

            tvShotThree.setText(null);
            tvShotThree.setBackgroundColor(Color.WHITE);
            tvShotThree.setBackgroundResource(R.drawable.cell_shape);
        } else if (arrowsEnd == 6) {
            for (TextView shotTextView : shotTextViews) {
                shotTextView.setText(null);
                shotTextView.setBackgroundColor(Color.WHITE);
                shotTextView.setBackgroundResource(R.drawable.cell_shape);
            }
        }
    }

    private void showRoundCompleteDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("Round Complete");
        dialog.setMessage("Go back and pick your next round.");

        dialog.setPositiveButton("YES", (dialog1, which) -> {
            mListener.nextRound(totalArrowsShot, arrowsScoreList);
        });

        dialog.setNegativeButton("Cancel", (dialog12, which) -> {
            // Handle cancel button click
        });

        dialog.show();
    }

    private void shooting() {
        int maxArrowVal;

        // Determine the maximum arrow value based on the scoring style
        if (scoringStyle == 0 || scoringStyle == 2 || scoringStyle == 3) {
            maxArrowVal = 10;
        } else if (scoringStyle == 1) {
            maxArrowVal = 9;
        } else {
            maxArrowVal = 0;
            Log.d("Scoring", "Error loading from shared preferences");
        }

        totalArrows = Integer.parseInt(arrowsAtDistance);
        int totalScore = totalArrows * maxArrowVal;

        if (arrowsEnd == 3) {
            // Handling shooting with 3 arrows per end
            handleThreeArrows(totalArrows, maxArrowVal, totalScore);
        } else if (arrowsEnd == 6) {
            // Handling shooting with 6 arrows per end
            handleSixArrows(totalArrows, maxArrowVal, totalScore);
        }

        // Calculating current round score
        int currentScore = 0;
        for (int i = 0; i < distanceValues.size(); i++) {
            currentScore += distanceValues.get(i);
            Log.i(TAG, "shooting: Current Score " + currentScore);
        }

        finalCurrentScore = currentScore;
        String totalScoreString = currentScore + "/" + totalScore;
        Log.i(TAG, "shooting: Total Score String " + totalScoreString);
        endScore_roundScore.setText(totalScoreString);
    }

    private void handleThreeArrows(int totalArrows, int maxArrowVal, int totalScore) {
        String shotOneString = tvShotOne.getText().toString();
        String shotTwoString = tvShotTwo.getText().toString();
        String shotThreeString = tvShotThree.getText().toString();

        if (totalArrowsShot != totalArrows) {
            // Check if there are available arrow slots

            if (shotOneString.isEmpty() && shotTwoString.isEmpty() && shotThreeString.isEmpty()) {
                // First arrow
                handleShot(tvShotOne, 1, maxArrowVal);
            } else if (!shotOneString.isEmpty() && shotTwoString.isEmpty() && shotThreeString.isEmpty()) {
                // Second arrow
                handleShot(tvShotTwo, 2, maxArrowVal);
            } else if (!shotOneString.isEmpty() && !shotTwoString.isEmpty() && shotThreeString.isEmpty()) {
                // Third arrow
                handleShot(tvShotThree, 3, maxArrowVal);
                nextBtn.setEnabled(true);
            }
        } else {
            // Show dialog when the last round is complete
            showLastRoundCompleteDialog();
        }
    }

    private void handleSixArrows(int totalArrows, int maxArrowVal, int totalScore) {
        if (totalArrowsShot != totalArrows) {
            // Check if there are available arrow slots

            TextView currentShotTextView = findEmptyShotTextView();
            if (currentShotTextView != null) {
                int shotIndex = Integer.parseInt(currentShotTextView.getTag().toString());
                String shotTextViewString = "Shot " + shotIndex;
                handleShot(currentShotTextView, shotIndex, maxArrowVal);
                if (shotIndex == 6) {
                    nextBtn.setEnabled(true);
                }
            }
        } else {
            // Show dialog when the last round is complete
            showLastRoundCompleteDialog();
        }
    }

    private void handleShot(TextView shotTextView, int shotIndex, int maxArrowVal) {
        shotTextView.requestFocus();
        shotTextView.setText(shotString);
        shotTextView.setBackgroundColor(bgColor);
        shotTextView.setTextColor(bgColor == Color.BLACK || bgColor == Color.BLUE ? Color.WHITE : Color.BLACK);
        arrowsScoreList.add(shotString);
        distanceValues.add(maxArrowVal);
    }

    private void showLastRoundCompleteDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("Last Round Complete");
        dialog.setMessage("Go back and save round.");

        dialog.setPositiveButton("YES", (dialog1, which) -> {
            mListener.nextRound(totalArrowsShot, arrowsScoreList);
        });
        dialog.show();
    }

    private TextView findEmptyShotTextView() {
        String[] shotTextViewIds = {
                "shotOneTextView", "shotTwoTextView", "shotThreeTextView",
                "shotFourTextView", "shotFiveTextView", "shotSixTextView"
        };

        for (String textViewId : shotTextViewIds) {
            TextView textView = view.findViewById(getResources().getIdentifier(textViewId, "id",
                    getActivity().getPackageName()));
            String textViewString = textView.getText().toString();
            if (textViewString.isEmpty()) {
                return textView;
            }
        }

        return null;
    }

//
//    private void shooting() {
//        int maxArrowVal;
//
//        if (scoringStyle == 0 || scoringStyle == 2 || scoringStyle == 3) {
//            maxArrowVal = 10;
//        } else if (scoringStyle == 1) {
//            maxArrowVal = 9;
//        } else {
//            maxArrowVal = 0;
//            Log.d("Scoring", "Error loading from shared preferences");
//        }
//
//        totalArrows = Integer.parseInt(arrowsAtDistance);
//        int totalScore = totalArrows * maxArrowVal;
//
//        if (arrowsEnd == 3) {
//            shotOneString = tvShotOne.getText().toString();
//            shotTwoString = tvShotTwo.getText().toString();
//            shotThreeString = tvShotThree.getText().toString();
//
//            if (totalArrowsShot != totalArrows) {
//                if (shotCount <= arrowsEnd || nextBtn.isEnabled()) {
//                    Log.i(TAG, "shooting: Shot Count 3 per end " + shotCount);
//
//                    if (shotOneString.isEmpty() && shotTwoString.isEmpty() && shotThreeString.isEmpty()) {
//                        unDoBtn.setEnabled(true);
//                        tvShotOne.requestFocus();
//                        Log.i(TAG, "shooting: First Arrow in Focus");
//                        tvShotOne.setText(shotString);
//                        tvShotOne.setBackgroundColor(bgColor);
//                        Log.i(TAG, "shooting: Shot String " + shotString);
//                        arrowsScoreList.add(shotString);
//                        distanceValues.add(keyValue);
//                        Log.i(TAG, "shooting: Distance value list: " + keyValue
//                                + " value added; distanceValues contains: " + distanceValues.size() + " items.");
//                        tvShotOne.setTextColor(bgColor == Color.BLACK || bgColor == Color.BLUE ? Color.WHITE : Color.BLACK);
//                    } else if (!shotOneString.isEmpty() && shotTwoString.isEmpty() && shotThreeString.isEmpty()) {
//                        tvShotTwo.requestFocus();
//                        Log.i(TAG, "shooting: Second Arrow in Focus");
//                        tvShotTwo.setText(shotString);
//                        tvShotTwo.setBackgroundColor(bgColor);
//                        Log.i(TAG, "shooting: Shot String " + shotString);
//                        arrowsScoreList.add(shotString);
//                        distanceValues.add(keyValue);
//                        Log.i(TAG, "shooting: Distance value list: " + keyValue
//                                + " value added; distanceValues contains: " + distanceValues.size() + " items.");
//                        tvShotTwo.setTextColor(bgColor == Color.BLACK || bgColor == Color.BLUE ? Color.WHITE : Color.BLACK);
//                    } else if (!shotOneString.isEmpty() && !shotTwoString.isEmpty() && shotThreeString.isEmpty()) {
//                        tvShotThree.requestFocus();
//                        Log.i(TAG, "shooting: Third Arrow in Focus");
//                        tvShotThree.setText(shotString);
//                        tvShotThree.setBackgroundColor(bgColor);
//                        Log.i(TAG, "shooting: Shot String " + shotString);
//                        arrowsScoreList.add(shotString);
//                        distanceValues.add(keyValue);
//                        Log.i(TAG, "shooting: Distance value list: " + keyValue
//                                + " value added; distanceValues contains: " + distanceValues.size() + " items.");
//                        tvShotThree.setTextColor(bgColor == Color.BLACK || bgColor == Color.BLUE ? Color.WHITE : Color.BLACK);
//                        nextBtn.setEnabled(true);
//                    }
//                }
//            } else {
//                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
//                dialog.setTitle("Last Round Complete");
//                dialog.setMessage("Go back and save round.");
//
//                dialog.setPositiveButton("YES", (dialog1, which) -> {
//                    mListener.nextRound(totalArrowsShot, arrowsScoreList);
//                });
//                dialog.show();
//            }
//        } else if (arrowsEnd == 6) {
//            if (totalArrowsShot != totalArrows) {
//                if (shotCount <= arrowsEnd || nextBtn.isEnabled()) {
//                    Log.i(TAG, "shooting: Shot Count 6 per end " + shotCount);
//
//                    TextView currentShotTextView = null;
//                    String[] shotTextViewIds = {
//                            "shotOneTextView", "shotTwoTextView", "shotThreeTextView",
//                            "shotFourTextView", "shotFiveTextView", "shotSixTextView"
//                    };
//
//                    for (String textViewId : shotTextViewIds) {
//                        TextView textView = view.findViewById(getResources().getIdentifier(textViewId, "id",
//                                requireActivity().getPackageName()));
//                        String textViewString = textView.getText().toString();
//                        if (textViewString.isEmpty()) {
//                            currentShotTextView = textView;
//                            break;
//                        }
//                    }
//
//                    if (currentShotTextView != null) {
//                        currentShotTextView.setFocusable(true);
//                        int shotIndex = Arrays.asList(shotTextViewIds).indexOf(currentShotTextView.getId());
//                        String shotTextViewString = "Shot " + (shotIndex + 1);
//                        Log.i(TAG, "shooting: " + shotTextViewString + " Arrow in Focus");
//                        currentShotTextView.setText(shotString);
//                        currentShotTextView.setBackgroundColor(bgColor);
//                        Log.i(TAG, "shooting: Shot String " + shotString);
//                        arrowsScoreList.add(shotString);
//                        distanceValues.add(keyValue);
//                        Log.i(TAG, "shooting: Distance value list: " + keyValue
//                                + " value added; distanceValues contains: " + distanceValues.size() + " items.");
//                        currentShotTextView.setTextColor(bgColor == Color.BLACK || bgColor == Color.BLUE ? Color.WHITE : Color.BLACK);
//                        if (shotIndex == 5) {
//                            nextBtn.setEnabled(true);
//                        }
//                    }
//                }
//            } else {
//                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
//                dialog.setTitle("Last Round Complete");
//                dialog.setMessage("Go back and save round.");
//
//                dialog.setPositiveButton("YES", (dialog1, which) -> {
//                    mListener.nextRound(totalArrowsShot, arrowsScoreList);
//                });
//                dialog.show();
//            }
//        }
//
//        int currentScore = 0;
//        // Calculating round total score
//        for (int i = 0; i < distanceValues.size(); i++) {
//            currentScore += distanceValues.get(i);
//            Log.i(TAG, "shooting: Current Score " + currentScore);
//        }
//
//        finalCurrentScore = currentScore;
//        String totalScoreString = currentScore + "/" + totalScore;
//        Log.i(TAG, "shooting: Total Score String " + totalScoreString);
//        endScore_roundScore.setText(totalScoreString);
//    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            v.performClick();

            if (v.getId() == R.id.x_bn) {
                shotString = "X";
                shotCount += 1;
                keyValue = 10;
                bgColor = Color.YELLOW;
                shooting();
                totalArrowsShot += 1;
            } else if (v.getId() == R.id.one_bn) {
                shotString = "1";
                shotCount += 1;
                keyValue = 1;
                bgColor = Color.WHITE;
                shooting();
                totalArrowsShot += 1;
            } else if (v.getId() == R.id.two_bn) {
                shotString = "2";
                shotCount += 1;
                keyValue = 2;
                bgColor = Color.WHITE;
                shooting();
                totalArrowsShot += 1;
            } else if (v.getId() == R.id.three_bn) {
                shotString = "3";
                shotCount += 1;
                keyValue = 3;
                bgColor = Color.BLACK;
                shooting();
                totalArrowsShot += 1;
            } else if (v.getId() == R.id.four_bn) {
                shotString = "4";
                shotCount += 1;
                keyValue = 4;
                bgColor = Color.BLACK;
                shooting();
                totalArrowsShot += 1;
            } else if (v.getId() == R.id.five_bn) {
                shotString = "5";
                shotCount += 1;
                keyValue = 5;
                bgColor = Color.BLUE;
                shooting();
                totalArrowsShot += 1;
            } else if (v.getId() == R.id.six_bn) {
                shotString = "6";
                shotCount += 1;
                keyValue = 6;
                bgColor = Color.BLUE;
                shooting();
                totalArrowsShot += 1;
            } else if (v.getId() == R.id.seven_bn) {
                shotString = "7";
                shotCount += 1;
                keyValue = 7;
                bgColor = Color.RED;
                shooting();
                totalArrowsShot += 1;
            } else if (v.getId() == R.id.eight_bn) {
                shotString = "8";
                shotCount += 1;
                keyValue = 8;
                bgColor = Color.RED;
                shooting();
                totalArrowsShot += 1;
            } else if (v.getId() == R.id.nine_bn) {
                shotString = "9";
                shotCount += 1;
                keyValue = 9;
                bgColor = Color.YELLOW;
                shooting();
                totalArrowsShot += 1;
            } else if (v.getId() == R.id.ten_bn) {
                shotString = "10";
                shotCount += 1;
                keyValue = 10;
                bgColor = Color.YELLOW;
                shooting();
                totalArrowsShot += 1;
            } else if (v.getId() == R.id.miss_bn) {
                shotString = "M";
                shotCount += 1;
                keyValue = 0;
                bgColor = Color.GREEN;
                shooting();
                totalArrowsShot += 1;
            }

        }

        return true;
    }

}
package com.fullsail.apolloarchery.fragments;

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
import com.fullsail.apolloarchery.object.Round;
import com.fullsail.apolloarchery.object.ShootingListener;

import java.util.ArrayList;
import java.util.List;

public class ShootingFragment extends Fragment implements View.OnTouchListener {
    public static final String TAG = "ShootingFragment";


    View view;
    private int arrowsEnd;
    private int end;
    private int scoringStyle;

    private int shotCount;
    private int keyValue;
    public static String shotString = "";
    private List<String> arrowsScoreList;
    private final List<Integer> distanceValues = new ArrayList<>();
    private int totalArrowsShot = 0;
    String mEndNum = "End";
    int endNum = 1;
    int totalArrows = 0;
    Button nextBtn, unDoBtn;
    TextView endNumber, distDisplay, endScore_roundScore, tvShotOne, tvShotTwo, tvShotThree;
    TextView shotOneTextView, shotTwoTextView, shotThreeTextView, shotFourTextView;
    TextView shotFiveTextView, shotSixTextView;
    List<String> arrowsDistances;
    List<String> arrowsAtDistance;
    ShootingListener mListener;
    int bgColor;
    int finalCurrentScore;
    LinearLayout threeShotLayout, sixShotLayout;

    String  shotOneString, shotTwoString, shotThreeString;

    String shotOne, shotTwo, shotThree, shotFour, shotFive, shotSix;

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
        arrowsScoreList = new ArrayList<>();
        threeShotLayout = v.findViewById(R.id.three_shot_layout);
        sixShotLayout = v.findViewById(R.id.six_shot_layout);

        shotOneTextView = v.findViewById(R.id.shot_one_text_view);
        shotTwoTextView = v.findViewById(R.id.shot_two_text_view);
        shotThreeTextView = v.findViewById(R.id.shot_three_text_view);
        shotFourTextView = v.findViewById(R.id.shot_four_text_view);
        shotFiveTextView = v.findViewById(R.id.shot_five_text_view);
        shotSixTextView = v.findViewById(R.id.shot_six_text_view);

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

        shotOne = shotOneTextView.getText().toString();
        shotTwo = shotTwoTextView.getText().toString();
        shotThree = shotThreeTextView.getText().toString();
        shotFour = shotFourTextView.getText().toString();
        shotFive = shotFiveTextView.getText().toString();
        shotSix = shotSixTextView.getText().toString();

        Round round = mListener.getRound();
        arrowsEnd = round.getArrowsPerEnd();
        scoringStyle = round.getScoringType();
        arrowsAtDistance = round.getArrowsDistances();
        arrowsDistances = round.getArrowsDistances();

        if (arrowsEnd == 3) {
            threeShotLayout.setVisibility(View.VISIBLE);
            sixShotLayout.setVisibility(View.GONE);
        }else if (arrowsEnd == 6) {
            threeShotLayout.setVisibility(View.GONE);
            sixShotLayout.setVisibility(View.VISIBLE);
        }

        nextBtn.setOnClickListener(v12 -> {

            if (totalArrowsShot != totalArrows) {

                if (arrowsEnd == 3) {

                    //Go to next End
                    endNum += 1;
                    // Reset all score views and values
                    shotCount = 0;

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

                }
                else if (arrowsEnd == 6) {

                    //Go to next End
                    endNum += 1;
                    // Reset all score views and values
                    shotCount = 0;

                    shotOneTextView.setText(null);
                    shotOneTextView.setBackgroundColor(Color.WHITE);
                    shotOneTextView.setBackgroundResource(R.drawable.cell_shape);
                    shotOneTextView.requestFocus();

                    shotTwoTextView.setText(null);
                    shotTwoTextView.setBackgroundColor(Color.WHITE);
                    shotTwoTextView.setBackgroundResource(R.drawable.cell_shape);

                    shotThreeTextView.setText(null);
                    shotThreeTextView.setBackgroundColor(Color.WHITE);
                    shotThreeTextView.setBackgroundResource(R.drawable.cell_shape);

                    shotFourTextView.setText(null);
                    shotFourTextView.setBackgroundColor(Color.WHITE);
                    shotFourTextView.setBackgroundResource(R.drawable.cell_shape);

                    shotFiveTextView.setText(null);
                    shotFiveTextView.setBackgroundColor(Color.WHITE);
                    shotFiveTextView.setBackgroundResource(R.drawable.cell_shape);

                    shotSixTextView.setText(null);
                    shotSixTextView.setBackgroundColor(Color.WHITE);
                    shotSixTextView.setBackgroundResource(R.drawable.cell_shape);

                    nextBtn.setEnabled(false);
                    unDoBtn.setEnabled(false);
                }

                // Update End text view
                String endNumberString = String.format(mEndNum + endNum);
                endNumber.setText(endNumberString);

            }else {


                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("Round Complete");
                dialog.setMessage("Go back and pick your next round.");

                dialog.setPositiveButton("YES", (dialog1, which) -> {
                    mListener.nextRound(totalArrowsShot, arrowsScoreList);
                });

                dialog.setNegativeButton("Cancel", (dialog12, which) -> {

                });
                dialog.show();

            }
        });

        unDoBtn.setOnClickListener(v1 -> {

            if (arrowsEnd == 3) {

                if (tvShotOne.getText() != null && tvShotTwo.getText() != null && tvShotThree.getText() != null) {
                    // Clear shot and reset focus
                    tvShotThree.setText(null);
                    tvShotThree.setBackgroundColor(Color.WHITE);
                    tvShotThree.setBackgroundResource(R.drawable.cell_shape);
                    tvShotTwo.requestFocus();

                    shotCount -= 1;
                    totalArrowsShot -= 1;
                    arrowsScoreList.remove(shotString);
                    distanceValues.remove(distanceValues.size() - 1);
                    nextBtn.setEnabled(false);

                } else if (tvShotThree.getText() == null && tvShotTwo.getText() != null && tvShotOne.getText() != null) {

                    tvShotTwo.setText(null);
                    tvShotTwo.setBackgroundColor(Color.WHITE);
                    tvShotTwo.setBackgroundResource(R.drawable.cell_shape);
                    tvShotOne.requestFocus();

                    shotCount -= 1;
                    totalArrowsShot -= 1;
                    arrowsScoreList.remove(shotString);
                    distanceValues.remove(distanceValues.size() - 1);

                } else if (tvShotThree.getText() == null && tvShotTwo.getText() == null && tvShotOne.getText() != null) {

                    tvShotOne.setText(null);
                    tvShotOne.setBackgroundColor(Color.WHITE);
                    tvShotOne.setBackgroundResource(R.drawable.cell_shape);

                    shotCount -= 1;
                    totalArrowsShot -= 1;
                    arrowsScoreList.remove(shotString);
                    distanceValues.remove(distanceValues.size() - 1);

                }

            }
            else if (arrowsEnd == 6) {

                if (shotOneTextView.getText() != null && shotTwoTextView.getText() != null && shotThreeTextView.getText() != null
                        && shotFourTextView.getText() != null && shotFiveTextView.getText() != null && shotSixTextView.getText() != null) {

                    shotSixTextView.setText(null);
                    shotSixTextView.setBackgroundColor(Color.WHITE);
                    shotSixTextView.setBackgroundResource(R.drawable.cell_shape);
                    shotFiveTextView.requestFocus();

                    shotCount -= 1;
                    totalArrowsShot -= 1;
                    arrowsScoreList.remove(shotString);
                    distanceValues.remove(distanceValues.size() - 1);
                    nextBtn.setEnabled(false);

                }else if (shotOneTextView.getText() != null && shotTwoTextView.getText() != null && shotThreeTextView.getText() != null
                        && shotFourTextView.getText() != null && shotFiveTextView.getText() != null && shotSixTextView.getText() == null) {

                    shotFiveTextView.setText(null);
                    shotFiveTextView.setBackgroundColor(Color.WHITE);
                    shotFiveTextView.setBackgroundResource(R.drawable.cell_shape);
                    shotFourTextView.requestFocus();

                    shotCount -= 1;
                    totalArrowsShot -= 1;
                    arrowsScoreList.remove(shotString);
                    distanceValues.remove(distanceValues.size() - 1);

                }else if (shotOneTextView.getText() != null && shotTwoTextView.getText() != null && shotThreeTextView.getText() != null
                        && shotFourTextView.getText() != null && shotFiveTextView.getText() == null && shotSixTextView.getText() == null) {

                    shotFourTextView.setText(null);
                    shotFourTextView.setBackgroundColor(Color.WHITE);
                    shotFourTextView.setBackgroundResource(R.drawable.cell_shape);
                    shotThreeTextView.requestFocus();

                    shotCount -= 1;
                    totalArrowsShot -= 1;
                    arrowsScoreList.remove(shotString);
                    distanceValues.remove(distanceValues.size() - 1);

                }else if (shotOneTextView.getText() != null && shotTwoTextView.getText() != null && shotThreeTextView.getText() != null
                        && shotFourTextView.getText() == null && shotFiveTextView.getText() == null && shotSixTextView.getText() == null) {

                    shotThreeTextView.setText(null);
                    shotThreeTextView.setBackgroundColor(Color.WHITE);
                    shotThreeTextView.setBackgroundResource(R.drawable.cell_shape);
                    shotTwoTextView.requestFocus();

                    shotCount -= 1;
                    totalArrowsShot -= 1;
                    arrowsScoreList.remove(shotString);
                    distanceValues.remove(distanceValues.size() - 1);

                }else if (shotOneTextView.getText() != null && shotTwoTextView.getText() != null && shotThreeTextView.getText() == null
                        && shotFourTextView.getText() == null && shotFiveTextView.getText() == null && shotSixTextView.getText() == null) {

                    shotTwoTextView.setText(null);
                    shotTwoTextView.setBackgroundColor(Color.WHITE);
                    shotTwoTextView.setBackgroundResource(R.drawable.cell_shape);
                    shotOneTextView.requestFocus();

                    shotCount -= 1;
                    totalArrowsShot -= 1;
                    arrowsScoreList.remove(shotString);
                    distanceValues.remove(distanceValues.size() - 1);

                }else if (shotOneTextView.getText() != null && shotTwoTextView.getText() == null && shotThreeTextView.getText() == null
                        && shotFourTextView.getText() == null && shotFiveTextView.getText() == null && shotSixTextView.getText() == null) {

                    shotOneTextView.setText(null);
                    shotOneTextView.setBackgroundColor(Color.WHITE);
                    shotOneTextView.setBackgroundResource(R.drawable.cell_shape);

                    shotCount -= 1;
                    totalArrowsShot -= 1;
                    arrowsScoreList.remove(shotString);
                    distanceValues.remove(distanceValues.size() - 1);

                }

            }
        });
    }

    private void shooting() {

        int maxArrowVal;

        if (scoringStyle == 0 || scoringStyle == 2 || scoringStyle == 3) {
            maxArrowVal = 10;
        }
        else if (scoringStyle == 1) {
            maxArrowVal = 9;
        }
        else {
            maxArrowVal = 0;
            Log.d("Scoring", "Error loading from shared preferences");
        }

        for (int i = 0; i < arrowsDistances.size(); i++) {
            totalArrows = Integer.parseInt(arrowsDistances.get(i));
            end = Integer.parseInt(arrowsDistances.get(i)) / arrowsEnd;
        }

        int totalScore = totalArrows * maxArrowVal;

        if (arrowsEnd == 3) {

            shotOneString = tvShotOne.getText().toString();
            shotTwoString = tvShotTwo.getText().toString();
            shotThreeString = tvShotThree.getText().toString();

            if (totalArrowsShot != totalArrows) {

                if (shotCount <= arrowsEnd || nextBtn.isEnabled()) {
                    Log.i(TAG, "shooting: Shot Count 3 per end " + shotCount);

                    if (shotOneString.isBlank() && shotTwoString.isBlank() && shotThreeString.isBlank()) {

                        unDoBtn.setEnabled(true);

                        tvShotOne.requestFocus();
                        Log.i(TAG, "shooting: First Arrow in Focus");
                        tvShotOne.setText(shotString);
                        tvShotOne.setBackgroundColor(bgColor);

                        Log.i(TAG, "shooting: Shot String " + shotString);

                        arrowsScoreList.add(shotString);
                        distanceValues.add(keyValue);
                        Log.i(TAG, "shooting: Distance value list: " + keyValue
                                + " value added; distanceValues contains: " + distanceValues.size() + " items.");

                        if (bgColor == Color.BLACK || bgColor == Color.BLUE) {
                        tvShotOne.setTextColor(Color.WHITE);
                        } else {
                        tvShotOne.setTextColor(Color.BLACK);
                        }

                    } else if (!shotOneString.isBlank() && shotTwoString.isBlank() && shotThreeString.isBlank()) {

                        tvShotTwo.requestFocus();
                        Log.i(TAG, "shooting: Second Arrow in Focus");
                        tvShotTwo.setText(shotString);
                        tvShotTwo.setBackgroundColor(bgColor);

                        Log.i(TAG, "shooting: Shot String " + shotString);

                        arrowsScoreList.add(shotString);
                        distanceValues.add(keyValue);
                        Log.i(TAG, "shooting: Distance value list: " + keyValue
                                + " value added; distanceValues contains: " + distanceValues.size() + " items.");


                        if (bgColor == Color.BLACK || bgColor == Color.BLUE) {
                        tvShotTwo.setTextColor(Color.WHITE);
                        } else {
                        tvShotTwo.setTextColor(Color.BLACK);
                        }

                    } else if (!shotOneString.isBlank() && !shotTwoString.isBlank() && shotThreeString.isBlank()) {

                        tvShotThree.requestFocus();
                        Log.i(TAG, "shooting: Third Arrow in Focus");
                        tvShotThree.setText(shotString);
                        tvShotThree.setBackgroundColor(bgColor);

                        Log.i(TAG, "shooting: Shot String " + shotString);

                        arrowsScoreList.add(shotString);
                        distanceValues.add(keyValue);
                        Log.i(TAG, "shooting: Distance value list: " + keyValue
                                + " value added; distanceValues contains: " + distanceValues.size() + " items.");

                        if (bgColor == Color.BLACK || bgColor == Color.BLUE) {
                            tvShotThree.setTextColor(Color.WHITE);
                        } else {
                        tvShotThree.setTextColor(Color.BLACK);
                        }
                        nextBtn.setEnabled(true);
                    }
                }
            }
        }
        else if (arrowsEnd == 6) {

            if (totalArrowsShot != totalArrows) {

                if (shotCount <= arrowsEnd || nextBtn.isEnabled()) {
                    Log.i(TAG, "shooting: Shot Count 6 per end " + shotCount);

                    if (shotOne.isBlank() && shotTwo.isBlank()  && shotThree.isBlank()
                            && shotFour.isBlank()  && shotFive.isBlank()  && shotSix.isBlank() ) {

                        unDoBtn.setEnabled(true);

                        shotOneTextView.setFocusable(true);
                        Log.i(TAG, "shooting: First Arrow in Focus");

                        shotOneTextView.setText(shotString);
                        shotOneTextView.setBackgroundColor(bgColor);

                        Log.i(TAG, "shooting: Shot String " + shotString);

                        arrowsScoreList.add(shotString);
                        distanceValues.add(keyValue);
                        Log.i(TAG, "shooting: Distance value list: " + keyValue
                                + " value added; distanceValues contains: " + distanceValues.size() + " items.");


                        if (bgColor == Color.BLACK || bgColor == Color.BLUE) {
                            shotOneTextView.setTextColor(Color.WHITE);
                        } else {
                            shotOneTextView.setTextColor(Color.BLACK);
                        }

                    }
                    else if (!shotOne.isBlank() && shotTwo.isBlank()  && shotThree.isBlank()
                            && shotFour.isBlank()  && shotFive.isBlank()  && shotSix.isBlank()) {

                        shotTwoTextView.setFocusable(true);
                        Log.i(TAG, "shooting: Second Arrow in Focus");

                        shotTwoTextView.setText(shotString);
                        shotTwoTextView.setBackgroundColor(bgColor);

                        Log.i(TAG, "shooting: Shot String " + shotString);

                        arrowsScoreList.add(shotString);
                        distanceValues.add(keyValue);
                        Log.i(TAG, "shooting: Distance value list: " + keyValue
                                + " value added; distanceValues contains: " + distanceValues.size() + " items.");


                        if (bgColor == Color.BLACK || bgColor == Color.BLUE) {
                            shotTwoTextView.setTextColor(Color.WHITE);
                        } else {
                            shotTwoTextView.setTextColor(Color.BLACK);
                        }
                    }
                    else if (!shotOne.isBlank() && !shotTwo.isBlank()  && shotThree.isBlank()
                            && shotFour.isBlank()  && shotFive.isBlank()  && shotSix.isBlank()) {

                        shotThreeTextView.setFocusable(true);
                        Log.i(TAG, "shooting: Third Arrow in Focus");

                        shotThreeTextView.setText(shotString);
                        shotThreeTextView.setBackgroundColor(bgColor);

                        Log.i(TAG, "shooting: Shot String " + shotString);

                        arrowsScoreList.add(shotString);
                        distanceValues.add(keyValue);
                        Log.i(TAG, "shooting: Distance value list: " + keyValue
                                + " value added; distanceValues contains: " + distanceValues.size() + " items.");


                        if (bgColor == Color.BLACK || bgColor == Color.BLUE) {
                            shotThreeTextView.setTextColor(Color.WHITE);
                        } else {
                            shotThreeTextView.setTextColor(Color.BLACK);
                        }
                    }
                    else if (!shotOne.isBlank() && !shotTwo.isBlank()  && !shotThree.isBlank()
                            && shotFour.isBlank()  && shotFive.isBlank()  && shotSix.isBlank()) {

                        shotFourTextView.setFocusable(true);
                        Log.i(TAG, "shooting: Fourth Arrow in Focus");

                        shotFourTextView.setText(shotString);
                        shotFourTextView.setBackgroundColor(bgColor);

                        Log.i(TAG, "shooting: Shot String " + shotString);

                        arrowsScoreList.add(shotString);
                        distanceValues.add(keyValue);
                        Log.i(TAG, "shooting: Distance value list: " + keyValue
                                + " value added; distanceValues contains: " + distanceValues.size() + " items.");


                        if (bgColor == Color.BLACK || bgColor == Color.BLUE) {
                            shotFourTextView.setTextColor(Color.WHITE);
                        } else {
                            shotFourTextView.setTextColor(Color.BLACK);
                        }
                    }
                    else if (!shotOne.isBlank() && !shotTwo.isBlank()  && !shotThree.isBlank()
                            && !shotFour.isBlank()  && shotFive.isBlank()  && shotSix.isBlank()) {

                        shotFiveTextView.setFocusable(true);
                        Log.i(TAG, "shooting: Fiftieth Arrow in Focus");

                        shotFiveTextView.setText(shotString);
                        shotFiveTextView.setBackgroundColor(bgColor);

                        Log.i(TAG, "shooting: Shot String " + shotString);

                        arrowsScoreList.add(shotString);
                        distanceValues.add(keyValue);
                        Log.i(TAG, "shooting: Distance value list: " + keyValue
                                + " value added; distanceValues contains: " + distanceValues.size() + " items.");


                        if (bgColor == Color.BLACK || bgColor == Color.BLUE) {
                            shotFiveTextView.setTextColor(Color.WHITE);
                        } else {
                            shotFiveTextView.setTextColor(Color.BLACK);
                        }
                    }
                    else if (!shotOne.isBlank() && !shotTwo.isBlank()  && !shotThree.isBlank()
                            && !shotFour.isBlank()  && !shotFive.isBlank()  && shotSix.isBlank()) {

                        shotSixTextView.setFocusable(true);
                        Log.i(TAG, "shooting: Sixth Arrow in Focus");

                        shotSixTextView.setText(shotString);
                        shotSixTextView.setBackgroundColor(bgColor);

                        Log.i(TAG, "shooting: Shot String " + shotString);

                        arrowsScoreList.add(shotString);
                        distanceValues.add(keyValue);
                        Log.i(TAG, "shooting: Distance value list: " + keyValue
                                + " value added; distanceValues contains: " + distanceValues.size() + " items.");

                        if (bgColor == Color.BLACK || bgColor == Color.BLUE) {
                            shotSixTextView.setTextColor(Color.WHITE);
                        } else {
                            shotSixTextView.setTextColor(Color.BLACK);
                        }
                        nextBtn.setEnabled(true);
                    }

                }
            }
        }

        int currentScore = 0;
        // Calculating round total score
        for (int i = 0; i < distanceValues.size(); i++) {
            currentScore += distanceValues.get(i);

            Log.i(TAG, "shooting: Current Score " + currentScore);
        }

        finalCurrentScore = currentScore;
        String totalScoreString = currentScore + "/" + totalScore;

        Log.i(TAG, "shooting: Total Score String " + totalScoreString);
        endScore_roundScore.setText(totalScoreString);
    }

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
package com.fullsail.apolloarchery.object;

import android.os.Parcel;
import android.os.Parcelable;


import androidx.annotation.NonNull;

import java.util.List;

public class HistoryRound implements Parcelable {

    private String date;

    private String roundName;

    private String round;

    private List<List<String>> arrowValues;

    private int totalScore;

    private String archerString;

    private String scorerString;

    public HistoryRound(String date, String roundName, String round, int totalScore, List<List<String>> arrowValues) {
        this.date = date;
        this.roundName = roundName;
        this.round = round;
        this.totalScore = totalScore;
        this.arrowValues = arrowValues;
    }

    public HistoryRound(String date, String roundName, String round, int totalScore) {
        this.date = date;
        this.roundName = roundName;
        this.round = round;
        this.totalScore = totalScore;
    }

    public HistoryRound(String archerString, String scorerString) {
        this.archerString = archerString;
        this.scorerString = scorerString;
    }

    protected HistoryRound(Parcel in) {
        date = in.readString();
        roundName = in.readString();
        round = in.readString();
        totalScore = in.readInt();
    }

    public static final Creator<HistoryRound> CREATOR = new Creator<HistoryRound>() {
        @Override
        public HistoryRound createFromParcel(Parcel in) {
            return new HistoryRound(in);
        }

        @Override
        public HistoryRound[] newArray(int size) {
            return new HistoryRound[size];
        }
    };

    public String getDate() {
        return date;
    }

    public String getRoundName() {
        return roundName;
    }

    public String getRound() {
        return round;
    }

    public List<List<String>> getArrowValues() {
        return arrowValues;
    }

    public void setArrowValues(List<List<String>> arrowValues) {
        this.arrowValues = arrowValues;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public String getArcherString() {
        return archerString;
    }

    public String getScorerString() {
        return scorerString;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(date);
        dest.writeString(roundName);
        dest.writeString(round);
        dest.writeInt(totalScore);
    }
}

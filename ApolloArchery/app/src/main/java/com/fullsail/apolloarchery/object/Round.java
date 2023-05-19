package com.fullsail.apolloarchery.object;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.List;

public class Round implements Parcelable {

    private String roundName;
    /* Scoring type: 0 - metric outdoors, 1 - imperial outdoors, 2 - indoors full,
       3 - indoors 3 spot,  */
    private int scoringType;
    // List of distances for the round
    private List<String> distances;
    // List of, number of arrows shot at each distance
    private List<String> arrowsDistances;
    // Arrows per an end
    private int arrowsPerEnd;

    private int distanceValue = 0;

    public Round() {
        // Required empty constructor

    }

    public Round(String roundName, int scoringType, List<String> distances, List<String> arrowsDistances, int arrowsPerEnd) {
        this.roundName = roundName;
        this.scoringType = scoringType;
        this.distances = distances;
        this.arrowsDistances = arrowsDistances;
        this.arrowsPerEnd = arrowsPerEnd;
    }

    protected Round(Parcel in) {
        roundName = in.readString();
        scoringType = in.readInt();
        distances = in.createStringArrayList();
        arrowsDistances = in.createStringArrayList();
        arrowsPerEnd = in.readInt();
    }

    public Round(int distanceValue) {

        this.distanceValue = distanceValue;
    }

    public static final Creator<Round> CREATOR = new Creator<Round>() {
        @Override
        public Round createFromParcel(Parcel in) {
            return new Round(in);
        }

        @Override
        public Round[] newArray(int size) {
            return new Round[size];
        }
    };

    public String getRoundName() {
        return roundName;
    }

    public int getScoringType() {
        return scoringType;
    }

    public List<String> getDistances() {
        return distances;
    }

    public List<String> getArrowsDistances() {
        return arrowsDistances;
    }

    public int getArrowsPerEnd() {
        return arrowsPerEnd;
    }

    public int getDistanceValue() {
        return distanceValue;
    }

    @NonNull
    @Override
    public String toString() {
        return roundName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(roundName);
        dest.writeInt(scoringType);
        dest.writeStringList(distances);
        dest.writeStringList(arrowsDistances);
        dest.writeInt(arrowsPerEnd);
    }
}

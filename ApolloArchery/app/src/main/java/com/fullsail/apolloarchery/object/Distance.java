package com.fullsail.apolloarchery.object;

import android.os.Parcel;
import android.os.Parcelable;


public class Distance implements Parcelable {

    private final String distance;
    // Number of arrows shot at this distance
    private final String arrowsAtDistance;
    // Scoring style (same numbering format as Round class
    private final int scoringStyle;

    // Arrows per end
    private final int arrowsEnd;
    // Distance value for ongoing rounds
    private final int distanceValue;

    // Constructor
    public Distance(String distance, String arrowsAtDistance, int scoringStyle, int arrowsEnd, int distanceValue) {
        this.distance = distance;
        this.arrowsAtDistance = arrowsAtDistance;
        this.scoringStyle = scoringStyle;
        this.arrowsEnd = arrowsEnd;
        this.distanceValue = distanceValue;
    }

    protected Distance(Parcel in) {
        distance = in.readString();
        arrowsAtDistance = in.readString();
        scoringStyle = in.readInt();
        arrowsEnd = in.readInt();
        this.distanceValue = in.readInt();
    }

    public static final Creator<Distance> CREATOR = new Creator<Distance>() {
        @Override
        public Distance createFromParcel(Parcel in) {
            return new Distance(in);
        }

        @Override
        public Distance[] newArray(int size) {
            return new Distance[size];
        }
    };

    // Getters
    public String getDistance() {
        return distance;
    }
    public String getArrowsAtDistance() {
        return arrowsAtDistance;
    }
    public int getScoringStyle() {
        return scoringStyle;
    }
    public int getArrowsEnd(){
        return arrowsEnd;
    }

    public int getDistanceValue() {
        return distanceValue;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(distance);
        parcel.writeString(arrowsAtDistance);
        parcel.writeInt(scoringStyle);
        parcel.writeInt(arrowsEnd);
    }
}

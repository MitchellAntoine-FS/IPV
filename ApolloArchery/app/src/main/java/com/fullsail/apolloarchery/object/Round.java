package com.fullsail.apolloarchery.object;

import androidx.annotation.NonNull;

import java.util.List;

public class Round {


    private String roundName;
    /* Scoring type: 0 - metric outdoors, 1 - imperial outdoors, 2 - indoors full,
       3 - indoors 3 spot, 4 - worcester */
    private int scoringType;
    // List of distances for the round include unit e.g. 90m or 100yd
    private List<String> distances;
    // List of number of arrows shot at each distance
    private List<String> arrowsDistance;
    // Arrows per an end
    private int arrowsPerEnd;

    public Round() {
        // Required empty constructor
    }

    public Round(String roundName, int scoringType, List<String> distances, List<String> arrowsDistance, int arrowsPerEnd) {
        this.roundName = roundName;
        this.scoringType = scoringType;
        this.distances = distances;
        this.arrowsDistance = arrowsDistance;
        this.arrowsPerEnd = arrowsPerEnd;
    }

    public String getRoundName() {
        return roundName;
    }

    public int getScoringType() {
        return scoringType;
    }

    public List<String> getDistances() {
        return distances;
    }

    public List<String> getArrowsDistance() {
        return arrowsDistance;
    }

    public int getArrowsPerEnd() {
        return arrowsPerEnd;
    }

    @NonNull
    @Override
    public String toString() {
        return roundName;
    }
}

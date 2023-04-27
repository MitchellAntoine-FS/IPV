package com.fullsail.apolloarchery.object;

import java.util.List;

public class Round {

    private final String roundName;
    /* Scoring type: 0 - metric outdoors, 1 - imperial outdoors, 2 - indoors full,
       3 - indoors 3 spot, 4 - worcester */
    private final int scoringType;
    // List of distances for the round include unit e.g. 90m or 100yd
    private final List<String> distances;
    // List of number of arrows shot at each distance
    private final List<String> arrowsDistance;
    // Arrows per an end
    private final int arrowsPerEnd;


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
}

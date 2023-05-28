package com.fullsail.apolloarchery.object;

public class SightMark {

    private final String distance;
    private final double sight_mark;

    public String getDistance() {
        return distance;
    }

    public double getSight_mark() {
        return sight_mark;
    }

    public SightMark(String distance, double sight_mark) {
        this.distance = distance;
        this.sight_mark = sight_mark;
    }
}

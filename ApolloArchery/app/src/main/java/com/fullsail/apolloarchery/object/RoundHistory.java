package com.fullsail.apolloarchery.object;

public class RoundHistory {

    public long id;

    private String date;

    private String roundName;

    // This is a serialised version of the round object created using gson
    private String round;

    // This is a serialised version of the List<Integer> object created using gson
    private String arrowValues;

    private int totalScore;

    private String archerString;

    private String scorerString;

    public RoundHistory() {
    }

    public RoundHistory(long id, String date, String roundName, String round, String arrowValues, int totalScore, String archerString, String scorerString) {
        this.id = id;
        this.date = date;
        this.roundName = roundName;
        this.round = round;
        this.arrowValues = arrowValues;
        this.totalScore = totalScore;
        this.archerString = archerString;
        this.scorerString = scorerString;
    }

    public long getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getRoundName() {
        return roundName;
    }

    public String getRound() {
        return round;
    }

    public String getArrowValues() {
        return arrowValues;
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
}

package com.fullsail.apolloarchery.object;

import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "HistoryRounds")
public class HistoryRounds implements Serializable {

    @PrimaryKey
    public long id;

    @ColumnInfo(name = "date")
    public String date;

    @ColumnInfo(name = "roundName")
    public String roundName;

    @ColumnInfo(name = "round")
    public String round;

    @ColumnInfo(name = "arrowValues")
    public String arrowValues;

    @ColumnInfo(name = "totalScore")
    public int totalScore;

    @ColumnInfo(name = "archerString")
    public String archerString;

    @ColumnInfo(name = "scorerString")
    public String scorerString;


    public HistoryRounds(String date, String roundName, String round, String arrowValues, int totalScore) {
        this.date = date;
        this.roundName = roundName;
        this.round = round;
        this.arrowValues = arrowValues;
        this.totalScore = totalScore;
    }

}

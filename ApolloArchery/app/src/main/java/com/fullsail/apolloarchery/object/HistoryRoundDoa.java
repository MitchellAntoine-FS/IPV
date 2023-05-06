package com.fullsail.apolloarchery.object;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface HistoryRoundDoa {

    // For saving a new round from the scoring activity
    @Query("INSERT INTO HistoryRounds (date, roundName, round, arrowValues, totalScore, " +
            "archerString, scorerString) VALUES (:today, :roundName, :round, :arrowValues, " +
            ":totalScore, :archerString, :scorerString)")
    long saveCompletedRound(String today, String roundName, String round, String arrowValues,
                            int totalScore, String archerString, String scorerString);

    // Getting the HistoryRounds object from the database
    @Query("SELECT * FROM HistoryRounds WHERE id = :id")
    HistoryRounds getRound(Long id);

    // Updating the signatures
    @Query("UPDATE HistoryRounds SET archerString = :archerString, scorerString = :scorerString WHERE id = :id")
    void updateSignatures(Long id, String archerString, String scorerString);

    // Get all HistoryRounds
    @Query("SELECT * FROM HistoryRounds ORDER BY id DESC")
    List<HistoryRounds> getAllRounds();

    // Get list of unique rounds in the database
    @Query("SELECT DISTINCT roundName FROM HistoryRounds")
    List<String> getRounds();

    // Get the serialised round object searching by roundName
    @Query("SELECT round FROM HistoryRounds WHERE roundName = :roundName")
    String getRoundObject(String roundName);

    // Delete round
    @Query("DELETE FROM HistoryRounds WHERE id = :id")
    void deleteRound(Long id);

    // Getting HistoryRounds object when searching by roundName
    @Query("SELECT * FROM HistoryRounds WHERE roundName LIKE '%' || :search || '%'")
    List<HistoryRounds> roundHistorySearch(String search);
}

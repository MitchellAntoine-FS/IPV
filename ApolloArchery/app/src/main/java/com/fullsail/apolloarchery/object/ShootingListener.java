package com.fullsail.apolloarchery.object;

public interface ShootingListener {
    Round getRound();

    void nextRound(HistoryRounds roundScore, int score);
}

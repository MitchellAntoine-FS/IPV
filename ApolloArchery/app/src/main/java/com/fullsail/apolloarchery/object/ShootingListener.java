package com.fullsail.apolloarchery.object;

public interface ShootingListener {
    Round getRound();

    void nextRound(HistoryRound roundScore, int score);
}

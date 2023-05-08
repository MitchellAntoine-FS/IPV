package com.fullsail.apolloarchery.object;

import java.util.List;

public interface ShootingListener {
    Round getRound();

    void nextRound(Round round, int finalCurrentScore, List<String> endScoreList);
}

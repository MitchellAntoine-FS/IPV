package com.fullsail.apolloarchery.object;

import java.util.List;

public interface ShootingListener {
    Round getRound();

    void nextRound(int totalArrowsShot, List<String> arrowsScoreList);

}

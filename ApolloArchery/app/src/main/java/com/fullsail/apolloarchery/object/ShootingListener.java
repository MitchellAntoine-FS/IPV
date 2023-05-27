package com.fullsail.apolloarchery.object;

import java.util.List;

public interface ShootingListener {

    Distance getDistance();

    void nextRound(int totalArrowsShot, List<String> arrowsScoreList);

}

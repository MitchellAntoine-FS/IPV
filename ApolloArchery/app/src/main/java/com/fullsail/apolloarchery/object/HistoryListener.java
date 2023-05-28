package com.fullsail.apolloarchery.object;

import java.util.ArrayList;

public interface HistoryListener {
    ArrayList<HistoryRounds> getHistory();
    ArrayList<RoundHistory> getFirebaseRoundHistory();
    void getHistoricalData(HistoryRounds historyRounds, int position);
}

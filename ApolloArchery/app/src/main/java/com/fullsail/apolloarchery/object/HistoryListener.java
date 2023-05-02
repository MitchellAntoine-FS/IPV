package com.fullsail.apolloarchery.object;

import java.util.ArrayList;

public interface HistoryListener {
    ArrayList<HistoryRound> getHistory();
    void getHistoricalData(HistoryRound historyRound, int position);
}

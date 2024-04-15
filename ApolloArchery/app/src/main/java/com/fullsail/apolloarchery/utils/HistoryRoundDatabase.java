package com.fullsail.apolloarchery.utils;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.fullsail.apolloarchery.object.HistoryRoundDoa;
import com.fullsail.apolloarchery.object.HistoryRounds;

@Database(entities = {HistoryRounds.class}, version = 1)
public abstract class HistoryRoundDatabase extends RoomDatabase {

    public abstract HistoryRoundDoa HistoryRoundDoa();
}

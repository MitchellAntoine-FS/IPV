package com.fullsail.apolloarchery.utils;

import android.content.Context;

import com.fullsail.apolloarchery.object.Round;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class RoundStorageUtil {
    private static final String FILE_NAME = "rounds.dat";

    public static void saveRound(Context _context, Round _round) {
        ArrayList<Round> rounds = loadRounds(_context);
        rounds.clear();
        rounds.add(_round);
        saveRounds(_context, rounds);
    }

    @SuppressWarnings("unchecked")
    public static ArrayList<Round> loadRounds(Context _context) {
        ArrayList<Round> rounds = null;

        try {
            FileInputStream fis = _context.openFileInput(FILE_NAME);
            ObjectInputStream ois = new ObjectInputStream(fis);
            rounds = (ArrayList<Round>)ois.readObject();
            ois.close();
        } catch(Exception e) {
            e.printStackTrace();
        }

        if(rounds == null) {
            rounds = new ArrayList<>();
        }

        return rounds;
    }

    private static void saveRounds(Context _context, ArrayList<Round> _rounds) {
        try {
            FileOutputStream fos = _context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(_rounds);
            oos.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}

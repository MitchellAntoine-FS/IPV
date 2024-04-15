package com.fullsail.apolloarchery.utils;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class DistanceStorageUtil {
    private static final String FILE_NAME = "distance.dat";

    public static void saveDistance(Context _context, Integer _distance) {
        ArrayList<Integer> distances = loadDistances(_context);
        distances.clear();
        distances.add(_distance);
        saveDistances(_context, distances);
    }

    @SuppressWarnings("unchecked")
    public static ArrayList<Integer> loadDistances(Context _context) {
        ArrayList<Integer> distances = null;

        try {
            FileInputStream fis = _context.openFileInput(FILE_NAME);
            ObjectInputStream ois = new ObjectInputStream(fis);
            distances = (ArrayList<Integer>)ois.readObject();
            ois.close();
        } catch(Exception e) {
            e.printStackTrace();
        }

        if(distances == null) {
            distances = new ArrayList<>();
        }

        return distances;
    }

    public static void saveDistances(Context _context, ArrayList<Integer> _distances) {
        try {
            FileOutputStream fos = _context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(_distances);
            oos.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}

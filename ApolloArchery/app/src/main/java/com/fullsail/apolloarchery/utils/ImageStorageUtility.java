package com.fullsail.apolloarchery.utils;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class ImageStorageUtility {

    public static File createImageFile(@NonNull Context _context, String _fileName, String _folderName) {
        File imageFile = getImageFileReference(_context,_fileName, _folderName);
        try {
            boolean fileCreated = imageFile.createNewFile();
            Log.i("FILELOG", "createImageFile: File created " + fileCreated);

            if (!fileCreated) {
                throw new IOException("Unable to create file at specified path. It already exists");
            }
        } catch(IOException e) {
            e.printStackTrace();
            return null;
        }
        return imageFile;
    }

    public static File getImageFileReference(Context context, String _fileName, String _folderName) {
        File protectedStorage = context.getExternalFilesDir(_folderName);

        String jpgs = Arrays.toString(protectedStorage.listFiles());

        if (jpgs.contains(".jpg")){
            Log.i("FILELOG", "getImageFileReference: " + jpgs );

            for (File file: Objects.requireNonNull(protectedStorage.listFiles())){


                Log.i("FILELOG", "getImageFileReference: " + file );
            }
        }
        return new File(protectedStorage, _fileName);
    }

}

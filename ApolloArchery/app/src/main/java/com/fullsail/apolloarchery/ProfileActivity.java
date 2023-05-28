package com.fullsail.apolloarchery;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.fullsail.apolloarchery.fragments.ProfileFragment;
import com.fullsail.apolloarchery.object.HistoryListener;
import com.fullsail.apolloarchery.object.HistoryRounds;
import com.fullsail.apolloarchery.util.ImageStorageUtility;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class ProfileActivity extends AppCompatActivity implements HistoryListener {
    private static final String TAG = "ProfileActivity";

    public static final String IMAGE_FOLDER = "image";
    public static final String IMAGE_NAME = "profile_picture.jpg";
    ArrayList<HistoryRounds> historyRounds;
    FirebaseFirestore db;
    FirebaseStorage storage;

    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        historyRounds = new ArrayList<>();

        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.profile_container, ProfileFragment.newInstance(), ProfileFragment.TAG)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.action_take_picture) {

            takePicture();
        }
        return true;
    }

    private void takePicture() {

        // Get Image file
        File file = ImageStorageUtility.createImageFile(getApplicationContext(),IMAGE_NAME, IMAGE_FOLDER);

        // Get image Uri
        Uri imageUri = null;
        if (file != null) {
            Log.i(TAG, "takePicture: File Path: " + file.getAbsolutePath());
            imageUri = FileProvider.getUriForFile(getApplicationContext(), "com.fullsail.apolloarchery", file);

        }
        Log.i(TAG, "takePicture: Image Uri: " + imageUri);

        // Start camera
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        ActivityCompat.startActivityForResult(this, intent, 0, null);
    }

    @Override
    public ArrayList<HistoryRounds> getHistory() {

        Task<QuerySnapshot> collRef = db.collection("history")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());

                                
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }

                });

        return historyRounds;
    }

    @Override
    public void getHistoricalData(HistoryRounds histRounds, int position) {

        Intent intent = new Intent(this, ScoreCardActivity.class);
        intent.putExtra("history", histRounds);
        intent.putExtra("position", position);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Get image file reference
        File imageFile = ImageStorageUtility.getImageFileReference(getApplicationContext(), IMAGE_NAME, IMAGE_FOLDER);

        // Get image uri
        Uri imageUri = FileProvider.getUriForFile(getApplicationContext(), "com.fullsail.apolloarchery", imageFile);

        uploadProfilePicture(imageUri);

    }

    private void uploadProfilePicture( Uri _imageUri) {

        StorageReference storageRef = storage.getReference();

        StorageReference riversRef = storageRef.child("images/"+_imageUri.getLastPathSegment());
        UploadTask uploadTask = riversRef.putFile(_imageUri);

        // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Log.i("FIREBASE", "onFailure: Storage upload failure");
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                Log.i("FIREBASE", "onSuccess: Storage upload success");
                // ...
            }
        });

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw Objects.requireNonNull(task.getException());
                }

                // Continue with the task to get the download URL
                return riversRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();

                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()

                            .setPhotoUri(Uri.parse(String.valueOf(downloadUri)))
                            .build();

                    if (user != null) {
                        user.updateProfile(profileUpdates)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Log.d("FIREBASE", "User profile updated.");
                                        }
                                    }
                                });
                    }
                } else {
                    // Handle failures
                    Log.i("FIREBASE", ": Failed to download Url" );
                    // ...
                }
            }
        });

    }

}
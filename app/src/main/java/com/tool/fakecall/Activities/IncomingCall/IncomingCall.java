package com.tool.fakecall.Activities.IncomingCall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.ImageView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.tool.fakecall.Common.BlurBuilder;
import com.tool.fakecall.R;

import java.util.List;

public class IncomingCall extends AppCompatActivity {

    FirebaseStorage storage;
    StorageReference storageRef;
    ImageView imageView,ivUserImage;
    private MediaPlayer mediaPlayer;
    private Vibrator vibrator;
    private Handler handler = new Handler();
    private boolean isVibrating = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incoming_call);

        imageView = findViewById(R.id.ivImage);
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();


        displayImageFromFolder("Ronaldo");

        // Initialize the MediaPlayer
        mediaPlayer = MediaPlayer.create(this, R.raw.iphone_ringtone);

        // Start playing the ringtone
        if (mediaPlayer != null) {
            mediaPlayer.start();
            // Set the completion listener to stop the vibration when the ringtone ends
            mediaPlayer.setOnCompletionListener(mediaPlayer -> {
                // Stop the vibration when the ringtone ends
                stopVibration();
                handler.removeCallbacks(vibrationRunnable);
            });
        }

        // Start the vibration scheduler
        handler.post(vibrationRunnable);
    }

    private void displayImageFromFolder(String folderName) {
        StorageReference folderRef = storageRef.child(folderName);

        folderRef.listAll().addOnSuccessListener(listResult -> {
            // Get the list of items (images) in the folder
            List<StorageReference> imageRefs = listResult.getItems();

            // Check if there are images in the folder
            if (!imageRefs.isEmpty()) {
                // Get the reference of the first image in the list
                StorageReference firstImageRef = imageRefs.get(0); // You can change this logic to select any other image

                firstImageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    String imageUrl = uri.toString();

//                    Bitmap bitmap = BitmapFactory.decodeFile(imageUrl);
//                    Bitmap blurredBitmap = BlurBuilder.blur(this, bitmap);
//                    imageView.setImageBitmap(blurredBitmap);

                    // Load the image from URL using Picasso (or any other library you prefer)
                    Picasso.get().load(imageUrl).into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            // Apply blur effect to the bitmap

                            Bitmap blurredBitmap = BlurBuilder.blur(IncomingCall.this, bitmap);
                            imageView.setImageBitmap(blurredBitmap);
                        }

                        @Override
                        public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                            // Handle failure
                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {
                            // Handle loading
                        }
                    });


                }).addOnFailureListener(e -> {
                    // Handle error
                });
            }
        }).addOnFailureListener(e -> {
            // Handle error
        });
    }

    // Method to play a ringtone from the raw folder
    // Method to start vibrating the device
    private void startVibration() {
        try {
            vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            if (vibrator != null) {
                // Vibrate indefinitely until the ringtone ends
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                    vibrator.vibrate(VibrationEffect.createWaveform(new long[]{0, 500}, 0));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to stop vibrating the device
    private void stopVibration() {
        try {
            if (vibrator != null) {
                vibrator.cancel(); // Cancel the vibration
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Runnable vibrationRunnable = new Runnable() {
        @Override
        public void run() {
            if (isVibrating) {
                stopVibration();
            } else {
                startVibration();
            }
            // Toggle the vibration state
            isVibrating = !isVibrating;
            // Schedule the next vibration after 1 second
            handler.postDelayed(this, 1000);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Release the MediaPlayer and stop the vibration when the activity is destroyed
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        stopVibration();
    }

}
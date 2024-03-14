package com.tool.fakecall.Activities.AudioCall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.tool.fakecall.R;
import com.tool.fakecall.databinding.ActivityAudioCallBinding;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class AudioCall extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private boolean isTimerRunning = false;
    private long startTimeInMillis;
    private CountDownTimer timer;
    FirebaseStorage storage;
    StorageReference storageRef;
    ActivityAudioCallBinding binding;
    // Define a boolean flag to track whether audio is currently playing
    private boolean isAudioPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_audio_call);

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        playAudioWithTimer();
    }

    private void playAudioWithTimer() {
        String folderName = "Ronaldo"; // Folder ka naam jo aapko play karna hai
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        int lastPlayedAudioIndex = sharedPreferences.getInt("lastPlayedAudioIndex", -1); // Initialize with -1

        StorageReference folderRef = storageRef.child(folderName);

        folderRef.listAll().addOnSuccessListener(listResult -> {
            // Get the list of items (audios) in the folder
            List<StorageReference> audioRefs = listResult.getItems();

            // Check if there are audios in the folder and audio is not already playing
            if (!audioRefs.isEmpty() && !isAudioPlaying) {
                // Calculate the index of the next audio to play
                int nextAudioIndex = (lastPlayedAudioIndex + 1) % audioRefs.size();

                // Get the reference of the next audio to play
                StorageReference nextAudioRef = audioRefs.get(nextAudioIndex);

                nextAudioRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    String audioUrl = uri.toString();

                    // Initialize MediaPlayer if not already initialized
                    if (mediaPlayer == null) {
                        mediaPlayer = new MediaPlayer();
                    }

                    try {
                        mediaPlayer.reset(); // Reset media player to clear previous audio
                        mediaPlayer.setDataSource(audioUrl);
                        mediaPlayer.prepare();
                        mediaPlayer.start();

                        // Start the timer
                        startTimeInMillis = System.currentTimeMillis();
                        // Start the countdown timer
                        startTimer();

                        // Update the index of the last played audio
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("lastPlayedAudioIndex", nextAudioIndex);
                        editor.apply();

                        // Set the flag indicating audio is playing
                        isAudioPlaying = true;
                    } catch (IOException e) {
                        // Handle error
                        e.printStackTrace();
                    }
                }).addOnFailureListener(e -> {
                    // Handle error
                });
            }
        }).addOnFailureListener(e -> {
            // Handle error
        });
    }

    private void startTimer() {
        // Start the timer with an initial delay of 0 and update every second
        timer = new CountDownTimer(Long.MAX_VALUE, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long elapsedTimeInMillis = System.currentTimeMillis() - startTimeInMillis;
                updateTimerDisplay(elapsedTimeInMillis);
            }

            @Override
            public void onFinish() {
                // This will never be called as the timer runs indefinitely
            }
        };
        timer.start();
        isTimerRunning = true;
    }

    private void updateTimerDisplay(long elapsedTimeInMillis) {
        // Convert milliseconds to minutes and seconds
        int minutes = (int) (elapsedTimeInMillis / 1000) / 60;
        int seconds = (int) (elapsedTimeInMillis / 1000) % 60;

        // Format the time string (00:00)
        String timeString = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        // Update your timer display with the formatted timeString
        // For example, if you have a TextView named timerTextView
        binding.timer.setText(timeString);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Call the method to play audio when activity resumes
        playAudioWithTimer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Stop the media player when activity is paused
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        // Stop the timer
        if (timer != null) {
            timer.cancel();
            isTimerRunning = false;
        }
        // Reset the flag indicating audio is not playing
        isAudioPlaying = false;
    }

}
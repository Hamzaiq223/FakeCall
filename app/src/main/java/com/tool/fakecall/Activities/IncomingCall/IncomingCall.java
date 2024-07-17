package com.tool.fakecall.Activities.IncomingCall;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.telecom.InCallService;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import com.ncorti.slidetoact.SlideToActView;
import com.tool.fakecall.Activities.VideoCalling.VideoCall;
import com.tool.fakecall.Common.BlurBuilder;
import com.tool.fakecall.Common.CharacterImageHelper;
import com.tool.fakecall.Common.FlashlightController;
import com.tool.fakecall.Common.SharedHelper;
import com.tool.fakecall.R;

public class IncomingCall extends AppCompatActivity {

    private FirebaseStorage storage;
    private StorageReference storageRef;
    private ImageView ivUser;
    private ImageView ivBlurBackground;
    private TextView tvName;
    private SlideToActView btnSlide;
    private LinearLayout layoutMessage;
    private TextView btnReturn;
    private LinearLayout layoutReminder;
    private MediaPlayer mediaPlayer;
    private Vibrator vibrator;
    private Handler handler = new Handler();
    private boolean isVibrating = false;
    private boolean flash = false;
    private Handler buttonHandler = new Handler();
    private String receivedString;
    private TextView tvCallStatus;
    private FlashlightController flashlightController;
    private Handler flashHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incoming_call);

        boolean volume;
        boolean vibration;

        volume = SharedHelper.getBoolean(this, "volume_off", false);
        vibration = SharedHelper.getBoolean(this, "vibration_off", false);
        flash = SharedHelper.getBoolean(this, "flash_off", false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        tvName = findViewById(R.id.tvName);
        ivUser = findViewById(R.id.ivUser);
        ivBlurBackground = findViewById(R.id.ivImage);
//        layoutMessage = findViewById(R.id.layoutMessage);
//        layoutReminder = findViewById(R.id.layoutReminder);
        btnSlide = findViewById(R.id.btnSlide);
        btnReturn = findViewById(R.id.btnReturn);
        tvCallStatus = findViewById(R.id.textView3);
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        receivedString = getIntent().getStringExtra("character_name");

        tvName.setText(receivedString);

        Context context = getApplicationContext();
        Integer characterDrawable = CharacterImageHelper.getCharacterImageResourceId(context, receivedString);

        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), characterDrawable);
        Bitmap blurredBitmap = BlurBuilder.blur(this, originalBitmap);

        btnSlide.setOnSlideCompleteListener(view -> {
            Intent intent = new Intent(IncomingCall.this, VideoCall.class);
            intent.putExtra("characterName", receivedString);
            startActivity(intent);
            remove();
            showButton();
        });

        btnReturn.setOnClickListener(v -> finish());

        ivBlurBackground.setImageBitmap(blurredBitmap);
        ivUser.setImageBitmap(originalBitmap);

        // Initialize the MediaPlayer
        mediaPlayer = MediaPlayer.create(this, R.raw.iphone_ringtone);

        if (!volume) {
            // Start playing the ringtone
            if (mediaPlayer != null) {
                mediaPlayer.start();
                mediaPlayer.setOnCompletionListener(mp -> {
                    // Stop the vibration when the ringtone ends
                    stopVibration();
                    handler.removeCallbacks(vibrationRunnable);
                });
            }
        }


        if (!vibration) {
            // Start the vibration scheduler
            handler.post(vibrationRunnable);
        }

        if (!flash) {
            flashlightController = new FlashlightController(this);
            // Start the flashlight scheduler
            flashHandler.post(flashlightRunnable);
        }
    }

    private Runnable flashlightRunnable = new Runnable() {
        @Override
        public void run() {
            if (!flash) {
                flashlightController.turnOnFlash();
                // To turn off the flashlight
                flashlightController.turnOffFlash();
            }
            // Schedule the next flashlight toggle after 1 second
            flashHandler.postDelayed(this, 1000);
        }
    };

    private void showButton() {
        int delayMillis = 1500; // Delay in milliseconds (2 seconds in this example)
        buttonHandler.postDelayed(() -> {
            // Code block to be executed after the delay
            btnSlide.setVisibility(View.GONE);
//            layoutMessage.setVisibility(View.GONE);
//            layoutReminder.setVisibility(View.GONE);
//            btnReturn.setVisibility(View.VISIBLE);
            tvCallStatus.setText(getString(R.string.call_ended));
        }, delayMillis);
    }

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

    private void remove() {
        handler.removeCallbacksAndMessages(null); // Cancel all scheduled callbacks
        stopVibration();
        flashHandler.removeCallbacksAndMessages(null); // Cancel all flashlight scheduled callbacks

        // Release the MediaPlayer and stop the vibration when the activity is destroyed
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        remove();
        buttonHandler.removeCallbacksAndMessages(null);
    }
}

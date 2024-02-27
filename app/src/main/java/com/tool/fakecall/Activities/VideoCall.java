package com.tool.fakecall.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.Toast;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.tool.fakecall.R;
import com.tool.fakecall.databinding.ActivityVideoCallBinding;

import java.util.Arrays;

public class VideoCall extends AppCompatActivity {

    private static final int REQUEST_CAMERA_PERMISSION = 200;
    private static final String TAG = "CameraActivity";
    private CameraManager cameraManager;
    private CameraDevice cameraDevice;
    private CameraCaptureSession cameraCaptureSession;
    private CaptureRequest.Builder captureRequestBuilder;

    private String currentCameraId,videoUrl;

    private Handler backgroundHandler;
    private HandlerThread backgroundThread;

    MediaPlayer mediaPlayer;

    FirebaseStorage storage;
    StorageReference storageRef;

    ActivityVideoCallBinding videoCallBinding;

    private AudioManager audioManager;

    private boolean isMuted = false;

    private int previousVolume;

    private void toggleMute() {
        if (isMuted) {
            // Unmute the device volume
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, previousVolume, 0);
            isMuted = false;
        } else {
            // Mute the device volume
            previousVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
            isMuted = true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        videoCallBinding = DataBindingUtil.setContentView(this, R.layout.activity_video_call);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        showLoader();

        String folderName = "Ronaldo"; // Folder ka naam jo aapko play karna hai

        StorageReference folderRef = storageRef.child(folderName);

        folderRef.listAll().addOnSuccessListener(listResult -> {
            for (StorageReference videoRef : listResult.getItems()) {
                videoRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    videoUrl = uri.toString();
                    videoCallBinding.videoView.setVideoURI(Uri.parse(videoUrl));
                    hideLoader();
                    videoCallBinding.videoView.start();

                }).addOnFailureListener(e -> {
                    // Handle error
                });
            }
        }).addOnFailureListener(e -> {
            // Handle error
        });

        // Simulate some background work
        new Handler().postDelayed(() -> {
            // Hide the progress bar after some delay (simulating the end of background work)
            hideLoader();
        }, 2000); // Adjust the delay as per your requirement


        videoCallBinding.btnVolume.setOnClickListener(view -> {
            toggleMute();
        });


        playVideoForAMinute();

        cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);
        videoCallBinding.textureView.setSurfaceTextureListener(surfaceTextureListener);

        try {
            currentCameraId = getFrontCameraId();
        } catch (CameraAccessException e) {
            throw new RuntimeException(e);
        }

        videoCallBinding.tvBackCamera.setOnClickListener(view -> {
            switchCamera();
        });
    }

    private void showLoader() {
        videoCallBinding.progressBar.setVisibility(View.VISIBLE);
    }

    private void hideLoader() {
        videoCallBinding.progressBar.setVisibility(View.GONE);
    }

    private void playVideoForAMinute() {
        new CountDownTimer(60000, 1000) { // 60000 milliseconds (1 minute), 1000 milliseconds interval
            public void onTick(long millisUntilFinished) {
                // Yahan pe kuchh nahi karna, agar aap chahte hain toh timer countdown dikha sakte hain
            }

            public void onFinish() {
                // Timer khatam hone par video playback ko stop karo
                videoCallBinding.videoView.stopPlayback();
            }
        }.start();
    }

    private TextureView.SurfaceTextureListener surfaceTextureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(@NonNull SurfaceTexture surfaceTexture, int width, int height) {
            openCamera();
        }

        @Override
        public void onSurfaceTextureSizeChanged(@NonNull SurfaceTexture surfaceTexture, int width, int height) {
        }

        @Override
        public boolean onSurfaceTextureDestroyed(@NonNull SurfaceTexture surfaceTexture) {
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(@NonNull SurfaceTexture surfaceTexture) {
        }
    };

    private CameraDevice.StateCallback cameraStateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(@NonNull CameraDevice camera) {
            cameraDevice = camera;
            createCameraPreview();
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice camera) {
            cameraDevice.close();
        }

        @Override
        public void onError(@NonNull CameraDevice camera, int error) {
            cameraDevice.close();
            cameraDevice = null;
        }
    };

    private void openCamera() {
        try {
            String cameraId = getFrontCameraId();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                return;
            }
            cameraManager.openCamera(cameraId, cameraStateCallback, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void createCameraPreview() {
        SurfaceTexture texture = videoCallBinding.textureView.getSurfaceTexture();
        texture.setDefaultBufferSize(640, 480);
        Surface surface = new Surface(texture);

        try {
            captureRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            captureRequestBuilder.addTarget(surface);

            cameraDevice.createCaptureSession(Arrays.asList(surface), new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(@NonNull CameraCaptureSession session) {
                    cameraCaptureSession = session;
                    updatePreview();
                }

                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession session) {
                    Toast.makeText(VideoCall.this, "Configuration change", Toast.LENGTH_SHORT).show();
                }
            }, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void updatePreview() {
        captureRequestBuilder.set(CaptureRequest.CONTROL_MODE, CaptureRequest.CONTROL_MODE_AUTO);
        try {
            cameraCaptureSession.setRepeatingRequest(captureRequestBuilder.build(), null, backgroundHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private String getFrontCameraId() throws CameraAccessException {
        for (String cameraId : cameraManager.getCameraIdList()) {
            if (cameraManager.getCameraCharacteristics(cameraId).get(CameraCharacteristics.LENS_FACING) == CameraCharacteristics.LENS_FACING_FRONT) {
                return cameraId;
            }
        }
        return null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(this, "Camera permission required", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        startBackgroundThread();
        if(videoUrl != null){
            videoCallBinding.videoView.setVideoURI(Uri.parse(videoUrl));
            videoCallBinding.videoView.start();
            playVideoForAMinute();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        stopBackgroundThread();
    }

    private void startBackgroundThread() {
        backgroundThread = new HandlerThread("Camera Background");
        backgroundThread.start();
        backgroundHandler = new Handler(backgroundThread.getLooper());
    }

    private void stopBackgroundThread() {
        backgroundThread.quitSafely();
        try {
            backgroundThread.join();
            backgroundThread = null;
            backgroundHandler = null;
        } catch (InterruptedException e) {
            Log.e(TAG, "Error closing background thread: " + e.getMessage());
        }
    }


    private void switchCamera() {
        // Close the current camera device
        if (cameraDevice != null) {
            cameraDevice.close();
            cameraDevice = null;
        }

        try {
            // Get the ID of the camera to switch to
            String cameraIdToSwitch = null;
            if (currentCameraId == null) {
                // If currentCameraId is null, assume we're using the front camera initially
                cameraIdToSwitch = getFrontCameraId();
            } else if (currentCameraId.equals(getFrontCameraId())) {
                // If the current camera is the front camera, switch to the back camera
                cameraIdToSwitch = getBackCameraId();
            } else {
                // If the current camera is the back camera or unknown, switch to the front camera
                cameraIdToSwitch = getFrontCameraId();
            }

            // Open the new camera
            if (cameraIdToSwitch != null) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                cameraManager.openCamera(cameraIdToSwitch, cameraStateCallback, null);
                currentCameraId = cameraIdToSwitch; // Update currentCameraId
            } else {
                Toast.makeText(this, "Camera not found", Toast.LENGTH_SHORT).show();
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private String getBackCameraId() throws CameraAccessException {
        for (String cameraId : cameraManager.getCameraIdList()) {
            if (cameraManager.getCameraCharacteristics(cameraId).get(CameraCharacteristics.LENS_FACING) == CameraCharacteristics.LENS_FACING_BACK) {
                return cameraId;
            }
        }
        return null;
    }
}
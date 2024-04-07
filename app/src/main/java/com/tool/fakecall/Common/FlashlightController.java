package com.tool.fakecall.Common;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;

public class FlashlightController {

    private final CameraManager cameraManager;
    private String cameraId;
    private boolean isFlashOn = false;

    public FlashlightController(Context context) {
        cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
        try {
            cameraId = cameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    public void turnOnFlash() {
        if (!isFlashOn) {
            try {
                cameraManager.setTorchMode(cameraId, true);
                isFlashOn = true;
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public void turnOffFlash() {
        if (isFlashOn) {
            try {
                cameraManager.setTorchMode(cameraId, false);
                isFlashOn = false;
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public void toggleFlash() {
        if (isFlashOn) {
            turnOffFlash();
        } else {
            turnOnFlash();
        }
    }
}

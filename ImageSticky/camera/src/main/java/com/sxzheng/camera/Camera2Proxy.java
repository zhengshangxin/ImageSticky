package com.sxzheng.camera;

import android.Manifest.permission;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraDevice.StateCallback;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraManager.AvailabilityCallback;
import android.os.Build.VERSION_CODES;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.SurfaceHolder;

/**
 * @author zheng.
 */
public class Camera2Proxy extends CameraProxy {

    private CameraManager mCameraManager;
    private String[] mCameraIds;

    @TargetApi(VERSION_CODES.LOLLIPOP)
    public Camera2Proxy(Context context) {

        mCameraManager =
                (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);

        try {
            mCameraIds = mCameraManager.getCameraIdList();

            CameraCharacteristics characteristics =
                    mCameraManager.getCameraCharacteristics(mCameraIds[0]);

            if (ActivityCompat.checkSelfPermission(context, permission.CAMERA) !=
                    PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[]
                // permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the
                // documentation
                // for ActivityCompat#requestPermissions for more details.

                throw new SecurityException();

            }
            mCameraManager.openCamera(mCameraIds[0], new StateCallback() {
                @Override
                public void onOpened(CameraDevice camera) {

                }

                @Override
                public void onDisconnected(CameraDevice camera) {

                }

                @Override
                public void onError(CameraDevice camera, int error) {

                }
            }, new Handler(Looper.getMainLooper()));

            mCameraManager.registerAvailabilityCallback(new AvailabilityCallback() {
                @Override
                public void onCameraAvailable(String cameraId) {
                    super.onCameraAvailable(cameraId);
                }

                @Override
                public void onCameraUnavailable(String cameraId) {
                    super.onCameraUnavailable(cameraId);
                }
            }, new Handler(Looper.getMainLooper()));


        } catch (CameraAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            Log.e(this.getClass().getName(), "Have not camera permission!");
        }

    }

    @Override
    public void setPreviewHolder(SurfaceHolder holder) {

    }

    @Override
    public void setPreviewTexture(SurfaceTexture surfaceTexture) {

    }

    @Override
    public void startPreview() {

    }

    @Override
    public void stopPreview() {

    }

    @Override
    public void release() {

    }

    @Override
    public void setPreviewSize(int width, int height) {

    }
}

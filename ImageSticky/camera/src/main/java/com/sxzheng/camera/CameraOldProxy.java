package com.sxzheng.camera;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.view.SurfaceHolder;

import java.io.IOException;

/**
 * @author zheng.
 */
public class CameraOldProxy extends CameraProxy {

    private Camera mCamera;

    private Parameters mParameters;

    public CameraOldProxy() {
        try {
            mCamera = Camera.open();
            mParameters = mCamera.getParameters();
            mCamera.setParameters(mParameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setPreviewHolder(SurfaceHolder holder) throws IOException {
        mCamera.setPreviewDisplay(holder);
    }

    @Override
    public void setPreviewTexture(SurfaceTexture surfaceTexture) throws IOException {
        mCamera.setPreviewTexture(surfaceTexture);
    }

    @Override
    public void startPreview() {
        mCamera.startPreview();
    }

    @Override
    public void stopPreview() {
        mCamera.stopPreview();
    }

    @Override
    public void release() {
        mCamera.release();
    }

    @Override
    public void setPreviewSize(int width, int height) {
        if (mParameters != null && mCamera != null) {
            mParameters.setPreviewSize(width, height);
            mCamera.setParameters(mParameters);
        }
    }
}

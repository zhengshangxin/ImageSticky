package com.sxzheng.camera;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.os.Build.VERSION_CODES;
import android.util.AttributeSet;
import android.view.TextureView;

import java.io.IOException;

/**
 * @author zheng.
 */
public class CameraPreview extends TextureView
        implements TextureView.SurfaceTextureListener {

    private CameraProxy mCameraProxy;

    public CameraPreview(Context context) {
        this(context, null);
    }

    public CameraPreview(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CameraPreview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    @TargetApi(VERSION_CODES.LOLLIPOP)
    public CameraPreview(Context context, AttributeSet attrs, int defStyleAttr,
            int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialize();
    }

    private void initialize() {
        mCameraProxy = new CameraManager(getContext()).getsInstance();
        setSurfaceTextureListener(this);
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width,
            int height) {
        try {
            mCameraProxy.setPreviewTexture(surface);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mCameraProxy.startPreview();
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width,
            int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        mCameraProxy.stopPreview();
        mCameraProxy.release();

        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }
}

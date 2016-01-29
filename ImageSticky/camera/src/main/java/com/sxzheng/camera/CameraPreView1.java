package com.sxzheng.camera;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build.VERSION_CODES;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

/**
 * @author zheng.
 */
public class CameraPreView1 extends SurfaceView implements SurfaceHolder.Callback2 {

    private CameraProxy mCameraProxy;

    public CameraPreView1(Context context) {
        this(context, null);
    }

    public CameraPreView1(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CameraPreView1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    @TargetApi(VERSION_CODES.LOLLIPOP)
    public CameraPreView1(Context context, AttributeSet attrs, int defStyleAttr,
            int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void initialize() {
        mCameraProxy = new CameraManager(getContext()).getsInstance();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        if (getHolder() != null) {
            getHolder().addCallback(this);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        int width =  right - left;
        int height = bottom - top;

    }

    @Override
    public void surfaceRedrawNeeded(SurfaceHolder holder) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            mCameraProxy.setPreviewHolder(holder);
            mCameraProxy.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
            int height) {
        //mCameraProxy.setPreviewSize(width, height);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}

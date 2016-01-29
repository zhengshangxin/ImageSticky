package com.sxzheng.camera;

import android.graphics.SurfaceTexture;
import android.view.SurfaceHolder;

import java.io.IOException;

/**
 * @author zheng.
 */
public abstract class CameraProxy {

    public abstract void setPreviewHolder(SurfaceHolder holder) throws IOException;

    public abstract void setPreviewTexture(SurfaceTexture surfaceTexture)
            throws IOException;

    public abstract void startPreview();

    public abstract void stopPreview();

    public abstract void release();

    public abstract void setPreviewSize(int width, int height);
}

package com.sxzheng.camera;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;

/**
 * @author zheng.
 */
public class CameraManager {

    private CameraProxy sInstance = null;

    public CameraManager(Context context) {
        if (VERSION.SDK_INT > VERSION_CODES.LOLLIPOP) {
            sInstance = new Camera2Proxy(context);
        } else {
            sInstance = new CameraOldProxy();
        }
    }

    public CameraProxy getsInstance() {
        return sInstance;
    }
}

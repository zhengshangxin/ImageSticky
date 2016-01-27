package com.sxzheng.imagesticky.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CameraUtil {
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    /**
     * Check if this device has a camera
     */
    public static boolean checkCameraHardware(Context context) {
        // this device has a camera
        // no camera on this device
        return context.getPackageManager()
                      .hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }


    public static Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT); // attempt to get a
            // Camera instance
        } catch (Exception e) {
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }

    /**
     * Create a File for saving an image or video
     */
    public static File getOutputMediaFile(int type) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "MyCameraApp");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
                .format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }
        return mediaFile;
    }

    public static File getOutputMediaFile() {
        //get the mobile Pictures directory
        File picDir = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(
                picDir.getPath() + File.separator + "IMAGE_" + timeStamp + ".jpg");
    }

    public static void adjustCamera(Context context, Camera camera) {
        if (context.getResources().getConfiguration().orientation !=
                Configuration.ORIENTATION_LANDSCAPE) {
            //parameters.set("orientation","portrait");
            camera.setDisplayOrientation(90);
        } else {
            //parameters.set("orientation","landscape");
            camera.setDisplayOrientation(0);
        }
    }

    public static Bitmap getPicFromCamera(byte[] data, Camera camera, Context context,
            String filePath, String fileName) {
        File pictureFile = CameraUtil.getOutputMediaFile(CameraUtil.MEDIA_TYPE_IMAGE);
        if (pictureFile == null) {
            return null;
        }

        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        if (!saveBitmap2SD(bitmap, context, filePath, fileName)) {
            return bitmap;
        }
        return bitmap;
    }

    public static boolean saveBitmap2SD(Bitmap bitmap, Context context, String filePath,
            String fileName) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { //Check sd whether usable.
            return false;
        }
        String name = new SimpleDateFormat().format(new Date()) + ".jpg";
        String filePath1 = "/sdcard/DCIM/Camera/";
        File file = new File(filePath1);
        if (!file.exists()) {
            if (!file.mkdirs()) return false;
        }
        String fileName1 = filePath1 + name;
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(fileName1);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            bos.flush();
            bos.close();
            fos.flush();
            //fos.write(data);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //        MediaStore.Images.Media.insertImage(context.getContentResolver(),
        // bitmap,"","");
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
                Uri.parse("file://" + Environment.getExternalStorageDirectory())));
        return true;
    }
}

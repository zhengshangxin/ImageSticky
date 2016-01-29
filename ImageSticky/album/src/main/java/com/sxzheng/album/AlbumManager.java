package com.sxzheng.album;

import android.content.Context;
import android.database.Cursor;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.ImageColumns;
import android.provider.MediaStore.Images.Media;

import com.sxzheng.album.model.Album;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zheng.
 */
public class AlbumManager {

    public Map<String, Album> findPicturesSync(Context context) {

        String dcim = Environment.getExternalStorageDirectory().getAbsolutePath() +
                "/DCIM/Camera";

        String[] projection =
                {MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA,
                        MediaStore.Images.Media.DATE_ADDED};

        Cursor cursor = context.getContentResolver().query(
                // from table_name
                Media.EXTERNAL_CONTENT_URI,
                // col, col,...
                projection,
                // where col = value
                Media.SIZE + ">?", new String[]{"100000"},
                // order by col, col,...
                Media.DATA);

        if (cursor != null) {
            cursor.moveToFirst();

            Map<String, Album> albumMap = new HashMap<>();

            ArrayList<String> bucketNames = new ArrayList<>();

            // folders
            while (cursor.moveToNext()) {
                cursor.getString(cursor.getColumnIndex(ImageColumns.BUCKET_ID));

            }
        }

        return null;
    }

}

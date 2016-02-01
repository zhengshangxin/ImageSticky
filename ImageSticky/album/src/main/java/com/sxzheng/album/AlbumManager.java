package com.sxzheng.album;

import android.content.Context;
import android.database.Cursor;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;

import com.sxzheng.album.model.Album;
import com.sxzheng.album.model.PicItem;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zheng.
 */
public class AlbumManager {

    private Context mContext;

    public AlbumManager(Context context) {
        mContext = context;
    }

    private String getSystemPicturePath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath() +
                "/DCIM/Camera";
    }

    public ArrayList<PicItem> findPicsInDir(String path) {
        ArrayList<PicItem> picItems = new ArrayList<PicItem>();

        File dir = new File(path);
        if (dir.exists() && dir.isDirectory()) {

            for (File file : dir.listFiles(new FileFilter() {

                @Override
                public boolean accept(File pathname) {
                    String filePath = pathname.getAbsolutePath();
                    return (filePath.endsWith(".png") || filePath.endsWith(".jpg") ||
                            filePath.endsWith(".jpeg"));
                }
            })) {
                picItems.add(new PicItem(file.getAbsolutePath(), file.lastModified()));
            }
        }

        Collections.sort(picItems);
        return picItems;
    }

    public Map<String, Album> findPicturesSync() {

        String dcim = getSystemPicturePath();

        String[] projection =
                {MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA,
                        MediaStore.Images.Media.DATE_ADDED};

        Cursor cursor = mContext.getContentResolver().query(
                // from table_name
                Media.EXTERNAL_CONTENT_URI,
                // col, col,...
                projection,
                // where col = value
                null, null,
                // order by col, col,...
                Media.DATE_ADDED);

        if (cursor != null) {
            cursor.moveToFirst();

            Map<String, Album> albumMap = new HashMap<>();

            ArrayList<String> bucketNames = new ArrayList<>();

            // folders
            while (cursor.moveToNext()) {
                String _idColumn = cursor.getString(cursor.getColumnIndex(Media._ID));

                if (_idColumn.lastIndexOf("/") < 1) {
                    continue;
                }

                String name = _idColumn.substring(0, _idColumn.lastIndexOf("/"));
            }
        }

        return null;
    }

}

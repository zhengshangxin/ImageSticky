package com.sxzheng.imagesticky;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.sxzheng.album.AlbumManager;
import com.sxzheng.imagesticky.base.BaseFragment;

/**
 * @author zheng.
 */
public class AlbumFragment extends BaseFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AlbumManager albumManager = new AlbumManager(getContext());
        albumManager.findPictures(getContext());
    }
}

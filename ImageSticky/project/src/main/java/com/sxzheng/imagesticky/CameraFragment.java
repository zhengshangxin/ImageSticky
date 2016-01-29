package com.sxzheng.imagesticky;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;

import com.sxzheng.camera.CameraPreView1;
import com.sxzheng.imagesticky.base.BaseFragment;

/**
 * @author zheng.
 */
public class CameraFragment extends BaseFragment {

    private CameraPreView1 mCameraPreView1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_camera, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        initCamera(view);
    }

    private void initCamera(View view) {

        mCameraPreView1 = (CameraPreView1) view.findViewById(R.id.camera_preview);

    }
}

package com.sxzheng.imagesticky.imagesticky;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.sxzheng.imagesticky.R;
import com.sxzheng.imagesticky.base.BaseFragment;
import com.sxzheng.imagesticky.utils.BitmapUtil;
import com.sxzheng.imagesticky.utils.CameraUtil;
import com.sxzheng.imagestickylib.DragRotate;
import com.sxzheng.imagestickylib.StickyView;

/**
 * @author zhengshangxin.
 */

public class StickyFragment extends BaseFragment {
    public static final String TAG = StickyFragment.class.getName();
    StickyView stickyView;
    DragRotate dragRotate;
    FrameLayout frameLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        frameLayout = new FrameLayout(getActivity());
        stickyView = new StickyView(getActivity());
        //stickyView.setBackgroundColor(getResources().getColor(R.color
        // .green_background_02c7c5));
        dragRotate = stickyView;
        frameLayout.setBackground(getResources().getDrawable(R.drawable.image));
        frameLayout.addView(stickyView);
        return frameLayout;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        DisplayMetrics metric = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metric);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img);
        Point point = new Point(metric.widthPixels / 2, metric.heightPixels / 2);
        dragRotate.setImageBitmap(bitmap, point, 0, 0.2f);
    }

    @Override
    public void onPause() {
        super.onPause();
        dragRotate.hideIcon(true);
        Bitmap bitmap = BitmapUtil.convertView2Bitmap(frameLayout);
        CameraUtil.saveBitmap2SD(bitmap, getActivity(), null, null);
    }
}

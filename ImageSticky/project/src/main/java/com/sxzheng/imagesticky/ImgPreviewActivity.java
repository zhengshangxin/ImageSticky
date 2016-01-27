package com.sxzheng.imagesticky;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class ImgPreviewActivity extends FragmentActivity {
    private List<View> mViewList = new ArrayList<>();
    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter = new PagerAdapter() {
        @Override
        public int getCount() {
            return mViewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //super.destroyItem(container, position, object);
            container.removeView(mViewList.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // return super.instantiateItem(container, position);
            container.addView(mViewList.get(position));
            return mViewList.get(position);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img_preview);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        View view1 = View.inflate(this, R.layout.fragment_camera, null);
        View view2 = View.inflate(this, R.layout.fragment_img_filter, null);
        View view3 = View.inflate(this, R.layout.fragment_image_rotate, null);
        mViewList.add(view1);
        mViewList.add(view2);
        mViewList.add(view3);

        mViewPager.setAdapter(mPagerAdapter);
    }
}

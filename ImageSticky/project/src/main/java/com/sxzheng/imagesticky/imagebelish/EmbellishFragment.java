package com.sxzheng.imagesticky.imagebelish;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Gallery;
import android.widget.TextView;

import com.sxzheng.imagesticky.R;
import com.sxzheng.imagesticky.base.BaseFragment;
import com.sxzheng.imagesticky.imagesticky.ImgRotateFragment;
import com.sxzheng.imagesticky.imagesticky.StickyFragment;

import java.util.ArrayList;
import java.util.List;

public class EmbellishFragment extends BaseFragment {
    public static final String TAG = EmbellishFragment.class.getName();

    private Gallery gallery;
    private View.OnClickListener clickListener;
    private ViewPager viewPager;

    private List<View> mViewList = new ArrayList<>();
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
            //            super.destroyItem(container, position, object);
            container.removeView(mViewList.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //            return super.instantiateItem(container, position);
            container.addView(mViewList.get(position));
            return mViewList.get(position);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_embellish, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.back).setOnClickListener(getClickListener());
        ((TextView) view.findViewById(R.id.txt_title))
                .setText(getString(R.string.embellish));
        gallery = (Gallery) view.findViewById(R.id.layout_gallery);
        view.findViewById(R.id.textview_filter).setOnClickListener(getClickListener());
        view.findViewById(R.id.textview_stick).setOnClickListener(getClickListener());

        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        RoundedImageView imageView = new RoundedImageView(getActivity());
        //        imageView.setImageResource(R.drawable.image);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image);
        imageView.setImageBitmap(bitmap);
        imageView.invalidate();
        //        imageView.setImageBitmap(BitmapUtil.getRoundedCornerBitmap(bitmap));
        mViewList.add(0, imageView);
        viewPager.setAdapter(mPagerAdapter);
    }

    private void goBack() {
        if (isAdded()) {
            getActivity().getSupportFragmentManager().popBackStack();
            //            UIHelper.finishActivity(getActivity());
        }
    }

    private void goForward() {
        goBack();
    }

    private View.OnClickListener getClickListener() {
        if (clickListener != null) {
            return clickListener;
        } else {
            clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = v.getId();
                    if (id == R.id.back) {
                        goBack();
                    } else if (id == R.id.next) {
                        goForward();
                    } else if (id == R.id.textview_filter) {
                        goFilter();
                    } else if (id == R.id.textview_stick) {
                        goStick();
                    }
                }
            };
        }
        return clickListener;
    }

    private void goFilter() {
        getFragmentManager().beginTransaction()
                            .add(android.R.id.content, new ImgRotateFragment())
                            .commit();
    }

    private void goStick() {
        getFragmentManager().beginTransaction()
                            .add(android.R.id.content, new StickyFragment()).commit();
    }
}

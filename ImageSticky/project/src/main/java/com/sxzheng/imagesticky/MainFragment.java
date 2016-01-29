package com.sxzheng.imagesticky;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sxzheng.imagesticky.base.BaseFragment;
import com.sxzheng.imagesticky.imagebelish.EmbellishFragment;
import com.sxzheng.imagesticky.imagefilter.ImgFilterFragment;
import com.sxzheng.imagesticky.imagesticky.StickyFragment;

public class MainFragment extends BaseFragment {
    public static String TAG = MainFragment.class.getName();
    private View.OnClickListener clickListener;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.textview_camera).setOnClickListener(getClickListener());
        view.findViewById(R.id.textview_album).setOnClickListener(getClickListener());

        view.findViewById(R.id.textview_imgfilter)
            .setOnClickListener(getClickListener());
        view.findViewById(R.id.textview_stick).setOnClickListener(getClickListener());
        view.findViewById(R.id.textview_imgpreview)
            .setOnClickListener(getClickListener());
        view.findViewById(R.id.textview_embellish)
            .setOnClickListener(getClickListener());
    }

    private View.OnClickListener getClickListener() {
        if (clickListener == null) {
            clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = v.getId();
                    if (id == R.id.textview_camera) {
                        goCamera();
                    } else if (id == R.id.textview_album) {
                        goAlbum();
                    } else if (id == R.id.textview_imgfilter) {
                        goImgFilter();//
                    } else if (id == R.id.textview_stick) {
                        goStick();
                    } else if (id == R.id.textview_imgpreview) {
                        goPreview();
                    } else if (id == R.id.textview_embellish) {
                        goEmbellish();
                    }
                }
            };
        }
        return clickListener;
    }

    private void goEmbellish() {
        getFragmentManager().beginTransaction()
                            .add(android.R.id.content, new EmbellishFragment())
                            .commit();
    }

    private void goCamera() {
        getFragmentManager().beginTransaction().add(android.R.id.content, new
                CameraFragment()).commit();
    }

    private void goAlbum() {
        //System album.
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, 100);
    }

    private void goImgFilter() {

        getFragmentManager().beginTransaction()
                            .add(android.R.id.content, new ImgFilterFragment())
                            .commit();
    }

    private void goStick() {

        getFragmentManager().beginTransaction()
                            .add(android.R.id.content, new StickyFragment()).commit();
    }

    private void goPreview() {
        startActivity(new Intent(getActivity(), ImgPreviewActivity.class));
    }
}

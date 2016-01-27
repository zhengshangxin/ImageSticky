package com.sxzheng.imagesticky.imagefilter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.sxzheng.imagesticky.R;
import com.sxzheng.imagesticky.base.BaseFragment;
import com.sxzheng.imagesticky.utils.BitmapUtil;

import java.io.File;
import java.io.FileOutputStream;

public class ImgFilterFragment extends BaseFragment {
    public static final String TAG = ImgFilterFragment.class.getName();
    private static final int SELECT_PHOTO = 100;
    private ImageView imageView;
    private Bitmap bitmap;
    private View.OnClickListener clickListener;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_img_filter, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageView = (ImageView) view.findViewById(R.id.effect_main);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image);

        view.findViewById(R.id.back).setOnClickListener(getClickListener());
        view.findViewById(R.id.next).setOnClickListener(getClickListener());
        view.findViewById(R.id.btn_pick_img).setOnClickListener(getClickListener());
        view.findViewById(R.id.effect_black).setOnClickListener(getClickListener());
        view.findViewById(R.id.effect_boost_1).setOnClickListener(getClickListener());
        view.findViewById(R.id.effect_boost_2).setOnClickListener(getClickListener());
        view.findViewById(R.id.effect_boost_3).setOnClickListener(getClickListener());
        view.findViewById(R.id.effect_brightness)
            .setOnClickListener(getClickListener());
        view.findViewById(R.id.effect_color_red).setOnClickListener(getClickListener());
        view.findViewById(R.id.effect_color_green)
            .setOnClickListener(getClickListener());
        view.findViewById(R.id.effect_color_blue)
            .setOnClickListener(getClickListener());
        view.findViewById(R.id.effect_color_depth_64)
            .setOnClickListener(getClickListener());
        view.findViewById(R.id.effect_color_depth_32)
            .setOnClickListener(getClickListener());
        view.findViewById(R.id.effect_contrast).setOnClickListener(getClickListener());
        view.findViewById(R.id.effect_emboss).setOnClickListener(getClickListener());
        view.findViewById(R.id.effect_engrave).setOnClickListener(getClickListener());
        view.findViewById(R.id.effect_flea).setOnClickListener(getClickListener());
        view.findViewById(R.id.effect_gaussian_blue)
            .setOnClickListener(getClickListener());
        view.findViewById(R.id.effect_gamma).setOnClickListener(getClickListener());
        view.findViewById(R.id.effect_grayscale).setOnClickListener(getClickListener());
        view.findViewById(R.id.effect_hue).setOnClickListener(getClickListener());
        view.findViewById(R.id.effect_invert).setOnClickListener(getClickListener());
        view.findViewById(R.id.effect_mean_remove)
            .setOnClickListener(getClickListener());
        //        view.findViewById(R.id.effect_reflaction).setOnClickListener
        // (getClickListener());
        view.findViewById(R.id.effect_round_corner)
            .setOnClickListener(getClickListener());
        view.findViewById(R.id.effect_saturation)
            .setOnClickListener(getClickListener());
        view.findViewById(R.id.effect_sepia).setOnClickListener(getClickListener());
        view.findViewById(R.id.effect_sepia_green)
            .setOnClickListener(getClickListener());
        view.findViewById(R.id.effect_sepia_blue)
            .setOnClickListener(getClickListener());
        view.findViewById(R.id.effect_smooth).setOnClickListener(getClickListener());
        view.findViewById(R.id.effect_sheding_cyan)
            .setOnClickListener(getClickListener());
        view.findViewById(R.id.effect_sheding_yellow)
            .setOnClickListener(getClickListener());
        view.findViewById(R.id.effect_sheding_green)
            .setOnClickListener(getClickListener());
        view.findViewById(R.id.effect_tint).setOnClickListener(getClickListener());
        view.findViewById(R.id.effect_watermark).setOnClickListener(getClickListener());

    }

    private View.OnClickListener getClickListener() {
        if (clickListener == null) {
            clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = v.getId();
                    if (id == R.id.back) {
                        goBack();
                    } else if (id == R.id.next) {
                        goNext();
                    } else {
                        buttonClicked(v);
                    }
                }
            };
        }
        return clickListener;
    }

    private void goBack() {
        if (isAdded()) {
            getActivity().getSupportFragmentManager().popBackStack();
            //            UIHelper.finishActivity(getActivity());
        }
    }

    private void goNext() {
        goBack();
    }

    public void buttonClicked(View v) {

        Toast.makeText(getActivity(), "Processing...", Toast.LENGTH_SHORT).show();
        ImageFilters imgFilter = new ImageFilters();
        if (v.getId() == R.id.btn_pick_img) {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, SELECT_PHOTO);
        }
        //        else if(v.getId() == R.id.effect_highlight)
        //            saveBitmap(imgFilter.applyHighlightEffect(bitmap),
        // "effect_highlight");
        else if (v.getId() == R.id.effect_black)
            saveBitmap(imgFilter.applyBlackFilter(bitmap), "effect_black");
        else if (v.getId() == R.id.effect_boost_1)
            saveBitmap(imgFilter.applyBoostEffect(bitmap, 1, 40), "effect_boost_1");
        else if (v.getId() == R.id.effect_boost_2)
            saveBitmap(imgFilter.applyBoostEffect(bitmap, 2, 30), "effect_boost_2");
        else if (v.getId() == R.id.effect_boost_3)
            saveBitmap(imgFilter.applyBoostEffect(bitmap, 3, 67), "effect_boost_3");
        else if (v.getId() == R.id.effect_brightness)
            saveBitmap(imgFilter.applyBrightnessEffect(bitmap, 80),
                    "effect_brightness");
        else if (v.getId() == R.id.effect_color_red)
            saveBitmap(imgFilter.applyColorFilterEffect(bitmap, 255, 0, 0),
                    "effect_color_red");
        else if (v.getId() == R.id.effect_color_green)
            saveBitmap(imgFilter.applyColorFilterEffect(bitmap, 0, 255, 0),
                    "effect_color_green");
        else if (v.getId() == R.id.effect_color_blue)
            saveBitmap(imgFilter.applyColorFilterEffect(bitmap, 0, 0, 255),
                    "effect_color_blue");
        else if (v.getId() == R.id.effect_color_depth_64)
            saveBitmap(imgFilter.applyDecreaseColorDepthEffect(bitmap, 64),
                    "effect_color_depth_64");
        else if (v.getId() == R.id.effect_color_depth_32)
            saveBitmap(imgFilter.applyDecreaseColorDepthEffect(bitmap, 32),
                    "effect_color_depth_32");
        else if (v.getId() == R.id.effect_contrast)
            saveBitmap(imgFilter.applyContrastEffect(bitmap, 70), "effect_contrast");
        else if (v.getId() == R.id.effect_emboss)
            saveBitmap(imgFilter.applyEmbossEffect(bitmap), "effect_emboss");
        else if (v.getId() == R.id.effect_engrave)
            saveBitmap(imgFilter.applyEngraveEffect(bitmap), "effect_engrave");
        else if (v.getId() == R.id.effect_flea)
            saveBitmap(imgFilter.applyFleaEffect(bitmap), "effect_flea");
        else if (v.getId() == R.id.effect_gaussian_blue)
            saveBitmap(imgFilter.applyGaussianBlurEffect(bitmap),
                    "effect_gaussian_blue");
        else if (v.getId() == R.id.effect_gamma)
            saveBitmap(imgFilter.applyGammaEffect(bitmap, 1.8, 1.8, 1.8),
                    "effect_gamma");
        else if (v.getId() == R.id.effect_grayscale)
            saveBitmap(imgFilter.applyGreyscaleEffect(bitmap), "effect_grayscale");
        else if (v.getId() == R.id.effect_hue)
            saveBitmap(imgFilter.applyHueFilter(bitmap, 2), "effect_hue");
        else if (v.getId() == R.id.effect_invert)
            saveBitmap(imgFilter.applyInvertEffect(bitmap), "effect_invert");
        else if (v.getId() == R.id.effect_mean_remove)
            saveBitmap(imgFilter.applyMeanRemovalEffect(bitmap), "effect_mean_remove");
            //        else if(v.getId() == R.id.effect_reflaction)
            //            saveBitmap(imgFilter.applyReflection(bitmap),
            // "effect_reflaction");
        else if (v.getId() == R.id.effect_round_corner)
            saveBitmap(imgFilter.applyRoundCornerEffect(bitmap, 45),
                    "effect_round_corner");
        else if (v.getId() == R.id.effect_saturation)
            saveBitmap(imgFilter.applySaturationFilter(bitmap, 1), "effect_saturation");
        else if (v.getId() == R.id.effect_sepia)
            saveBitmap(imgFilter.applySepiaToningEffect(bitmap, 10, 1.5, 0.6, 0.12),
                    "effect_sepia");
        else if (v.getId() == R.id.effect_sepia_green)
            saveBitmap(imgFilter.applySepiaToningEffect(bitmap, 10, 0.88, 2.45, 1.43),
                    "effect_sepia_green");
        else if (v.getId() == R.id.effect_sepia_blue)
            saveBitmap(imgFilter.applySepiaToningEffect(bitmap, 10, 1.2, 0.87, 2.1),
                    "effect_sepia_blue");
        else if (v.getId() == R.id.effect_smooth)
            saveBitmap(imgFilter.applySmoothEffect(bitmap, 100), "effect_smooth");
        else if (v.getId() == R.id.effect_sheding_cyan)
            saveBitmap(imgFilter.applyShadingFilter(bitmap, Color.CYAN),
                    "effect_sheding_cyan");
        else if (v.getId() == R.id.effect_sheding_yellow)
            saveBitmap(imgFilter.applyShadingFilter(bitmap, Color.YELLOW),
                    "effect_sheding_yellow");
        else if (v.getId() == R.id.effect_sheding_green)
            saveBitmap(imgFilter.applyShadingFilter(bitmap, Color.GREEN),
                    "effect_sheding_green");
        else if (v.getId() == R.id.effect_tint)
            saveBitmap(imgFilter.applyTintEffect(bitmap, 100), "effect_tint");
        else if (v.getId() == R.id.effect_watermark) saveBitmap(imgFilter
                .applyWaterMarkEffect(bitmap, "kpbird.com", 200, 200, Color.GREEN, 80,
                        24, false), "effect_watermark");

    }

    private void saveBitmap(Bitmap bmp, String fileName) {
        try {
            imageView.setImageBitmap(bmp);
            File f = new File(
                    Environment.getExternalStorageDirectory().getAbsolutePath() + "/" +
                            fileName + ".png");
            FileOutputStream fos = new FileOutputStream(f);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, fos);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case SELECT_PHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    Uri selectedImage = data.getData();
                    Bitmap bmp = BitmapUtil.decodeUri(selectedImage, getActivity());
                    if (bmp != null) {
                        bitmap = bmp;
                        imageView.setImageBitmap(bitmap);
                    }
                }
        }
    }

}

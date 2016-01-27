package com.sxzheng.imagesticky.imagesticky;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.sxzheng.imagesticky.R;

public class ImgRotateFragment extends Fragment {

    public static final String TAG = ImgRotateFragment.class.getName();
    private ImageView imageView;
    private TextView textView;
    private Matrix matrix = new Matrix();
    private SeekBar.OnSeekBarChangeListener seekBarChangeListener;
    private View.OnClickListener clickListener;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_rotate, container, false);
        imageView = (ImageView) view.findViewById(R.id.imageview);
        SeekBar seekBar = (SeekBar) view.findViewById(R.id.seekbar);
        textView = (TextView) view.findViewById(R.id.textview);
        seekBar.setOnSeekBarChangeListener(getSeekBarChangeListener());
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.back).setOnClickListener(getClickListener());
        view.findViewById(R.id.next).setOnClickListener(getClickListener());
    }

    private SeekBar.OnSeekBarChangeListener getSeekBarChangeListener() {
        if (seekBarChangeListener == null) {
            seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress,
                        boolean fromUser) {
                    if (seekBar.getId() == R.id.seekbar) {
                        rotateImg(progress);
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            };
        }
        return seekBarChangeListener;
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

    private void rotateImg(int progress) {
        try {
            Bitmap bitmap =
                    ((BitmapDrawable) (getResources().getDrawable(R.drawable.image)))
                            .getBitmap();
            matrix.setRotate(progress);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), matrix, true);
            imageView.setImageBitmap(bitmap);
            textView.setText(String.valueOf(progress) +
                    getResources().getString(R.string.degree));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

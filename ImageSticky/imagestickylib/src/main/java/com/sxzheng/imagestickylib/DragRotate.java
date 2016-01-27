package com.sxzheng.imagestickylib;

import android.graphics.Bitmap;
import android.graphics.Point;

public interface DragRotate {

    public void setViewLayout(int w, int h, int l, int t);

    public void setCentrePoint(Point c);

    public void setImageBitmap(Bitmap bmp, Point pt, float angle,
            float scaleCoefficient);

    public void cancel();

    public void hideIcon(boolean bl);
}

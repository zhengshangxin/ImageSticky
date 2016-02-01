package com.sxzheng.album.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author zheng.
 */
public class PicItem implements Parcelable, Comparable<PicItem> {

    public static final Creator<PicItem> CREATOR = new Creator<PicItem>() {
        @Override
        public PicItem createFromParcel(Parcel in) {
            return new PicItem(in);
        }

        @Override
        public PicItem[] newArray(int size) {
            return new PicItem[size];
        }
    };

    private int mData;
    private long mDate;
    private String mImageUri;
    private boolean mChecked;
    private String mDateStr;

    public PicItem(Parcel in) {
        mData = in.readInt();
    }

    public PicItem(String absolutePath, long l) {

    }

    @Override
    public int describeContents() {
        return mData;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mData);
    }

    @Override
    public int compareTo(PicItem another) {
        if (another == null) {
            return 1;
        }
        return (int) ((another.getDate() - mDate));
    }

    public long getDate() {
        return mDate;
    }
}

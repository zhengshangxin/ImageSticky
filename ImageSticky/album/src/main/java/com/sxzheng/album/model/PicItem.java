package com.sxzheng.album.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author zheng.
 */
public class PicItem implements Parcelable {

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

    protected PicItem(Parcel in) {
        mData = in.readInt();
    }

    @Override
    public int describeContents() {
        return mData;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mData);
    }
}

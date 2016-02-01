package com.sxzheng.album.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author zheng.
 */
public class Album implements Serializable {

    private static final long serialVersionUID = 5702699517846159671L;

    private String mAlbumUri;
    private String mTitle;
    private ArrayList<PicItem> mPicItems;

    public Album(String title, String uri, ArrayList<PicItem> picItems) {
        this.mTitle = title;
        this.mAlbumUri = uri;
        this.mPicItems = picItems;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;

        if (o != null && o instanceof Album) {
            String oAlbumUri = ((Album) o).getAlbumUri();
            return mAlbumUri.equals(oAlbumUri);
        }

        return super.equals(o);
    }

    @Override
    public int hashCode() {
        if (mAlbumUri == null) {

            return super.hashCode();
        } else {
            return mAlbumUri.hashCode();
        }
    }

    public String getAlbumUri() {
        return mAlbumUri;
    }

    public void setAlbumUri(String albumUri) {
        this.mAlbumUri = albumUri;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public ArrayList<PicItem> getPicItems() {
        return mPicItems;
    }

    public void setPicItems(ArrayList<PicItem> picItems) {
        this.mPicItems = picItems;
    }
}

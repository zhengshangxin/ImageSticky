package com.sxzheng.album.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author zheng.
 */
public class Album implements Serializable {

    private static final long serialVersionUID = 5702699517846159671L;

    private String albumUri;
    private String title;
    private ArrayList<PicItem> photos;

    public Album(String title, String uri, ArrayList<PicItem> photos) {
        this.title = title;
        this.albumUri = uri;
        this.photos = photos;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;

        if (o != null && o instanceof Album) {
            String oAlbumUri = ((Album) o).getAlbumUri();
            return albumUri.equals(oAlbumUri);
        }

        return super.equals(o);
    }

    @Override
    public int hashCode() {
        if (albumUri == null) {

            return super.hashCode();
        } else {
            return albumUri.hashCode();
        }
    }

    public String getAlbumUri() {
        return albumUri;
    }

    public void setAlbumUri(String albumUri) {
        this.albumUri = albumUri;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<PicItem> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<PicItem> photos) {
        this.photos = photos;
    }
}

package com.sxzheng.imagesticky.imagebelish;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

public class GalleryAdapter extends BaseAdapter {
    private List<Bitmap> bitmapList;
    private Context mContext;
    private List<Integer> images;

    public GalleryAdapter(Context context,List<Bitmap> images){
        this.mContext = context;
//        this.images = images;
    }
    @Override
    public int getCount() {
        return bitmapList==null?0:bitmapList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            viewHolder = new ViewHolder();
            convertView = new ImageView(mContext);
            viewHolder.imageView = (ImageView)convertView;
            convertView.setTag(viewHolder);
            return convertView;
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        initView(viewHolder,position);
        return convertView;
    }
    private void initView(ViewHolder viewHolder,int position){

//        viewHolder.imageView.setImageDrawable(mContext.getDrawable(images[position]));
    }
    private class ViewHolder{
        public ImageView imageView;
    }
}

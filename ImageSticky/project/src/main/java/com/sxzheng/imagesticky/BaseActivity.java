package com.sxzheng.imagesticky;

import android.support.v4.app.FragmentActivity;

/**
 * @author zhengshangxin.
 */
public class BaseActivity extends FragmentActivity {

    private int mIdContainer = android.R.id.content;
    protected int getId(){
        return mIdContainer;
    }
}

package com.sxzheng.imagesticky;

import android.os.Bundle;

public class MainActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().replace(getId(),new
                    MainFragment()).commit();
        }
    }

}

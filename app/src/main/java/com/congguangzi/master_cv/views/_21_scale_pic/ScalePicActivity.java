package com.congguangzi.master_cv.views._21_scale_pic;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.congguangzi.master_cv.R;

/**
 * @author congguangzi (congspark@163.com) 2018/7/28.
 */
public class ScalePicActivity extends Activity {

    ScalePicView scalePicView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.l_21_scale_pic);

        scalePicView = findViewById(R.id.scale_pic);
    }
}

package com.congguangzi.master_cv.views._20_TabLayout;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.congguangzi.master_cv.R;
import com.congguangzi.master_cv.views._21_scale_pic.ScalePicActivity;

/**
 * @author congguangzi (congspark@163.com) 2018/7/28.
 */

public class CustomFrameLayout extends FrameLayout implements View.OnClickListener {

    Button bt;

    public CustomFrameLayout(@NonNull Context context) {
        super(context);
    }

    public CustomFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        bt = findViewById(R.id.bt_move_to);
        bt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getContext(), ScalePicActivity.class);
        getContext().startActivity(intent);
    }
}

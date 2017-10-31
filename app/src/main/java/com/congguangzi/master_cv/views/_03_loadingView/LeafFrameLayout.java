package com.congguangzi.master_cv.views._03_loadingView;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.congguangzi.master_cv.R;

/**
 * @author congguangzi (congspark@163.com) 2017/10/31.
 */

public class LeafFrameLayout extends FrameLayout {

    LeafLoadingView loadingView;
    Button bt;

    public LeafFrameLayout(@NonNull Context context) {
        super(context);
    }

    public LeafFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LeafFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        loadingView = (LeafLoadingView) findViewById(R.id.leaf_loading);
        bt = (Button) findViewById(R.id.bt_start_anim);

        bt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator.ofInt(loadingView, "progress", 0, 100)
                        .setDuration(2000)
                        .start();
            }
        });
    }


}

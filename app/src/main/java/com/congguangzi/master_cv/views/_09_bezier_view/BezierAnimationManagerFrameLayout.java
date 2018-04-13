package com.congguangzi.master_cv.views._09_bezier_view;

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
 * 简介: 用于启动动画
 *
 * @author congguangzi (congspark@163.com) 2018/4/10.
 */
public class BezierAnimationManagerFrameLayout extends FrameLayout {

    Button bt_start;

    BezierBoundCircleView bezierBoundCircleView;


    public BezierAnimationManagerFrameLayout(@NonNull Context context) {
        super(context);
    }

    public BezierAnimationManagerFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BezierAnimationManagerFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        bt_start = findViewById(R.id.bt_start);
        bezierBoundCircleView = findViewById(R.id.bezier_bound_view);

        bt_start.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                // 添加平移动画
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(bezierBoundCircleView,
                        "progress", 0f, 1f);
                objectAnimator.setDuration(5000);
                objectAnimator.start();

            }
        });

    }


}

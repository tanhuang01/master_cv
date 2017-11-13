package com.congguangzi.master_cv.views._05_sin_circle_view;

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.CycleInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;

import com.congguangzi.master_cv.R;

/**
 * @author congguangzi (congspark@163.com) 2017/11/10.
 */

public class SinCircleLayout extends FrameLayout {

    SinCircleView circleView;
    Button bt;

    public SinCircleLayout(@NonNull Context context) {
        super(context);
    }

    public SinCircleLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SinCircleLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        circleView = (SinCircleView) findViewById(R.id.sin_circle);
        bt = (Button) findViewById(R.id.bt_start_anim);
        bt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(circleView, "timeX", ((float) (Math.PI * 2)));
                animator.setInterpolator(new LinearInterpolator());
                animator.setDuration(3000);
                animator.setRepeatCount(ValueAnimator.INFINITE);
                animator.start();
            }
        });
    }
}

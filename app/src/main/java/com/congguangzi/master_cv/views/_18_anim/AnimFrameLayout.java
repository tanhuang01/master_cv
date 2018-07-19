package com.congguangzi.master_cv.views._18_anim;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;

import com.congguangzi.master_cv.R;

import java.util.concurrent.TimeUnit;

/**
 * brief:
 *
 * @author congguangzi (congspark@163.com) 2018/7/19.
 */
public class AnimFrameLayout extends FrameLayout implements View.OnClickListener {

    private AnimationView animationView;

    private Button bt_start;

    public AnimFrameLayout(@NonNull Context context) {
        super(context);
    }

    public AnimFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AnimFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        animationView = findViewById(R.id.anim);
        bt_start = findViewById(R.id.bt_start);

        bt_start.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        animationView.getAnimator().start();
    }
}

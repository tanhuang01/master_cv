package com.congguangzi.master_cv.views._04_progress_fresher;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.congguangzi.master_cv.R;

import org.xml.sax.helpers.AttributesImpl;

/**
 * @author congguangzi (congspark@163.com) 2017/11/10.
 */

public class ProgressSophomoreLayout extends FrameLayout {

    Button bt;
    ProgressBar bar;

    public ProgressSophomoreLayout(@NonNull Context context) {
        super(context);
    }

    public ProgressSophomoreLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ProgressSophomoreLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        bar = (ProgressBar) findViewById(R.id.progress_sophomore);
        bt = (Button) findViewById(R.id.bt_start_sophomore);
        bt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator anim = ObjectAnimator.ofInt(bar, "progress", 0, 100);
                anim.setInterpolator(new LinearInterpolator());
                anim.setDuration(3000);
                anim.start();
            }
        });
    }
}

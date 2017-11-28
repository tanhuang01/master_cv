package com.congguangzi.master_cv.views._04_progress_fresher;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import com.congguangzi.master_cv.R;

/**
 * @author congguangzi (congspark@163.com) 2017/11/8.
 */

public class ProgressFrameLayout extends FrameLayout {

    ProgressBar bar;
    Button bt;
    Switch aSwitch;

    public ProgressFrameLayout(@NonNull Context context) {
        super(context);
    }

    public ProgressFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ProgressFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        bar = (ProgressBar) findViewById(R.id.progress_fresher);
        bt = (Button) findViewById(R.id.bt_start_fresher);

        bt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator anim = ObjectAnimator.ofInt(bar, "progress", 0, 100);
                anim.setDuration(3000);
                anim.setInterpolator(new LinearInterpolator());
                anim.start();
                aSwitch.setChecked(true);
            }
        });

        aSwitch = (Switch) findViewById(R.id.custom_switch);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(getContext(), "isChecked: " + isChecked, Toast.LENGTH_SHORT).show();
            }
        });
    }
}

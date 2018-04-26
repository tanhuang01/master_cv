package com.congguangzi.master_cv.views._14_circle_click;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.congguangzi.master_cv.R;

/**
 * @author congguangzi (congspark@163.com) 2018/4/26.
 */

public class ClickFrameLayout extends FrameLayout {

    CircleClickView clickView;

    public ClickFrameLayout(@NonNull Context context) {
        super(context);
    }

    public ClickFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ClickFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        clickView = findViewById(R.id.click_circle);

        clickView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "on click listener", Toast.LENGTH_SHORT).show();
            }
        });

        clickView.setOnMenuClickListener(new CircleClickView.OnMenuClickListener() {
            @Override
            public void onCenterClick() {
                Toast.makeText(getContext(), "on Center Click", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLeftClick() {
                Toast.makeText(getContext(), "on Left Click", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTopClick() {
                Toast.makeText(getContext(), "on Top Click", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightClick() {
                Toast.makeText(getContext(), "on Right Click", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBottomClick() {
                Toast.makeText(getContext(), "on Bottom Click", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

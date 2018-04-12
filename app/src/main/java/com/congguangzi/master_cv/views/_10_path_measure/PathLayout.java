package com.congguangzi.master_cv.views._10_path_measure;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;

import com.congguangzi.master_cv.R;

/**
 * @author congguangzi (congspark@163.com) 2018/4/12.
 */
public class PathLayout extends FrameLayout {

    PathMeasureView pathMeasureView;

    PathSearch pathSearch;

    Button button;

    public PathLayout(Context context) {
        super(context);
    }

    public PathLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PathLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        pathMeasureView = findViewById(R.id.path_measure);
        pathSearch = findViewById(R.id.path_search);
        button = findViewById(R.id.bt_start);

        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                // 灰机
                ObjectAnimator animator = ObjectAnimator.ofFloat(pathMeasureView,
                        "progress",
                        0, 1, 2);
                animator.setDuration(5000);
                animator.setInterpolator(new LinearInterpolator());
                animator.start();

                // 搜索图标
                ObjectAnimator search = ObjectAnimator.ofFloat(pathSearch,
                        "searchProgress"
                        , 0, 1);
                search.setDuration(2000);
                // 圆
                ObjectAnimator circle = ObjectAnimator.ofFloat(pathSearch,
                        "circleProgress",
                        0, 1);
                circle.setDuration(2000);

                ObjectAnimator search_reverse = ObjectAnimator.ofFloat(pathSearch,
                        "searchProgress",
                        1, 0);
                search_reverse.setDuration(2000);

                AnimatorSet set = new AnimatorSet();
                set.playSequentially(search, circle, circle.clone(),  search_reverse);
                set.start();
            }
        });
    }
}

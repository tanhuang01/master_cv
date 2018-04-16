package com.congguangzi.master_cv.views._11_matrix;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.congguangzi.master_cv.R;

/**
 * @author congguangzi (congspark@163.com) 2018/4/16.
 */

public class MatrixFrameLayout extends FrameLayout {

    RadioGroup group;
    MatrixPolyTestView polyTestView;

    public MatrixFrameLayout(@NonNull Context context) {
        super(context);
    }

    public MatrixFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MatrixFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        polyTestView = findViewById(R.id.matrix);
        group = findViewById(R.id.rg_matrix_point);

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_0:
                        polyTestView.setTestPoint(0);
                        break;
                    case R.id.rb_1:
                        polyTestView.setTestPoint(1);
                        break;
                    case R.id.rb_2:
                        polyTestView.setTestPoint(2);
                        break;
                    case R.id.rb_3:
                        polyTestView.setTestPoint(3);
                        break;
                    case R.id.rb_4:
                        polyTestView.setTestPoint(4);
                        break;
                }
            }
        });
    }
}

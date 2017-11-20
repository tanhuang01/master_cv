package com.congguangzi.master_cv.views._04_progress_fresher;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * 简介: 倒计时功能 textView
 *
 * @author congguangzi (congspark@163.com) 2017/11/20.
 */
public class CountDownTextView extends AppCompatTextView {

    // TODO(congguangzi) : custome attribute
    private int countDownTime = 5000;
    private int countDownInterval = 1000;

    public CountDownTextView(Context context) {
        super(context);
    }

    public CountDownTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CountDownTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setOnClickListener(OnClickListener onClickListener) {
        super.setOnClickListener(onClickListener);
    }

    @Override
    public boolean performClick() {
        boolean clicked = super.performClick();

        setEnabled(false);
        new CountDownTimer(countDownTime, countDownInterval) {
            @Override
            public void onTick(long leftTime) {
                StringBuilder builder = new StringBuilder("获取");
                builder.append("(").append(leftTime / 1000).append(")");
                setText(builder.toString());
            }

            @Override
            public void onFinish() {
                setText("获取");
                CountDownTextView.this.setEnabled(true);
            }
        }.start();

        return clicked;
    }
}

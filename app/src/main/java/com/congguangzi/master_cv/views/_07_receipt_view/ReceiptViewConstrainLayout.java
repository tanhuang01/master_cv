package com.congguangzi.master_cv.views._07_receipt_view;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.congguangzi.master_cv.R;
import com.congguangzi.master_cv.ViewUtils;

/**
 * 简介:
 *
 * @author congguangzi (congspark@163.com) 2018/1/5.
 */
public class ReceiptViewConstrainLayout extends ConstraintLayout {

    ReceiptView receiptView;

    Button bt_switchWith;
    int widthClickCount = 1;

    Button bt_switchLine;
    int lineClickCount = 1;

    public ReceiptViewConstrainLayout(Context context) {
        super(context);
    }

    public ReceiptViewConstrainLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ReceiptViewConstrainLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        receiptView = (ReceiptView) findViewById(R.id.receipt);
        receiptView.setTexts(ReceiptViewUtil.texts);

        bt_switchWith = (Button) findViewById(R.id.bt_switch_witch);
        bt_switchWith.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ConstraintLayout.LayoutParams layoutParams = (LayoutParams) receiptView.getLayoutParams();
                switch (widthClickCount % 3) {
                    case 0:
                        // 设置宽度为 58
                        layoutParams.width = ViewUtils.dpToPixel(180);
                        break;
                    case 1:
                        // 设置宽度为 76
                        layoutParams.width = ViewUtils.dpToPixel(220);
                        break;
                    case 2:
                        // 设置宽度为 80
                        layoutParams.width = ViewUtils.dpToPixel(240);
                        break;
                }
                receiptView.setLayoutParams(layoutParams);
                widthClickCount++;
            }
        });

        bt_switchLine = (Button) findViewById(R.id.bt_empty_line);
        bt_switchLine.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutParams layoutParams = (LayoutParams) receiptView.getLayoutParams();

                switch (lineClickCount % 3) {
                    case 0:
                        layoutParams.topMargin = ViewUtils.dpToPixel(0);
                        break;
                    case 1:
                        layoutParams.topMargin = ViewUtils.dpToPixel(10);
                        break;
                    case 2:
                        layoutParams.topMargin = ViewUtils.dpToPixel(20);
                        break;
                }
                receiptView.setLayoutParams(layoutParams);
                lineClickCount++;
            }
        });
    }
}

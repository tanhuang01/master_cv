package com.congguangzi.master_cv.views._08_text_tip_view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;

import com.congguangzi.master_cv.R;
import com.congguangzi.master_cv.ViewUtils;

/**
 * 简介: 在 TextView 的基础上, 在右上角绘制一个 tip(小红圈加数字), 提示消息数量.
 * 回执 tip 提示 遵循: <br>
 * <p>
 * 1. 仅仅在 view 的   selected 状态为 false 时绘制该 tip 提示, 若选中(selected 属性置位 true), 会清除 tip 提示; <br>
 * 2. view 的宽度需要大于文字的宽度, 否则绘制效果影响用户体验.
 * <p>
 * <p>
 * NOTE: 目前简化问题, 仅仅在右上角绘制. 后期添加绘制的方位控制, 添加触觉反馈控制等.
 *
 * @author congguangzi (congspark@163.com) 2018/3/9.
 */
public class TextTipView extends android.support.v7.widget.AppCompatTextView {

    // tip count tag
    public static final String TIP_BROADCAST_TAG = "TIP_BROADCAST_TAG";

    public static final String TIP_COUNT_TAG = "TIP_COUNT_TAG";

    // tip text paint
    Paint textPaint;

    // tip background paint
    Paint tipPaint;

    // the view's height and width
    int height;
    int width;

    // the tip rect
    RectF rectF;

    // msg tip count
    int tipCount = 11;

    private int tipTextSize;

    TipChangeReceiver tipChangeReceiver;

    public TextTipView(Context context) {
        this(context, null);
    }

    public TextTipView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, android.R.attr.textViewStyle);
    }

    public TextTipView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TextTipView, defStyleAttr, 0);
        int textColor = array.getColor(R.styleable.TextTipView_tip_textColor, Color.WHITE);
        int tipColor = array.getColor(R.styleable.TextTipView_tip_background, Color.RED);
        tipTextSize = array.getDimensionPixelSize(R.styleable.TextTipView_tip_textSize, ViewUtils.dpToPixel(10));

        // tip text
        textPaint.setTextSize(tipTextSize);
        textPaint.setColor(textColor);

        // tip background
        tipPaint.setColor(tipColor);
        array.recycle();
    }

    {
        rectF = new RectF();

        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setColor(Color.WHITE);

        tipPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        tipPaint.setColor(Color.RED);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        IntentFilter intentFilter = new IntentFilter(TIP_BROADCAST_TAG);
        tipChangeReceiver = new TipChangeReceiver();
        getContext().registerReceiver(tipChangeReceiver, intentFilter);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getContext().unregisterReceiver(tipChangeReceiver);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        height = h;
        width = w;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // only draw tip when the view is not selected
        if (isDrawTip()) {
            final int textSize = tipTextSize;

            // circle rectF, top right corner
            rectF.top = 0;
            rectF.left = width - textSize * 2;
            rectF.bottom = textSize * 2;
            rectF.right = width;

            // tip background
            canvas.drawCircle(rectF.centerX(), rectF.centerY(), rectF.width() / 2, tipPaint);

            // tip count
            // get the baseline point
            textPaint.setTextSize(textSize);

            Paint.FontMetrics metrics = textPaint.getFontMetrics();
            int offsetY = (int) (-(metrics.ascent + metrics.descent) / 2);
            // draw tip count text
            canvas.drawText(String.valueOf(tipCount), rectF.centerX(), rectF.centerY() + offsetY, textPaint);
        }
    }

    /**
     * only draw the tip when the view is not selected and has enough space
     *
     * @return true - draw the tip
     */
    private boolean isDrawTip() {
        // the text of this TextView
        String text = getText().toString();
        Rect textBounds = new Rect();
        getPaint().getTextBounds(text, 0, text.length(), textBounds);
        boolean enoughWidth = (textBounds.width() + (tipTextSize << 1)) < width;
        boolean enoughHeight = (textBounds.height() + (tipTextSize << 1)) < height;
        return !isSelected() && (enoughHeight || enoughWidth);
    }

    public void setTipCount(int tipCount) {
        this.tipCount = tipCount;
        invalidate();
    }

    class TipChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int tipCount = intent.getIntExtra(TextTipView.TIP_COUNT_TAG, 0);
            Log.w("tip", "tipCount: " + tipCount);
            if (tipCount > 0) {
                setTipCount(tipCount);
            }
        }
    }

}


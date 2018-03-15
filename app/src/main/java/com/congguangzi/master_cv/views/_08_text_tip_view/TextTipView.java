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
 * 绘制 tip 提示 遵循一下规则: <br>
 * <p>
 * 1. 仅仅在 TextView 的 selected 状态为 false 时绘制该 tip 提示, 若选中(selected 属性置位 true), 会清除 tip 提示; <br>
 * 2. view 的宽度需要大于 文字的宽度 + padding; 或者 view 的高度要大于 文字的高度 + padding.  否则绘制效果影响用户体验.
 * 3. tipCount 小于 0 时不进行绘制, 0-100 范围内绘制相应数值, 大于 100 绘制 "..".
 * <p>
 * <p>
 * NOTE: 目前简化问题, 仅仅在右上角绘制. 后期添加绘制的方位控制, 添加触觉反馈控制等.
 *
 * @author congguangzi (congspark@163.com) 2018/3/9.
 */
public class TextTipView extends android.support.v7.widget.AppCompatTextView {

    // tip position tag, means the tip is on the right, or right|top etc.
    private static final int TIP_RIGHT_POSITION = 1;
    private static final int TIP_LEFT_POSITION = TIP_RIGHT_POSITION << 1;
    private static final int TIP_TOP_POSITION = TIP_LEFT_POSITION << 1;
    private static final int TIP_BOTTOM_POSITION = TIP_TOP_POSITION << 1;

    // tip count tag
    public static final String TIP_BROADCAST_TAG = "TIP_BROADCAST_TAG";

    public static final String TIP_COUNT_TAG = "TIP_COUNT_TAG";

    // tip text paint
    private Paint textPaint;

    // tip background paint
    private Paint tipPaint;

    // the view's height and width
    private int height;
    private int width;

    // the tip background rect
    private RectF rectF;

    // the text-Bound of this TextView, to measure text size
    private Rect textBounds;

    // msg tip count
    private int tipCount = 11;

    private int tipTextSize;

    private int tipPosition;

    // the padding from edge to the tip.
    private int tipPadding;

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
        tipPosition = array.getInt(R.styleable.TextTipView_tip_position,
                TIP_TOP_POSITION | TIP_RIGHT_POSITION);
        tipPadding = array.getDimensionPixelSize(R.styleable.TextTipView_tip_padding, 0);
        Log.w("tip position", "" + tipPosition);
        tipTextSize = array.getDimensionPixelSize(R.styleable.TextTipView_tip_textSize, ViewUtils.dpToPixel(10));
        array.recycle();

        // tip text
        textPaint.setTextSize(tipTextSize);
        textPaint.setColor(textColor);

        // tip background
        tipPaint.setColor(tipColor);
    }

    {
        rectF = new RectF();
        textBounds = new Rect();

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
            calculateTipRect(rectF, textSize * 2);

            // tip background
            canvas.drawCircle(rectF.centerX(), rectF.centerY(), rectF.width() / 2, tipPaint);

            // calculate the tip text baseline
            textPaint.setTextSize(textSize);
            Paint.FontMetrics metrics = textPaint.getFontMetrics();
            int offsetY = (int) (-(metrics.ascent + metrics.descent) / 2);

            // draw tip count text, if the count greater than 100, draw ".." instead
            if (tipCount > 0 && tipCount < 100) {
                canvas.drawText(String.valueOf(tipCount), rectF.centerX(), rectF.centerY() + offsetY, textPaint);
            } else if (tipCount >= 100) {
                canvas.drawText("..", rectF.centerX(), rectF.centerY() + offsetY, textPaint);
            }
        }
    }

    /**
     * only draw the tip when the view is not selected and has enough space
     *
     * @return true - draw the tip
     */
    private boolean isDrawTip() {

        // if the view is selected, do not need to calculate whether space is enough
        if (isSelected()) {
            return false;
        }

        // the text of this TextView
        String text = getText().toString();
        textBounds.setEmpty();
        getPaint().getTextBounds(text, 0, text.length(), textBounds);

        // the tip height or width is twice to the tipTextSize
        boolean hasEnoughWidth =
                (textBounds.width() + (tipTextSize << 1) + getPaddingLeft() + getPaddingRight() + tipPadding) < width;
        boolean hasEnoughHeight =
                (textBounds.height() + (tipTextSize << 1) + getPaddingTop() + getPaddingBottom() + tipPadding) < height;
        return hasEnoughHeight || hasEnoughWidth;
    }


    /**
     * calculate the position of the tip-background Rect.
     *
     * @param rectF the rectF to calculate according to the tip position.
     */
    private void calculateTipRect(RectF rectF, int rectSize) {

        // if the tag is not set, or set error, default horizontal position is RIGHT
        if ((tipPosition & TIP_LEFT_POSITION) == TIP_LEFT_POSITION) {
            rectF.left = tipPadding;
            rectF.right = tipPadding + rectSize;
        } else {
            rectF.left = width - rectSize - tipPadding;
            rectF.right = width - tipPadding;
        }

        // if the tag is not set, or set error, default vertical position is TOP
        if ((tipPosition & TIP_BOTTOM_POSITION) == TIP_BOTTOM_POSITION) {
            rectF.bottom = height - tipPadding;
            rectF.top = height - tipPadding - rectSize;
        } else {
            rectF.bottom = rectSize + tipPadding;
            rectF.top = tipPadding;
        }
    }


    private void setTipCount(int tipCount) {
        this.tipCount = tipCount;
        invalidate();
    }

    /**
     * brief:
     */
    class TipChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int tipCount = intent.getIntExtra(TextTipView.TIP_COUNT_TAG, 0);
            if (tipCount > 0) {
                setTipCount(tipCount);
            }
        }
    }

}


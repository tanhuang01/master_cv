package com.congguangzi.master_cv.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.congguangzi.master_cv.R;

/**
 * 简介: 自动扩充的文字的 text
 *
 * @author congguangzi (congspark@163.com) 2017/11/13.
 */
public class _06_RawTextView extends View {

    private int textSize;
    private int textColor;
    private String text;

    private Paint textPaint;

    private Rect textBounds = new Rect();

    private Paint.FontMetrics metrics;

    private int height;
    private int width;

    public _06_RawTextView(Context context) {
        this(context, null);
    }

    public _06_RawTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public _06_RawTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RawTextView, defStyleAttr, 0);
        textSize = array.getDimensionPixelSize(R.styleable.RawTextView_textSize, 10);
        textColor = array.getColor(R.styleable.RawTextView_textColor, Color.BLACK);
        text = array.getString(R.styleable.RawTextView_text);
        array.recycle();

        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);
        textPaint.getTextBounds(text, 0, text.length(), textBounds);
        metrics = textPaint.getFontMetrics();
    }

    {
        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 垂直方向上直接居中绘制.
        int textY = (int) ((height >> 1) + ((-(metrics.descent + metrics.ascent)) / 2));
        int textX = 0;

        // 水平方向上频分空间, 如果文字长度大于控件长度, 则不作处理.
        if (textBounds.width() > width) {
            canvas.drawText(text, 0, textY, textPaint);
            return;
        }


        int remainedWidth = width - textBounds.width();
        int eachGap = remainedWidth / (text.length() - 1);

        for (int i = 0; i < text.length(); i++) {
            textX = i * eachGap + ((int) textPaint.measureText(text, 0, i));
            canvas.drawText(text, i, i + 1, textX, textY, textPaint);
        }
    }
}

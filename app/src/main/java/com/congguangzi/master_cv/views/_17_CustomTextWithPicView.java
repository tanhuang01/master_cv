package com.congguangzi.master_cv.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.GuardedBy;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.congguangzi.master_cv.R;
import com.congguangzi.master_cv.ViewUtils;

/**
 * brief: a view shows picture and texts around it.
 *
 * @author congguangzi (congspark@163.com) 2018/7/17.
 */
public class _17_CustomTextWithPicView extends View {

    private final int PIC_X = 400;
    private final int PIC_Y = 200;

    TextPaint paint;
    Bitmap bitmap;

    int height, width;

    // record the height of the texts when the canvas is drawing.
    float textHeight;
    float[] measureWidth = new float[1];

    private final String text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. " +
            "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, " +
            "when an unknown printer took a galley of type and scrambled it to make a type specimen book. " +
            "It has survived not only five centuries, but also the leap into electronic typesetting, " +
            "remaining essentially unchanged. " +
            "It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, " +
            "and more recently with desktop publishing software like " +
            "Aldus PageMaker including versions of Lorem Ipsum.";

    StaticLayout staticLayout;

    Paint.FontMetrics metrics;
    // one line text height
    float oneLineHeight;

    {
        paint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(ViewUtils.dpToPixel(15));
        paint.setColor(Color.BLACK);

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable._01_maps);

        metrics = paint.getFontMetrics();
        oneLineHeight = metrics.bottom - metrics.top;
    }

    public _17_CustomTextWithPicView(Context context) {
        super(context);
    }

    public _17_CustomTextWithPicView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public _17_CustomTextWithPicView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        height = h;
        width = w;

        staticLayout = new StaticLayout(text, paint, width, Layout.Alignment.ALIGN_NORMAL, 1, 0, false);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int count = 0;
        int startIndex = 0;
        int lines = 1;
        int remained = text.length();

        do {

            if (lines * oneLineHeight > PIC_Y && lines * oneLineHeight < PIC_Y + bitmap.getHeight()) {
                count = paint.breakText(text, startIndex, text.length(), true, PIC_X, measureWidth);
                canvas.drawText(text, startIndex, startIndex + count, 0, lines * oneLineHeight + metrics.descent, paint);

                // the right side texts of the picture, if has enough space.
                if (remained - count > 0 && PIC_X + bitmap.getWidth() > ViewUtils.dpToPixel(20)) {
                    startIndex += count;
                    remained -= count;
                    count = paint.breakText(text, startIndex, text.length(), true, width - PIC_X - bitmap.getWidth(), measureWidth);
                    canvas.drawText(text, startIndex, startIndex + count, PIC_X + bitmap.getWidth(), lines * oneLineHeight + metrics.descent, paint);
                    lines++;
                    startIndex++;
                    remained -= count;
                    continue;
                }

            } else {
                count = paint.breakText(text, startIndex, text.length(), true, width, measureWidth);
                canvas.drawText(text, startIndex, startIndex + count, 0, lines * oneLineHeight + metrics.descent, paint);
            }

            lines++;
            startIndex += count;
            remained -= count;
        } while (remained > 0);


        canvas.drawBitmap(bitmap, PIC_X, PIC_Y, paint);

    }
}

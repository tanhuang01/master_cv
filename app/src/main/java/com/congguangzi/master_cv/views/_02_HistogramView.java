package com.congguangzi.master_cv.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 直方图.
 *
 * @author congguangzi (congspark@163.com) 2017/10/9.
 */

public class _02_HistogramView extends View {

    Paint paint = new Paint();
    int width = 100;
    Point[] points = {
            new Point(20,-10),
            new Point(140,-30),
            new Point(260,-30),
            new Point(380,-200),
            new Point(500,-400),
            new Point(620,-450),
            new Point(740,-180),
    };
    String[] texts = {
            "Froyo",
            "GB",
            "ICs",
            "JB",
            "KitKat",
            "L",
            "M"
    };

    public _02_HistogramView(Context context) {
        super(context);
    }

    public _02_HistogramView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public _02_HistogramView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        综合练习
//        练习内容：使用各种 Canvas.drawXXX() 方法画直方图

        canvas.translate(100, 550);
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(3);
        canvas.drawLine(0, 0, 0, -500, paint);
        canvas.drawLine(0, 0, 900, 0, paint);

        paint.setTextSize(40);
        canvas.drawText("直方图", 400, 120, paint);

        // 绘制正方形.
        paint.setColor(Color.GREEN);
        for (Point point : points) {
            canvas.drawRect(point.x, point.y, point.x + width, 0, paint);
        }

        // 绘制文字
        paint.setTextSize(25);
        paint.setColor(Color.WHITE);
        Rect rect = new Rect();
        paint.getTextBounds(texts[0], 0, texts[0].length(), rect);
        int height = rect.height();
        for (int i = 0; i < points.length; i++) {
            int textWidth = getTextWidth(paint, texts[i]);
            canvas.drawText(texts[i], points[i].x + ((width - textWidth) >>> 1), height + 5, paint);
        }

    }

    public int getTextWidth(Paint paint, String str) {
        int iRet = 0;
        if (str != null && str.length() > 0) {
            int len = str.length();
            float[] widths = new float[len];
            paint.getTextWidths(str, widths);
            for (int j = 0; j < len; j++) {
                iRet += (int) Math.ceil(widths[j]);
            }
        }
        return iRet;
    }
}

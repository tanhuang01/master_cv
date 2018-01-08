package com.congguangzi.master_cv.views._07_receipt_view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.congguangzi.master_cv.ViewUtils;

import java.io.UnsupportedEncodingException;


/**
 * 简介: 单据样式展示类.
 * <p>
 * 根据字符串数组, 展示单据信息, 并且实现动态切换的效果.
 * <p>
 * 目前使用的是 {@link StaticLayout}, 来辅助对文字的绘制.
 * <p>
 * 打印纸的效果是, 后期如果需要精确控制, 可以使用
 *
 * @author congguangzi (congspark@163.com) 2018/1/5.
 */
public class ReceiptView extends View {

    private static final String TAG = "receipt_view";

    private String title = "【张三的便利店】";

    private String[] texts = {};


    private Paint titlePaint;
    private TextPaint textPaint;

    private int width;
    private int centerX;
    private int height;


    StaticLayout staticLayout;

    {
        titlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        titlePaint.setTextSize(ViewUtils.dpToPixel(14));
        titlePaint.setTextAlign(Paint.Align.CENTER);

        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(ViewUtils.dpToPixel(12));
        textPaint.setTextAlign(Paint.Align.LEFT);
//        textPaint.setTypeface(Typeface.MONOSPACE);
    }

    public ReceiptView(Context context) {
        super(context);
    }

    public ReceiptView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ReceiptView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        centerX = width >>> 1;
        staticLayout = new StaticLayout(getContainedString(this.texts),
                textPaint, width, Layout.Alignment.ALIGN_NORMAL,
                1, 0, true);
        Log.i(TAG, "on size changed");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.i(TAG, "on draw");
        super.onDraw(canvas);
        canvas.drawText(title, centerX, titlePaint.getFontSpacing() * 2, titlePaint);

        textPaint.setTextScaleX(0.9f);
        canvas.save();
        canvas.translate(0, getTitleHeight());
        staticLayout.draw(canvas);
        canvas.restore();
    }

    private int getTitleHeight() {
        return (int) (titlePaint.getFontSpacing() * 3);
    }

    public void setTexts(String[] texts) {
        Log.i(TAG, "set Text");
        this.texts = texts;
        int h = (int) (getTitleHeight() +
                ReceiptViewUtil.getTextLines(this.texts) * textPaint.getFontSpacing() +
                titlePaint.getFontSpacing());
        // 设置高度, 会回调 onSizeChanged() 方法.
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        layoutParams.height = h;
        setLayoutParams(layoutParams);
    }

    /**
     * 将字符数组转换成一个字符串.
     *
     * @param strs 字符数组
     * @return 对应的字符串.
     */
    @NonNull
    private String getContainedString(String[] strs) {
        StringBuilder builder = new StringBuilder();
        for (String str : strs) {
            builder.append(str);
        }
        return builder.toString();
    }

    /**
     * 计算该行文字的字节数. GB2312 规则.
     *
     * @param text 每一行的文字.
     * @return 字节数. (最后一个回车符不计.)
     */
    private int getLineTextWidth(String text) {
        try {
            return text.getBytes("GB2312").length - 1;
        } catch (UnsupportedEncodingException e) {
            // this shouldn't be happend, since the "GB2312" si supported.
            e.printStackTrace();
            return 1;
        }
    }

}

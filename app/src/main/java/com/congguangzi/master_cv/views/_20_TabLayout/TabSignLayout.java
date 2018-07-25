package com.congguangzi.master_cv.views._20_TabLayout;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.congguangzi.master_cv.ViewUtils;

import java.util.Arrays;

/**
 * brief:  a tab-layout.
 * <p>
 *
 * @author congguangzi (congspark@163.com) 2018/7/23.
 */
public class TabSignLayout extends ViewGroup {

    private Rect[] childrenRect;
    private int childCount;

    private final int RIGHT_MARGIN = ViewUtils.dpToPixel(10);

    public TabSignLayout(Context context) {
        super(context);
    }

    public TabSignLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TabSignLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int lineUsedWidth = 0;
        int lineUsedHeight = 0;
        int lineMaxHeight = 0;

        // indicate the count of the sign in one line.
        int lineSignCount = 0;

        int childCount = getChildCount();

        // initialize of resize the
        if (childrenRect == null) {
            childrenRect = new Rect[childCount];
        } else if (childrenRect.length < childCount) {
            childrenRect = Arrays.copyOf(childrenRect, childCount);
        }

        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            measureChildWithMargins(child, widthMeasureSpec, 0,
                    heightMeasureSpec, 0);

            Rect childBound = childrenRect[i];
            if (childBound == null) {
                childBound = childrenRect[i] = new Rect();
            }
            // this should by fine, because we have generate a MarginLayoutParams
            // in this class.
            MarginLayoutParams params = (MarginLayoutParams) child.getLayoutParams();

            // move to another line.
            if ((lineUsedWidth + child.getMeasuredWidth() + params.leftMargin)
                    > MeasureSpec.getSize(widthMeasureSpec) - RIGHT_MARGIN) {

                // modify the left padding to make the sign aline center.
                int linePaddingLeft = (MeasureSpec.getSize(widthMeasureSpec) - lineUsedWidth) >> 1;
                for (int j = 1; j <= lineSignCount; j++) {
                    childrenRect[i - j].offset(linePaddingLeft, 0);
                }

                lineUsedWidth = 0;
                lineUsedHeight += lineMaxHeight;
                lineMaxHeight = 0;
                lineSignCount = 0;
            }

            childBound.set(lineUsedWidth + params.leftMargin,
                    lineUsedHeight + params.topMargin,
                    lineUsedWidth + child.getMeasuredWidth() + params.leftMargin,
                    lineUsedHeight + child.getMeasuredHeight() + params.topMargin);

            lineSignCount++;
            lineUsedWidth += (child.getMeasuredWidth() + params.leftMargin);
            lineMaxHeight = Math.max(lineMaxHeight, child.getMeasuredHeight() + params.topMargin);
        }

        // make the last line in center if exists
        int linePaddingLeft = (MeasureSpec.getSize(widthMeasureSpec) - lineUsedWidth) >> 1;
        for (int i = 1; i <= lineSignCount; i++) {
            childrenRect[childCount - i].offset(linePaddingLeft, 0);
        }

        int width = lineUsedWidth;
        int height = lineUsedHeight + lineMaxHeight;

        setMeasuredDimension(resolveSizeAndState(width, widthMeasureSpec, 0),
                resolveSizeAndState(height, heightMeasureSpec, 0));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            Rect rect = childrenRect[i];
            child.layout(rect.left, rect.top, rect.right, rect.bottom);
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}

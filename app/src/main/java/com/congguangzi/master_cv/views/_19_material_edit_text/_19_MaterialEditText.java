package com.congguangzi.master_cv.views._19_material_edit_text;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;

import com.congguangzi.master_cv.ViewUtils;


/**
 * brief: a label with the edittext.
 *
 * @author congguangzi (congspark@163.com) 2018/7/20.
 */
public class _19_MaterialEditText extends AppCompatEditText {

    private static final int LABEL_PADDING_LEFT = ViewUtils.dpToPixel(4);

    // use custom attribute to optimize
    private final float LABEL_SIZE = ViewUtils.dpToPixel(12);
    private final int LABEL_PADDING_TOP = ViewUtils.dpToPixel(12);
    private final int LABEL_PADDING_BOTTOM = ViewUtils.dpToPixel(12);
    // use the color-primary to get the label color
    private final int LABEL_COLOR = Color.BLACK;

    private String label;

    private int width, height;

    // move animation start y.
    private float labelPosY;
    private float labelStartY;

    private Paint labelPaint;
    private int labelColor = LABEL_COLOR;
    private int curColor;
    private float labelSize;

    private boolean labelShowed = false;

    private float labelAlpha = 0;

    private AnimatorSet showLabelAnimatorSet;
    private AnimatorSet disappearAnimatorSet;

    public _19_MaterialEditText(Context context) {
        super(context);
    }

    public _19_MaterialEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, new int[]{android.R.attr.colorAccent});
        int c = Color.parseColor("#7f03014f");
        labelColor = array.getColor(0, Color.BLACK);
        curColor = labelColor;
        array.recycle();
    }

    public _19_MaterialEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private TextWatcher watcher = new MaterialEditTextWatch() {
        @Override
        public void afterTextChanged(Editable s) {
            String text = s.toString();
            if (text.length() > 0 && !labelShowed) {
                // add label
                labelShowed = true;
                getShowLabelAnim().start();
                _19_MaterialEditText.this.setHint("");
            } else if (text.length() == 0 && labelShowed) {
                // remove label
                labelShowed = false;
                getDisappearAnim().start();
            }
        }
    };


    {
        labelPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        labelPaint.setTextSize(LABEL_SIZE);

        setPadding(getPaddingLeft(), (int) (getPaddingTop() + LABEL_SIZE + LABEL_PADDING_BOTTOM),
                getPaddingRight(), getPaddingBottom());

        // get hint of the edit-text
        if (getHint() != null) {
            label = getHint().toString();
        }

        // set the initial alpha of the label
        if (getText().length() > 0) {
            labelPaint.setAlpha(255);
        } else {
            labelPaint.setAlpha(0);
        }

        addTextChangedListener(watcher);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        labelStartY = LABEL_PADDING_BOTTOM + LABEL_PADDING_TOP + LABEL_SIZE +
                ((h - LABEL_PADDING_BOTTOM - LABEL_PADDING_TOP - ((int) LABEL_SIZE)) >> 1);
        labelPosY = labelStartY;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawText(label, LABEL_PADDING_LEFT, labelPosY, labelPaint);

    }

    private AnimatorSet getShowLabelAnim() {
        if (showLabelAnimatorSet != null) {
            return showLabelAnimatorSet;
        } else {
            ObjectAnimator animAlpha =
                    ObjectAnimator.ofFloat(this, "labelAlpha", 0, 1);
            ObjectAnimator animPos =
                    ObjectAnimator.ofFloat(this, "labelPosY",
                            labelStartY, LABEL_PADDING_BOTTOM + LABEL_SIZE);
            ObjectAnimator animColor =
                    ObjectAnimator.ofInt(this, "curColor", getCurrentHintTextColor(), labelColor);
            animColor.setEvaluator(new ArgbEvaluator());
            ObjectAnimator animSize =
                    ObjectAnimator.ofFloat(this, "labelSize", getTextSize(), LABEL_SIZE);

            showLabelAnimatorSet = new AnimatorSet();
            showLabelAnimatorSet.playTogether(animAlpha, animPos, animColor, animSize);
            return showLabelAnimatorSet;
        }
    }

    private AnimatorSet getDisappearAnim() {
        if (disappearAnimatorSet != null) {
            return disappearAnimatorSet;
        } else {
            ObjectAnimator animAlpha =
                    ObjectAnimator.ofFloat(this, "labelAlpha", 1, 0);
            ObjectAnimator animPos =
                    ObjectAnimator.ofFloat(this, "labelPosY",
                            LABEL_PADDING_BOTTOM + LABEL_SIZE, labelStartY);
            ObjectAnimator animColor =
                    ObjectAnimator.ofInt(this, "curColor", labelColor, getCurrentHintTextColor());
            animColor.setEvaluator(new ArgbEvaluator());
            ObjectAnimator animSize =
                    ObjectAnimator.ofFloat(this, "labelSize", LABEL_SIZE, getTextSize());

            disappearAnimatorSet = new AnimatorSet();
            disappearAnimatorSet.playTogether(animAlpha, animPos, animColor, animSize);
            disappearAnimatorSet.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    setHint(label);
                }
            });
            return disappearAnimatorSet;
        }
    }

    public float getLabelAlpha() {
        return labelAlpha;
    }

    public void setLabelAlpha(float labelAlpha) {
        this.labelAlpha = labelAlpha;
    }

    public float getLabelPosY() {
        return labelPosY;
    }

    public void setLabelPosY(float labelPosY) {
        this.labelPosY = labelPosY;
    }

    public int getCurColor() {
        return curColor;
    }

    public void setCurColor(int labelColor) {
        this.curColor = labelColor;
        labelPaint.setColor(labelColor);
        labelPaint.setAlpha((int) (labelAlpha * 255));
        invalidate();
    }

    public float getLabelSize() {
        return labelSize;
    }

    public void setLabelSize(float labelSize) {
        this.labelSize = labelSize;
        labelPaint.setTextSize(labelSize);
    }
}

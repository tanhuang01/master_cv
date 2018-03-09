package com.congguangzi.master_cv.views._08_text_tip_view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.congguangzi.master_cv.R;

/**
 * brief:
 *
 * @author congguangzi (congspark@163.com) 2018/3/9.
 */
public class TextTipViewConstraintLayout extends ConstraintLayout implements View.OnClickListener {

    TextView tv_tipText;

    // click to send a broadcast
    Button bt_sendBroadcast;

    // change the selected state of the tip view
    Button bt_changeTipSelected;

    // the number of broadcast to send.
    EditText et_msgNum;

    public TextTipViewConstraintLayout(@NonNull Context context) {
        super(context);
    }

    public TextTipViewConstraintLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TextTipViewConstraintLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        bt_changeTipSelected = findViewById(R.id.bt_change_seleted);
        bt_sendBroadcast = findViewById(R.id.bt_send_broadcast);
        et_msgNum = findViewById(R.id.et_msg_count);
        tv_tipText = findViewById(R.id.tv_tipText);

        bt_sendBroadcast.setOnClickListener(this);
        bt_changeTipSelected.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.bt_send_broadcast:
                String str_num = et_msgNum.getText().toString();
                int num;
                try {
                    num = Integer.valueOf(str_num);
                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "请输入合法的整数", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (num < 0) {
                    Toast.makeText(getContext(), "请输入大于零的整数", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(TextTipView.TIP_BROADCAST_TAG);
                intent.putExtra(TextTipView.TIP_COUNT_TAG, num);
                getContext().sendBroadcast(intent);
                break;

            case R.id.bt_change_seleted:
                tv_tipText.setSelected(!tv_tipText.isSelected());
                break;
        }

    }
}

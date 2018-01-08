package com.congguangzi.master_cv.views._07_receipt_view;

import java.io.UnsupportedEncodingException;

/**
 * 简介:
 *
 * @author congguangzi (congspark@163.com) 2018/1/5.
 */
public class ReceiptViewUtil {

    static String[] texts = {
            "门店名称：【1012】默认门店\n",        // 26
            "销售类型：零售 销售单\n",            // 21
            "收银机号：【pos004】pos004\n",      // 26
            "收银员：【changwei】常伟\n",         // 24
            "单号：100111709273919410\n",        // 24
            "交易时间：2017/09/16 19:30:21\n",  // 29
            "收银分店：默认分店\n",             // 18
            "一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十\n",
            "12345678901234567890123456789012345678901234567890\n",
            "abcdefghijabcdefghijabcdefghijabcdefghijabcdefghij\n",
            "ABCDEFGHIJABCDEFGHIJABCDEFGHIJABCDEFGHIJABCDEFGHIJ\n",
    };

    /**
     * 获取显示内容的行数.
     * <p>
     * 当前行超出指定的字符数时, 按多行计算.
     *
     * @return
     */
    static int getTextLines(String[] texts) {
        int textLines = 0;
        for (int i = 0; i < texts.length; i++) {
            try {
                // TODO(congguangzi) : 动态的切换 32  这个值, 对应不同的宽度

                // 32 个字符算是一行, 不足一行按照一行处理.
                textLines += (texts[i].getBytes("GB2312").length - 1) / 32 + 1;

            } catch (UnsupportedEncodingException e) {
                // this shouldn't be happend. since the "GB2312" is supported
                throw new RuntimeException("the GB2312 font code is not supported");
            }
        }
        return textLines;
    }
}

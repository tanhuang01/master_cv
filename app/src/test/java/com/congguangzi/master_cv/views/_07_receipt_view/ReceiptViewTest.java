package com.congguangzi.master_cv.views._07_receipt_view;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.*;

/**
 * @author congguangzi (congspark@163.com) 2018/1/5.
 */
@RunWith(JUnit4.class)
public class ReceiptViewTest {

    private String[] texts = {
            "门店名称：【1012】默认门店\n",    // 26
            "销售类型：零售 销售单\n",    // 21
            "收银机号：【pos004】pos004\n",    // 26
            "收银员：【changwei】常伟\n",   // 24
            "单号：100111709273919410\n",  // 24
            "交易时间：2017/09/16 19:30:21\n",   // 29
            "收银分店：默认分点\n",  // 18
    };

    private int[] textsLength = {26, 21, 26, 24, 24, 29, 18,};


    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testTextWidth() throws UnsupportedEncodingException {
        for (int i = 0; i < texts.length; i++) {
            System.out.println("text[" + i + "] = " + (texts[i].getBytes("GB2312").length - 1));
            assertEquals(textsLength[i], texts[i].getBytes("GB2312").length - 1);
        }
    }

}
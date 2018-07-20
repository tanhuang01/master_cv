package com.congguangzi.master_cv.views._19_material_edit_text;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * brief: a class implements the {@link TextWatcher} to avoid the too many {@literal @}Override
 * methods in use.
 *
 * @author congguangzi (congspark@163.com) 2018/7/20.
 */
public class MaterialEditTextWatch implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}

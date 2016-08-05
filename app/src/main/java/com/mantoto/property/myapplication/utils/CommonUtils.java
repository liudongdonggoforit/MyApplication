package com.mantoto.property.myapplication.utils;

import android.widget.EditText;

/**
 * Created by liudongdong on 2016/8/4.
 */
public class CommonUtils {
    /**
     * EditText 点击EditText 编辑框删除hint预设字
     * @param editText
     * @param hasFocus
     */
    public static void deleteHint(EditText editText, boolean hasFocus) {
        String hint;
        if (hasFocus){
            hint = editText.getHint().toString();
            editText.setTag(hint);
            editText.setHint("");
        }else {
            hint = editText.getTag().toString();
            editText.setHint(hint);
        }
    }
}

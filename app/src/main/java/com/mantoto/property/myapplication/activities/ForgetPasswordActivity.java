package com.mantoto.property.myapplication.activities;

import android.view.View;

import com.mantoto.property.myapplication.R;

/**
 * Created by liudongdong on 2016/8/1.
 */
public class ForgetPasswordActivity extends BaseActivity implements View.OnClickListener{
    @Override
    protected int getContentViewResId() {
        return R.layout.activity_forget_password;
    }

    @Override
    protected int getTopBarTextRes() {
        return R.string.forget_password_reset_password;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.top_bar_left_img :
                finish();
                break;
        }
    }
}

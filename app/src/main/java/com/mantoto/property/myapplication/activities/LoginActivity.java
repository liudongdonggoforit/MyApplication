package com.mantoto.property.myapplication.activities;

import android.content.Intent;
import android.view.View;

import com.mantoto.property.myapplication.R;

/**
 * Created by liudongdong on 2016/7/31.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener{
    @Override
    protected int getContentViewResId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initViews() {
        super.initViews();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.login_forget_password_btn:
                intent.setClass(LoginActivity.this,ForgetPasswordActivity.class);
                break;
            case R.id.login_register_btn:
                intent.setClass(LoginActivity.this,RegisterActivity.class);
                break;
            case R.id.login_button:
                intent.setClass(LoginActivity.this,MainActivity.class);
                break;
        }
        startActivity(intent);
        }
    }

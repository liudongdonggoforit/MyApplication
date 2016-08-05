package com.mantoto.property.myapplication.activities;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mantoto.property.myapplication.R;
import com.mantoto.property.myapplication.utils.CommonUtils;

/**
 * Created by liudongdong on 2016/7/31.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener ,View.OnFocusChangeListener{
    private EditText countEt,passwordEt;
    @Override
    protected int getContentViewResId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initViews() {
        super.initViews();
        countEt = (EditText) findViewById(R.id.login_count_et);
        passwordEt = (EditText) findViewById(R.id.login_password_et);
        countEt.setOnFocusChangeListener(new editOnFocusListener());
        passwordEt.setOnFocusChangeListener(new editOnFocusListener());
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.login_forget_password_btn:
                Toast.makeText(LoginActivity.this,"123",Toast.LENGTH_LONG).show();
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

    /**
     * EditText 点击EditText 编辑框删除hint预设字
     * @param v    (为何不起作用)
     * @param hasFocus
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()){
            case R.id.login_count_et:
                Toast.makeText(LoginActivity.this,"123",Toast.LENGTH_LONG).show();
                CommonUtils.deleteHint(countEt,hasFocus);
                break;
            case R.id.login_password_et :
                Toast.makeText(LoginActivity.this,"123",Toast.LENGTH_LONG).show();
                CommonUtils.deleteHint(passwordEt,hasFocus);
                break;
        }
    }

    /**
     * 点击EditText 编辑框删除hint预设字
     */
    private class editOnFocusListener implements View.OnFocusChangeListener{

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            switch (v.getId()){
                case R.id.login_count_et:
                    Toast.makeText(LoginActivity.this,"123",Toast.LENGTH_LONG).show();
                    CommonUtils.deleteHint(countEt,hasFocus);
                    break;
                case R.id.login_password_et :
                    Toast.makeText(LoginActivity.this,"123",Toast.LENGTH_LONG).show();
                    CommonUtils.deleteHint(passwordEt,hasFocus);
                    break;
            }
        }
    }
}

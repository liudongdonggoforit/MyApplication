package com.mantoto.property.myapplication.activities;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.VolleyError;
import com.google.gson.JsonObject;
import com.mantoto.property.myapplication.R;
import com.mantoto.property.myapplication.common.Constant;
import com.mantoto.property.myapplication.utils.CommonUtils;
import com.mantoto.property.myapplication.utils.ToastU;
import com.mantoto.property.myapplication.volley.IRequest;
import com.mantoto.property.myapplication.volley.RequestListener;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by liudongdong on 2016/8/1.
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener,View.OnFocusChangeListener {
    private EditText mobile,verificationCode,inputPassword,confirmPassword;
    private Button verification,confirm;
    private String phoneNumber,verificationNumber;
    private int i = 60;
    @Override
    protected int getContentViewResId() {
        return R.layout.activity_register;
    }

    @Override
    protected int getTopBarTextRes() {
        return  R.string.register_top_title;
    }

    /**
     * 初始化布局文件
     */
    @Override
    protected void initViews() {
        super.initViews();
        mobile = (EditText) findViewById(R.id.register_mobile_et);
        verificationCode = (EditText) findViewById(R.id.register_verification_code_et);
        inputPassword = (EditText) findViewById(R.id.register_input_et);
        confirmPassword = (EditText) findViewById(R.id.register_confirm_et);
        verification = (Button) findViewById(R.id.register_verification_btn);
        confirm = (Button) findViewById(R.id.register_confirm_btn);
        confirm.setText(R.string.register_confirm);
        //设置focuschange
        mobile.setOnFocusChangeListener(this);
        verificationCode.setOnFocusChangeListener(this);
        inputPassword.setOnFocusChangeListener(this);
        confirmPassword.setOnFocusChangeListener(this);
    }

    @Override
    protected void loadDatas() {
        super.loadDatas();
        JSONObject object = new JSONObject();
        try {
            object.put("phoneNum",phoneNumber);
            object.put("templateStr","SMS_5032123");
            object.put("code","123456");
            object.put("product","易修到家");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        IRequest.postJson(RegisterActivity.this, Constant.GET_RANT_CODE, object, new RequestListener() {
            @Override
            public void requestSuccess(JSONObject json) {
                ToastU.showShort(RegisterActivity.this,json.toString());
            }

            @Override
            public void requestError(VolleyError error) {
                ToastU.showShort(RegisterActivity.this,error.getMessage());
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_verification_btn:
                if (CommonUtils.isEmpty(mobile)){
                    ToastU.showShort(RegisterActivity.this,R.string.please_input_mobile);
                    return;
                }
                phoneNumber = mobile.getText().toString().trim();
                verificationNumber = mobile.getText().toString().trim();
                loadDatas();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (;i > 0;i--){
                            handler.sendEmptyMessage(1);
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();

                break;
            case R.id.register_confirm_btn:
                if (CommonUtils.isEmpty(mobile)){
                    ToastU.showShort(RegisterActivity.this,R.string.please_input_mobile);
                    return;
                }
                if (CommonUtils.isEmpty(verificationCode)){
                    ToastU.showShort(RegisterActivity.this,R.string.please_input_verification_code);
                    return;
                }
                break;
        }
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1){
                verification.setText("重新发送("+i+")");
            }
        }
    };

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()){
            case R.id.register_mobile_et:
                CommonUtils.deleteHint(mobile,hasFocus);
                break;
            case R.id.register_verification_code_et:
                CommonUtils.deleteHint(verificationCode,hasFocus);
                break;
            case R.id.register_input_et:
                CommonUtils.deleteHint(inputPassword,hasFocus);
                break;
            case R.id.register_confirm_et:
                CommonUtils.deleteHint(confirmPassword,hasFocus);
                break;
        }
    }
}

package com.mantoto.property.myapplication.activities;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.mantoto.property.myapplication.MantotoApplication;
import com.mantoto.property.myapplication.R;
import com.mantoto.property.myapplication.common.Constant;
import com.mantoto.property.myapplication.model.PropertyVo;
import com.mantoto.property.myapplication.model.User;
import com.mantoto.property.myapplication.model.VarificationCode;
import com.mantoto.property.myapplication.utils.CommonUtils;
import com.mantoto.property.myapplication.utils.JsonUtils;
import com.mantoto.property.myapplication.utils.LogU;
import com.mantoto.property.myapplication.utils.MD5;
import com.mantoto.property.myapplication.utils.RSAEncryptor;
import com.mantoto.property.myapplication.utils.ToastU;
import com.mantoto.property.myapplication.volley.IRequest;
import com.mantoto.property.myapplication.volley.RequestListener;
import com.mantoto.property.myapplication.volley.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by liudongdong on 2016/8/1.
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener,View.OnFocusChangeListener {
    private static final String TAG = "RegisterActivity";
    private EditText mobile,verificationCode,inputPassword,confirmPassword;
    private Button verification,confirm;
    private String phoneNumber,verificationNumber;
    private int i = 60;
    private String RandCode = "";
    private static RSAEncryptor rsaEncryptor;
    private long propertyId ;//小区id
    /**
     *
     * @return
     */
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
        getPropertyId();
    }

    @Override
    protected void loadDatas() {
        super.loadDatas();
        JSONObject object = new JSONObject();
        try {
            rsaEncryptor = new RSAEncryptor(Constant.RSA_PUBLIC_KEY,Constant.PKCS8_PRIVATE_KEY);
            object.put("appName", "mantutu");
            object.put("sendType", 1);
            object.put("phoneNum", rsaEncryptor.encryptWithBase64(phoneNumber));
        } catch (Exception e) {
            e.printStackTrace();
        }
        /**
         * 获取验证码
         */
        IRequest.postJson(RegisterActivity.this, Constant.GET_PHONE_CODE, object, new RequestListener() {
            @Override
            public void requestSuccess(JSONObject json) {
                LogU.i(TAG,json.toString());
                VarificationCode varificationCode = JsonUtils.object(json.toString(),VarificationCode.class);
                if (varificationCode.bSuccess){
                    ToastU.showShort(RegisterActivity.this,"验证码已发送");
                }else {
                    ToastU.showShort(RegisterActivity.this,varificationCode.desc);
                }

            }
            @Override
            public void requestError(VolleyError error) {
                ToastU.showShort(RegisterActivity.this,error.toString());
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
                //本地生成 六位验证码 用于以后改版使用
             /*   Random random = new Random();
                for (int i =0; i< 6;i++){
                    RandCode += random.nextInt(10);
                }*/
                phoneNumber = mobile.getText().toString().trim();
                verificationNumber = mobile.getText().toString().trim();
                loadDatas();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (;i > 0;i--){
                            handler.sendEmptyMessage(1);
                            if (i<=0){
                                break;
                            }
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        handler.sendEmptyMessage(2);
                    }
                }).start();

                break;
            case R.id.register_confirm_btn:
                verifyLogin();
                JSONObject reqObject = new JSONObject();
                try {
                    reqObject.put("phoneNum", phoneNumber);
                    reqObject.put("smsCode", verificationCode.getText().toString().trim());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                IRequest.postJson(RegisterActivity.this, Constant.GET_VERIFY_CODE, reqObject, new RequestListener() {
                    @Override
                    public void requestSuccess(JSONObject json) {
                        LogU.i(TAG,json.toString());
                        VarificationCode varificationCode = JsonUtils.object(json.toString(),VarificationCode.class);
                        if (varificationCode.bSuccess){
                            register();
                        }else {
                            ToastU.showShort(RegisterActivity.this,varificationCode.desc);
                        }
                    }

                    @Override
                    public void requestError(VolleyError error) {

                    }
                });
                break;
        }
    }

    /**
     * 验证登录信息
     */
    private void verifyLogin() {
        if (CommonUtils.isEmpty(mobile)){
            ToastU.showShort(RegisterActivity.this,R.string.please_input_mobile);
            return;
        }
        if (CommonUtils.isEmpty(verificationCode)){
            ToastU.showShort(RegisterActivity.this,R.string.please_input_verification_code);
            return;
        }
        if (CommonUtils.isEmpty(inputPassword)){
            ToastU.showShort(RegisterActivity.this,R.string.register_please_input_password);
            return;
        }
        if (CommonUtils.isEmpty(confirmPassword)){
            ToastU.showShort(RegisterActivity.this,R.string.register_please_confirm_password);
            return;
        }
        /**
         * 判断本地生产和短信验证码是否相同  以后改版使用
         */
//        if (!RandCode.equals(verificationCode.getText().toString())){
//            ToastU.showShort(RegisterActivity.this,R.string.please_phone_code_error);
//            return;
//        }
        if (!inputPassword.getText().toString().trim().equals(confirmPassword.getText().toString().trim())){
            ToastU.showShort(RegisterActivity.this,R.string.please_confirm_password);
            return;
        }
    }

    /**
     * 接收处理数据
     */
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1){
                verification.setText("重新发送("+i+")");
                verification.setClickable(false);
            }else if (msg.what == 2){
                verification.setText("获取验证码");
                verification.setClickable(true);
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

    /**
     * 获取小区id
     */
    private void getPropertyId() {
        JSONObject object = new JSONObject();
        try {
            object.put("description", "未找到小区");
            object.put("pageindex", 1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        IRequest.postJson(RegisterActivity.this, Constant.Get_PROPERTY_BY_NAME, object, new RequestListener() {
            @Override
            public void requestSuccess(JSONObject json) {
                LogU.i(TAG,json.toString());
                PropertyVo propertys = JsonUtils.object(json.toString(),PropertyVo.class);
                if (propertys.code == 200){
                    propertyId = propertys.propertyarray.get(0).PropertyID;
                    LogU.i(TAG,""+propertyId);
                }else {
                    ToastU.showLong(RegisterActivity.this,propertys.desc);
                }
            }
            @Override
            public void requestError(VolleyError error) {

            }
        });
    }

    private void register() {
        JSONObject object = new JSONObject();
        try {
            object.put("telnumber", phoneNumber);
            object.put("truename", phoneNumber);
            object.put("room", "");
            object.put("password", MD5.toMD5(inputPassword.getText().toString()));
            object.put("propertyid", propertyId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        IRequest.postJson(RegisterActivity.this, Constant.USER_REGISTER_URL, object, new RequestListener() {
            @Override
            public void requestSuccess(JSONObject json) {
                LogU.i(TAG,json.toString());
                User user = JsonUtils.object(json.toString(),User.class);
                if (user.code == 200){
                    Intent intent = new Intent();
                    intent.setClass(RegisterActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    ToastU.showLong(RegisterActivity.this,user.desc);
                }
            }

            @Override
            public void requestError(VolleyError error) {

            }
        });
    }


    /**
     * 验证码登录（以后改版会用到）
     */
        /*
            object.put("phoneNum",phoneNumber);
            object.put("templateStr","SMS_5032123");
            object.put("code",RandCode);
            object.put("product","易修到家")
       IRequest.postJson(RegisterActivity.this, Constant.GET_RANT_CODE, object, new RequestListener() {
            @Override
            public void requestSuccess(JSONObject json) {

            }

            @Override
            public void requestError(VolleyError error) {

            }
        });*/
}

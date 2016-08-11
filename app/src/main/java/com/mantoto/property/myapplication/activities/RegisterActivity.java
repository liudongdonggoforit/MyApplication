package com.mantoto.property.myapplication.activities;

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
import com.mantoto.property.myapplication.utils.CommonUtils;
import com.mantoto.property.myapplication.utils.ToastU;
import com.mantoto.property.myapplication.volley.IRequest;
import com.mantoto.property.myapplication.volley.RequestListener;
import com.mantoto.property.myapplication.volley.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liudongdong on 2016/8/1.
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener,View.OnFocusChangeListener {
    private EditText mobile,verificationCode,inputPassword,confirmPassword;
    private Button verification,confirm;
    private String phoneNumber,verificationNumber;
    private int i = 60;
    public static RequestQueue queue;
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
        queue = Volley.newRequestQueue(RegisterActivity.this);
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
        StringRequest request = new StringRequest(Request.Method.POST, Constant.GET_PHONE_CODE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ToastU.showShort(RegisterActivity.this,response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ToastU.showShort(RegisterActivity.this,error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("phoneNum", "13552662536");
                hashMap.put("appName", "mantutu");
                hashMap.put("sendType", "1");
                return hashMap;
            }
        };
        queue.add(request);
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
                            if (i<=0){
                                break;
                            }
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
                verification.setClickable(false);
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

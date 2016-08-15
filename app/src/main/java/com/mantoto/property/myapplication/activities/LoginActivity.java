package com.mantoto.property.myapplication.activities;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.mantoto.property.myapplication.R;
import com.mantoto.property.myapplication.common.Constant;
import com.mantoto.property.myapplication.common.LocalStore;
import com.mantoto.property.myapplication.utils.MD5;
import com.mantoto.property.myapplication.model.UserInfo;
import com.mantoto.property.myapplication.utils.CommonUtils;
import com.mantoto.property.myapplication.utils.JsonUtils;
import com.mantoto.property.myapplication.volley.IRequest;
import com.mantoto.property.myapplication.volley.RequestListener;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by liudongdong on 2016/7/31.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener ,View.OnFocusChangeListener{
    private EditText countEt,passwordEt;
    private String userName,userPassword;
    @Override
    protected int getContentViewResId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initViews() {
        super.initViews();
        countEt = (EditText) findViewById(R.id.login_count_et);
        passwordEt = (EditText) findViewById(R.id.login_password_et);
        countEt.setOnFocusChangeListener(this);
        passwordEt.setOnFocusChangeListener(this);
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

    /**
     * 加载数据
     */
    @Override
    protected void loadDatas() {
        super.loadDatas();
        /**
         * 登陆请求
         */
        RequestLogin();
    }

    private void RequestLogin() {
        userName = "13552662536";
        userPassword = MD5.toMD5("12345");
        JSONObject post = new JSONObject();
        try {
            post.put("username",userName);
            post.put("password",userPassword);
            post.put("softtype",1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        IRequest.postJson(LoginActivity.this, Constant.GET_USER_VERIFY, post, new RequestListener() {
            @Override
            public void requestSuccess(JSONObject json) {
                Log.i("MyLog", "json" + json.toString());
                UserInfo userInfo = JsonUtils.object(json.toString(),UserInfo.class);
                if (userInfo == null){
                    return;
                }
                if (userInfo.code == 200){
                    LocalStore.setUserInfo(LoginActivity.this,userInfo);
                }else if (userInfo.code == 403){

                }else {
                    Toast.makeText(LoginActivity.this, userInfo.desc, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void requestError(VolleyError error) {
                Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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
                CommonUtils.deleteHint(countEt,hasFocus);
                break;
            case R.id.login_password_et :
                CommonUtils.deleteHint(passwordEt,hasFocus);
                break;
        }
    }
}

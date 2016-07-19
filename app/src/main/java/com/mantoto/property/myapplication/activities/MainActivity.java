package com.mantoto.property.myapplication.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.mantoto.property.myapplication.R;
import com.mantoto.property.myapplication.common.Constant;
import com.mantoto.property.myapplication.volley.IRequest;
import com.mantoto.property.myapplication.volley.RequestListener;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    private TextView mText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mText = (TextView) findViewById(R.id.weather);
        Map<String,String> map = new HashMap<String,String>();
        map.put("cityname","北京");
        JSONObject jsonObject = new JSONObject(map);
        IRequest.post2(MainActivity.this, Constant.GET_WEATHER, jsonObject, new RequestListener() {
            @Override
            public void requestSuccess(String json) {

            }

            @Override
            public void requestSuccess(JSONObject json) {
                mText.setText(json.toString());
            }

            @Override
            public void requestError(VolleyError error) {
                Log.e(TAG, error.getMessage(), error);
                mText.setText(error.getMessage());
                Log.i(TAG, "请求错误：" + error.getMessage());
                Toast.makeText(MainActivity.this,"请求出错："+error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}

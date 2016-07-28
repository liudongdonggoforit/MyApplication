package com.mantoto.property.myapplication.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.mantoto.property.myapplication.R;
import com.mantoto.property.myapplication.common.Constant;
import com.mantoto.property.myapplication.fragments.MainFragment;
import com.mantoto.property.myapplication.fragments.UserCenterFragment;
import com.mantoto.property.myapplication.volley.IRequest;
import com.mantoto.property.myapplication.volley.RequestListener;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    private TextView mText;
    private View[] mBottom;//存放底部切换view
    private final Class[] mFragments = new Class[]{MainFragment.class, UserCenterFragment.class};
    private final Map<Integer, Fragment> mFragmentMaps = new HashMap<Integer, Fragment>();
    private int mCurrentSelectedFragmentPosition = -1;

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
        super.initViews();
        mBottom = new View[]{findViewById(R.id.main_bottom_main), findViewById(R.id.main_bottom_user)};
        mBottom[0].setSelected(true);
        setSelected(0);
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        for (int i = 0; i < mBottom.length; i++) {
            final int temp = i;
            mBottom[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setSelected(temp);
                }
            });
        }
    }

    private void setSelected(int position) {
        for (int i = 0; i < mBottom.length; i++) {
            if (i == position) {
                mBottom[i].setSelected(true);
            } else {
                mBottom[i].setSelected(false);
            }
        }
        if (mCurrentSelectedFragmentPosition == position) {
            return;
        }
        try {
            Fragment fragment = mFragmentMaps.get(position);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if (mCurrentSelectedFragmentPosition != -1) {
                transaction.hide(mFragmentMaps.get(mCurrentSelectedFragmentPosition));
            }
            if (fragment == null) {
                fragment = (Fragment) mFragments[position].newInstance();
                mFragmentMaps.put(position, fragment);
                transaction.add(R.id.main_content, fragment);
            } else {
                transaction.show(fragment);
                transaction.commit();
            }
            mCurrentSelectedFragmentPosition = position;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void loadDatas() {
        super.loadDatas();
        Map<String, String> map = new HashMap<String, String>();
        map.put("cityname", "北京");
        JSONObject jsonObject = new JSONObject(map);
        IRequest.post2(MainActivity.this, Constant.GET_WEATHER, jsonObject, new RequestListener() {
            @Override
            public void requestSuccess(String json) {

            }

            @Override
            public void requestSuccess(JSONObject json) {

            }

            @Override
            public void requestError(VolleyError error) {
                Log.e(TAG, error.getMessage(), error);
                Log.i(TAG, "请求错误：" + error.getMessage());
                Toast.makeText(MainActivity.this, "请求出错：" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}

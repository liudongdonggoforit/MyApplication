package com.mantoto.property.myapplication.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mantoto.property.myapplication.R;

/**
 * Created by Mr.liu
 * On 2016/7/20
 * At 14:50
 * My Application
 */
public class UserCenterFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return mView = inflater.inflate(R.layout.fragment_user_center,null);
    }

    @Override
    protected void initViews() {
        super.initViews();
    }

    @Override
    protected void onLoadData() {
        super.onLoadData();
    }
}

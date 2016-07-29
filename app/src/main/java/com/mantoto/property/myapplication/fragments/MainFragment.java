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
 * At 14:47
 * My Application
 */
public class MainFragment extends BaseFragment {
    private int[] itemTags = {R.string.menu_catering,R.string.menu_express,R.string.menu_flea,
            R.string.menu_living,R.string.menu_medical,R.string.menu_message,R.string.menu_note,
            R.string.menu_notification,R.string.menu_payment,R.string.menu_repair,R.string.menu_service,R.string.menu_shopping,
            R.string.menu_traffic,R.string.menu_wifi};
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return mView = inflater.inflate(R.layout.fragment_main,null);
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

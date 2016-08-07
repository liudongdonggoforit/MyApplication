package com.mantoto.property.myapplication.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mantoto.property.myapplication.R;
import com.mantoto.property.myapplication.activities.AboutActivity;
import com.mantoto.property.myapplication.activities.AddressActivity;
import com.mantoto.property.myapplication.activities.CollectionActivity;
import com.mantoto.property.myapplication.activities.CouponActivity;
import com.mantoto.property.myapplication.activities.MyInformationActivity;
import com.mantoto.property.myapplication.activities.OrderActivity;
import com.mantoto.property.myapplication.activities.SettingActivity;
import com.mantoto.property.myapplication.activities.VoucherActivity;

/**
 * Created by Mr.liu
 * On 2016/7/20
 * At 14:50
 * My Application
 */
public class UserCenterFragment extends BaseFragment implements View.OnClickListener{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return mView = inflater.inflate(R.layout.fragment_user_center,null);
    }

    @Override
    protected void initViews() {
        super.initViews();
        mContext.findViewById(R.id.user_my_info).setOnClickListener(this);
        mContext.findViewById(R.id.user_my_collection).setOnClickListener(this);
        mContext.findViewById(R.id.user_my_order).setOnClickListener(this);
        mContext.findViewById(R.id.user_my_address_manage).setOnClickListener(this);
        mContext.findViewById(R.id.user_my_voucher).setOnClickListener(this);
        mContext.findViewById(R.id.user_my_coupon).setOnClickListener(this);
        mContext.findViewById(R.id.user_my_setting).setOnClickListener(this);
        mContext.findViewById(R.id.user_my_about).setOnClickListener(this);
    }

    @Override
    protected void onLoadData() {
        super.onLoadData();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        Toast.makeText(mContext,"123",Toast.LENGTH_SHORT).show();
        switch (v.getId()){
            case R.id.user_my_info:
                Toast.makeText(mContext,"123",Toast.LENGTH_SHORT).show();
                intent.setClass(mContext, MyInformationActivity.class);
                break;
            case R.id.user_my_collection:
                intent.setClass(mContext, CollectionActivity.class);
                break;
            case R.id.user_my_order:
                intent.setClass(mContext, OrderActivity.class);
                break;
            case R.id.user_my_address_manage:
                intent.setClass(mContext, AddressActivity.class);
                break;
            case R.id.user_my_voucher:
                intent.setClass(mContext, VoucherActivity.class);
                break;
            case R.id.user_my_coupon:
                intent.setClass(mContext, CouponActivity.class);
                break;
            case R.id.user_my_setting:
                intent.setClass(mContext, SettingActivity.class);
                break;
            case R.id.user_my_about:
                intent.setClass(mContext, AboutActivity.class);
                break;
        }
        mContext.startActivity(intent);
    }
}

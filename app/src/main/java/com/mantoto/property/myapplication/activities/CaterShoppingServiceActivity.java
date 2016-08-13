package com.mantoto.property.myapplication.activities;

import android.support.v7.widget.RecyclerView;

import com.mantoto.property.myapplication.R;
import com.mantoto.property.myapplication.adapter.CateringAdapter;
import com.mantoto.property.myapplication.views.SuperSwipeRefreshLayout;

/**
 * Created by liudongdong on 2016/8/12.
 */
public class CaterShoppingServiceActivity extends BaseActivity {
    private SuperSwipeRefreshLayout swipeRefreshLayout;
    private CateringAdapter mAdapter;
    private RecyclerView mRecyclerView;
    @Override
    protected int getContentViewResId() {
        return R.layout.activity_catering_shoping_service;
    }

    @Override
    protected int getTopBarTextRes() {
        return R.string.catering;
    }

    @Override
    protected void initViews() {
        super.initViews();
        swipeRefreshLayout = (SuperSwipeRefreshLayout) findViewById(R.id.superSwipeRefresh);
        mRecyclerView = (RecyclerView) findViewById(R.id.cateringRecyclerView);
    }

    @Override
    protected void loadDatas() {
        super.loadDatas();

    }
}

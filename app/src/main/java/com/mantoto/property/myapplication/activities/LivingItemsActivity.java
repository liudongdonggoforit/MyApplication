package com.mantoto.property.myapplication.activities;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.android.volley.VolleyError;
import com.mantoto.property.myapplication.R;
import com.mantoto.property.myapplication.adapter.LivingItemAdapter;
import com.mantoto.property.myapplication.common.Constant;
import com.mantoto.property.myapplication.common.LocalStore;
import com.mantoto.property.myapplication.model.LivingItemInfo;
import com.mantoto.property.myapplication.utils.JsonUtils;
import com.mantoto.property.myapplication.utils.LogU;
import com.mantoto.property.myapplication.utils.ToastU;
import com.mantoto.property.myapplication.views.SuperSwipeRefreshLayout;
import com.mantoto.property.myapplication.volley.IRequest;
import com.mantoto.property.myapplication.volley.RequestListener;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Mr.liu
 * On 2016/8/13
 * At 11:42
 * My ApplicationMyApplication
 */
public class LivingItemsActivity extends BaseActivity implements View.OnClickListener{
    private static final String TAG = "LivingItemsActivity";
    private SuperSwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager layoutManager;
    private LivingItemAdapter mAdapter;
    @Override
    protected int getContentViewResId() {
        return R.layout.activity_livingitems;
    }

    @Override
    protected int getTopBarTextRes() {
        return R.string.catering;
    }

    @Override
    protected void initViews() {
        super.initViews();
        swipeRefreshLayout = (SuperSwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        loadDatas();
    }

    @Override
    protected void loadDatas() {
        super.loadDatas();
        JSONObject object = new JSONObject();
        try {
            object.put("propertyid",1);
            object.put("livingcategoryid",1);
            object.put("pageindex",1);
            object.put("latitude",0);
            object.put("longitude",0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        IRequest.postJson(LivingItemsActivity.this, Constant.GET_LIVING_ITEMS, object, new RequestListener() {
            @Override
            public void requestSuccess(JSONObject json) {
                LogU.i(TAG,json.toString());
                LivingItemInfo livingItemInfo = JsonUtils.object(json.toString(),LivingItemInfo.class);
                mAdapter = new LivingItemAdapter(LivingItemsActivity.this,livingItemInfo.livingitemarray);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void requestError(VolleyError error) {
                ToastU.showShort(LivingItemsActivity.this,error.toString());
            }
        });
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}

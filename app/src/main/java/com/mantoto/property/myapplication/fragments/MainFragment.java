package com.mantoto.property.myapplication.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.android.volley.VolleyError;
import com.mantoto.property.myapplication.R;
import com.mantoto.property.myapplication.activities.LivingItemsActivity;
import com.mantoto.property.myapplication.activities.MainActivity;
import com.mantoto.property.myapplication.adapter.LivingItemAdapter;
import com.mantoto.property.myapplication.common.Constant;
import com.mantoto.property.myapplication.model.LivingItemInfo;
import com.mantoto.property.myapplication.model.Menu;
import com.mantoto.property.myapplication.model.MenuList;
import com.mantoto.property.myapplication.utils.JsonUtils;
import com.mantoto.property.myapplication.utils.LogU;
import com.mantoto.property.myapplication.utils.ToastU;
import com.mantoto.property.myapplication.volley.IRequest;
import com.mantoto.property.myapplication.volley.RequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mr.liu
 * On 2016/7/20
 * At 14:47
 * My Application
 */
public class MainFragment extends BaseFragment implements AdapterView.OnItemClickListener{
    /**每一个菜单项对应一个tag*/
    private int[] itemTags = {R.string.menu_catering,R.string.menu_express,R.string.menu_flea,
            R.string.menu_living,R.string.menu_medical,R.string.menu_message,R.string.menu_note,
            R.string.menu_notification,R.string.menu_payment,R.string.menu_repair,R.string.menu_service,R.string.menu_shopping,R.string.menu_traffic,R.string.menu_wifi};
    /**使菜单和标题对应*/
    private Map<String,String> imageRes ;
    /**获取的菜单项*/
    private List<Menu> menuList;
    /**菜单数据源*/
    private List<HashMap<String,Object>> menuData;
    // 定义跳转activity集合
    private Map<String, Object> activityRes;
    private List<Class> propertyActivityRes;
    private GridView mGridView;
    private int propertyId = 1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return mView = inflater.inflate(R.layout.fragment_main,null);
    }

    @Override
    protected void initViews() {
        super.initViews();
        propertyActivityRes = new ArrayList<Class>();
        mGridView = (GridView) mView.findViewById(R.id.main_gridView);
        mGridView.setNumColumns(4);
        mGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        mGridView.setOnItemClickListener(this);
        initImageRes();
        initActivityRes();
    }

    private void initActivityRes() {
        activityRes = new HashMap<String,Object>();
        activityRes.put(getResources().getString(R.string.menu_catering), MainActivity.class);
        activityRes.put(getResources().getString(R.string.menu_express),MainActivity.class);
        activityRes.put(getResources().getString(R.string.menu_flea),MainActivity.class);
        activityRes.put(getResources().getString(R.string.menu_living),LivingItemsActivity.class);
        activityRes.put(getResources().getString(R.string.menu_medical),MainActivity.class);
        activityRes.put(getResources().getString(R.string.menu_message),MainActivity.class);
        activityRes.put(getResources().getString(R.string.menu_note),MainActivity.class);
        activityRes.put(getResources().getString(R.string.menu_notification),MainActivity.class);
        activityRes.put(getResources().getString(R.string.menu_payment),MainActivity.class);
        activityRes.put(getResources().getString(R.string.menu_repair),MainActivity.class);
        activityRes.put(getResources().getString(R.string.menu_service),MainActivity.class);
        activityRes.put(getResources().getString(R.string.menu_shopping),MainActivity.class);
        activityRes.put(getResources().getString(R.string.menu_traffic),MainActivity.class);
        activityRes.put(getResources().getString(R.string.menu_wifi),MainActivity.class);
    }

    private void initMenuData() {
        menuData = new ArrayList<HashMap<String, Object>>();
        for(int i = 0;i < menuList.size(); i++){
            HashMap<String,Object> map = new HashMap<String,Object>();
            for (int j = 0; j < itemTags.length; j++) {
                if (getResources().getString(itemTags[j]).equals(menuList.get(i).Tag)){
                    map.put("itemImageView",imageRes.get(menuList.get(i).Tag));
                    map.put("itemTextView",menuList.get(i).MenuName);
                    propertyActivityRes.add((Class) activityRes.get(menuList.get(i).Tag));
                    menuData.add(map);
                }
            }
        }
    }

    private void initImageRes() {
        imageRes = new HashMap<String,String>();
        imageRes.put(getResources().getString(R.string.menu_catering),""+R.drawable.menu_catering);
        imageRes.put(getResources().getString(R.string.menu_express),""+R.drawable.menu_express);
        imageRes.put(getResources().getString(R.string.menu_flea),""+R.drawable.menu_flea);
        imageRes.put(getResources().getString(R.string.menu_living),""+R.drawable.menu_living);
        imageRes.put(getResources().getString(R.string.menu_medical),""+R.drawable.menu_medical);
        imageRes.put(getResources().getString(R.string.menu_message),""+R.drawable.menu_message);
        imageRes.put(getResources().getString(R.string.menu_note),""+R.drawable.menu_note);
        imageRes.put(getResources().getString(R.string.menu_notification),""+R.drawable.menu_notification);
        imageRes.put(getResources().getString(R.string.menu_payment),""+R.drawable.menu_payment);
        imageRes.put(getResources().getString(R.string.menu_repair),""+R.drawable.menu_repair);
        imageRes.put(getResources().getString(R.string.menu_service),""+R.drawable.menu_service);
        imageRes.put(getResources().getString(R.string.menu_shopping),""+R.drawable.menu_shopping);
        imageRes.put(getResources().getString(R.string.menu_traffic),""+R.drawable.menu_traffic);
        imageRes.put(getResources().getString(R.string.menu_wifi),""+R.drawable.menu_wifi);
    }

    @Override
    protected void onLoadData() {
        super.onLoadData();
        JSONObject object = new JSONObject();
        try {
            object.put("propertyid",propertyId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        IRequest.postJson(mContext, Constant.GET_MENU_LIST, object, new RequestListener() {

            @Override
            public void requestSuccess(JSONObject json) {
                Log.i("MyLog", " json = " + json.toString());
                MenuList menus = JsonUtils.object(json.toString(), MenuList.class);
                menuList = menus.menuarray;
                Log.i("MyLog", " menulist = " + menus.code);
                initMenuData();
                SimpleAdapter simpleAdapter = new SimpleAdapter(mContext, menuData, R.layout.item_main_menu, new String[]{"itemImageView", "itemTextView"}, new int[]{R.id.itemImageView, R.id.itemTextView});
                mGridView.setAdapter(simpleAdapter);
            }

            @Override
            public void requestError(VolleyError error) {

            }
        });

        JSONObject object2 = new JSONObject();
        try {
            object2.put("propertyid",propertyId);
            object2.put("livingcategoryid",1);
            object2.put("pageindex",1);
            object2.put("latitude",0);
            object2.put("longitude",0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        IRequest.postJson(mContext, Constant.GET_LIVING_ITEMS, object2, new RequestListener() {
            @Override
            public void requestSuccess(JSONObject json) {
                LogU.i("123", json.toString());

            }

            @Override
            public void requestError(VolleyError error) {
                ToastU.showShort(mContext, error.toString());
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        intent.setClass(mContext,propertyActivityRes.get(position));
        mContext.startActivity(intent);
    }
}

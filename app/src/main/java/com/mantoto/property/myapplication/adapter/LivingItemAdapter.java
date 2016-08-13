package com.mantoto.property.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mantoto.property.myapplication.R;
import com.mantoto.property.myapplication.model.LivingItem;

import java.util.List;

/**
 * Created by Mr.liu
 * On 2016/8/13
 * At 15:59
 * My ApplicationMyApplication
 */
public class LivingItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<LivingItem> list;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public LivingItemAdapter(Context mContext, List<LivingItem> list) {
        this.list = list;
        this.mContext = mContext;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContentViewHolder(mLayoutInflater.inflate(R.layout.item_living,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //内容 ViewHolder
    public static class ContentViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public ContentViewHolder(View itemView) {
            super(itemView);
        }
    }
}

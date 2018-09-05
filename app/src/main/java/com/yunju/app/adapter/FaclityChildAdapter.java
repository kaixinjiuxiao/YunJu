package com.yunju.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yunju.app.R;
import com.yunju.app.entity.FacilityData;

import java.util.List;

/**
 * Author : Captain
 * Time : 2018/5/9
 * Describe :
 */

public class FaclityChildAdapter extends RecyclerView.Adapter<FaclityChildAdapter.ChildViewHolder> {
    private Context mContext;
    private List<FacilityData.DateBean> mBeanList;

    public FaclityChildAdapter(Context context, List<FacilityData.DateBean> beanList) {
        mContext = context;
        mBeanList = beanList;
    }

    @Override
    public ChildViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_faclity_child,parent,false);
        return new ChildViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChildViewHolder holder, int position) {
        holder.txt.setText(mBeanList.get(position).getName());
        Glide.with(mContext).load(mBeanList.get(position).getIconurl()).into(holder.img);
      }

    @Override
    public int getItemCount() {
        return mBeanList.size();
    }

    public class ChildViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView txt;
        public ChildViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            txt = itemView.findViewById(R.id.txt1);
        }
    }
}

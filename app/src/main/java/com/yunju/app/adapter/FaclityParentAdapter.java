package com.yunju.app.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yunju.app.R;
import com.yunju.app.entity.FacilityData;

import java.util.List;

/**
 * Author : Captain
 * Time : 2018/5/9
 * Describe :
 */

public class FaclityParentAdapter extends RecyclerView.Adapter<FaclityParentAdapter.MyViewHolder> {
    private Context mContext;
    private List<FacilityData> mStringList;

    public FaclityParentAdapter(Context context, List<FacilityData> stringList) {
        mContext = context;
        mStringList = stringList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_faclity_paent,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.txt.setText(mStringList.get(position).getStr());
        FaclityChildAdapter adapter = new FaclityChildAdapter(mContext,mStringList.get(position).getList());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext,4);
        holder.mRecyclerView.setLayoutManager(gridLayoutManager);
        holder.mRecyclerView.setAdapter(adapter);

    }

    @Override
    public int getItemCount() {
        return mStringList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView txt;
        RecyclerView mRecyclerView;
        public MyViewHolder(View itemView) {
            super(itemView);
            txt = itemView.findViewById(R.id.txt);
            mRecyclerView = itemView.findViewById(R.id.childRecycler);
        }
    }
}

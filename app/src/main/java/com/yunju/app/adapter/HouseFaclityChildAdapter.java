package com.yunju.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunju.app.R;
import com.yunju.app.entity.HouseFaclityData;

import java.util.List;

/**
 * Author : Captain
 * Time : 2018/5/9
 * Describe :
 */

public class HouseFaclityChildAdapter extends RecyclerView.Adapter<HouseFaclityChildAdapter.ChildViewHolder> {
    private Context mContext;
    private List<HouseFaclityData.DataBean> mBeanList;


    public HouseFaclityChildAdapter(Context context, List<HouseFaclityData.DataBean> beanList) {
        mContext = context;
        mBeanList = beanList;
    }

    @Override
    public ChildViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_edit_faclity_child, parent, false);
        return new ChildViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ChildViewHolder holder, final int position) {
        holder.txt.setText(mBeanList.get(position).getName());
        holder.img.setImageResource(mBeanList.get(position).getIconRes());
        if (mBeanList.get(position).isSelected()) {
            holder.txt.setSelected(true);
            holder.img.setSelected(true);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (holder.img.isSelected()) {
                    holder.img.setSelected(false);
                    holder.txt.setSelected(false);
                } else {
                    holder.img.setSelected(true);
                    holder.txt.setSelected(true);
                }
                mBeanList.get(position).setSelected(holder.img.isSelected());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBeanList.size();
    }

    public class ChildViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView txt;

        public ChildViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            txt = itemView.findViewById(R.id.txt1);
        }
    }

}

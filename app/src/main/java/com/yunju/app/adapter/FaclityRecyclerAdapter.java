package com.yunju.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yunju.app.R;
import com.yunju.app.entity.HouseDetails;
import com.yunju.app.interfac.OnRecyclerViewItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sm on 2018/3/26 0026.
 * 设施服务
 */
public class FaclityRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private LayoutInflater inflater;
    private List<HouseDetails.FacilitiesLimitBean> mList = new ArrayList<>();

    public FaclityRecyclerAdapter(Context context, List<HouseDetails.FacilitiesLimitBean> traceList) {
        inflater = LayoutInflater.from(context);
        this.mContext = context;
        this.mList = traceList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_house_facility, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
      final   ViewHolder itemHolder = (ViewHolder) holder;
        if(!TextUtils.isEmpty(mList.get(position).getIconurl())){
            Glide.with(mContext).load(mList.get(position).getIconurl()).into(itemHolder.imgFaclity);
            itemHolder.txtFaclity.setText(mList.get(position).getName());
        }else{
            itemHolder.imgFaclity.setVisibility(View.GONE);
            itemHolder.txtTotal.setVisibility(View.VISIBLE);
            itemHolder.txtTotal.setText(mList.get(position).getName().substring(2,mList.get(position).getName().length())+"+");
            itemHolder.txtFaclity.setText(mList.get(position).getName().substring(0,2));
        }
        itemHolder.linearHouseFac.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (mOnRecyclerViewItemClickListener != null) {
                   int layoutPosition = itemHolder.getLayoutPosition();
                   if(layoutPosition==3){
                       mOnRecyclerViewItemClickListener.onRecyclerViewItemClick(v, layoutPosition);
                   }
               }
           }
       });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.faclityType)
        TextView faclityType;
        @BindView(R.id.imgFaclity)
        ImageView imgFaclity;
        @BindView(R.id.txtFaclity)
        TextView txtFaclity;
        @BindView(R.id.txtTotal)
        TextView txtTotal;
        @BindView(R.id.linearHouseFac)
        LinearLayout linearHouseFac;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    private OnRecyclerViewItemClickListener mOnRecyclerViewItemClickListener;

    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        mOnRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }
}

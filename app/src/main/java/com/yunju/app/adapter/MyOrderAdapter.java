package com.yunju.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yunju.app.R;
import com.yunju.app.entity.OrderListResponse;
import com.yunju.app.interfac.OnRecyclerViewItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: captain
 * Time:  2018/5/17 0017
 * Describe:
 */
public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.MyOrderHolder> {
    private Context context;
    private List<OrderListResponse.DataBean> list;

    public MyOrderAdapter(Context context, List<OrderListResponse.DataBean> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public MyOrderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rv_order_list,parent,false);
        return new MyOrderHolder(view);
    }

    @Override
    public void onBindViewHolder(MyOrderHolder holder, final int position) {
        holder.tvSource.setText(list.get(position).getHname());
        holder.tvStatus.setText(list.get(position).getTrade_remark());
        holder.tvDate.setText(list.get(position).getCheck_in_time()+"~"+list.get(position).getCheck_out_time());
        Glide.with(context).load(list.get(position).getHimg()).error(R.drawable.pic_main).into(holder.ivPicture);
        if(list.get(position).getTrade_remark().equals("待支付")){
            holder.btnToComment.setText("去支付");
        }else{
            holder.btnToComment.setVisibility(View.GONE);
        }
        holder.ivPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnRecyclerViewItemClickListener.onRecyclerViewItemClick(v,position);
            }
        });
        holder.btnToComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnRePayListener.onReyPay(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyOrderHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_order_source)
        TextView tvSource;
        @BindView(R.id.item_order_status)
        TextView tvStatus;
        @BindView(R.id.item_order_date)
        TextView tvDate;
        @BindView(R.id.item_order_picture)
        ImageView ivPicture;
        @BindView(R.id.item_order_toComment)
        Button btnToComment;

        public MyOrderHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    private OnRecyclerViewItemClickListener mOnRecyclerViewItemClickListener;

    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        mOnRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }

    private OnRePayListener mOnRePayListener;

    public OnRePayListener getOnRePayListener() {
        return mOnRePayListener;
    }

    public void setOnRePayListener(OnRePayListener onRePayListener) {
        mOnRePayListener = onRePayListener;
    }

    public interface OnRePayListener{
        void onReyPay(int position);
    }
}

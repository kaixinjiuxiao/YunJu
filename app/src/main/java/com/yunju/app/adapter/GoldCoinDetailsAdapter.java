package com.yunju.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yunju.app.R;
import com.yunju.app.entity.GoldCoinData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GoldCoinDetailsAdapter extends RecyclerView.Adapter<GoldCoinDetailsAdapter.IntegralViewHolder> {


    private Context mContext;
    private List<GoldCoinData.MsgBean> mList;


    public GoldCoinDetailsAdapter(Context context, List<GoldCoinData.MsgBean> list) {
        mContext = context;
        mList = list;
    }


    @Override
    public IntegralViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recyclerview_balance, parent, false);
        return new IntegralViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IntegralViewHolder holder, int position) {
        holder.mItemBalanceDate.setText(mList.get(position).getRemark());
        holder.mItemBalanceContent.setText(mList.get(position).getAddtime());
        holder.mItemBalanceMoney.setText(mList.get(position).getMoney());
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class IntegralViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_balance_date)
        TextView mItemBalanceDate;
        @BindView(R.id.item_balance_content)
        TextView mItemBalanceContent;
        @BindView(R.id.item_balance_money)
        TextView mItemBalanceMoney;

        public IntegralViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}

package com.yunju.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yunju.app.R;
import com.yunju.app.entity.Bill;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GoldcoinRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Bill> list;

    public GoldcoinRecyclerAdapter(Context context, List<Bill> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recyclerview_goldcoin, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder viewHolder = (ItemViewHolder) holder;
        Bill bill = list.get(position);
        viewHolder.tvTitle.setText(bill.getTitle());
        viewHolder.tvDate.setText(bill.getDate());
        viewHolder.tvMoney.setText("" + bill.getMoney());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_goldcoin_title)
        TextView tvTitle;
        @BindView(R.id.item_goldcoin_date)
        TextView tvDate;
        @BindView(R.id.item_goldcoin_money)
        TextView tvMoney;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

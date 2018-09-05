package com.yunju.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yunju.app.R;
import com.yunju.app.entity.Order;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Order> list;

    public OrderRecyclerAdapter(Context context, List<Order> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recyclerview_order, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder viewHolder = (ItemViewHolder) holder;
        Order order = list.get(position);
        viewHolder.tvSource.setText(order.getSource());
        switch (order.getStatus()) {
            case 1:
                viewHolder.tvStatus.setText("待确认");
                break;
            case 2:
                viewHolder.tvStatus.setText("待入住");
                break;
            case 3:
                viewHolder.tvStatus.setText("已入住");
                break;
            case 4:
                viewHolder.tvStatus.setText("已离店");
                break;
            case 5:
                viewHolder.tvStatus.setText("已取消");
                break;
        }
        viewHolder.tvDate.setText(order.getDate());
        viewHolder.tvName.setText(order.getName()+"\u3000\u3000"+order.getPersonNum()+"人 "+ order.getStayNum()+"晚");
        viewHolder.tvTitle.setText(order.getTenement().getTitle());

        String str="套数：" + order.getTenement().getNum() + "\u3000\u3000总房费：￥" + order.getTenement().getPrice();
        int start1 = str.indexOf("：")+1;
        int end1 = start1+order.getTenement().getNum()+"".length();
        int start2 = str.lastIndexOf("：")+1;
        int end2 = str.length();
        SpannableStringBuilder spanBuilder = new SpannableStringBuilder(str);
        spanBuilder.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.colorPrimary)), start1,end1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        spanBuilder.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.colorPrimary)), start2,end2, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        viewHolder.tvDesrible.setText(spanBuilder);
        Glide.with(context).load(order.getTenement().getPicture()).error(R.drawable.pic_main).into(viewHolder.ivPicture);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_order_source)
        TextView tvSource;
        @BindView(R.id.item_order_status)
        TextView tvStatus;
        @BindView(R.id.item_order_date)
        TextView tvDate;
        @BindView(R.id.item_order_name)
        TextView tvName;
        @BindView(R.id.item_order_picture)
        ImageView ivPicture;
        @BindView(R.id.item_order_title)
        TextView tvTitle;
        @BindView(R.id.item_order_desrible)
        TextView tvDesrible;
        @BindView(R.id.item_order_toComment)
        Button btnToComment;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

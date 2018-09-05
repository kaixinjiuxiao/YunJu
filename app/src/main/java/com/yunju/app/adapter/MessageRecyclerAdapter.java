package com.yunju.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yunju.app.R;
import com.yunju.app.entity.Message;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessageRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Message> list;

    public MessageRecyclerAdapter(Context context, List<Message> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recyclerview_message, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder viewHolder = (ItemViewHolder) holder;
        Message message = list.get(position);
        viewHolder.tvTitle.setText(message.getTitle());
        viewHolder.tvDate.setText(message.getDate());
        viewHolder.tvContent.setText(message.getContent());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_message_title)
        TextView tvTitle;
        @BindView(R.id.item_message_date)
        TextView tvDate;
        @BindView(R.id.item_message_content)
        TextView tvContent;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

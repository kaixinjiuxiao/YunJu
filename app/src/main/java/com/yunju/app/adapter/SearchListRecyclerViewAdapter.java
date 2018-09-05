package com.yunju.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yunju.app.R;
import com.yunju.app.entity.HouseSearchResponse;
import com.yunju.app.interfac.OnRecyclerViewItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/2/28 0028.
 */

public class SearchListRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<HouseSearchResponse.DataBean> list;
    private Context context;

    public SearchListRecyclerViewAdapter(Context context, List<HouseSearchResponse.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_searchlist, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        HouseSearchResponse.DataBean tenement = list.get(position);
        final ItemViewHolder itemHolder = (ItemViewHolder) holder;
        Glide.with(context).load(tenement.getImg_logo()).error(R.drawable.pic_mine).into(itemHolder.ivPicture);
        itemHolder.tvTitle.setText(tenement.getHname());
        itemHolder.tvPrice.setText("¥"+tenement.getMoney());
        itemHolder.tvDescrible.setText(tenement.getLiving_room()+"居/"+tenement.getBednum()+"床/宜住"+tenement.getPeople_number()+"人."+tenement.getAddress());
        itemHolder.ivPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnRecyclerViewItemClickListener != null) {
                    int layoutPosition = itemHolder.getLayoutPosition();
                    mOnRecyclerViewItemClickListener.onRecyclerViewItemClick(v, layoutPosition);
                }
            }
        });
      //  itemHolder.cbCollect.setSelected(true);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_recycleview_searchlist_picture)
        ImageView ivPicture;
        @BindView(R.id.item_recycleview_searchlist_price)
        TextView tvPrice;
        @BindView(R.id.item_recycleview_searchlist_collectCbx)
        CheckBox cbCollect;
        @BindView(R.id.item_recycleview_searchlist_title)
        TextView tvTitle;
        @BindView(R.id.item_recycleview_searchlist_describle)
        TextView tvDescrible;


        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private OnRecyclerViewItemClickListener mOnRecyclerViewItemClickListener;

    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        mOnRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }
}

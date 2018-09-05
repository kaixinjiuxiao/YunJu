package com.yunju.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yunju.app.R;
import com.yunju.app.entity.HouseDetails;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: captain
 * Time:  2018/6/13 0013
 * Describe:
 */
public class HouseImageAdapter extends RecyclerView.Adapter<HouseImageAdapter.HouseViewHolder> {


    private Context mContext;
    private List<HouseDetails.ImgBean> mList;
    private OnItemClickListener mOnItemClickListener;

    public OnItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public HouseImageAdapter(Context context, List<HouseDetails.ImgBean> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public HouseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recyclerview_himg, parent, false);
        return new HouseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HouseViewHolder holder, int position) {
        Glide.with(mContext).load(mList.get(position).getImgurl()).into(holder.mImgHouse);
        holder.mImgHouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onImemClick();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class HouseViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgHouse)
        ImageView mImgHouse;
        public HouseViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface OnItemClickListener{
        void onImemClick();
    }
}

package com.yunju.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yunju.app.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: captain
 * Time:  2018/7/1 0001
 * Describe:
 */
public class HouseBedAdapter extends RecyclerView.Adapter<HouseBedAdapter.HouseBedViewHolder> {

    private Context mContext;
    private List<String> mList;
    private int selected = -1;
    int mCurrentPosition;

    private TestHouseBedAdapter.OnItemClickListener mOnItemClickListener;


    public TestHouseBedAdapter.OnItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }

    public void setOnItemClickListener(TestHouseBedAdapter.OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public HouseBedAdapter(Context context, List<String> list) {
        mContext = context;
        mList = list;

    }


    @Override
    public HouseBedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_house_bed, parent, false);
        return new HouseBedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HouseBedViewHolder holder, final int position) {
        holder.mContent.setText(mList.get(position));

        if(selected == position){
            holder.mContent.setSelected(true);
            holder.itemView.setSelected(true);
        } else {
            holder.mContent.setSelected(false);
            holder.itemView.setSelected(false);
        }
        if (mOnItemClickListener != null)
        {
            holder.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    mOnItemClickListener.onItemClick(holder.itemView, holder.getAdapterPosition());
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setSelection(int position){
        this.selected = position;
        notifyDataSetChanged();
    }

    public class HouseBedViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.content)
        TextView mContent;
        @BindView(R.id.chooice)
        ImageView mChooice;
        @BindView(R.id.houseBed)
        RelativeLayout mHouseBed;

        public HouseBedViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public interface OnItemClickListener {
        void onItemClick(View view,int position);
    }
}

package com.yunju.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunju.app.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: captain
 * Time:  2018/6/28 0004
 * Describe:
 */
public class ReceptionTimeAdapter extends RecyclerView.Adapter<ReceptionTimeAdapter.ReceptionTimeViewHolder> {

    private Context mContext;
    private List<String> mList;



    public OnDeleteReceptionTimeListener mOnDeleteReceptionTimeListener;

    public OnDeleteReceptionTimeListener getOnDeleteReceptionTimeListener() {
        return mOnDeleteReceptionTimeListener;
    }

    public void setOnDeleteReceptionTimeListener(OnDeleteReceptionTimeListener onDeleteReceptionTimeListener) {
        mOnDeleteReceptionTimeListener = onDeleteReceptionTimeListener;
    }

    public interface OnDeleteReceptionTimeListener {
        void deleteReceptionTime(int position);
    }


    public ReceptionTimeAdapter(Context context, List<String> list) {
        mContext = context;
        mList = list;
    }





    @Override
    public ReceptionTimeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_reception_time, parent, false);
        return new ReceptionTimeViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final ReceptionTimeViewHolder holder, final int position) {

        holder.mReceptionTime.setText(mList.get(position));
        holder.mImgChooice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnDeleteReceptionTimeListener.deleteReceptionTime(position);
            }
        });


    }



    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ReceptionTimeViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgClose)
        ImageView mImgChooice;
        @BindView(R.id.receptionTime)
        TextView mReceptionTime;

        public ReceptionTimeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }
}

package com.yunju.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunju.app.R;
import com.yunju.app.entity.EnterMan;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: captain
 * Time:  2017/12/4 0004
 * Describe:
 */
public class EnterManAdapter extends RecyclerView.Adapter<EnterManAdapter.EnderManViewHolder> {

    private Context mContext;
    private List<EnterMan> mList;
    private HashMap<Integer, Boolean> mMap;
    int mCurrentPosition;


    public OnEditAddresListener mOnEditListener;


    public interface OnEditAddresListener {
        void editAddress(int position);
    }


    public EnterManAdapter(Context context, List<EnterMan> list, HashMap<Integer, Boolean> map) {
        mContext = context;
        mList = list;
        mMap = map;
    }


    public OnEditAddresListener getOnEditListener() {
        return mOnEditListener;
    }

    public void setOnEditListener(OnEditAddresListener onEditListener) {
        mOnEditListener = onEditListener;
    }


    @Override
    public EnderManViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_enter_man, parent, false);
        return new EnderManViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final EnderManViewHolder holder, final int position) {

        holder.mEnterName.setText(mList.get(position).getName());
        holder.mEnterPhone.setText(mList.get(position).getPhone());
        holder.mEnterCard.setText(mList.get(position).getAddress());
        holder.mImgChooice.setSelected(mMap.get(position));

        holder.mImgChooice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.put(position, !mMap.get(position));
                for (int i = 0; i < mList.size(); i++) {
                    mList.get(i).setSelected(false);
                }
                mList.get(position).setSelected(true);
                //刷新适配器
                notifyDataSetChanged();
                //单选
                singlesel(position);
            }
        });
        holder.mImgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnEditListener.editAddress(position);
            }
        });

    }

    /**
     * 单选
     *
     * @param postion
     */
    public void singlesel(int postion) {
        mCurrentPosition = postion;
        Set<Map.Entry<Integer, Boolean>> entries = mMap.entrySet();
        for (Map.Entry<Integer, Boolean> entry : entries) {
            entry.setValue(false);
        }
        mMap.put(postion, true);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class EnderManViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgChooice)
        ImageView mImgChooice;
        @BindView(R.id.enter_name)
        TextView mEnterName;
        @BindView(R.id.enter_phone)
        TextView mEnterPhone;
        @BindView(R.id.enter_card)
        TextView mEnterCard;
        @BindView(R.id.imgEdit)
        ImageView mImgEdit;

        public EnderManViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }
}

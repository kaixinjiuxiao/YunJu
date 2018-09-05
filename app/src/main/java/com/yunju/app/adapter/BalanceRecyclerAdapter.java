package com.yunju.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yunju.app.R;
import com.yunju.app.entity.BanlanceData;

import java.util.List;

public class BalanceRecyclerAdapter extends BaseAdapter {


    private Context mContext;
    private List<BanlanceData.DataBean> mList;


    public BalanceRecyclerAdapter(Context context, List<BanlanceData.DataBean> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_recyclerview_balance,parent,false);
            holder.mItemBalanceDate= (TextView) convertView.findViewById(R.id.item_balance_date);
            holder.mItemBalanceWeek= (TextView) convertView.findViewById(R.id.item_balance_week);
            holder.mItemBalanceContent= (TextView) convertView.findViewById(R.id.item_balance_content);
            holder.mItemBalanceMoney= (TextView) convertView.findViewById(R.id.item_balance_money);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        holder.mItemBalanceDate.setText(mList.get(position).getAddtime());
        holder.mItemBalanceWeek.setText(mList.get(position).getWeek());
        holder.mItemBalanceContent.setText(mList.get(position).getRemark());
        holder.mItemBalanceMoney.setText(mList.get(position).getMoney());
        return convertView;
    }


  public static class ViewHolder {
        TextView mItemBalanceDate;
        TextView mItemBalanceWeek;
        TextView mItemBalanceContent;
        TextView mItemBalanceMoney;

    }

}

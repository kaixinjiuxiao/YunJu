package com.yunju.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yunju.app.R;
import com.yunju.app.entity.BankBranchData;

import java.util.List;


/**
 * Created by Administrator on 2017/7/12.
 */

public class BankBranchAdapter extends BaseAdapter {
    private Context mContext;
    private List<BankBranchData.DataBean> mList;

    public BankBranchAdapter(Context mContext, List<BankBranchData.DataBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_spinner,parent,false);
            holder.name= (TextView) convertView.findViewById(R.id.txt);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        holder.name.setText(mList.get(position).getText());
        return convertView;
    }

    static class ViewHolder{
        TextView name;
    }
}

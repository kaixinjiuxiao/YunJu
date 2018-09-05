package com.yunju.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunju.app.R;
import com.yunju.app.entity.EditBed;

import java.util.List;

/**
 * @author: captain
 * Time:  2018/7/1 0001
 * Describe:
 */
public class BedDatailsAdapter extends BaseAdapter {
    private Context mContext;
    private List<EditBed> mList;

    public BedDatailsAdapter(Context context, List<EditBed> list) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_house_bed,parent,false);
            holder.name= (TextView) convertView.findViewById(R.id.content);
            holder.chooice=(ImageView) convertView.findViewById(R.id.chooice);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        if(mList.get(position).isChooice()){
            holder.name.setSelected(true);
            holder.chooice.setVisibility(View.VISIBLE);
        }
        holder.name.setText(mList.get(position).getName());
        return convertView;
    }

    static class ViewHolder{
        TextView name;
        ImageView chooice;
    }
}

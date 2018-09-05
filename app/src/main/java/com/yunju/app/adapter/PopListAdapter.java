package com.yunju.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunju.app.R;
import com.yunju.app.entity.FilterData;

import java.util.List;


public class PopListAdapter extends BaseAdapter {
    private List<FilterData> list;
    private Context context;
    private int selectedPos = 0;

    public PopListAdapter(Context context, List<FilterData> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_lv_popwindow_list, parent, false);
            holder = new ViewHolder();
            holder.tvItemText = convertView.findViewById(R.id.item_lv_popwindow_list_itemText);
            holder.ivCheck = convertView.findViewById(R.id.item_lv_popwindow_list_ivCheck);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        FilterData data = (FilterData) getItem(position);
        holder.tvItemText.setText(data.getItemText());
        holder.tvItemText.setTextColor(selectedPos == position ? context.getResources().getColor(R.color.colorPrimary) : 0xFF333333);
        holder.ivCheck.setVisibility(selectedPos==position?View.VISIBLE:View.GONE);

        return convertView;
    }

    //选中的position,及时更新数据
    public void setSelectedPos(int selectedPos) {
        this.selectedPos = selectedPos;
        notifyDataSetChanged();
    }

    public void setData(List<FilterData> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    class ViewHolder {
        private TextView tvItemText;
        private ImageView ivCheck;
    }
}

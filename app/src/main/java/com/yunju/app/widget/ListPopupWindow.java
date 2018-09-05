package com.yunju.app.widget;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yunju.app.R;
import com.yunju.app.adapter.PopListAdapter;
import com.yunju.app.entity.FilterData;

import java.util.List;

public class ListPopupWindow extends PopupWindow {
    private Window window;
    private List<FilterData> list;
    private PopListAdapter listAdapter;

    public ListPopupWindow(Context context, Window win, String title, final List<FilterData> dataList) {
        super(context);
        this.window = win;
        this.list = dataList;
        View view = LayoutInflater.from(context).inflate(R.layout.layout_popwindow_list, null);
        ImageView ivClose = view.findViewById(R.id.popwindow_list_close);
        TextView tvTitle = view.findViewById(R.id.popwindow_list_title);
        ListView listView = view.findViewById(R.id.popwindow_list_listview);
        tvTitle.setText(title);
        listAdapter = new PopListAdapter(context, list);
        listView.setAdapter(listAdapter);
        setContentView(view);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setAnimationStyle(R.style.popwindowAnimation);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listAdapter.setSelectedPos(position);
                if (onMenuCheckedListener != null) {
                    onMenuCheckedListener.onCheck(list.get(position));
                }
                dismiss();
            }
        });
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = window.getAttributes();
                lp.alpha = 1.0f;
                window.setAttributes(lp);
            }
        });
    }

    public void setDefaultItem(FilterData defData) {
        if(defData==null){
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            if (defData.getId() == list.get(i).getId()) {
                listAdapter.setSelectedPos(i);
            }
        }
    }

    public void show() {
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = 0.4f;
        window.setAttributes(lp);
        showAtLocation(window.getDecorView(), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    private OnMenuCheckedListener onMenuCheckedListener;

    public interface OnMenuCheckedListener {
        void onCheck(FilterData menuData);
    }

    public void setOnMenuCheckedListener(OnMenuCheckedListener onMenuCheckedListener) {
        this.onMenuCheckedListener = onMenuCheckedListener;
    }


}

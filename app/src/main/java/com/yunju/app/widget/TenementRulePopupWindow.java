package com.yunju.app.widget;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yunju.app.R;

import java.util.List;

/**
 * @author: captain
 * Time:  2018/6/27 0027
 * Describe:
 */
public class TenementRulePopupWindow extends PopupWindow {
    private Window window;
    private List<String> list;
    private String mChooice;
    public TenementRulePopupWindow(Context context, Window win, String title, final List<String> dataList) {
        super(context);
        this.window = win;
        this.list = dataList;
        View view = LayoutInflater.from(context).inflate(R.layout.tenement_rule_house, null);
        TextView cancel =view.findViewById(R.id.cancelAction);
        TextView titleView =view.findViewById(R.id.title);
        titleView.setText(title);
        TextView sure =view.findViewById(R.id.sureAction);
        WheelView wheelView = view.findViewById(R.id.wheelView);
        wheelView.setOffset(1);
        wheelView.setItems(dataList);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getChooiceDate();
                dismiss();
            }
        });
        wheelView.setOnWheelViewListener(new WheelView.OnWheelViewListener(){
            @Override
            public void onSelected(int selectedIndex, String item) {
                super.onSelected(selectedIndex, item);
                mChooice = item;
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


    public void show() {
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = 0.4f;
        window.setAttributes(lp);
        showAtLocation(window.getDecorView(), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    public String getChooiceDate(){
            return mChooice;
    }

}

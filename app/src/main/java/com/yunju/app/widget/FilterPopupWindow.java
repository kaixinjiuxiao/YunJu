package com.yunju.app.widget;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yunju.app.R;
import com.yunju.app.entity.FilterData;
import com.yunju.app.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class FilterPopupWindow extends PopupWindow {
    private Window window;
    private AmountView amountView;
    private List<String> pList;

    public FilterPopupWindow(final Context context, Window win) {
        super(context);
        this.window = win;
        View view = LayoutInflater.from(context).inflate(R.layout.layout_popwindow_filter, null);
        ImageView ivClose = view.findViewById(R.id.popwindow_filter_close);
        TextView tvClear = view.findViewById(R.id.popwindow_filter_clear);
        amountView = view.findViewById(R.id.popwindow_filter_amountview);
        Button btnSubmit=view.findViewById(R.id.popwindow_filter_submit);
        ATDragView dragView = view.findViewById(R.id.popwindow_filter_dragview);
        final List<String> data = new ArrayList<>();
        data.add("￥0");
        data.add("￥200");
        data.add("￥300");
        data.add("￥400");
        data.add("￥500");
        data.add("￥800");
        data.add("不限");
        dragView.setData(data, new ATDragView.OnDragFinishedListener() {
            @Override
            public void dragFinished(int leftPostion, int rightPostion) {
                ToastUtil.showShort(data.get(leftPostion) + "~" + data.get(rightPostion));
            }
        });

        pList = new ArrayList<>();
        pList.add("不限");
        pList.add("1人");
        pList.add("2人");
        pList.add("3人");
        pList.add("4人");
        pList.add("5人");
        pList.add("6人");
        pList.add("7人");
        pList.add("8人");
        pList.add("9人");
        pList.add("10人+");
        amountView.setDataList(pList);
        amountView.setOnAmountChangeListener(new AmountView.OnAmountChangeListener() {
            @Override
            public void onAmountChange(View view, int amount) {
                ToastUtil.showShort(pList.get(amount));
            }
        });

        setContentView(view);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setAnimationStyle(R.style.popwindowAnimation);
        ivClose.setOnClickListener(new MyOnClickListener());
        tvClear.setOnClickListener(new MyOnClickListener());
        btnSubmit.setOnClickListener(new MyOnClickListener());

        setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = window.getAttributes();
                lp.alpha = 1.0f;
                window.setAttributes(lp);
            }
        });
    }


    public void setDefaultItem(FilterData defData) {
        if (defData == null) {
            return;
        }
    }

    private class MyOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.popwindow_filter_close:
                    dismiss();
                    break;
                case R.id.popwindow_filter_clear:
                    break;
                case R.id.popwindow_filter_submit:
                    if(onSubmitListener!=null){
//                        onSubmitListener.onSubmit();
                    }
                    dismiss();
                    break;
            }
        }
    }

    public void show() {
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = 0.4f;
        window.setAttributes(lp);
        showAtLocation(window.getDecorView(), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    private OnSubmitListener onSubmitListener;

    public interface OnSubmitListener {
        void onSubmit(FilterData menuData);
    }

    public void setOnSubmitListener(OnSubmitListener onSubmitListener) {
        this.onSubmitListener = onSubmitListener;
    }
}

package com.yunju.app.widget;

import android.content.Context;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yunju.app.R;
import com.yunju.app.adapter.FaclityParentAdapter;
import com.yunju.app.entity.FacilityData;

import java.util.List;

/**
 * @author: captain
 * Time:  2018/5/10 0010
 * Describe:
 */
public class RecyclerPopupWindow extends PopupWindow {
    private Window window;
    public RecyclerPopupWindow (Context context,Window win,String title, List<FacilityData>date){
        super(context);
        this.window =win;
        View view = LayoutInflater.from(context).inflate(R.layout.rv_popup_list,null);
        TextView txt = view.findViewById(R.id.txtTitle);
        txt.setText(title);
        RecyclerView recyclerView = view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new DividerItemDecoration(context,DividerItemDecoration.VERTICAL));
        FaclityParentAdapter adapter = new FaclityParentAdapter(context,date);
        recyclerView.setAdapter(adapter);
        ImageView imageView = view.findViewById(R.id.imgCancel);
        setContentView(view);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setAnimationStyle(R.style.popwindowAnimation);
        imageView.setOnClickListener(new View.OnClickListener() {
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

    public void show() {
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = 0.4f;
        window.setAttributes(lp);
        showAtLocation(window.getDecorView(), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }
}

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
import com.yunju.app.adapter.MenuDialogAdapter;
import com.yunju.app.entity.FilterData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sm on 2018/3/6 0006.
 */

public class ThreeMenuListPopupWindow extends PopupWindow {
    private Context context;
    private Window window;
    private ListView listView1, listView2, listView3;
    private TextView tvTitle;
    private MenuDialogAdapter mListView1Adapter, mListView2Adapter, mListView3Adapter;
    private List<FilterData> listAll;

    public ThreeMenuListPopupWindow(Context contxt, Window win, List<FilterData> menuDataList) {
        super(contxt);
        this.context = contxt;
        this.window = win;
        this.listAll=menuDataList;
        View view = LayoutInflater.from(context).inflate(R.layout.layout_popwindow_location, null);
        ImageView ivClose = view.findViewById(R.id.popwindow_location_close);
        tvTitle=view.findViewById(R.id.popwindow_location_title);

        listView1 = view.findViewById(R.id.viewpager_number_listview1);
        listView2 = view.findViewById(R.id.viewpager_number_listview2);
        listView3 = view.findViewById(R.id.viewpager_number_listview3);

        List<FilterData> list1 = getDataList(-1);
        mListView1Adapter = new MenuDialogAdapter(context, list1);
        mListView1Adapter.setSelectedBackgroundResource(R.color.white);//选中时
//        mListView1Adapter.setSelectedBackgroundResource(R.drawable.select_white);//选中时
        mListView1Adapter.setHasDivider(false);
        mListView1Adapter.setNormalBackgroundResource(R.color.menudialog_bg_gray);//未选中
        listView1.setAdapter(mListView1Adapter);

        List<FilterData> list2 = getDataList(list1.get(0).getId());
        if (!list2.isEmpty()) {
            mListView2Adapter = new MenuDialogAdapter(context, list2);
            mListView2Adapter.setNormalBackgroundResource(R.color.white);
            mListView2Adapter.setSelectedPos(-1);
            listView2.setAdapter(mListView2Adapter);
        }

        List<FilterData> list3 = getDataList(list2.get(0).getId());
        if (!list3.isEmpty()) {
            mListView3Adapter = new MenuDialogAdapter(context, list3);
            mListView3Adapter.setNormalBackgroundResource(R.color.white);
            listView3.setAdapter(mListView3Adapter);
        }

//        itemViews.add(itemView1);
//        itemViews.add(itemView2);
//        itemViews.add(itemView3);
//        viewPager.setAdapter(new MyPagerAdapter(itemViews));

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mListView1Adapter != null)
                    mListView1Adapter.setSelectedPos(position);
                if (mListView2Adapter != null)
                    mListView2Adapter.setSelectedPos(-1);
                if (mListView3Adapter != null) {
//                    mListView3Adapter.setSelectedPos(-1);
                    //清空三级列表
                    mListView3Adapter.setData(new ArrayList<FilterData>());
                }

//                if (itemViews.contains(itemView3)) {
//                    itemViews.remove(itemView3);
//                    viewPager.getAdapter().notifyDataSetChanged();
//                }
                FilterData menuData = (FilterData) parent.getItemAtPosition(position);
                if (menuData.getId() == 0) {//不限
                    if (mListView2Adapter != null) {
                        mListView2Adapter.setData(new ArrayList<FilterData>());
                        mListView2Adapter.notifyDataSetChanged();
                    }

                    setResultDate(menuData);
                } else {
                    List<FilterData> list2 = getDataList(menuData.getId());
                    if (mListView2Adapter == null) {
                        mListView2Adapter = new MenuDialogAdapter(context, list2);
                        mListView2Adapter.setNormalBackgroundResource(R.color.white);
                        listView2.setAdapter(mListView2Adapter);
                    } else {
                        mListView2Adapter.setData(list2);
                        mListView2Adapter.notifyDataSetChanged();
                    }
//                    mRootView.invalidate();
                }

            }
        });

        //二级
        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                if (mListView2Adapter != null) {
                    mListView2Adapter.setSelectedPos(position);
//                    mListView2Adapter.setSelectedBackgroundResource(R.drawable.select_gray);//选中时
                }
                if (mListView3Adapter != null)
                    mListView3Adapter.setSelectedPos(-1);
//
//                if (itemViews.contains(itemView3)) {
//                    itemViews.remove(itemView3);
//                }

                FilterData fData = (FilterData) parent.getItemAtPosition(position);
                List<FilterData> list3 = getDataList(fData.getId());
                if (list3.isEmpty()) {
                    //无三级列表
                    setResultDate(fData);
                }
                if (mListView3Adapter == null) {
                    mListView3Adapter = new MenuDialogAdapter(context, list3);
                    mListView3Adapter.setHasDivider(true);
                    mListView3Adapter.setNormalBackgroundResource(R.color.menudialog_bg_gray);//未选中
                    listView3.setAdapter(mListView3Adapter);
                } else {
                    mListView3Adapter.setData(list3);
                    mListView3Adapter.notifyDataSetChanged();
                }
            }
        });

        //三级
        listView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mListView3Adapter != null) {
                    mListView3Adapter.setSelectedPos(position);
                }
                FilterData menuData = (FilterData) parent.getItemAtPosition(position);
                setResultDate(menuData);
            }
        });

        setContentView(view);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setAnimationStyle(R.style.popwindowAnimation);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = window.getAttributes();
                lp.alpha = 1.0f;
                window.setAttributes(lp);
            }
        });
    }

    private List<FilterData> getDataList(int pid) {

        List<FilterData> listData = new ArrayList<>();
        for (int i = 0; i < listAll.size(); i++) {
            if (listAll.get(i).getpId() == pid) {
                listData.add(listAll.get(i));
            }
        }

        return listData;
    }

    private void setResultDate(FilterData menuData) {
        if (onMenuCheckedListener != null) {
            onMenuCheckedListener.onCheck(menuData);
            dismiss();
        }
    }

    public void setDefaultCheckedData(FilterData filterData){
        if (filterData != null) {  //已有选项
            int pid = filterData.getpId();
            int id = filterData.getId();

            for (int m = 0; m < listAll.size(); m++) {
                if (pid == listAll.get(m).getId()) {
                    FilterData dt = listAll.get(m);
                    List<FilterData> list = getDataList(dt.getpId());
                    if (dt.getpId() != -1) {
                        //上级是第二级
                        List<FilterData> eList = getDataList(pid);
                        mListView3Adapter.setData(eList);
                        for (int j = 0; j < eList.size(); j++) {
                            if (id == eList.get(j).getId()) {
                                mListView3Adapter.setSelectedPos(j);
                            }
                        }

                        mListView2Adapter.setData(list);
                        for (int n = 0; n < list.size(); n++) {
                            if (dt.getId() == list.get(n).getId()) {
                                mListView2Adapter.setSelectedPos(n);
                            }
                        }
                        List<FilterData> pList = getDataList(-1);
                        for (int n = 0; n < pList.size(); n++) {
                            if (dt.getpId() == pList.get(n).getId()) {
                                mListView1Adapter.setSelectedPos(n);
                            }
                        }
                    } else {
                        //上级是第一级
                        mListView1Adapter.setData(list);
                        for (int n = 0; n < list.size(); n++) {
                            if (dt.getId() == list.get(n).getId()) {
                                mListView1Adapter.setSelectedPos(n);
                            }
                        }

                        List<FilterData> subList = getDataList(pid);
                        mListView2Adapter.setData(subList);
                        for (int k = 0; k < subList.size(); k++) {
                            if (id == subList.get(k).getId()) {
                                mListView2Adapter.setSelectedPos(k);
                            }
                        }

                        if (mListView3Adapter != null) {
                            //清空三级列表
                            mListView3Adapter.setData(new ArrayList<FilterData>());
                        }
                    }
                }
            }
        }else{
            mListView1Adapter.setSelectedPos(0);
            mListView2Adapter.setData(getDataList(getDataList(-1).get(0).getId()));
        }
    }

    public void setPopTitle(String title){
        tvTitle.setText(title);
    }

    private OnMenuCheckedListener onMenuCheckedListener;

    public interface OnMenuCheckedListener {
        void onCheck(FilterData menuData);
    }

    public void setOnMenuCheckedListener(OnMenuCheckedListener onMenuCheckedListener) {
        this.onMenuCheckedListener = onMenuCheckedListener;
    }

    public void show() {
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = 0.4f;
        window.setAttributes(lp);
        showAtLocation(window.getDecorView(), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }
}

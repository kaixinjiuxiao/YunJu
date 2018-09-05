package com.yunju.app.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yunju.app.R;
import com.yunju.app.adapter.SearchFilterHorizontalRecyclerAdapter;
import com.yunju.app.adapter.SearchListRecyclerViewAdapter;
import com.yunju.app.base.BaseActivity;
import com.yunju.app.entity.FilterData;
import com.yunju.app.entity.HouseSearchResponse;
import com.yunju.app.interfac.OnRecyclerViewItemClickListener;
import com.yunju.app.util.ActivityManager;
import com.yunju.app.util.Constant;
import com.yunju.app.util.DateUtil;
import com.yunju.app.widget.CalendarPopupWindow;
import com.yunju.app.widget.FilterPopupWindow;
import com.yunju.app.widget.ListPopupWindow;
import com.yunju.app.widget.MyItemDecoration;
import com.yunju.app.widget.ThreeMenuListPopupWindow;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 查找列表页
 */
public class SearchListActivity extends BaseActivity {

    @BindView(R.id.search_list_filter_recyclerView)
    RecyclerView filterRecyclerView;
    @BindView(R.id.search_list_date)
    TextView tvDate;
    @BindView(R.id.search_list_sort)
    TextView tvSort;
    @BindView(R.id.search_list_location)
    TextView tvLocation;
    @BindView(R.id.search_list_recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    private List<String> filterList;
    private List<HouseSearchResponse.DataBean> tenementList;
    private SearchListRecyclerViewAdapter adapter;
    private CalendarPopupWindow popupWindowDate;
    private ThreeMenuListPopupWindow popupWindowLocation;
    private ListPopupWindow popupWindowSort;
    private FilterPopupWindow popupWindowFilter;
    private FilterData selectedSort, selectedLocation;
    private Context context;
    private String fromDate, toDate;
    private int stayNum;
    private int page=1;
    private List<String> test = new ArrayList<>();

    @Override
    public int getLayoutResource() {
        return R.layout.activity_search_list;
    }

    @Override
    protected void initView() {
        context = this;
        ActivityManager.getInstance().addActivity(SearchListActivity.this);
    }

    @Override
    protected void initData() {
        filterList = new ArrayList<>();
        filterList.add("优选");
        filterList.add("闪订");
        filterList.add("超赞新房");
        filterList.add("自营民宿");
        filterList.add("别墅");
        filterList.add("豪宅");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        filterRecyclerView.setLayoutManager(layoutManager);
        filterRecyclerView.addItemDecoration(new MyItemDecoration(filterList.size(), 20, false));
        filterRecyclerView.setAdapter(new SearchFilterHorizontalRecyclerAdapter(this, filterList));

        tenementList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SearchListRecyclerViewAdapter(this, tenementList);
        recyclerView.setAdapter(adapter);
        getData(1, "");
        String currentDate = DateUtil.dateToStr(new Date());
        fromDate = currentDate;
        toDate = DateUtil.getDate(currentDate, 1);
        stayNum = 1;
        tvDate.setText(DateUtil.getWantDate(fromDate, "MM.dd") + "-" + DateUtil.getWantDate(toDate, "MM.dd"));
        popupWindowDate = new CalendarPopupWindow(context, getWindow(), test);
        popupWindowLocation = new ThreeMenuListPopupWindow(context, getWindow(), getLocationItemList());
        popupWindowSort = new ListPopupWindow(context, getWindow(), "排序", getSortItemList());
        popupWindowFilter = new FilterPopupWindow(context, getWindow());
    }

    private void getData(final int index, String condition) {
        OkHttpUtils.post().url(Constant.HOUSU_SEARCH).addParams("page", String.valueOf(index))
                .addParams("housenam", condition).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
               try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getInt("status") == 1) {
                        HouseSearchResponse houseSearch = new Gson().fromJson(response, HouseSearchResponse.class);

                        if (houseSearch.getData().size() == 0) {
                            if (index == 1) {
                                ///  mTxtNoBanlance.setVisibility(View.VISIBLE);
                            } else {
                                toast("暂无更多数据加载");
                            }
                        } else {
                            for (int i = 0; i < houseSearch.getData().size(); i++) {
                                tenementList.add(houseSearch.getData().get(i));
                            }
                            adapter.notifyDataSetChanged();
                        }
                    } else {
                        toast(jsonObject.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void initEvent() {
        adapter.setOnRecyclerViewItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onRecyclerViewItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("house_id", tenementList.get(position).getId() + "");
                openActivity(TenementDetailActivity.class, bundle);
            }
        });
        popupWindowLocation.setOnMenuCheckedListener(new ThreeMenuListPopupWindow.OnMenuCheckedListener() {
            @Override
            public void onCheck(FilterData filterData) {
                selectedLocation = filterData;
                tvLocation.setText(filterData.getItemText());
            }
        });
        popupWindowSort.setOnMenuCheckedListener(new ListPopupWindow.OnMenuCheckedListener() {
            @Override
            public void onCheck(FilterData menuData) {
                selectedSort = menuData;
                tvSort.setText(menuData.getItemText());
            }
        });
        popupWindowDate.setOnDateSubmitListener(new CalendarPopupWindow.OnDateSubmitListener() {
            @Override
            public void submit(String fDate, String tDate, int sNum) {
                fromDate = fDate;
                toDate = tDate;
                stayNum = sNum;
                tvDate.setText(DateUtil.getWantDate(fromDate, "MM.dd") + "-" + DateUtil.getWantDate(toDate, "MM.dd"));
            }
        });
        popupWindowFilter.setOnSubmitListener(new FilterPopupWindow.OnSubmitListener() {
            @Override
            public void onSubmit(FilterData menuData) {
                //TODO 筛选
            }
        });
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tenementList.clear();
                        adapter.notifyDataSetChanged();
                        getData(1, "");
                        mRefreshLayout.finishRefresh();
                    }
                }, 2000);

            }
        });
        //加载更多
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page++;
                        getData(page, "");
                        mRefreshLayout.finishLoadMore();
                    }
                }, 2000);
            }
        });
    }

    private List<FilterData> getSortItemList() {
        List<FilterData> sortItemList = new ArrayList<>();
        sortItemList.add(new FilterData(1, -1, "推荐排序"));
        sortItemList.add(new FilterData(2, -1, "距离优先"));
        sortItemList.add(new FilterData(3, -1, "好评优先"));
        sortItemList.add(new FilterData(4, -1, "价格从低到高"));
        sortItemList.add(new FilterData(5, -1, "价格从高到低"));

        selectedSort = sortItemList.get(0);
        return sortItemList;
    }

    private List<FilterData> getLocationItemList() {
        List<FilterData> listAll = new ArrayList<>();
        listAll.add(new FilterData(1, -1, "热门推荐"));
        listAll.add(new FilterData(2, -1, "观光景点"));
        listAll.add(new FilterData(3, -1, "商圈"));
        listAll.add(new FilterData(4, -1, "行政区"));
        listAll.add(new FilterData(5, -1, "地铁线路"));

        listAll.add(new FilterData(6, 1, "不限"));
        listAll.add(new FilterData(7, 1, "天安门"));
        listAll.add(new FilterData(8, 1, "古水北镇"));
        listAll.add(new FilterData(9, 2, "北海公园"));
        listAll.add(new FilterData(10, 2, "故宫"));
        listAll.add(new FilterData(11, 2, "大观园"));
        listAll.add(new FilterData(12, 3, "朝阳门"));
        listAll.add(new FilterData(13, 3, "安贞商圈"));
        listAll.add(new FilterData(14, 3, "北京站/建国门"));
        listAll.add(new FilterData(15, 4, "东城区"));
        listAll.add(new FilterData(16, 4, "朝阳区"));
        listAll.add(new FilterData(17, 4, "海淀区"));
        listAll.add(new FilterData(18, 4, "西城区"));
        listAll.add(new FilterData(19, 4, "丰台区"));
        listAll.add(new FilterData(20, 5, "1号线"));
        listAll.add(new FilterData(21, 5, "2号线"));
        listAll.add(new FilterData(22, 5, "5号线"));

        listAll.add(new FilterData(23, 20, "全线"));
        listAll.add(new FilterData(24, 20, "苹果园"));
        listAll.add(new FilterData(25, 20, "古城"));
        listAll.add(new FilterData(26, 20, "八角游乐园"));
        listAll.add(new FilterData(27, 21, "全线"));
        listAll.add(new FilterData(28, 21, "西直门"));
        listAll.add(new FilterData(29, 21, "车公庄"));
        listAll.add(new FilterData(30, 21, "阜成门"));
        listAll.add(new FilterData(31, 22, "全线"));
        listAll.add(new FilterData(32, 22, "天通苑北"));
        listAll.add(new FilterData(33, 22, "天通苑"));

        return listAll;
    }

    @OnClick({R.id.search_list_back, R.id.search_list_date, R.id.search_list_sort, R.id.search_list_location, R.id.search_list_filter})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.search_list_back:
                finish();
                break;
            case R.id.search_list_date:
                popupWindowDate.setData(fromDate, toDate, stayNum);
                popupWindowDate.show();
                break;
            case R.id.search_list_sort:
                popupWindowSort.setDefaultItem(selectedSort);
                popupWindowSort.show();
                break;
            case R.id.search_list_location:
                popupWindowLocation.setDefaultCheckedData(selectedLocation);
                popupWindowLocation.show();
                break;
            case R.id.search_list_filter:
                popupWindowFilter.show();
                break;
        }
    }
}

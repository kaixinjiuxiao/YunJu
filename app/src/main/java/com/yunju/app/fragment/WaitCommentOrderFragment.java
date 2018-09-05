package com.yunju.app.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yunju.app.R;
import com.yunju.app.activity.OrderDetailsActivity;
import com.yunju.app.adapter.MyOrderAdapter;
import com.yunju.app.base.BaseFragment;
import com.yunju.app.entity.OrderListResponse;
import com.yunju.app.interfac.OnRecyclerViewItemClickListener;
import com.yunju.app.util.Constant;
import com.yunju.app.util.SharedPreferenceUtil;
import com.yunju.app.widget.MyItemDecoration;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

/**
 * Created by sm on 2018/3/23 0026.
 */

public class WaitCommentOrderFragment extends BaseFragment {
    @BindView(R.id.fragment_order_viewpager_recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.txtNoOrder)
    TextView mNoOrder;
    private MyItemDecoration itemDecoration;
    private LinearLayoutManager manager;
     private List<OrderListResponse.DataBean> mOrderList;
    private MyOrderAdapter mOrderAdapter;
    private String trade = "2";
    private int page = 1;
    private String mUid;




    @Override
    public int getLayoutResource() {
        return R.layout.fragment_order_viewpager;
    }

    @Override
    protected void initData() {
        mUid = SharedPreferenceUtil.getAccessToken();
        manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        if (itemDecoration == null) {
            itemDecoration = new MyItemDecoration(2);
            recyclerView.addItemDecoration(itemDecoration);
        }
        mOrderList = new ArrayList<>();
        mOrderAdapter = new MyOrderAdapter(getActivity(),mOrderList);
        recyclerView.setAdapter(mOrderAdapter);
        getOrderList(mUid,page,trade);
    }


    @Override
    protected void initEvent() {
        mSmartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getOrderList(mUid,page,trade);
                        mSmartRefreshLayout.finishLoadMore();
                    }
                },2000);
            }
        });
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mOrderList.clear();
                        getOrderList(mUid,1,trade);
                        mSmartRefreshLayout.finishRefresh();
                    }
                },2000);
            }
        });
        mOrderAdapter.setOnRecyclerViewItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onRecyclerViewItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("type",trade);
                bundle.putString("oid",mOrderList.get(position).getId()+"");
                openActivity(OrderDetailsActivity.class,bundle);
            }
        });
    }

    private void getOrderList(String uid,final int index,String trade){
        OkHttpUtils.post().url(Constant.ORDER_LIST)
                .addParams("uid",uid)
                .addParams("page",String.valueOf(index))
                .addParams("trade",trade)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.getInt("status")==1){
                                OrderListResponse orderList = new Gson().fromJson(response,OrderListResponse.class);
                                if(orderList.getData().size()==0){
                                    if (index == 1) {
                                        mNoOrder.setVisibility(View.VISIBLE);
                                    } else {
                                        toast("暂无更多数据加载");
                                    }
                                }else{
                                    for (int i = 0; i <orderList.getData().size() ; i++) {
                                        mOrderList.add(orderList.getData().get(i));
                                    }
                                }
                                mOrderAdapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

}

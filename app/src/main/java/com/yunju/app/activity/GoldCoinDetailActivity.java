package com.yunju.app.activity;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yunju.app.R;
import com.yunju.app.adapter.GoldCoinDetailsAdapter;
import com.yunju.app.base.BaseActivity;
import com.yunju.app.entity.GoldCoinData;
import com.yunju.app.util.Constant;
import com.yunju.app.util.SharedPreferenceUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class GoldCoinDetailActivity extends BaseActivity {

    @BindView(R.id.titleview_title)
    TextView tvTitle;
    @BindView(R.id.titleview_btnLeft)
    ImageView mTitleviewBtnLeft;

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.txtNoBanlance)
    TextView mTxtNoBanlance;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    private String mUid;
    private GoldCoinDetailsAdapter mAdapter;
    private List<GoldCoinData.MsgBean> mList;
    private int page;
    private LinearLayoutManager manager;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_goldcoin_detail;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        tvTitle.setText("金币明细");
         mList = new ArrayList<>();
        manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerview.setLayoutManager(manager);
        mAdapter = new GoldCoinDetailsAdapter(this, mList);
       mRecyclerview.setAdapter(mAdapter);
        mUid = SharedPreferenceUtil.getAccessToken();
        getCoinDetails(mUid, 0);
    }

    private void getCoinDetails(String uid, final int index) {
        OkHttpUtils.post().url(Constant.COIN_DETAILS)
                .addParams("uid", uid)
                .addParams("page", String.valueOf(index))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getInt("status") == 1) {
                                GoldCoinData goldCoinData = new Gson().fromJson(response, GoldCoinData.class);

                                if (goldCoinData.getMsg().size() == 0) {
                                    if (index == 0) {
                                        mTxtNoBanlance.setVisibility(View.VISIBLE);
                                    } else {
                                        toast("暂无更多数据加载");
                                    }
                                } else {
                                    for (int i = 0; i < goldCoinData.getMsg().size(); i++) {
                                        mList.add(goldCoinData.getMsg().get(i));
                                    }
                                    mAdapter.notifyDataSetChanged();
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
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mList.clear();
                        mAdapter.notifyDataSetChanged();
                        getCoinDetails(mUid, 0);
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
                        getCoinDetails(mUid, page);
                        mRefreshLayout.finishLoadMore();
                    }
                }, 2000);
            }
        });
    }

    @OnClick(R.id.titleview_btnLeft)
    public void onViewClicked() {
        finish();
    }
}

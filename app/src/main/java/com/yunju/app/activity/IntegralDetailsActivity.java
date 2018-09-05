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
import com.yunju.app.adapter.IntegralDetailsAdapter;
import com.yunju.app.base.BaseActivity;
import com.yunju.app.entity.IntegralData;
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

/**
 * @author: captain
 * Time:  2018/6/6 0006
 * Describe:
 */
public class IntegralDetailsActivity extends BaseActivity {
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
    private IntegralDetailsAdapter mAdapter;
    private List<IntegralData.MsgBean> mList;
    private String mUid;
    private int page;
    private LinearLayoutManager manager;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_integral_details;
    }


    @Override
    protected void initView() {
        tvTitle.setText("积分明细");
        mList = new ArrayList<>();
        manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerview.setLayoutManager(manager);
        mAdapter = new IntegralDetailsAdapter(this, mList);
        mRecyclerview.setAdapter(mAdapter);
        mUid = SharedPreferenceUtil.getAccessToken();
        getIntegral(mUid, 0);
    }


    @Override
    protected void initData() {

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
                        getIntegral(mUid, 0);
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
                        getIntegral(mUid, page);
                        mRefreshLayout.finishLoadMore();
                    }
                }, 2000);
            }
        });
    }

    private void getIntegral(String uid, final int index) {
        OkHttpUtils.post().url(Constant.INTEGRAL_DETAILS)
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
                                IntegralData integralData = new Gson().fromJson(response, IntegralData.class);

                                if (integralData.getMsg().size() == 0) {
                                    if (index == 0) {
                                        mTxtNoBanlance.setVisibility(View.VISIBLE);
                                    } else {
                                        toast("暂无更多数据加载");
                                    }
                                } else {
                                    for (int i = 0; i < integralData.getMsg().size(); i++) {
                                        mList.add(integralData.getMsg().get(i));
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


    @OnClick(R.id.titleview_btnLeft)
    public void onClick() {
        finish();
    }
}

package com.yunju.app.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yunju.app.R;
import com.yunju.app.activity.LoginActivity;
import com.yunju.app.activity.TenementDetailActivity;
import com.yunju.app.adapter.CollectListRecyclerViewAdapter;
import com.yunju.app.base.BaseFragment;
import com.yunju.app.entity.HouseSearchResponse;
import com.yunju.app.entity.MessageEvent;
import com.yunju.app.interfac.OnRecyclerViewItemClickListener;
import com.yunju.app.util.Constant;
import com.yunju.app.util.SharedPreferenceUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 收藏
 */
public class CollectionFragment extends BaseFragment {

    @BindView(R.id.titleview_btnLeft)
    ImageView btnLeft;
    @BindView(R.id.titleview_title)
    TextView tvTitle;
    @BindView(R.id.fragment_collection_login)
    View viewToLogin;
    @BindView(R.id.goto_login_icon)
    ImageView ivGotoLogin;
    @BindView(R.id.goto_login_text)
    TextView tvGotoLogin;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.txtNoCollect)
    TextView mNoCollect;
    private LinearLayoutManager manager;
    private List<HouseSearchResponse.DataBean> tenementList;
    private CollectListRecyclerViewAdapter adapter;
    private String mUid;
    private int page = 1;

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_collection;
    }

    @Override
    protected void initData() {
        btnLeft.setVisibility(View.INVISIBLE);
        tvTitle.setText("收藏");
        EventBus.getDefault().register(this);
        ivGotoLogin.setImageResource(R.drawable.icon_mycollection);
        tvGotoLogin.setText(R.string.goto_login_text_collection);
        tenementList = new ArrayList<>();
        manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        //  recyclerView.setLayoutManager(manager);
        mRecyclerView.setLayoutManager(manager);
        adapter = new CollectListRecyclerViewAdapter(getActivity(), tenementList);
        mRecyclerView.setAdapter(adapter);
        if (!SharedPreferenceUtil.getLoginStatus()) {
            viewToLogin.setVisibility(View.VISIBLE);
        } else {
            viewToLogin.setVisibility(View.GONE);

//            String login = SharedPreferenceUtil.getJsonString("login");
//            LoginResponse response = new Gson().fromJson(login, LoginResponse.class);
            mUid = SharedPreferenceUtil.getAccessToken();
            getData(mUid, page);
        }
    }

    private void getData(String uid, final int index) {
        OkHttpUtils.post()
                .url(Constant.COLLECT_LIST)
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
                            if ((jsonObject.getInt("status") == 0) && jsonObject.getInt("code") == 101) {
                                toast("登录已过期，请重新登录");
                                viewToLogin.setVisibility(View.VISIBLE);
                                return;
                            }
                            if (jsonObject.getInt("status") == 1) {
                                HouseSearchResponse houseSearch = new Gson().fromJson(response, HouseSearchResponse.class);
                                if(houseSearch.getData().size()==0){
                                    if(index==1){
                                        mNoCollect.setVisibility(View.VISIBLE);
                                    }else{
                                        toast(houseSearch.getMsg());
                                    }
                                }else{
                                    for (int i = 0; i < houseSearch.getData().size(); i++) {
                                        tenementList.add(houseSearch.getData().get(i));
                                    }
                                }
                                adapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent messageEvent) {
        if (messageEvent.getMessage().equals("1")) {
            viewToLogin.setVisibility(View.GONE);
            if (TextUtils.isEmpty(mUid)) {
//                LoginResponse response = new Gson().fromJson(SharedPreferenceUtil.getJsonString("login"), LoginResponse.class);
//                mUid = response.getData().getId();
                mUid = SharedPreferenceUtil.getAccessToken();
                getData(mUid, 1);
            }
        }
    }

    @Override
    protected void initEvent() {
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tenementList.clear();
                        getData(mUid, 1);
                        mSmartRefreshLayout.finishRefresh();
                    }
                }, 2000);

            }
        });
        //加载更多
        mSmartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page++;
                        getData(mUid, page);
                        mSmartRefreshLayout.finishLoadMore();
                    }
                }, 2000);
            }
        });
        adapter.setOnRecyclerViewItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onRecyclerViewItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("house_id", tenementList.get(position).getId() + "");
                openActivity(TenementDetailActivity.class, bundle);
            }
        });
    }

    @OnClick(R.id.goto_login_button)
    public void onViewClicked() {
        openActivity(LoginActivity.class);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}

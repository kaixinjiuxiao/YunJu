package com.yunju.app.activity;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yunju.app.R;
import com.yunju.app.adapter.BalanceRecyclerAdapter;
import com.yunju.app.base.BaseActivity;
import com.yunju.app.entity.BanlanceData;
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

public class BalanceActivity extends BaseActivity {

    @BindView(R.id.titleview_title)
    TextView tvTitle;
    @BindView(R.id.titleview_btnRight)
    Button btnRight;
    @BindView(R.id.titleview_btnLeft)
    ImageView mTitleviewBtnLeft;
    @BindView(R.id.listview)
    ListView mListView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.txtNoBanlance)
    TextView mTxtNoBanlance;
    TextView mTotal,mToday,mCurrentMonth;
    private BalanceRecyclerAdapter mAdapter;
    private List<BanlanceData.DataBean> mList;
    private String mUid;
    private int page;
    @Override
    public int getLayoutResource() {
        return R.layout.activity_balance;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        tvTitle.setText("余额明细");
        mList = new ArrayList<>();
        View view = LayoutInflater.from(this).inflate(R.layout.banlance_head,null);
        mTotal = view.findViewById(R.id.balance_income_total);
        mToday=view.findViewById(R.id.balance_income_today);
         mCurrentMonth =view.findViewById(R.id.balance_income_month);
        mListView.addHeaderView(view);
        mAdapter = new BalanceRecyclerAdapter(this,mList);
        mListView.setAdapter(mAdapter);
        mUid = SharedPreferenceUtil.getAccessToken();
        getBanlance(mUid,0);
    }




    private void getBanlance(String uid, final int index) {
        OkHttpUtils.post().url(Constant.BANLANCE_DETAILS)
                .addParams("uid", uid)
                .addParams("page",String.valueOf(index))
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
                                BanlanceData banlanceData = new Gson().fromJson(response,BanlanceData.class);
                                mTotal.setText(banlanceData.getBalace());
                                mToday.setText(banlanceData.getToday()+"");
                                mCurrentMonth.setText(banlanceData.getMonth());
                                if(banlanceData.getData().size()==0){
                                    if(index==0){
                                        mTxtNoBanlance.setVisibility(View.VISIBLE);
                                    }else{
                                        toast("暂无更多数据加载");
                                    }
                                }else{
                                    for (int i = 0; i <banlanceData.getData().size() ; i++) {
                                        mList.add(banlanceData.getData().get(i));
                                    }
                                    mAdapter.notifyDataSetChanged();
                                }
                            }else{
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
                new android.os.Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mList.clear();
                        getBanlance(mUid, 0);
                        mRefreshLayout.finishRefresh();
                    }
                }, 2000);

            }
        });
        //加载更多
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                new android.os.Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page++;
                        getBanlance(mUid, page);
                        mRefreshLayout.finishLoadMore();
                    }
                }, 2000);
            }
        });
    }

    @OnClick({R.id.titleview_btnLeft, R.id.titleview_btnRight})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.titleview_btnLeft:
                finish();
                break;
            case R.id.titleview_btnRight:
//                openActivity(TiXianActivity.class);
                break;
        }
    }

}

package com.yunju.app.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.yunju.app.R;
import com.yunju.app.activity.TenementDetailActivity;
import com.yunju.app.adapter.MainHorizontalRecyclerAdapter;
import com.yunju.app.base.BaseFragment;
import com.yunju.app.entity.Tenement;
import com.yunju.app.interfac.OnRecyclerViewItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by sm on 2018/2/26 0026.
 */

public class MainViewPagerFragment extends BaseFragment implements OnRecyclerViewItemClickListener{

    public static final String PARAM_TAB = "tab";
    @BindView(R.id.fragment_viewpager_item_recyclerview)
    RecyclerView recyclerView;
    private MainHorizontalRecyclerAdapter rvAdapter;
    private String tab;
    private List<Tenement> list;

    public static MainViewPagerFragment newInstance(String tab) {
        MainViewPagerFragment fragment = new MainViewPagerFragment();
        Bundle args = new Bundle();
        args.putString(PARAM_TAB, tab);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tab = getArguments().getString(PARAM_TAB);
        }
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_main_viewpager;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        Log.i("MainViewPagerFragment", "onFragmentVisibleChange");
        if (isVisible) {
            getData();
            LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            recyclerView.setLayoutManager(layoutManager);
            rvAdapter=new MainHorizontalRecyclerAdapter(getActivity(), list);
            recyclerView.setAdapter(rvAdapter);
            rvAdapter.setOnRecyclerViewItemClickListener(this);
        }
    }

    private void getData() {
        list = new ArrayList<>();
        Tenement h1 = new Tenement();
        h1.setId(1);
        h1.setPicture("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2376883547,2828451901&fm=27&gp=0.jpg");
        h1.setTitle("住进绝美风景");
        Tenement h2 = new Tenement();
        h2.setId(2);
        h2.setPicture("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3117383508,3197617605&fm=27&gp=0.jpg");
        h2.setTitle("开启完美旅程");
        Tenement h3 = new Tenement();
        h3.setId(3);
        h3.setPicture("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3635877977,2205094079&fm=27&gp=0.jpg");
        h3.setTitle("住进绝美风景");
        list.add(h1);
        list.add(h2);
        list.add(h3);
    }

    @Override
    protected void initEvent() {
    }

    @Override
    public void onRecyclerViewItemClick(View view, int position) {
        openActivity(TenementDetailActivity.class);
    }
}

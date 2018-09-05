package com.yunju.app.activity;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunju.app.R;
import com.yunju.app.adapter.MyFragmentPagerAdapter;
import com.yunju.app.base.BaseActivity;
import com.yunju.app.entity.MessageEvent;
import com.yunju.app.fragment.AllOrderFragment;
import com.yunju.app.fragment.WaitCommentOrderFragment;
import com.yunju.app.fragment.WaitLiveOrderFragment;
import com.yunju.app.fragment.WaitPayOrderFragment;
import com.yunju.app.util.SharedPreferenceUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class OrderActivity extends BaseActivity {

    @BindView(R.id.order_login)
    View viewToLogin;
    @BindView(R.id.goto_login_icon)
    ImageView ivGotoLogin;
    @BindView(R.id.goto_login_text)
    TextView tvGotoLogin;
    @BindView(R.id.order_tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.order_viewPager)
    ViewPager viewPager;
    private boolean isLandLord; //是否为房东

    @Override
    public int getLayoutResource() {
        return R.layout.activity_order;
    }

    @Override
    protected void initData() {
        EventBus.getDefault().register(this);
        ivGotoLogin.setImageResource(R.drawable.icon_myorder);
        tvGotoLogin.setText(R.string.goto_login_text_order);
        if (!SharedPreferenceUtil.getLoginStatus()) {
            viewToLogin.setVisibility(View.VISIBLE);
        } else {
            viewToLogin.setVisibility(View.GONE);
            initView();
        }
        isLandLord=getIntent().getBooleanExtra("isLandLord",false);
    }

    @Override
    protected void initView(){
        List<String> tabTextList = new ArrayList<>();
        tabTextList.add("全部");
        tabTextList.add("待支付");
        tabTextList.add("待点评");
        tabTextList.add("待入住");
//        tabTextList.add("已入住");
//        tabTextList.add("已离店");
//        tabTextList.add("已取消");
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new AllOrderFragment());
        fragmentList.add(new WaitPayOrderFragment());
        fragmentList.add(new WaitCommentOrderFragment());
        fragmentList.add(new WaitLiveOrderFragment());
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(this.getSupportFragmentManager(), tabTextList, fragmentList);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(1);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent messageEvent){
        if(messageEvent.getMessage().equals("1")){
            viewToLogin.setVisibility(View.GONE);
            initView();
        }
    }

    @Override
    protected void initEvent() {

    }

    @OnClick(R.id.goto_login_button)
    public void onViewClicked() {
        openActivity(LoginActivity.class);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }
}

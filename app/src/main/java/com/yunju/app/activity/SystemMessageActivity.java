package com.yunju.app.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.yunju.app.R;
import com.yunju.app.adapter.MessageRecyclerAdapter;
import com.yunju.app.base.BaseActivity;
import com.yunju.app.entity.Message;
import com.yunju.app.widget.MyItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class SystemMessageActivity extends BaseActivity {

    @BindView(R.id.fragment_message_recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.titleview_title)
    TextView tvTitle;
    private List<Message> msgList;
    private MessageRecyclerAdapter adapter;
    private MyItemDecoration itemDecoration;

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_message;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        tvTitle.setText("系统消息");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setPadding(0,0,0,0);
        if (itemDecoration == null) {
            itemDecoration = new MyItemDecoration(2);
            recyclerView.addItemDecoration(itemDecoration);
        }
        msgList = new ArrayList<>();
        getData();
    }

    private void getData() {
        msgList.clear();
        msgList.add(new Message(1, "系统通知", "尊敬的商户您好：由于某项指标未达到考核标准......", "2018-04-22"));
        msgList.add(new Message(2, "优惠促销", "红包免费领，点击查看", "2018-04-23"));
        if (adapter == null) {
            adapter = new MessageRecyclerAdapter(this, msgList);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void initEvent() {

    }

    @OnClick(R.id.titleview_btnLeft)
    public void onViewClicked() {
        finish();
    }
}

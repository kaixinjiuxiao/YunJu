package com.yunju.app.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunju.app.R;
import com.yunju.app.adapter.MessageRecyclerAdapter;
import com.yunju.app.base.BaseFragment;
import com.yunju.app.entity.Message;
import com.yunju.app.widget.MyItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 消息
 */
public class MessageFragment extends BaseFragment {

    @BindView(R.id.fragment_message_recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.titleview_btnLeft)
    ImageView btnLeft;
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
    protected void initData() {
        btnLeft.setVisibility(View.INVISIBLE);
        tvTitle.setText("消息");
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
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
            adapter = new MessageRecyclerAdapter(getActivity(), msgList);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void initEvent() {
    }
}

package com.yunju.app.activity;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yunju.app.R;
import com.yunju.app.adapter.TenementManageRecyclerAdapter;
import com.yunju.app.base.BaseActivity;
import com.yunju.app.entity.Tenement;
import com.yunju.app.interfac.OnRecyclerViewItemClickListener;
import com.yunju.app.widget.MyItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 复制已有房间
 */
public class CopyTenementActivity extends BaseActivity implements OnRecyclerViewItemClickListener{


    @BindView(R.id.copy_tenement_recyclerview)
    RecyclerView recyclerview;
    private List<Tenement> list;
    private TenementManageRecyclerAdapter adapter;
    private Context context;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_copy_tenement;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        context=this;
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.addItemDecoration(new MyItemDecoration(2));
        list=new ArrayList<>();
        getData();
    }

    private void getData(){
        list.clear();
        list.add(new Tenement(1,"","五一广场地铁口...",1,200));
        list.add(new Tenement(2,"","五一广场豪华...",1,200));
        list.add(new Tenement(3,"","五一广场豪华...",1,200));
        list.add(new Tenement(4,"","五一广场地铁口...",1,200));
        list.add(new Tenement(5,"","五一广场豪华...",1,200));
        list.add(new Tenement(6,"","五一广场豪华...",1,200));
        list.add(new Tenement(7,"","五一广场地铁口...",1,200));
        if(adapter==null){
            adapter=new TenementManageRecyclerAdapter(this,list,2);
            recyclerview.setAdapter(adapter);
            adapter.setOnRecyclerViewItemClickListener(this);
        }else{
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void initEvent() {

    }

    @OnClick(R.id.copy_tenement_close)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onRecyclerViewItemClick(View view, int position) {
        openActivity(EditTenementLocationActivity.class);
    }
}

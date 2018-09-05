package com.yunju.app.activity;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunju.app.R;
import com.yunju.app.adapter.TenementManageRecyclerAdapter;
import com.yunju.app.base.BaseActivity;
import com.yunju.app.entity.Section;
import com.yunju.app.entity.Tenement;
import com.yunju.app.widget.MyItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 房源管理
 */
public class TenementManagementActivity extends BaseActivity implements TenementManageRecyclerAdapter.OnEditBtnListener{


    @BindView(R.id.titleview_title)
    TextView tvTitle;
    @BindView(R.id.titleview_ivRight)
    ImageView ivRight;
    @BindView(R.id.tenement_management_recyclerview)
    RecyclerView recyclerview;
    private List<Tenement> list;
    private TenementManageRecyclerAdapter adapter;
    private Context context;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_tenement_management;
    }

    @Override
    protected void initView() {
        context=this;
        tvTitle.setText("房源管理");
        ivRight.setVisibility(View.VISIBLE);
        ivRight.setImageResource(R.drawable.icon_add);
    }

    @Override
    protected void initData() {
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.addItemDecoration(new MyItemDecoration(2));
        list=new ArrayList<>();
       getData();
    }

    private void getData(){
        list.clear();
        Tenement t1=new Tenement();
        t1.setId(1);
        t1.setSection(new Section(1,"已上架"));
        list.add(t1);
        list.add(new Tenement(2,"","五一广场豪华...",1,200));
        list.add(new Tenement(3,"","五一广场豪华...",1,200));
        list.add(new Tenement(4,"","五一广场地铁口...",1,200));
        Tenement t2=new Tenement();
        t2.setId(5);
        t2.setSection(new Section(2,"未上架"));
        list.add(t2);
        list.add(new Tenement(6,"","五一广场豪华...",1,200));
        list.add(new Tenement(7,"","五一广场地铁口...",1,200));
        if(adapter==null){
            adapter=new TenementManageRecyclerAdapter(context,list,1);
            recyclerview.setAdapter(adapter);
            adapter.setOnEditBtnListener(this);
        }else{
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void initEvent() {

    }

    @OnClick({R.id.titleview_btnLeft, R.id.titleview_ivRight})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.titleview_btnLeft:
                finish();
                break;
            case R.id.titleview_ivRight:
                openActivity(RentOutWayActivity.class);
                break;
        }
    }

    @Override
    public void onEdit(Tenement tenement) {
        openActivity(EditTenementLocationActivity.class);
    }
}

package com.yunju.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunju.app.R;
import com.yunju.app.adapter.HouseImageAdapter;
import com.yunju.app.base.BaseActivity;
import com.yunju.app.entity.HouseDetails;
import com.yunju.app.widget.MyItemDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author: captain
 * Time:  2018/6/13 0013
 * Describe:
 */
public class HouseImageActivity extends BaseActivity {
    @BindView(R.id.titleview_btnLeft)
    ImageView mTitleviewBtnLeft;
    @BindView(R.id.titleview_title)
    TextView mTitleviewTitle;
    @BindView(R.id.recyclervew_abulm)
    RecyclerView mRecyclervewAbulm;
    private List<HouseDetails.ImgBean>  mImgBeanList;
    private HouseImageAdapter mHouseImageAdapter;
    private HouseDetails mHouseDetails;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_look_houseimg;
    }

    @Override
    protected void initView() {
        mTitleviewTitle.setText("住宿相册");
    }

    @Override
    protected void initData() {
        mHouseDetails = (HouseDetails) getIntent().getExtras().getSerializable("image");
        mImgBeanList = mHouseDetails.getImg();
        mRecyclervewAbulm.setLayoutManager(new GridLayoutManager(HouseImageActivity.this,2));
        mRecyclervewAbulm.addItemDecoration(new MyItemDecoration(2,10,true));
        mHouseImageAdapter = new HouseImageAdapter(this,mImgBeanList);
        mRecyclervewAbulm.setAdapter(mHouseImageAdapter);
    }

    @Override
    protected void initEvent() {
        mHouseImageAdapter.setOnItemClickListener(new HouseImageAdapter.OnItemClickListener() {
            @Override
            public void onImemClick() {
                if (mHouseDetails != null) {
                    Intent intent = new Intent(HouseImageActivity.this, HouseImageDetailsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("image", mHouseDetails);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
    }



    @OnClick(R.id.titleview_btnLeft)
    public void onClick() {
        finish();
    }
}

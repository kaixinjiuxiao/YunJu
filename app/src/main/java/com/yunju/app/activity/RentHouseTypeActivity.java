package com.yunju.app.activity;

import android.view.View;
import android.widget.TextView;

import com.yunju.app.R;
import com.yunju.app.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 出租房屋类型
 */
public class RentHouseTypeActivity extends BaseActivity {


    @BindView(R.id.titleview_title)
    TextView tvTitle;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_rent_house_type;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        tvTitle.setText("您的房屋以什么方式出租");
    }

    @Override
    protected void initEvent() {

    }

    @OnClick({R.id.titleview_btnLeft, R.id.rent_house_type_whole, R.id.rent_house_type_single})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.titleview_btnLeft:
                finish();
                break;
            case R.id.rent_house_type_whole:
                openActivity(PublishTenementActivity.class);
                //openActivity(EditTenementLocationActivity.class);
                break;
            case R.id.rent_house_type_single:
                openActivity(PublishTenementActivity.class);
               // openActivity(EditTenementLocationActivity.class);
                break;
        }
    }
}

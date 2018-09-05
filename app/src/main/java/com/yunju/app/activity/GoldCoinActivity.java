package com.yunju.app.activity;

import android.view.View;
import android.widget.TextView;

import com.yunju.app.R;
import com.yunju.app.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class GoldCoinActivity extends BaseActivity {

    @BindView(R.id.titleview_title)
    TextView tvTitle;
    @BindView(R.id.goldcoin_total)
    TextView tvGoldcoinTotal;
    private String mCoin;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_goldcoin;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        tvTitle.setText("金币");
        mCoin = getIntent().getStringExtra("coin");
        tvGoldcoinTotal.setText(mCoin);
    }

    @Override
    protected void initEvent() {

    }

    @OnClick({R.id.titleview_btnLeft, R.id.goldcoin_tixian, R.id.goldcoin_detail})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.titleview_btnLeft:
                finish();
                break;
            case R.id.goldcoin_tixian:
                openActivity(TiXianActivity.class);
                break;
            case R.id.goldcoin_detail:

                openActivity(GoldCoinDetailActivity.class);
                break;
        }
    }
}

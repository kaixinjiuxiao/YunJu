package com.yunju.app.activity;

import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunju.app.R;
import com.yunju.app.base.BaseActivity;
import com.yunju.app.util.ActivityManager;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author: captain
 * Time:  2018/5/17 0017
 * Describe:
 */
public class PayResultActivity extends BaseActivity {
    @BindView(R.id.titleview_btnLeft)
    ImageView mTitleviewBtnLeft;
    @BindView(R.id.titleview_title)
    TextView mTitleviewTitle;
    @BindView(R.id.gotoMain)
    Button mGotoMain;
    @BindView(R.id.gotoOrder)
    Button mGotoOrder;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_pay_result;
    }

    @Override
    protected void initView() {
        mTitleviewTitle.setText("支付结果");
        ActivityManager.getInstance().addActivity(PayResultActivity.this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

    }


    @OnClick({R.id.titleview_btnLeft, R.id.gotoMain, R.id.gotoOrder})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.titleview_btnLeft:
                ActivityManager.getInstance().finishAllActivity();
                break;
            case R.id.gotoMain:
                ActivityManager.getInstance().finishAllActivity();
                break;
            case R.id.gotoOrder:
                openActivity(OrderActivity.class);
                ActivityManager.getInstance().finishAllActivity();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            ActivityManager.getInstance().finishAllActivity();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

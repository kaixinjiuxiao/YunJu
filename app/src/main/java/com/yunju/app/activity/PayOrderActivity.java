package com.yunju.app.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yunju.app.R;
import com.yunju.app.base.BaseActivity;
import com.yunju.app.util.ActivityManager;
import com.yunju.app.util.Constant;
import com.yunju.app.util.SharedPreferenceUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * @author: captain
 * Time:  2018/5/7 0007
 * Describe:
 */
public class PayOrderActivity extends BaseActivity {

    @BindView(R.id.titleview_btnLeft)
    ImageView mTitleviewBtnLeft;
    @BindView(R.id.titleview_title)
    TextView mTitleviewTitle;
    @BindView(R.id.wxPay)
    RelativeLayout mWxPay;
    @BindView(R.id.aliPay)
    RelativeLayout mAliPay;
    @BindView(R.id.banlancePay)
    RelativeLayout mBanLancePay;
    @BindView(R.id.houseMoney)
    TextView mHouseMoney;
    @BindView(R.id.houseYaJin)
    TextView mHouseYaJin;
    @BindView(R.id.totalPay)
    TextView mTotalPay;
    private String orderCode;
    private String money,mUid;
    @Override
    public int getLayoutResource() {
        return R.layout.activity_pay_order;
    }

    @Override
    protected void initView() {
        ActivityManager.getInstance().addActivity(PayOrderActivity.this);
        mTitleviewTitle.setText("订单支付");
    }

    @Override
    protected void initData() {
        mUid = SharedPreferenceUtil.getAccessToken();
        Bundle bundle = getIntent().getExtras();
        orderCode = bundle.getString("osn");
        money = bundle.getString("money");
        if(TextUtils.isEmpty(money)){
            getOrderDetails(mUid,orderCode);
        }else{
            mHouseMoney.setText(money.replace("线上支付 ","房费：").replace("￥","¥"));
            mHouseYaJin.setText("住房押金 ¥0.00");
            mTotalPay.setText(money.replace("线上支付","").replace("￥","¥"));
        }
    }

    @Override
    protected void initEvent() {

    }


    @OnClick({R.id.titleview_btnLeft, R.id.wxPay, R.id.aliPay,R.id.banlancePay})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.titleview_btnLeft:
                finish();
                break;
            case R.id.wxPay:
                toast("支付尚未开放，请等待");
                break;
            case R.id.aliPay:
                toast("支付尚未开放，请等待");
                break;
            case R.id.banlancePay:
                startPay(mUid,orderCode,"2");
                break;
        }
    }

    private void startPay(String uid,String osn,String type){
        OkHttpUtils.post().url(Constant.ORDER_PAY)
                .addParams("uid",uid)
                .addParams("order_sn",osn)
                .addParams("pay_type",type)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.getInt("status")==1){
                                openActivity(PayResultActivity.class);
                            }else{
                                toast(jsonObject.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    private void getOrderDetails(String uid, String osn) {
        OkHttpUtils.post().url(Constant.ORDER_DETAILS)
                .addParams("uid", uid)
                .addParams("oid", osn)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getInt("status") == 1) {
                                JSONObject data = jsonObject.getJSONObject("data");
                                money = data.getString("pay_price");
                                mHouseMoney.setText("房费：¥"+money);
                                mHouseYaJin.setText("住房押金 ¥0.00");
                                mTotalPay.setText("¥"+money);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }
}

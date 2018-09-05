package com.yunju.app.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yunju.app.R;
import com.yunju.app.base.BaseActivity;
import com.yunju.app.entity.MessageEvent;
import com.yunju.app.util.Constant;
import com.yunju.app.util.SharedPreferenceUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * @author: captain
 * Time:  2018/5/17 0017
 * Describe:
 */
public class OrderDetailsActivity extends BaseActivity {
    @BindView(R.id.titleview_btnLeft)
    ImageView mTitleviewBtnLeft;
    @BindView(R.id.titleview_title)
    TextView mTitleviewTitle;
    @BindView(R.id.details_enter_time)
    TextView mDetailsEnterTime;
    @BindView(R.id.details_out_time)
    TextView mDetailsOutTime;
    @BindView(R.id.details_notice_time)
    TextView mDetailsNoticeTime;
    @BindView(R.id.details_house_name)
    TextView mDetailsHouseName;
    @BindView(R.id.details_house_condition)
    TextView mDetailsHouseCondition;
    @BindView(R.id.details_house_address)
    TextView mDetailsHouseAddress;
    @BindView(R.id.details_daohang)
    LinearLayout mDetailsDaohang;
    @BindView(R.id.details_fd_name)
    TextView mDetailsFdName;
    @BindView(R.id.details_fd_type)
    TextView mDetailsFdType;
    @BindView(R.id.details_chat)
    LinearLayout mDetailsChat;
    @BindView(R.id.details_phone)
    LinearLayout mDetailsPhone;
    @BindView(R.id.details_enter_name)
    TextView mDetailsEnterName;
    @BindView(R.id.details_enter_phone)
    TextView mDetailsEnterPhone;
    @BindView(R.id.details_house_money)
    TextView mDetailsHouseMoney;
    @BindView(R.id.details_pay_explain)
    LinearLayout mDetailsPayExplain;
    @BindView(R.id.details_house_yj)
    TextView mDetailsHouseYj;
    @BindView(R.id.details_yajin_explain)
    LinearLayout mDetailsYajinExplain;
    @BindView(R.id.details_tishi_one)
    TextView mDetailsTSOne;
    @BindView(R.id.details_tishi_two)
    TextView mDetailsTSTwo;
    @BindView(R.id.details_tishi_three)
    TextView mDetailsTSThree;
    @BindView(R.id.cancelOrder)
    TextView mCancelOrder;
    @BindView(R.id.relativeCancel)
    RelativeLayout mRelativeCancel;
    private String mUid, mPhone, mHousePrice, mHouseYj;
    private PopupWindow popupWindow, mPopupWindowMoney;
    private String mOid;
    private String mType;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_order_details;
    }

    @Override
    protected void initView() {
        mUid = SharedPreferenceUtil.getAccessToken();
        mOid = getIntent().getExtras().getString("oid");
        mType = getIntent().getExtras().getString("type");
        if(mType.equals("1")){
            mRelativeCancel.setVisibility(View.VISIBLE);
        }

        mTitleviewTitle.setText("订单详情");
        getOrderDetails(mUid, mOid);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

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
                                mDetailsEnterTime.setText(data.getString("check_in_time"));
                                mDetailsOutTime.setText(data.getString("check_out_time"));
                                mDetailsNoticeTime.setText(data.getString("check_in_time"));
                                mDetailsHouseName.setText(data.getString("hname"));
                                JSONObject houseInfo = data.getJSONObject("house_info");
                                mDetailsHouseAddress.setText(houseInfo.getString("address"));
                                mDetailsHouseCondition.setText("整套出租" + houseInfo.getString("living_room") + "居/宜住" + houseInfo.getString("people_number") + "人/" + houseInfo.getString("bednum") + "床");
                                mDetailsEnterName.setText(data.getString("occupant_realname"));
                                mDetailsEnterPhone.setText(data.getString("occupant_mobile"));
                                mDetailsHouseMoney.setText("¥" + data.getString("pay_price"));
                                mHouseYj = data.getString("deposit_price");
                                mDetailsHouseYj.setText("¥" + mHouseYj);
                                JSONObject fd = data.getJSONObject("landlord_info");
                                mDetailsFdName.setText(fd.getString("name"));
                                mDetailsFdType.setText("个人房东");
                                mPhone = fd.getString("mobile");
                                mDetailsTSOne.setText("需" + data.getString("check_in_time") + getString(R.string.order_details_tishi1));
                                mDetailsTSTwo.setText("需" + data.getString("check_out_time") + getString(R.string.order_details_tishi2));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }


    @OnClick({R.id.titleview_btnLeft, R.id.details_daohang, R.id.details_chat, R.id.details_phone,
            R.id.details_pay_explain, R.id.details_yajin_explain, R.id.cancelOrder})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.titleview_btnLeft:
                finish();
                break;
            case R.id.details_daohang:
                break;
            case R.id.details_chat:
                break;
            case R.id.details_phone:
                callPhone(mPhone);
                break;
            case R.id.details_pay_explain:
                showPayDetailsPoprpWindow();
                break;
            case R.id.details_yajin_explain:
                showDepositPoprpWindow();
                break;
            case R.id.cancelOrder:
                showCancelDialog();
                break;
            default:
                break;
        }
    }

    private void showCancelDialog(){
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("确定要取消此订单吗？");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cancelOrder(mUid,mOid,mType);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showDepositPoprpWindow() {
        View layout = View.inflate(OrderDetailsActivity.this, R.layout.deposit_explain, null);
        mPopupWindowMoney = new PopupWindow(layout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setBackgroundAlpha(OrderDetailsActivity.this, 0.5f);
        mPopupWindowMoney.setFocusable(true); // 点击空白处时，隐藏掉POP窗口
        mPopupWindowMoney.setOutsideTouchable(true); // 点击外部时，隐藏掉POP窗口
        mPopupWindowMoney.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindowMoney.setAnimationStyle(R.style.popwindowAnimation);
        mPopupWindowMoney.showAtLocation(OrderDetailsActivity.this.findViewById(R.id.order_details), Gravity.BOTTOM, 20, 0);
        TextView txt = layout.findViewById(R.id.txtYJ);
        txt.setText(mHouseYj);
        ImageView imageView = layout.findViewById(R.id.imgClose);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindowMoney.dismiss();
                setBackgroundAlpha(OrderDetailsActivity.this, 1.0f);
            }
        });
        mPopupWindowMoney.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                mPopupWindowMoney.dismiss();
                setBackgroundAlpha(OrderDetailsActivity.this, 1.0f);
            }
        });

    }

    private void showPayDetailsPoprpWindow() {
        View layout = View.inflate(OrderDetailsActivity.this, R.layout.pay_details_explain, null);
        popupWindow = new PopupWindow(layout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setBackgroundAlpha(OrderDetailsActivity.this, 0.5f);
        popupWindow.setFocusable(true); // 点击空白处时，隐藏掉POP窗口
        popupWindow.setOutsideTouchable(true); // 点击外部时，隐藏掉POP窗口
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setAnimationStyle(R.style.popwindowAnimation);
        popupWindow.showAtLocation(OrderDetailsActivity.this.findViewById(R.id.order_details), Gravity.BOTTOM, 20, 0);
        TextView txt = layout.findViewById(R.id.totalDeposit);
        txt.setText(mHouseYj);
        ImageView imageView = layout.findViewById(R.id.imgClose);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                setBackgroundAlpha(OrderDetailsActivity.this, 1.0f);
            }
        });
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                popupWindow.dismiss();
                setBackgroundAlpha(OrderDetailsActivity.this, 1.0f);
            }
        });

    }

    private void cancelOrder(String uid,String oid,String trade){
        OkHttpUtils.post().url(Constant.CANCEL_ORDER)
                .addParams("uid",uid)
                .addParams("oid",oid)
                .addParams("trade",trade)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                       try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getInt("status")==1){
                                EventBus.getDefault().post(new MessageEvent("cancel"));
                                finish();
                            }else{
                                toast("取消失败");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}

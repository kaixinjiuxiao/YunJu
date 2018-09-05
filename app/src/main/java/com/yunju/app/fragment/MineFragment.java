package com.yunju.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yunju.app.R;
import com.yunju.app.activity.BalanceActivity;
import com.yunju.app.activity.EditIdentityActivity;
import com.yunju.app.activity.GoldCoinActivity;
import com.yunju.app.activity.IntegralDetailsActivity;
import com.yunju.app.activity.LoginActivity;
import com.yunju.app.activity.MyCollectionActivity;
import com.yunju.app.activity.OrderActivity;
import com.yunju.app.activity.ShareFriendActivity;
import com.yunju.app.activity.TenementManagementActivity;
import com.yunju.app.activity.UserInfoActivity;
import com.yunju.app.base.BaseFragment;
import com.yunju.app.entity.LoginResponse;
import com.yunju.app.util.Constant;
import com.yunju.app.util.SharedPreferenceUtil;
import com.yunju.app.widget.HeadZoomScrollView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class MineFragment extends BaseFragment {
    @BindView(R.id.fragment_mine_headimage)
    ImageView ivHeadImage;
    @BindView(R.id.fragment_mine_bg_image)
    ImageView ivBgImage;
    @BindView(R.id.fragment_mine_scrollview)
    HeadZoomScrollView scrollView;
    @BindView(R.id.fragment_mine_account)
    TextView tvAccount;
    @BindView(R.id.fragment_mine_type)
    TextView txtType;
    @BindView(R.id.mine_balance)
    TextView balance;
    @BindView(R.id.integer)
    TextView integer;
    @BindView(R.id.coin)
    TextView coin;
    @BindView(R.id.linearLandlord)
    LinearLayout linearLord;
    @BindView(R.id.imgSetting)
    ImageView imgSetting;
    @BindView(R.id.fragment_mine_share)
    TextView mShareFriend;
    private String mCoin;
    @Override
    public int getLayoutResource() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initData() {
        scrollView.setZoomView(ivBgImage);

    }

    @Override
    public void onResume() {
        super.onResume();
        if (!SharedPreferenceUtil.getLoginStatus()) {
            tvAccount.setText("登录/注册");
            ivHeadImage.setImageResource(R.mipmap.default_avatar);
        } else {
            String login = SharedPreferenceUtil.getJsonString("login");
            LoginResponse response = new Gson().fromJson(login, LoginResponse.class);
            String phone = response.getData().getMobile().substring(0, 3) + "****" + response.getData().getMobile().substring(7, 11);
            txtType.setVisibility(View.VISIBLE);
            tvAccount.setText(phone);
            txtType.setText(response.getData().getGrade_remark());
            ivHeadImage.setImageResource(R.mipmap.my_avatar);
            getUserData(response.getData().getId());
        }
    }

    private void getUserData(String uid) {
        OkHttpUtils.post().url(Constant.USER_CENTER).addParams("uid", uid)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getInt("status") == 1) {
                        JSONObject object = jsonObject.getJSONObject("data");
                        balance.setText(object.getString("balance"));
                        integer.setText(object.getString("integral"));
                        mCoin = object.getString("total_coin");
                        coin.setText(object.getString("total_coin"));
                    } else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void initEvent() {

    }

    @OnClick({R.id.fragment_mine_account, R.id.fragment_mine_balance, R.id.fragment_mine_integral, R.id.fragment_mine_goldcoin,
            R.id.fragment_mine_order_checkin, R.id.fragment_mine_order_rentout, R.id.fragment_mine_mycollection, R.id.fragment_mine_tenement_management
            , R.id.fragment_mine_system_message, R.id.imgSetting, R.id.fragment_mine_share,R.id.fragment_mine_words,R.id.fragment_mine_call_cleaning,
            R.id.fragment_mine_order_comment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_mine_account:
                if (!SharedPreferenceUtil.getLoginStatus()) {
                    //登录
                    openActivity(LoginActivity.class);
                }
                break;
            case R.id.fragment_mine_balance:
               // 余额
                if(!SharedPreferenceUtil.getLoginStatus()){
                    openActivity(LoginActivity.class);
                }else {
                    openActivity(BalanceActivity.class);
                }
                break;
            case R.id.fragment_mine_integral:
                //积分
                if(!SharedPreferenceUtil.getLoginStatus()){
                    openActivity(LoginActivity.class);
                }else {
                    openActivity(IntegralDetailsActivity.class);
                }
                break;
            case R.id.fragment_mine_goldcoin:
                //金币
                if(!SharedPreferenceUtil.getLoginStatus()){
                    openActivity(LoginActivity.class);
                }else {
                    Intent intent = new Intent(getActivity(),GoldCoinActivity.class);
                    intent.putExtra("coin",mCoin);
                    startActivity(intent);
                  //  openActivity(GoldCoinActivity.class);
                }
                break;
            case R.id.fragment_mine_tenement_management://房源管理

                  openActivity(TenementManagementActivity.class);
              //  toast("请先升级成房东");
                break;
            case R.id.fragment_mine_mycollection:
                openActivity(MyCollectionActivity.class);
                break;
            case R.id.fragment_mine_order_checkin:
                if(!SharedPreferenceUtil.getLoginStatus()){
                    openActivity(LoginActivity.class);
                }else {
                    Bundle b1 = new Bundle();
                    b1.putBoolean("isLandLord", false);
                    openActivity(OrderActivity.class, b1);
                }
                break;
            case R.id.fragment_mine_order_rentout://出租订单
                toast("请先升级成房东");
//                Bundle b2 = new Bundle();
//                b2.putBoolean("isLandLord", true);
                // openActivity(OrderActivity.class,b2);
                break;
            case R.id.fragment_mine_system_message://系统消息
                toast("请先升级成房东");
                //openActivity(SystemMessageActivity.class);
                break;
            case R.id.fragment_mine_words://留言管理
                toast("请先升级成房东");
                break;
            case R.id.fragment_mine_call_cleaning://呼叫保洁
                toast("请先升级成房东");
                break;
            case R.id.fragment_mine_order_comment:
                toast("请先升级成房东");
                break;
            case R.id.imgSetting:
                if(!SharedPreferenceUtil.getLoginStatus()){
                    openActivity(LoginActivity.class);
                }else {
                    openActivity(UserInfoActivity.class);
                }
                break;
            case R.id.fragment_mine_share:
                if(!SharedPreferenceUtil.getLoginStatus()){
                    openActivity(LoginActivity.class);
                }else {
                    openActivity(ShareFriendActivity.class);
                }
                break;
                default:
                    break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

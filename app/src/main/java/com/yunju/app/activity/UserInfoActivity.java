package com.yunju.app.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunju.app.R;
import com.yunju.app.base.BaseActivity;
import com.yunju.app.entity.RefreshUserInfo;
import com.yunju.app.util.Constant;
import com.yunju.app.util.SharedPreferenceUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * @author: captain
 * Time:  2018/5/15 0015
 * Describe:
 */
public class UserInfoActivity extends BaseActivity {

    @BindView(R.id.titleview_btnLeft)
    ImageView mTitleviewBtnLeft;
    @BindView(R.id.titleview_title)
    TextView mTitleviewTitle;
    @BindView(R.id.info_recommend)
    TextView mInfoRecommend;
    @BindView(R.id.info_mobile)
    TextView mInfoMobile;
    @BindView(R.id.info_bank)
    TextView mInfoBank;
    @BindView(R.id.info_bank_code)
    TextView mInfoBankCode;
    @BindView(R.id.btnLoginOut)
    Button mBtnLoginOut;
    @BindView(R.id.info_authentication)
    TextView mInfoAuthentication;
    private String mUid;


    @Override
    public int getLayoutResource() {
        return R.layout.activity_user_info;
    }

    @Override
    protected void initView() {
        mTitleviewTitle.setText("个人信息");
        mUid = SharedPreferenceUtil.getAccessToken();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    //定义处理接收的方法
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEventBus(RefreshUserInfo userInfo) {
        if (userInfo.getMessage().equals("refresh")) {
            getUserInfo();
        }
    }

    @Override
    protected void initData() {
        getUserInfo();
    }


    private void getUserInfo() {
        OkHttpUtils.post().url(Constant.USER_INFO)
                .addParams("uid", mUid)
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
                                JSONObject object = jsonObject.getJSONObject("data");
                                if (object.getString("pmobile").equals("null") || TextUtils.isEmpty(object.getString("pmobile"))) {
                                    mInfoRecommend.setText("暂无");
                                } else {
                                    mInfoRecommend.setText(object.getString("pmobile"));
                                }
                                mInfoMobile.setText(object.getString("mobile"));
                                if (TextUtils.isEmpty(object.getString("bank_user"))) {
                                    mInfoBank.setText("绑定银行");
                                } else {
                                    mInfoBank.setText(object.getString("bank_user"));
                                }
                                mInfoBankCode.setText(object.getString("bank_card"));
                                if(object.getInt("finish_check")==0){
                                    mInfoAuthentication.setText("未认证");
                                }else if(object.getInt("finish_check")==1){
                                    mInfoAuthentication.setText("认证中");
                                }else{
                                    mInfoAuthentication.setText("已认证");
                                }
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

    @OnClick({R.id.titleview_btnLeft, R.id.info_bank, R.id.btnLoginOut,R.id.info_authentication})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.titleview_btnLeft:
                finish();
                break;
            case R.id.info_bank:
                openActivity(ModifiyInfoActivity.class);
                break;
            case R.id.info_authentication:
                openActivity(IDCradAuthenticationActivity.class);
                break;
            case R.id.btnLoginOut:
                showExitDialog();
                break;
        }
    }

    private void showExitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("您确定要退出吗?");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferenceUtil.removeAll();
                openActivity(LoginActivity.class);
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}

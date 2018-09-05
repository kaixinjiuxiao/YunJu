package com.yunju.app.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yunju.app.R;
import com.yunju.app.base.BaseActivity;
import com.yunju.app.entity.MessageEvent;
import com.yunju.app.util.Constant;
import com.yunju.app.util.SharedPreferenceUtil;
import com.yunju.app.util.StringUtil;
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

public class LoginActivity extends BaseActivity {


    @BindView(R.id.titleview_title)
    TextView tvTitle;
    @BindView(R.id.titleview_btnRight)
    Button btnRegister;
    @BindView(R.id.login_phone)
    EditText etPhone;
    @BindView(R.id.login_password)
    EditText etPassword;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        tvTitle.setText(R.string.login_login);
        btnRegister.setVisibility(View.VISIBLE);
        btnRegister.setText(R.string.register_register);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initEvent() {
    }

    private String phoneStr, passwordStr;
    @OnClick({R.id.titleview_btnLeft, R.id.titleview_btnRight, R.id.login_login})
    public void onViewClicked(View view) {
        phoneStr = etPhone.getText() + "".trim();
        passwordStr = etPassword.getText() + "".trim();
        switch (view.getId()) {
            case R.id.titleview_btnLeft:
                finish();
                break;
            case R.id.titleview_btnRight:
                //注册
                openActivity(RegisterActivity.class);
                break;
            case R.id.login_login:
                if (!StringUtil.isPhoneLegal(phoneStr)) {
                    toast("请输入可用的手机号");
                    return;
                }
                if (StringUtil.isNullString(passwordStr)) {
                    toast("密码不能为空");
                    return;
                }
                login(phoneStr,passwordStr);

                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent messageEvent){
        if(messageEvent.getMessage().equals("1")){
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }


    private void login(String phone ,String pws){
        OkHttpUtils.post().url(Constant.LOGIN)
                .addParams("mobile",phone)
                .addParams("password",pws)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.getInt("status")==0){
                            toast(jsonObject.getString("msg"));
                    }else if(jsonObject.getInt("status")==1){
                        JSONObject object = jsonObject.getJSONObject("data");
                        String uid = object.getString("id");
                        SharedPreferenceUtil.saveLoginStatus(true);
                        SharedPreferenceUtil.saveJsonString("login",response);
                        SharedPreferenceUtil.saveAccessToken(uid);
                        EventBus.getDefault().post(new MessageEvent("1"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

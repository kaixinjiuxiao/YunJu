package com.yunju.app.activity;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.yunju.app.R;
import com.yunju.app.base.BaseActivity;
import com.yunju.app.entity.MessageEvent;
import com.yunju.app.util.Constant;
import com.yunju.app.util.SharedPreferenceUtil;
import com.yunju.app.util.StringUtil;
import com.yunju.app.util.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class RegisterActivity extends BaseActivity {


    @BindView(R.id.titleview_title)
    TextView tvTitle;
    @BindView(R.id.register_phone)
    EditText etPhone;
    @BindView(R.id.register_get_code)
    Button btnGetCode;
    @BindView(R.id.register_code)
    EditText etCode;
    @BindView(R.id.register_password)
    EditText etPassword;
    @BindView(R.id.register_password_again)
    EditText etPasswordAgain;
    @BindView(R.id.register_type)
    Spinner spinnerType;
    @BindView(R.id.register_agreement)
    CheckBox cbAgreement;
    private boolean isAgree = true;
    private int userType=-1;
    private boolean canClick = true;
    public static int UPDATE = 60;
    private static Runnable mRun;
    private static Handler mHandler = new Handler();
    @Override
    public int getLayoutResource() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        tvTitle.setText(R.string.register_register);
    }

    @Override
    protected void initEvent() {
        cbAgreement.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    isAgree = true;
                } else {
                    isAgree = false;
                }
            }
        });
        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                userType=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @OnClick({R.id.titleview_btnLeft, R.id.register_get_code, R.id.register_user_agreement, R.id.register_register})
    public void onViewClicked(View view) {
        String phoneStr = etPhone.getText() + "".trim();
        String codeStr = etCode.getText() + "".trim();
        String passwordStr = etPassword.getText() + "".trim();
        String passwordAgainStr = etPasswordAgain.getText() + "".trim();
        switch (view.getId()) {
            case R.id.titleview_btnLeft:
                finish();
                break;
            case R.id.register_get_code:
                if (!StringUtil.isPhoneLegal(phoneStr)) {
                    toast("请输入可用的手机号");
                    return;
                }
                if (canClick) {
                    startTime();
                    getSMSCode(phoneStr);
                }

                break;
            case R.id.register_user_agreement:
                break;
            case R.id.register_register:
                if (!StringUtil.isPhoneLegal(phoneStr)) {
                    toast("请输入可用的手机号");
                    return;
                }
                if (StringUtil.isNullString(codeStr)) {
                    toast("验证码不能为空");
                    return;
                }
                if (StringUtil.isNullString(passwordStr)) {
                    toast("密码不能为空");
                    return;
                }
                if (!passwordStr.equals(passwordAgainStr)) {
                    toast("密码不一致");
                    return;
                }
//                if (userType<0) {
//                    toast("请选择身份");
//                    return;
//                }
                if (!isAgree) {
                    toast("请先勾选用户协议");
                    return;
                }
                register(phoneStr,passwordStr,codeStr);
                break;
        }
    }


    /**
     * 获取验证码
     * @param phone
     */
    private void getSMSCode(final String phone) {
        OkHttpUtils.post()
                .url(Constant.SMS_CODE)
                .addParams("mobile", phone).addParams("state", "0")
                .build().execute(new StringCallback() {

            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.getInt("code")==1){
                        ToastUtil.showShort("发送成功");
                    }else{
                        ToastUtil.showShort(jsonObject.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void startTime() {
        canClick = false;
        mRun = new Runnable() {
            @Override
            public void run() {
                btnGetCode.setText(UPDATE-- + "");
                if (UPDATE == 0) {
                    btnGetCode.setClickable(true);
                    btnGetCode.setText("发送验证码");
                    UPDATE = 60;
                    canClick = true;
                } else {
                    mHandler.postDelayed(this, 1000);
                }
            }
        };
        mHandler.post(mRun);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(mRun);
        UPDATE = 60;
    }



    private void register(String phone,String pwd,String  code){
        OkHttpUtils.post().url(Constant.REGISTER)
                .addParams("mobile",phone)
                .addParams("password",pwd)
                .addParams("verify",code)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.getInt("status")==0){
                        ToastUtil.showShort(jsonObject.getString("msg"));
                    }else{
                        toast("注册成功");
                        SharedPreferenceUtil.saveLoginStatus(true);
                        SharedPreferenceUtil.saveJsonString("login",response);
                        EventBus.getDefault().post(new MessageEvent("1"));
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

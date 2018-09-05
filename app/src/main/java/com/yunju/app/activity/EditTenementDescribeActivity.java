package com.yunju.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yunju.app.R;
import com.yunju.app.base.BaseActivity;
import com.yunju.app.util.Constant;
import com.yunju.app.util.SharedPreferenceUtil;
import com.yunju.app.widget.CustomEditTextShowNum;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class EditTenementDescribeActivity extends BaseActivity {

    @BindView(R.id.titleview_title)
    TextView tvTitle;
    @BindView(R.id.titleview_btnRight)
    Button btnRight;
    @BindView(R.id.edit_tenement_describe_name)
    CustomEditTextShowNum etName;
    @BindView(R.id.edit_tenement_describe_feature)
    CustomEditTextShowNum etFeature;
    private String mUid;
    @Override
    public int getLayoutResource() {
        return R.layout.activity_edit_tenement_describe;
    }

    @Override
    protected void initView() {
        tvTitle.setText("您的房屋有哪些特色");
        btnRight.setVisibility(View.VISIBLE);
        btnRight.setText("保存");
        etName.setMaxNum(10);
        etName.setContentHint("如“上海静安石库门地中海风情屋”");
        etFeature.setMaxNum(1000);
        etFeature.setMinLines(8);
        etFeature.setContentHint("房屋配有落地窗、阳台...");
        mUid = SharedPreferenceUtil.getAccessToken();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

    }

    @OnClick({R.id.titleview_btnLeft, R.id.titleview_btnRight, R.id.edit_tenement_describe_traffic, R.id.edit_tenement_describe_periphery, R.id.edit_tenement_describe_btnNext})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.titleview_btnLeft:
                finish();
                break;
            case R.id.titleview_btnRight:
                Intent intent = new Intent(this, PublishTenementActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;
            case R.id.edit_tenement_describe_traffic:
                Bundle b1=new Bundle();
                b1.putInt("flag",EditTextActivity.FLAG_TRAFFIC);
                openActivity(EditTextActivity.class,b1);
                break;
            case R.id.edit_tenement_describe_periphery:
                Bundle b2=new Bundle();
                b2.putInt("flag",EditTextActivity.FLAG_PERIPHERY);
                openActivity(EditTextActivity.class,b2);
                break;
            case R.id.edit_tenement_describe_btnNext:
                openActivity(EditTenementFacilityActivity.class);
                finish();
                break;
        }
    }

    private void saveHouseDetails(){
        Map<String,String> parmas = new HashMap<>();
        parmas.put("uid",mUid);
        parmas.put("hid",mUid);
        parmas.put("module","detail");
        parmas.put("hname",mUid);
        OkHttpUtils.post().url(Constant.SAVE_HOUSE_INFO).params(parmas)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {

            }
        });
    }
}

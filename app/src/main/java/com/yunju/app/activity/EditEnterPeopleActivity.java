package com.yunju.app.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunju.app.R;
import com.yunju.app.base.BaseActivity;
import com.yunju.app.entity.EnterMan;
import com.yunju.app.util.Constant;
import com.yunju.app.util.SharedPreferenceUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * @author: captain
 * Time:  2018/5/12 0012
 * Describe:编辑入住联系人
 */
public class EditEnterPeopleActivity extends BaseActivity {
    @BindView(R.id.titleview_btnLeft)
    ImageView mTitleviewBtnLeft;
    @BindView(R.id.titleview_title)
    TextView mTitleviewTitle;
    @BindView(R.id.edt_addman_name)
    EditText mEdtAddmanName;
    @BindView(R.id.edt_addman_phone)
    EditText mEdtAddmanPhone;
    @BindView(R.id.edt_addman_card)
    EditText mEdtAddmanCard;
    @BindView(R.id.btnCommit)
    Button mBtnCommit;
    private String mUserId,mInfoId;
    @Override
    public int getLayoutResource() {
        return R.layout.edit_enterman_info;
    }

    @Override
    protected void initView() {
        mTitleviewTitle.setText("编辑入住人");
        mUserId = SharedPreferenceUtil.getAccessToken();
    }

    @Override
    protected void initData() {
        EnterMan enterMan= (EnterMan) getIntent().getSerializableExtra("enterInfo");
        if (enterMan!=null){
            mInfoId = enterMan.getId();
            mEdtAddmanName.setText(enterMan.getName());
            mEdtAddmanPhone.setText(enterMan.getPhone());
            mEdtAddmanCard.setText(enterMan.getAddress());
        }
    }

    @Override
    protected void initEvent() {

    }


    @OnClick({R.id.titleview_btnLeft, R.id.btnCommit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.titleview_btnLeft:
                finish();
                break;
            case R.id.btnCommit:
                String realName = mEdtAddmanName.getText().toString();
                String phone = mEdtAddmanPhone.getText().toString();
                String idCard = mEdtAddmanCard.getText().toString();
                if(TextUtils.isEmpty(realName)){
                    toast("真实姓名不能为空!");
                  return;
                }
                if(TextUtils.isEmpty(phone)){
                    toast("手机号码不能为空!");
                    return;
                }
                if(TextUtils.isEmpty(idCard)){
                    toast("身份证号码不能为空!");
                    return;
                }
                if(TextUtils.isEmpty(mInfoId)){
                    commitEnterMan(mUserId,realName,phone,idCard,"");
                }else{
                    commitEnterMan(mUserId,realName,phone,idCard,mInfoId);
                }
                break;
        }
    }


    private void commitEnterMan(String uid,String name,String phone,String idcard,String id){
        Map<String,String> parmas= new HashMap<>();
        parmas.put("uid",uid);
        parmas.put("realname",name);
        parmas.put("mobile",phone);
        parmas.put("id_card",idcard);
        if(!TextUtils.isEmpty(id)){
            parmas.put("id",id);
        }
        OkHttpUtils.post().url(Constant.ADD_ENTERMAN)
                .params(parmas)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.getInt("status")==0){

                    }else{
                        toast("添加成功!");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

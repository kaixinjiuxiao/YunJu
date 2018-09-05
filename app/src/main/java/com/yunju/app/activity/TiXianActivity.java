package com.yunju.app.activity;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yunju.app.R;
import com.yunju.app.adapter.WithDrawlAdapter;
import com.yunju.app.base.BaseActivity;
import com.yunju.app.entity.WithDrawData;
import com.yunju.app.util.Constant;
import com.yunju.app.util.SharedPreferenceUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class TiXianActivity extends BaseActivity {


    @BindView(R.id.titleview_title)
    TextView tvTitle;
    @BindView(R.id.titleview_btnLeft)
    ImageView mTitleviewBtnLeft;
    @BindView(R.id.withdraw_coin_total)
    TextView mWithdrawCoinTotal;
    @BindView(R.id.withdraw_edt_number)
    EditText mWithdrawEdtNumber;
    @BindView(R.id.withdraw_txt_account)
    TextView mWithdrawTxtAccount;
    @BindView(R.id.withdraw_txt_bankName)
    TextView mWithdrawTxtBankName;
    @BindView(R.id.withdraw_txt_bankBranch)
    TextView mWithdrawTxtBankBranch;
    @BindView(R.id.withdraw_txt_bankcode)
    TextView mWithdrawTxtBankcode;
    @BindView(R.id.withdraw_txt_name)
    TextView mWithdrawTxtName;
    @BindView(R.id.withdraw_btn_sure)
    Button mWithdrawBtnSure;
    @BindView(R.id.withdraw_spn)
    Spinner mWithdrawSpn;
    private WithDrawlAdapter mAdapter;
    private String mUid, mCoin, mRecommendCoin, mLeaderCoin, mSuccessCoin,mType;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_ti_xian;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        tvTitle.setText("提现");
        mUid = SharedPreferenceUtil.getAccessToken();
        List<String>data =new ArrayList<>();
        data.add("金币提现");
        data.add("推荐奖励金币提现");
        data.add("领导奖励金币提现");
        data.add("成就奖励金币提现");
        mAdapter = new WithDrawlAdapter(this,data);
        mWithdrawSpn.setAdapter(mAdapter);
        getBanlanceByType(mUid);
    }

    @Override
    protected void initEvent() {
        mWithdrawSpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        mWithdrawCoinTotal.setText("金币("+mCoin+")");
                        mType="1";
                        break;
                    case 1:
                        mWithdrawCoinTotal.setText("推荐奖励金币("+mRecommendCoin+")");
                        mType="2";
                        break;
                    case 2:
                        mWithdrawCoinTotal.setText("领导奖励金币("+mLeaderCoin+")");
                        mType="3";
                        break;
                    case 3:
                        mWithdrawCoinTotal.setText("成就奖励金币("+mSuccessCoin+")");
                        mType="4";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mWithdrawEdtNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(TextUtils.isEmpty(s)){
                   return;
                }
                String str = s.toString();
                Double number = Double.parseDouble(str);
                BigDecimal bigDecimal = new BigDecimal(number*0.97).setScale(2,BigDecimal.ROUND_HALF_UP);
                mWithdrawTxtAccount.setText(bigDecimal+"");
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });
    }

    @OnClick({R.id.titleview_btnLeft, R.id.withdraw_btn_sure})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.titleview_btnLeft:
                finish();
                break;
            case R.id.withdraw_btn_sure:
                if(TextUtils.isEmpty(mWithdrawEdtNumber.getText().toString())){
                    toast("请输入要提现的金额");
                    return;
                }
                commitData(mUid,mType,mWithdrawEdtNumber.getText().toString());
                break;
        }
    }

    private void getBanlanceByType(String uid) {
        OkHttpUtils.post().url(Constant.WITHDRAWL_BANLANCE)
                .addParams("uid", uid)
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
                                WithDrawData withDrawData = new Gson().fromJson(response,WithDrawData.class);
                                mWithdrawCoinTotal.setText("金币("+withDrawData.getMsg().getCoin()+")");
                                mWithdrawTxtBankName.setText(withDrawData.getMsg().getBank_user());
                                mWithdrawTxtBankcode.setText(withDrawData.getMsg().getBank_card());
                                mWithdrawTxtName.setText(withDrawData.getMsg().getBank_name());
                                mWithdrawTxtBankBranch.setText(withDrawData.getMsg().getBank_address());
                                mCoin = withDrawData.getMsg().getCoin();
                                mRecommendCoin = withDrawData.getMsg().getPush_coin();
                                mLeaderCoin = withDrawData.getMsg().getLead_coin();
                                mSuccessCoin = withDrawData.getMsg().getCj_coin();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    private void commitData(String uid, final String type, String number){
        OkHttpUtils.post().url(Constant.WITHDRAWL_COMMIT)
                .addParams("uid",uid)
                .addParams("type",type)
                .addParams("money",number)
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
                                toast(jsonObject.getString("msg"));
                                finish();
                            }else{
                                toast(jsonObject.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}

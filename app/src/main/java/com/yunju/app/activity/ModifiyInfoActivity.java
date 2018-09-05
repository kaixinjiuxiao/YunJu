package com.yunju.app.activity;

import android.text.TextUtils;
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
import com.yunju.app.adapter.BankAdapter;
import com.yunju.app.adapter.BankBranchAdapter;
import com.yunju.app.adapter.CityAdapter;
import com.yunju.app.adapter.ProvinceAdapter;
import com.yunju.app.base.BaseActivity;
import com.yunju.app.entity.BankBranchData;
import com.yunju.app.entity.RefreshUserInfo;
import com.yunju.app.util.Constant;
import com.yunju.app.util.SharedPreferenceUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * @author: captain
 * Time:  2018/5/16 0016
 * Describe: 编辑银行卡信息
 */
public class ModifiyInfoActivity extends BaseActivity {

    @BindView(R.id.titleview_btnLeft)
    ImageView mTitleviewBtnLeft;
    @BindView(R.id.titleview_title)
    TextView mTitleviewTitle;
    @BindView(R.id.bankSpn)
    Spinner mBankSpn;
    @BindView(R.id.spnPre)
    Spinner mSpnPre;
    @BindView(R.id.spnCity)
    Spinner mSpnCity;
    @BindView(R.id.spnBankBranch)
    Spinner mSpnBankBranch;
    @BindView(R.id.edtBankCode)
    EditText mEdtBankCode;
    @BindView(R.id.edtBankName)
    EditText mEdtBankName;
    @BindView(R.id.btnSure)
    Button mBtnSure;
    private BankAdapter mBankAdapter;
    private ProvinceAdapter mProvinceAdapter;
    private CityAdapter mCityAdapter;
    private BankBranchAdapter mBankBranchAdapter;
    private List<String> mListBank;
    private List<String> mListProvince;
    private List<String> mListCity;
    private List<String> mData;
    private List<BankBranchData.DataBean> mBankBranchData;
    private String mBank,mProvince,mCity,mToken,mBankBranch;
    @Override
    public int getLayoutResource() {
        return R.layout.layout_add_bank;
    }

    @Override
    protected void initView() {
        mTitleviewTitle.setText("编辑信息");
        mToken = SharedPreferenceUtil.getAccessToken();
        mListBank = new ArrayList<>();
        mListProvince = new ArrayList<>();
        mListCity = new ArrayList<>();
        mData = new ArrayList<>();
        mBankBranchData= new ArrayList<>();
        mBankAdapter= new BankAdapter(this,mListBank);
        mBankSpn.setAdapter(mBankAdapter);
        mProvinceAdapter= new ProvinceAdapter(this,mListProvince);
        mSpnPre.setAdapter(mProvinceAdapter);
        mCityAdapter= new CityAdapter(this,mListCity);
        mSpnCity.setAdapter(mCityAdapter);
        mBankBranchAdapter = new BankBranchAdapter(this,mBankBranchData);
        mSpnBankBranch.setAdapter(mBankBranchAdapter);
        getBank();
        getArea();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {
        mBankSpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mBank = mListBank.get(position);
                }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mSpnPre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mProvince = mListProvince.get(position);
                if(mListCity.size()>0){
                    mListCity.clear();
                }
             //   mCityAdapter.notifyDataSetChanged();

                for (int i = 0; i <mData.size() ; i++) {
                    if(mData.get(i).contains(mProvince)){
                        try {
                            JSONObject jsonObject = new JSONObject(mData.get(i));
                            JSONArray cityArray = jsonObject.getJSONArray("city");

                            for (int j = 0; j <cityArray.length() ; j++) {
                                    JSONObject cityObject = cityArray.getJSONObject(j);
                                    String city = cityObject.getString("name");
                                    mListCity.add(city);
                            }
                            mCityAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                mSpnCity.setSelection(0);
                getBankBranch(mListCity.get(0));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mSpnCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0){
                    mCity =mListCity.get(position);
                    getBankBranch(mCity);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mSpnBankBranch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mBankBranch = mBankBranchData.get(position).getText();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getBankBranch(String city) {
        Map<String,String >parmas = new HashMap<>();
        parmas.put("uid",mToken);
        parmas.put("bank_user",mBank);
        parmas.put("province",mProvince);
        parmas.put("city",city);
        OkHttpUtils.post().url(Constant.BANK_BRANCH_LIST).params(parmas)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        BankBranchData  bankBranchData = new Gson().fromJson(response,BankBranchData.class);
                        if(mBankBranchData.size()>0){
                            mBankBranchData.clear();
                        }
                        for (int i = 0; i <bankBranchData.getData().size() ; i++) {
                            mBankBranchData.add(bankBranchData.getData().get(i));
                        }
                        mBankBranchAdapter.notifyDataSetChanged();
                    }
                });
    }

    @OnClick({R.id.titleview_btnLeft, R.id.btnSure})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.titleview_btnLeft:
                finish();
                break;
            case R.id.btnSure:
                String bankCode = mEdtBankCode.getText().toString();
                String name = mEdtBankName.getText().toString();
                if(TextUtils.isEmpty(bankCode)){
                    toast("卡号不能为空");
                    return;
                }
                if(TextUtils.isEmpty(name)){
                    toast("卡号不能为空");
                    return;
                }
                modifiyInfo(bankCode,name);
                break;
            default:
                break;
        }
    }


    private void getBank() {
        OkHttpUtils.get().url(Constant.BANK_LIST)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        String string = jsonArray.getJSONObject(i).getString("bank_user");
                        mListBank.add(string);
                        if (mBankAdapter != null) {
                            mBankAdapter.notifyDataSetChanged();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
    private void getArea() {
        OkHttpUtils.get().url(Constant.AREA_LIST)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i <jsonArray.length() ; i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String province = jsonObject.getString("name");
                                mListProvince.add(province);
                                mData.add(jsonObject.toString());
                            }
                            mProvinceAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    private void modifiyInfo(String bankCode,String name){
        Map<String,String>parmas = new HashMap<>();
        parmas.put("uid",mToken);
        parmas.put("bank_user",mBank);
        parmas.put("bank_address",mBankBranch);
        parmas.put("bank_card",bankCode);
        parmas.put("bank_name",name);
        OkHttpUtils.post().url(Constant.NODIFIY_USER_INDO)
                .params(parmas)
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
                                EventBus.getDefault().post(new RefreshUserInfo("refresh"));
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

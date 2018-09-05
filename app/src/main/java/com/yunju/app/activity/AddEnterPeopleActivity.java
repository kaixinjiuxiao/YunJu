package com.yunju.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yunju.app.R;
import com.yunju.app.adapter.EnterManAdapter;
import com.yunju.app.base.BaseActivity;
import com.yunju.app.entity.EnterMan;
import com.yunju.app.entity.EnterManList;
import com.yunju.app.util.Constant;
import com.yunju.app.util.SharedPreferenceUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * @author: captain
 * Time:  2018/5/12 0012
 * Describe:添加入住人列表
 */
public class AddEnterPeopleActivity extends BaseActivity {

    @BindView(R.id.titleview_btnLeft)
    ImageView mTitleviewBtnLeft;
    @BindView(R.id.titleview_title)
    TextView mTitleviewTitle;
    @BindView(R.id.relativeAdd)
    RelativeLayout mRelativeAdd;
    @BindView(R.id.enterManRecycler)
    RecyclerView mEnterManRecycler;
    @BindView(R.id.noEnterMan)
    TextView mNoEnterMan;
    @BindView(R.id.btnCommit)
    Button mBtnCommit;
    private EnterManAdapter mEnterManAdapter;
    private List<EnterMan> mList;
    private HashMap<Integer, Boolean> map = new HashMap<>();
    private String mToken;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_add_ep;
    }

    @Override
    protected void initView() {
        mTitleviewTitle.setText("选择入住人");
        mToken = SharedPreferenceUtil.getAccessToken();
        mList = new ArrayList<>();
        mEnterManRecycler.setLayoutManager(new LinearLayoutManager(this));
        mEnterManRecycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mEnterManAdapter = new EnterManAdapter(this, mList, map);
        mEnterManRecycler.setAdapter(mEnterManAdapter);
        getEnterMan(mToken);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {
        mEnterManAdapter.setOnEditListener(new EnterManAdapter.OnEditAddresListener() {
            @Override
            public void editAddress(int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("enterInfo", mList.get(position));
                openActivity(EditEnterPeopleActivity.class, bundle);
            }
        });
    }


    @OnClick({R.id.titleview_btnLeft, R.id.relativeAdd, R.id.btnCommit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.titleview_btnLeft:
                finish();
                break;
            case R.id.relativeAdd:
                openActivity(EditEnterPeopleActivity.class);
                break;
            case R.id.btnCommit:
                EnterMan enterMan = null;
                for (int i = 0; i <mList.size() ; i++) {
                    if(mList.get(i).isSelected()) {
                        enterMan = mList.get(i);
                    }
                }
                if(enterMan==null){
                    toast("请选择入住人");
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra("name",enterMan.getName());
                intent.putExtra("phone",enterMan.getPhone());
                intent.putExtra("idCard",enterMan.getAddress());
                setResult(2,intent);
                finish();
                break;
        }
    }

    private void getEnterMan(String uid) {
        OkHttpUtils.post().url(Constant.ENTERMAN_LIST)
                .addParams("uid", uid)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getInt("status") == 1) {
                        EnterManList list = new Gson().fromJson(response, EnterManList.class);
                        List<EnterManList.DataBean> data = list.getData();
                        for (int i = 0; i < data.size(); i++) {
                            EnterMan enterMan = new EnterMan(String.valueOf(data.get(i).getId()), data.get(i).getRealname(), data.get(i).getMobile(), data.get(i).getId_card());
                            mList.add(enterMan);
                            map.put(i, false);
                        }
                        mEnterManAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

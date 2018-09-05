package com.yunju.app.activity;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.yunju.app.R;
import com.yunju.app.base.BaseActivity;
import com.yunju.app.util.Constant;
import com.yunju.app.util.SharedPreferenceUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 发布新房间房源
 */
public class RentOutWayActivity extends BaseActivity {


    @BindView(R.id.titleview_title)
    TextView tvTitle;
    private String mUid,mLandlordId;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_rent_out_way;
    }

    @Override
    protected void initView() {
    mUid = SharedPreferenceUtil.getAccessToken();
    }


    @Override
    protected void initData() {
        tvTitle.setText("发布房屋");
    }

    @Override
    protected void initEvent() {

    }

    @OnClick({R.id.titleview_btnLeft, R.id.rent_out_way_new, R.id.rent_out_way_copy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.titleview_btnLeft:
                finish();
                break;
            case R.id.rent_out_way_new:
             openActivity(RentHouseTypeActivity.class);
                break;
            case R.id.rent_out_way_copy:
                openActivity(CopyTenementActivity.class);
                break;
        }
    }


}

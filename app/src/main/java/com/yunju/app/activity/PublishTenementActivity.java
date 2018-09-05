package com.yunju.app.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunju.app.R;
import com.yunju.app.base.BaseActivity;
import com.yunju.app.util.Constant;
import com.yunju.app.util.DialogUtil;
import com.yunju.app.util.SharedPreferenceUtil;
import com.yunju.app.widget.HeadZoomScrollView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class PublishTenementActivity extends BaseActivity {

    @BindView(R.id.titleview_title)
    TextView tvTitle;
    @BindView(R.id.publish_tenement_image)
    ImageView ivImages;
    @BindView(R.id.publish_tenement_location)
    TextView tvLocation;
    @BindView(R.id.publish_tenement_location_status)
    TextView tvLocationStatus;
    @BindView(R.id.publish_tenement_detail)
    TextView tvDetail;
    @BindView(R.id.publish_tenement_detail_status)
    TextView tvDetailStatus;
    @BindView(R.id.publish_tenement_describe)
    TextView tvDescribe;
    @BindView(R.id.publish_tenement_describe_status)
    TextView tvDescribeStatus;
    @BindView(R.id.publish_tenement_facility)
    TextView tvFacility;
    @BindView(R.id.publish_tenement_facility_status)
    TextView tvFacilityStatus;
    @BindView(R.id.publish_tenement_rule)
    TextView tvRule;
    @BindView(R.id.publish_tenement_rule_status)
    TextView tvRuleStatus;
    @BindView(R.id.publish_tenement_price)
    TextView tvPrice;
    @BindView(R.id.publish_tenement_price_status)
    TextView tvPriceStatus;
    @BindView(R.id.publish_tenement_identity)
    TextView tvIdentity;
    @BindView(R.id.publish_tenement_identity_status)
    TextView tvIdentityStatus;
    @BindView(R.id.publish_tenement_scrollview)
    HeadZoomScrollView scrollview;
    private List<String> picList;
    private String mUid;
    @Override
    public int getLayoutResource() {
        return R.layout.activity_publish_tenement;
    }

    @Override
    protected void initView() {
        tvTitle.setText("完成7步即可发布房源");
        if (picList != null) {
            scrollview.setZoomView(ivImages);
        }
        mUid = SharedPreferenceUtil.getAccessToken();
    }


    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.i("PublishTenementActivity","调用了———onNewIntent");
    }

    @OnClick({R.id.titleview_btnLeft, R.id.publish_tenement_location_layout, R.id.publish_tenement_detail_layout,
            R.id.publish_tenement_describe_layout, R.id.publish_tenement_facility_layout, R.id.publish_tenement_rule_layout,
            R.id.publish_tenement_price_layout, R.id.publish_tenement_identity_layout, R.id.publish_tenement_btnDelete,
            R.id.publish_tenement_btnSubmit,R.id.publish_tenement_image})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.titleview_btnLeft:
                finish();
                break;
            case R.id.publish_tenement_image:
                openActivity(EditTenementImageActivity.class);
                break;
            case R.id.publish_tenement_location_layout:
                openActivity(EditTenementLocationActivity.class);
                break;
            case R.id.publish_tenement_detail_layout:
                openActivity(EditTenementDetailActivity.class);
                break;
            case R.id.publish_tenement_describe_layout:
                openActivity(EditTenementDescribeActivity.class);
                break;
            case R.id.publish_tenement_facility_layout:
                openActivity(EditTenementFacilityActivity.class);
                break;
            case R.id.publish_tenement_rule_layout:
                openActivity(EditTenementRuleActivity.class);
                break;
            case R.id.publish_tenement_price_layout:
                openActivity(EditTenementPriceActivity.class);
                break;
            case R.id.publish_tenement_identity_layout:
                openActivity(EditIdentityActivity.class);
                break;
            case R.id.publish_tenement_btnDelete:
                DialogUtil.showDialogCallback(this, "您确定删除此房源吗？\n删除后不可撤销。", new DialogUtil.DialogListener() {
                    @Override
                    public void onClickPositive() {
                        finish();
                    }
                });
                break;
            case R.id.publish_tenement_btnSubmit:
                break;
        }
    }
}

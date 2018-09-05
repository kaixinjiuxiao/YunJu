package com.yunju.app.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.yunju.app.R;
import com.yunju.app.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class EditTenementPriceActivity extends BaseActivity {

    @BindView(R.id.titleview_title)
    TextView tvTitle;
    @BindView(R.id.titleview_btnRight)
    Button btnRight;
    @BindView(R.id.edit_tenement_price_radiogroup)
    RadioGroup radiogroup;
    @BindView(R.id.unsubscribe)
    TextView mUnsubscribe;
    @BindView(R.id.unit)
    TextView mUnit;
    @BindView(R.id.edit_tenement_price_rb_yes)
    RadioButton mEditTenementPriceRbYes;
    @BindView(R.id.edit_tenement_price_rb_no)
    RadioButton mEditTenementPriceRbNo;
    @BindView(R.id.imgYes)
    ImageView mImgYes;
    @BindView(R.id.imgNo)
    ImageView mImgNo;
    @BindView(R.id.editYaJin)
    EditText mEditYaJin;
    @BindView(R.id.linearYaJin)
    LinearLayout mLinearYaJin;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_edit_tenement_price;
    }

    @Override
    protected void initView() {
        tvTitle.setText("您将怎样收取费用");
        btnRight.setVisibility(View.VISIBLE);
        btnRight.setText("保存");
    }

    @Override
    protected void initData() {
        radiogroup.check(R.id.edit_tenement_price_rb_no);
    }

    @Override
    protected void initEvent() {

    }

    @OnClick({R.id.titleview_btnLeft, R.id.edit_tenement_price_btnNext, R.id.unsubscribe,R.id.edit_tenement_price_rb_yes,R.id.edit_tenement_price_rb_no})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.titleview_btnLeft:
                finish();
                break;
            case R.id.unsubscribe:
                Intent intent = new Intent(EditTenementPriceActivity.this, EditUnsubscribeRuleActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.edit_tenement_price_btnNext:
                openActivity(EditIdentityActivity.class);
                finish();
                break;
            case R.id.edit_tenement_price_rb_yes:
                mLinearYaJin.setVisibility(View.VISIBLE);
                break;
            case R.id.edit_tenement_price_rb_no:
                mLinearYaJin.setVisibility(View.GONE);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 2) {
            String days = data.getStringExtra("days");
            String frese = data.getStringExtra("free");
        }
    }


}

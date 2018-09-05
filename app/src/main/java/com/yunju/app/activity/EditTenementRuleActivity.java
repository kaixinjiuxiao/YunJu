package com.yunju.app.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yunju.app.R;
import com.yunju.app.adapter.ReceptionTimeAdapter;
import com.yunju.app.base.BaseActivity;
import com.yunju.app.entity.ReceptionRule;
import com.yunju.app.widget.TenementRulePopupWindow;
import com.yunju.app.widget.WheelView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 入住要求
 */
public class EditTenementRuleActivity extends BaseActivity {

    @BindView(R.id.titleview_title)
    TextView tvTitle;
    @BindView(R.id.titleview_btnRight)
    Button btnRight;
    @BindView(R.id.edit_tenement_rule_checkinDate)
    TextView mRuleCheckInDate;
    @BindView(R.id.edit_tenement_rule_checkoutDate)
    TextView mRuleCheckOutDate;
    @BindView(R.id.edit_tenement_rule_clean)
    TextView mRuleClean;
    @BindView(R.id.edit_tenement_rule_replaceSheet)
    TextView mRuleReplace;
    @BindView(R.id.reception_time_recyclerview)
    RecyclerView mReceptionRecyler;
    private TenementRulePopupWindow mRulePopupWindow;
    private List<String> mTimes, mSwapRule, mReplace;
    private ReceptionRule mReceptionRule;
    private String mOtherPrompt;
    private ReceptionTimeAdapter mReceptionTimeAdapter;
    private List<String> mReceptionList = new ArrayList<>();
    @Override
    public int getLayoutResource() {
        return R.layout.activity_edit_tenement_rule;
    }

    @Override
    protected void initView() {
        tvTitle.setText("您对接待房客有哪些要求");
        btnRight.setVisibility(View.VISIBLE);
        btnRight.setText("保存");
        mReceptionRecyler.setLayoutManager(new LinearLayoutManager(this));
        mReceptionTimeAdapter = new ReceptionTimeAdapter(this,mReceptionList);
        mReceptionRecyler.setAdapter(mReceptionTimeAdapter);
    }

    @Override
    protected void initData() {
        initTime();
    }

    private void initTime() {
        mTimes = new ArrayList<>();
        mSwapRule = new ArrayList<>();
        mReplace = new ArrayList<>();
        for (int i = 0; i <= 24; i++) {
            if (i < 10) {
                mTimes.add("0" + i + ":00");
            } else {
                mTimes.add(i + ":00");
            }
        }
        mSwapRule.add("1客1扫");
        mReplace.add("1客1换");
        for (int i = 1; i <= 7; i++) {
            mSwapRule.add(i + "天1扫");
            mReplace.add(i + "天1换");
        }


    }

    @Override
    protected void initEvent() {
        mReceptionTimeAdapter.setOnDeleteReceptionTimeListener(new ReceptionTimeAdapter.OnDeleteReceptionTimeListener() {
            @Override
            public void deleteReceptionTime(int position) {
                mReceptionList.remove(position);
                mReceptionTimeAdapter.notifyDataSetChanged();
            }
        });
    }

    @OnClick({R.id.titleview_btnLeft, R.id.titleview_btnRight, R.id.edit_tenement_rule_btnNext, R.id.edit_tenement_rule_checkinDate,
            R.id.edit_tenement_rule_checkoutDate, R.id.edit_tenement_rule_clean, R.id.edit_tenement_rule_replaceSheet,
            R.id.edit_tenement_rule_reception, R.id.edit_tenement_rule_remind,R.id.edit_tenement_rule_addTime
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.titleview_btnLeft:
                finish();
                break;
            case R.id.titleview_btnRight:
                break;
            case R.id.edit_tenement_rule_btnNext:
                openActivity(EditTenementPriceActivity.class);
                finish();
                break;
            case R.id.edit_tenement_rule_checkinDate:
                showWindow("房客入住时间", mTimes, mRuleCheckInDate, 0);
                setBackgroundAlpha(EditTenementRuleActivity.this, 0.5f);
                break;
            case R.id.edit_tenement_rule_checkoutDate:
                showWindow("房客离店时间", mTimes, mRuleCheckOutDate, 1);
                setBackgroundAlpha(EditTenementRuleActivity.this, 0.5f);
                break;
            case R.id.edit_tenement_rule_clean:
                showWindow("请选择", mSwapRule, mRuleClean, 2);
                setBackgroundAlpha(EditTenementRuleActivity.this, 0.5f);
                break;
            case R.id.edit_tenement_rule_replaceSheet:
                showWindow("请选择", mReplace, mRuleReplace, 2);
                setBackgroundAlpha(EditTenementRuleActivity.this, 0.5f);
                break;
            case R.id.edit_tenement_rule_addTime:
                showWindow("您的接待时间", mTimes, mReceptionRecyler, 3);
                setBackgroundAlpha(EditTenementRuleActivity.this, 0.5f);
                break;
            case R.id.edit_tenement_rule_reception:
                Intent intent = new Intent(EditTenementRuleActivity.this, EditTenementReceptionRuleActivity.class);
                if (mReceptionRule != null) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("rule", mReceptionRule);
                    intent.putExtras(bundle);
                }
                startActivityForResult(intent, 1);
                break;
            case R.id.edit_tenement_rule_remind:
                Intent intent2 = new Intent(EditTenementRuleActivity.this, EditTextActivity.class);
                Bundle b2=new Bundle();
                b2.putInt("flag",EditTextActivity.FLAG_OHTER_PROMAP);
                intent2.putExtras(b2);
                if(TextUtils.isEmpty(mOtherPrompt)){
                    intent2.putExtra("other",mOtherPrompt);
                }
                startActivityForResult(intent2,1);
                break;
        }
    }

    private String chooice = "";
    private String times;
    private void showWindow(String title, List<String> dataList, final View target, final int flag) {

        View layout = View.inflate(EditTenementRuleActivity.this, R.layout.tenement_rule_house, null);
        final PopupWindow mHouseTypePopw = new PopupWindow(layout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mHouseTypePopw.setFocusable(true); // 点击空白处时，隐藏掉POP窗口
        mHouseTypePopw.setOutsideTouchable(true); // 点击外部时，隐藏掉POP窗口
        mHouseTypePopw.setBackgroundDrawable(new BitmapDrawable());
        mHouseTypePopw.setAnimationStyle(R.style.popupwindow_anim_style);
        mHouseTypePopw.showAtLocation(EditTenementRuleActivity.this.findViewById(R.id.tenmentRule), Gravity.BOTTOM, 20, 0);

        TextView cancel = layout.findViewById(R.id.cancelAction);
        TextView titleView = layout.findViewById(R.id.title);
        titleView.setText(title);
        TextView sure = layout.findViewById(R.id.sureAction);
        final WheelView wheelView = layout.findViewById(R.id.wheelView);
        LinearLayout linearLayout  = layout.findViewById(R.id.linearRule);
        if(flag==3){
            linearLayout.setVisibility(View.VISIBLE);
        }
      final   WheelView wheelView2 = layout.findViewById(R.id.wheelView2);
        wheelView.setOffset(1);
        wheelView2.setOffset(1);
        wheelView.setItems(dataList);
        wheelView2.setItems(dataList);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHouseTypePopw.dismiss();
                setBackgroundAlpha(EditTenementRuleActivity.this, 1.0f);
            }
        });
        wheelView.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                super.onSelected(selectedIndex, item);
                chooice = item;
            }
        });
        wheelView2.setOnWheelViewListener(new WheelView.OnWheelViewListener(){
            @Override
            public void onSelected(int selectedIndex, String item) {
                super.onSelected(selectedIndex, item);

            }
        });
        mHouseTypePopw.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(EditTenementRuleActivity.this, 1.0f);
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag == 0) {
                    ((TextView)target).setText(chooice + "后");
                } else if (flag == 1) {
                    ((TextView)target).setText(chooice + "前");
                } else if(flag==2){
                    ((TextView)target).setText(chooice);
                }else{
                   mReceptionList.add(wheelView.getSeletedItem()+"-"+wheelView2.getSeletedItem());
                   mReceptionTimeAdapter.notifyDataSetChanged();
                }
                mHouseTypePopw.dismiss();
                setBackgroundAlpha(EditTenementRuleActivity.this, 1.0f);
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            switch (resultCode){
                case 2:
                    Bundle bundle =data.getExtras();
                    mReceptionRule = (ReceptionRule) bundle.getSerializable("saveDate");
                    break;
                case 3:
                    mOtherPrompt = data.getStringExtra("content");
                    break;
            }
        }
    }
}

package com.yunju.app.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yunju.app.R;
import com.yunju.app.base.BaseActivity;
import com.yunju.app.widget.WheelView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author: captain
 * Time:  2018/7/2 0002
 * Describe:
 */
public class EditUnsubscribeRuleActivity extends BaseActivity {
    @BindView(R.id.titleview_title)
    TextView mTitleviewTitle;
    @BindView(R.id.titleview_btnRight)
    Button mTitleviewBtnRight;
    @BindView(R.id.titleview_ivRight)
    ImageView mTitleviewIvRight;
    @BindView(R.id.days)
    TextView mDays;
    @BindView(R.id.free)
    TextView mFree;
    private List<String>daysList;
    private List<String>freeList;
    @Override
    public int getLayoutResource() {
        return R.layout.activity_edit_unsubscribe_rule;
    }

    @Override
    protected void initView() {
        mTitleviewTitle.setText("退款规则");
        mTitleviewBtnRight.setText("保存");
        mTitleviewBtnRight.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initData() {
        daysList = new ArrayList<>();
        daysList.add("当天");
        daysList.add("前1天");
        daysList.add("前2天");
        daysList.add("前3天");
        daysList.add("前4天");
        daysList.add("前5天");
        daysList.add("前6天");
        daysList.add("前7天");
        daysList.add("前15天");
        freeList = new ArrayList<>();
        for (int i = 0; i <=10 ; i++) {
            if(i==0){
                freeList.add("0%");
            }else{
                freeList.add(i+"0%");
            }
        }
    }

    @Override
    protected void initEvent() {

    }



    @OnClick({R.id.titleview_btnLeft, R.id.titleview_btnRight, R.id.linearDays, R.id.linearFree})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.titleview_btnLeft:
                finish();
                break;
            case R.id.titleview_btnRight:
                Intent intent = new Intent();
                intent.putExtra("days",mDays.getText().toString());
                intent.putExtra("free",mFree.getText().toString());
                setResult(2,intent);
                finish();
                break;
            case R.id.linearDays:
                showWindow("入住前",daysList,mDays,0);
                setBackgroundAlpha(EditUnsubscribeRuleActivity.this, 0.5f);
                break;
            case R.id.linearFree:
                showWindow("住房费百分比",freeList,mFree,0);
                setBackgroundAlpha(EditUnsubscribeRuleActivity.this, 0.5f);
                break;
        }
    }


    private String chooice;
    private void showWindow(String title, List<String> dataList, final View target, final int flag) {

        View layout = View.inflate(EditUnsubscribeRuleActivity.this, R.layout.tenement_rule_house, null);
        final PopupWindow mHouseTypePopw = new PopupWindow(layout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mHouseTypePopw.setFocusable(true); // 点击空白处时，隐藏掉POP窗口
        mHouseTypePopw.setOutsideTouchable(true); // 点击外部时，隐藏掉POP窗口
        mHouseTypePopw.setBackgroundDrawable(new BitmapDrawable());
        mHouseTypePopw.setAnimationStyle(R.style.popupwindow_anim_style);
        mHouseTypePopw.showAtLocation(EditUnsubscribeRuleActivity.this.findViewById(R.id.unsubscribeRule), Gravity.BOTTOM, 20, 0);

        TextView cancel = layout.findViewById(R.id.cancelAction);
        TextView titleView = layout.findViewById(R.id.title);
        titleView.setText(title);
        TextView sure = layout.findViewById(R.id.sureAction);
        final WheelView wheelView = layout.findViewById(R.id.wheelView);
        LinearLayout linearLayout  = layout.findViewById(R.id.linearRule);

        wheelView.setOffset(1);

        wheelView.setItems(dataList);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHouseTypePopw.dismiss();
                setBackgroundAlpha(EditUnsubscribeRuleActivity.this, 1.0f);
            }
        });
        wheelView.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                super.onSelected(selectedIndex, item);
                chooice = item;
            }
        });

        mHouseTypePopw.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(EditUnsubscribeRuleActivity.this, 1.0f);
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TextView)target).setText(chooice);
                mHouseTypePopw.dismiss();
                setBackgroundAlpha(EditUnsubscribeRuleActivity.this, 1.0f);
            }
        });

    }

}

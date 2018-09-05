package com.yunju.app.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yunju.app.R;
import com.yunju.app.base.BaseActivity;
import com.yunju.app.util.StringUtil;
import com.yunju.app.widget.CustomEditTextShowNum;

import butterknife.BindView;
import butterknife.OnClick;

public class EditTextActivity extends BaseActivity {


    @BindView(R.id.titleview_title)
    TextView tvTitle;
    @BindView(R.id.titleview_btnRight)
    Button btnRight;
    @BindView(R.id.edit_text_content)
    CustomEditTextShowNum etContent;
    public static final int FLAG_TRAFFIC = 1;
    public static final int FLAG_PERIPHERY = 2;
    public static final int FLAG_OHTER_PROMAP = 3;
    private int flag;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_edit_text;
    }

    @Override
    protected void initView() {
        btnRight.setVisibility(View.VISIBLE);
        btnRight.setText("保存");
        etContent.setMaxNum(500);
        etContent.setMinLines(14);
    }

    @Override
    protected void initData() {
        flag = getIntent().getIntExtra("flag", FLAG_TRAFFIC);
        if (flag == FLAG_TRAFFIC) {
            tvTitle.setText("交通位置");
            etContent.setContentHint("位于朝阳门外大街，从朝阳门地铁站A口5分钟到达。楼下有38路、101路等多条公交线路，十分方便。");
        } else if (flag == FLAG_PERIPHERY) {
            tvTitle.setText("周边介绍");
            etContent.setContentHint("小区东侧有条美食街，特别推荐冒牌火锅菜、海鲜烧烤；\n附近配有超市便利店、辉煌量贩式KTV、24小时药店、建行ATM，生活便利；\n最近的医院是**医院，距离2公里，车程仅5分钟，出租车起步价就到。");
        } else if (flag == FLAG_OHTER_PROMAP) {
            tvTitle.setText("其它入住提示");
            etContent.setContentHint("若对顾客又其他入住要求，请说明；\n例如：水电燃气费用，额外打扫费用，入住所需证件等");
        }
    }

    @Override
    protected void initEvent() {

    }

    @OnClick({R.id.titleview_btnLeft, R.id.titleview_btnRight})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.titleview_btnLeft:
                finish();
                break;
            case R.id.titleview_btnRight:
                String contentStr = etContent.getContentText();
                Intent intent = new Intent();

                if (StringUtil.isNullString(contentStr) || contentStr.length() < 10) {
                    toast("输入内容需不少于30个字符");
                    return;
                }
                intent.putExtra("content",contentStr);
                if (flag == FLAG_TRAFFIC) {
                } else if (flag == FLAG_PERIPHERY) {
                } else if (flag == FLAG_OHTER_PROMAP) {
                    setResult(3,intent);
                }
                finish();
                break;
        }
    }
}

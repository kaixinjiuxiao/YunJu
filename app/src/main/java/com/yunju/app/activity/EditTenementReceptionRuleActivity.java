package com.yunju.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunju.app.R;
import com.yunju.app.base.BaseActivity;
import com.yunju.app.entity.ReceptionRule;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author: captain
 * Time:  2018/6/28 0028
 * Describe:
 */
public class EditTenementReceptionRuleActivity extends BaseActivity {
    @BindView(R.id.titleview_title)
    TextView mTitleviewTitle;
    @BindView(R.id.titleview_btnRight)
    Button mTitleviewBtnRight;
    @BindView(R.id.imgChild)
    ImageView mImgChild;
    @BindView(R.id.imgOld)
    ImageView mImgOld;
    @BindView(R.id.imgMan)
    ImageView mImgMan;
    @BindView(R.id.imgWoman)
    ImageView mImgWoman;
    @BindView(R.id.imgSmoking)
    ImageView mImgSmoking;
    @BindView(R.id.imgPet)
    ImageView mImgPet;
    @BindView(R.id.imgCook)
    ImageView mImgCook;
    @BindView(R.id.imgParty)
    ImageView mImgParty;
    @BindView(R.id.imgPhoto)
    ImageView mImgPhoto;
    @BindView(R.id.imgBed)
    ImageView mImgBed;
    @BindView(R.id.imgAddMan)
    ImageView mImgAddMan;
    private List<ImageView> mImageViews;
    @Override
    public int getLayoutResource() {
        return R.layout.activity_edit_reception_rule;
    }

    @Override
    protected void initView() {

        mTitleviewTitle.setText("设置接待要求");
        mTitleviewBtnRight.setText("保存");
        mTitleviewBtnRight.setVisibility(View.VISIBLE);
        mImageViews = new ArrayList<>();
        mImageViews.add(mImgChild);
        Intent data = getIntent();
        Bundle bundle = data.getExtras();
        if(bundle!=null){
            ReceptionRule receptionRule = (ReceptionRule) bundle.getSerializable("rule");
            mImgChild.setSelected(receptionRule.isChild());
            mImgOld.setSelected(receptionRule.isOld());
            mImgMan.setSelected(receptionRule.isMan());
            mImgWoman.setSelected(receptionRule.isWoman());
            mImgSmoking.setSelected(receptionRule.isSmooking());
            mImgPet.setSelected(receptionRule.isPet());
            mImgCook.setSelected(receptionRule.isCook());
            mImgParty.setSelected(receptionRule.isParty());
            mImgPhoto.setSelected(receptionRule.isPhoto());
            mImgBed.setSelected(receptionRule.isAddBed());
            mImgAddMan.setSelected(receptionRule.isAddMan());
        }

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

    }

    @OnClick({R.id.titleview_btnLeft, R.id.reception_rule_child, R.id.reception_rule_old, R.id.reception_rule_man,
            R.id.reception_rule_woman, R.id.reception_rule_smoking, R.id.reception_rule_pet, R.id.reception_rule_cook,
            R.id.reception_rule_party, R.id.reception_rule_photo, R.id.reception_rule_addBed, R.id.reception_rule_addMan,
            R.id.titleview_btnRight
            })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.titleview_btnLeft:
                finish();
                break;
            case R.id.reception_rule_child:
                setImgSelected(mImgChild);
                break;
            case R.id.reception_rule_old:
                setImgSelected(mImgOld);
                break;
            case R.id.reception_rule_man:
                setImgSelected(mImgMan);
                break;
            case R.id.reception_rule_woman:
                setImgSelected(mImgWoman);
                break;
            case R.id.reception_rule_smoking:
                setImgSelected(mImgSmoking);
                break;
            case R.id.reception_rule_pet:
                setImgSelected(mImgPet);
                break;
            case R.id.reception_rule_cook:
                setImgSelected(mImgCook);
                break;
            case R.id.reception_rule_party:
                setImgSelected(mImgParty);
                break;
            case R.id.reception_rule_photo:
                setImgSelected(mImgPhoto);
                break;
            case R.id.reception_rule_addBed:
                setImgSelected(mImgBed);
                break;
            case R.id.reception_rule_addMan:
                setImgSelected(mImgAddMan);
                break;
            case R.id.titleview_btnRight:
                ReceptionRule receptionRule = new ReceptionRule(mImgChild.isSelected(),mImgOld.isSelected(),mImgMan.isSelected()
                        ,mImgWoman.isSelected(),mImgSmoking.isSelected(),mImgPet.isSelected(),mImgCook.isSelected(),mImgParty.isSelected()
                        ,mImgPhoto.isSelected(),mImgBed.isSelected(),mImgAddMan.isSelected());
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("saveDate",receptionRule);
                        intent.putExtras(bundle);

                setResult(2,intent);
                finish();
                break;
                default:
                    break;
        }
    }

    private void setImgSelected(ImageView img){
        if(img.isSelected()){
            img.setSelected(false);
        }else{
            img.setSelected(true);
        }
    }


}

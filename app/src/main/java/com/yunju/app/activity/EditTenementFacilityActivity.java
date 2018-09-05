package com.yunju.app.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yunju.app.R;
import com.yunju.app.adapter.HouseFaclityParentAdapter;
import com.yunju.app.base.BaseActivity;
import com.yunju.app.entity.HouseFaclityData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class EditTenementFacilityActivity extends BaseActivity {


    @BindView(R.id.titleview_title)
    TextView tvTitle;
    @BindView(R.id.titleview_btnRight)
    Button btnRight;
    @BindView(R.id.edit_tenement_facilityrecyclerview)
    RecyclerView mEditRecyclerview;
    private HouseFaclityParentAdapter mFaclityParentAdapter;
    private List<HouseFaclityData> mList;
    private List<Integer> mChooiceData = new ArrayList<>();
    @Override
    public int getLayoutResource() {
        return R.layout.activity_edit_tenement_facility;
    }

    @Override
    protected void initView() {
        tvTitle.setText("您有哪些设施可以提供");
        btnRight.setVisibility(View.VISIBLE);
        btnRight.setText("保存");
        mList = new ArrayList<>();
        mChooiceData.add(15);
        mChooiceData.add(3);
        mChooiceData.add(29);
        mChooiceData.add(35);
        mChooiceData.add(42);
        mChooiceData.add(50);

        getData();
        for (int i = 0; i <mList.size() ; i++) {
            HouseFaclityData data = mList.get(i);
            for (int j = 0; j <data.getData().size() ; j++) {
                for (int k = 0; k <mChooiceData.size() ; k++) {
                    if(data.getData().get(j).getId()==mChooiceData.get(k)){
                        data.getData().get(j).setSelected(true);
                    }
                }
            }
        }
        mEditRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mFaclityParentAdapter = new HouseFaclityParentAdapter(this,mList);
        mEditRecyclerview.setAdapter(mFaclityParentAdapter);


    }

    @Override
    protected void initData() {


    }




    private void getData(){
        List<HouseFaclityData.DataBean> homeList = new ArrayList<>();
        homeList.add(new HouseFaclityData.DataBean(2,R.drawable.facility_tv_selector,"电视",false));
        homeList.add(new HouseFaclityData.DataBean(3,R.drawable.facility_icebox_selector,"冰箱",false));
        homeList.add(new HouseFaclityData.DataBean(4,R.drawable.facility_washingmachine_selector,"洗衣机",false));
        homeList.add(new HouseFaclityData.DataBean(5,R.drawable.facility_wifi_selector,"无线网络",false));
        homeList.add(new HouseFaclityData.DataBean(6,R.drawable.facility_centralair_conditioning_selector,"空调",false));
        homeList.add(new HouseFaclityData.DataBean(7,R.drawable.facility_aircleaner_selector,"空气净化器",false));
        homeList.add(new HouseFaclityData.DataBean(8,R.drawable.facility_humidifier_selector,"加湿器",false));
        homeList.add(new HouseFaclityData.DataBean(9,R.drawable.facility_heating_selector,"暖气",false));
        homeList.add(new HouseFaclityData.DataBean(10,R.drawable.facility_kettle_selector,"电热水壶",false));
        homeList.add(new HouseFaclityData.DataBean(11,R.drawable.facility_hairdryer_selector,"电吹风",false));
        homeList.add(new HouseFaclityData.DataBean(12,R.drawable.facility_electriciron_selector,"电熨斗\\挂烫机",false));
        homeList.add(new HouseFaclityData.DataBean(13,R.drawable.facility_detergent_selector,"洗衣粉",false));
        homeList.add(new HouseFaclityData.DataBean(14,R.drawable.facility_dryingrack_selector,"晾衣架",false));
        homeList.add(new HouseFaclityData.DataBean(15,R.drawable.facility_cleantool_selector,"打扫工具",false));
        homeList.add(new HouseFaclityData.DataBean(16,R.drawable.facility_slipper_selector,"拖鞋",false));
        HouseFaclityData houseFaclityData = new HouseFaclityData("居家",homeList);
        mList.add(houseFaclityData);
        List<HouseFaclityData.DataBean> bathList = new ArrayList<>();
        bathList.add(new HouseFaclityData.DataBean(19,R.drawable.facility_independentbathroom_selector,"独立卫浴",false));
        bathList.add(new HouseFaclityData.DataBean(20,R.drawable.facility_towel_selector,"毛巾",false));
        bathList.add(new HouseFaclityData.DataBean(21,R.drawable.facility_bathtowel_selector,"浴巾",false));
        bathList.add(new HouseFaclityData.DataBean(22,R.drawable.facility_toothbrush_selector,"牙具",false));
        bathList.add(new HouseFaclityData.DataBean(23,R.drawable.facility_soap_selector,"香皂",false));
        bathList.add(new HouseFaclityData.DataBean(24,R.drawable.facility_hairandbodylotion_selector,"洗发水",false));
        bathList.add(new HouseFaclityData.DataBean(25,R.drawable.facility_tissues_selector,"卫生纸",false));
        HouseFaclityData bathFaclityData = new HouseFaclityData("卫浴",bathList);
        mList.add(bathFaclityData);
        List<HouseFaclityData.DataBean> kitchenList = new ArrayList<>();
        kitchenList.add(new HouseFaclityData.DataBean(27,R.drawable.facility_gasstove_selector,"燃气灶",false));
        kitchenList.add(new HouseFaclityData.DataBean(28,R.drawable.facility_inductioncooking_selector,"电磁炉",false));
        kitchenList.add(new HouseFaclityData.DataBean(29,R.drawable.facility_cookingpots_selector,"烹饪锅具",false));
        kitchenList.add(new HouseFaclityData.DataBean(30,R.drawable.facility_cuttingtool_selector,"刀具菜板",false));
        kitchenList.add(new HouseFaclityData.DataBean(31,R.drawable.facility_seasoning_selector,"调料",false));
        kitchenList.add(new HouseFaclityData.DataBean(32,R.drawable.facility_ricecooker_selector,"电饭煲",false));
        kitchenList.add(new HouseFaclityData.DataBean(33,R.drawable.facility_tableware_selector,"餐具",false));
        kitchenList.add(new HouseFaclityData.DataBean(34,R.drawable.facility_microwaveoven_selector,"微波炉",false));
        kitchenList.add(new HouseFaclityData.DataBean(35,R.drawable.facility_grill_selector,"烧烤器具",false));
        kitchenList.add(new HouseFaclityData.DataBean(36,R.drawable.facility_washer_selector,"洗涤用具",false));
        HouseFaclityData kitchenFaclityData = new HouseFaclityData("餐厨",kitchenList);
        mList.add(kitchenFaclityData);
        List<HouseFaclityData.DataBean> entertainmentList = new ArrayList<>();
        entertainmentList.add(new HouseFaclityData.DataBean(38,R.drawable.facility_karaoke_selector,"卡拉OK",false));
        entertainmentList.add(new HouseFaclityData.DataBean(39,R.drawable.facility_mahjongmachine_selector,"麻将机",false));
        HouseFaclityData entertainmentData = new HouseFaclityData("娱乐",entertainmentList);
        mList.add(entertainmentData);

        List<HouseFaclityData.DataBean> aroundList = new ArrayList<>();
        aroundList.add(new HouseFaclityData.DataBean(41,R.drawable.facility_supermarket_selector,"超市",false));
        aroundList.add(new HouseFaclityData.DataBean(42,R.drawable.facility_vegetablemarket_selector,"菜市场",false));
        aroundList.add(new HouseFaclityData.DataBean(43,R.drawable.facility_conferenceroom_selector,"餐厅",false));
        aroundList.add(new HouseFaclityData.DataBean(44,R.drawable.facility_pharmacy_selector,"药店",false));
        aroundList.add(new HouseFaclityData.DataBean(45,R.drawable.facility_atm_selector,"提款机",false));
        aroundList.add(new HouseFaclityData.DataBean(46,R.drawable.facility_freeparking_selector,"免费停车",false));
        HouseFaclityData aroundData = new HouseFaclityData("周边500米",aroundList);
        mList.add(aroundData);

        List<HouseFaclityData.DataBean> specialList = new ArrayList<>();
        specialList.add(new HouseFaclityData.DataBean(48,R.drawable.facility_viewingterrace_selector,"观景露台",false));
        specialList.add(new HouseFaclityData.DataBean(49,R.drawable.facility_privategarden_selector,"私家花园",false));
        specialList.add(new HouseFaclityData.DataBean(50,R.drawable.facility_securityguard_selector,"保安",false));
        specialList.add(new HouseFaclityData.DataBean(51,R.drawable.facility_accesscontrol_selector,"门禁系统",false));
        specialList.add(new HouseFaclityData.DataBean(52,R.drawable.facility_fireextinguisher_selector,"灭火器",false));
        specialList.add(new HouseFaclityData.DataBean(53,R.drawable.facility_firealarm_selector,"火灾警报器",false));
        specialList.add(new HouseFaclityData.DataBean(54,R.drawable.facility_elevator_selector,"电梯",false));
        specialList.add(new HouseFaclityData.DataBean(55,R.drawable.facility_window_selector,"有窗户",false));
        HouseFaclityData specialData = new HouseFaclityData("特色及其他",specialList);
        mList.add(specialData);

        List<HouseFaclityData.DataBean> serviceList = new ArrayList<>();
        serviceList.add(new HouseFaclityData.DataBean(57,R.drawable.facility_luggage_selector,"行李寄存",false));
        HouseFaclityData serviceData = new HouseFaclityData("服务",serviceList);
        mList.add(serviceData);

    }


    @Override
    protected void initEvent() {


    }

    @OnClick({R.id.titleview_btnLeft, R.id.titleview_btnRight, R.id.edit_tenement_facility_btnNext})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.titleview_btnLeft:
                finish();
                break;
            case R.id.titleview_btnRight:
                break;
            case R.id.edit_tenement_facility_btnNext:
                openActivity(EditTenementRuleActivity.class);
                finish();
                break;
        }
    }
}

package com.yunju.app.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yunju.app.R;
import com.yunju.app.adapter.HouseBedAdapter;
import com.yunju.app.adapter.ReceptionTimeAdapter;
import com.yunju.app.adapter.TestHouseBedAdapter;
import com.yunju.app.base.BaseActivity;
import com.yunju.app.entity.HouseBedType;
import com.yunju.app.util.Constant;
import com.yunju.app.widget.AmountView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 编辑房屋详情
 */
public class EditTenementDetailActivity extends BaseActivity {


    @BindView(R.id.titleview_title)
    TextView tvTitle;
    @BindView(R.id.titleview_btnRight)
    Button btnRight;
    @BindView(R.id.edit_tenement_detail_type)
    TextView tvType;
    @BindView(R.id.edit_tenement_detail_housetype)
    TextView tvHousetype;
    @BindView(R.id.edit_tenement_detail_area)
    EditText etArea;
    @BindView(R.id.edit_tenement_detail_people)
    EditText edtNumber;
    @BindView(R.id.edit_tenement_detail_bed_recyclerview)
    RecyclerView bedRecyclerview;

    @BindView(R.id.edit_tenement_detail_num)
    EditText etNum;
    private PopupWindow mHouseTypePopw;
    private ReceptionTimeAdapter mReceptionTimeAdapter;
    private List<HouseBedType> mHouseBedTypes =new ArrayList<>();
    private List<String> mBedList = new ArrayList<>();
    private List<String> strings = new ArrayList<>();
    private List<String> mNums = new ArrayList<>();
    private List<String> mChooiceBed = new ArrayList<>();
    private PopupWindow mBedTypePop;
    private String mUid;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_edit_tenement_detail;
    }

    @Override
    protected void initView() {
        tvTitle.setText("您的房屋是怎样的");
        btnRight.setVisibility(View.VISIBLE);
        btnRight.setText("保存");
        steData();
        bedRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mReceptionTimeAdapter = new ReceptionTimeAdapter(this,mChooiceBed);
        bedRecyclerview.setAdapter(mReceptionTimeAdapter);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {
        mReceptionTimeAdapter.setOnDeleteReceptionTimeListener(new ReceptionTimeAdapter.OnDeleteReceptionTimeListener() {
            @Override
            public void deleteReceptionTime(int position) {
                mChooiceBed.remove(position);
                mReceptionTimeAdapter.notifyDataSetChanged();
            }
        });

    }

    @OnClick({R.id.titleview_btnLeft, R.id.titleview_btnRight, R.id.edit_tenement_detail_type_layout, R.id.edit_tenement_detail_housetype_layout, R.id.edit_tenement_detail_addBed, R.id.edit_tenement_detail_btnNext})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.titleview_btnLeft:
                finish();
                break;
            case R.id.titleview_btnRight:
                Intent intent = new Intent(this, PublishTenementActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;
            case R.id.edit_tenement_detail_type_layout:
                break;
            case R.id.edit_tenement_detail_housetype_layout:
                showHouseType();
                setBackgroundAlpha(EditTenementDetailActivity.this, 0.5f);
                break;
            case R.id.edit_tenement_detail_addBed:
                showBedType();
                setBackgroundAlpha(EditTenementDetailActivity.this, 0.5f);
                break;
            case R.id.edit_tenement_detail_btnNext:
                openActivity(EditTenementDescribeActivity.class);
                finish();
                break;
        }
    }

    private List<String> number = new ArrayList<>();

    private void steData() {
        for (int i = 0; i <= 20; i++) {
            number.add(i + "");
        }

        List<String> bigBed = new ArrayList<>();
        bigBed.add("2x1.8米");
        bigBed.add("2x1.5米");
        bigBed.add("1.8x1.5米");
        bigBed.add("2x2米");
        bigBed.add("2x1.6米");
        bigBed.add("2.2x2.2米");
        HouseBedType big = new HouseBedType("大床",bigBed);
        mHouseBedTypes.add(big);
        List<String> circleBed = new ArrayList<>();
        circleBed.add("直径2米");
        circleBed.add("直径2.2米");
        HouseBedType circle = new HouseBedType("园床",circleBed);
        mHouseBedTypes.add(circle);
        List<String> doubleBed = new ArrayList<>();
        doubleBed.add("0.9x1.9米上床,1.2x1.9米下床");
        doubleBed.add("1.2x1.9米上床,1.5x1.9米下床");
        doubleBed.add("1.35x1.9米上床,1.5x1.9米下床");
        doubleBed.add("0.9x1.9米");
        doubleBed.add("1x1.9米");
        doubleBed.add("1.2x1.9米");
        HouseBedType douBed = new HouseBedType("双层床",doubleBed);
        mHouseBedTypes.add(douBed);
        List<String> doubleSofa = new ArrayList<>();
        doubleSofa.add("2x1.5米");
        HouseBedType douSofa = new HouseBedType("双人沙发床",doubleSofa);
        mHouseBedTypes.add(douSofa);
        List<String> singleBed = new ArrayList<>();
        singleBed.add("2x1米");
        singleBed.add("2x1.2米");
        singleBed.add("1.9x1.2米");
        singleBed.add("2x0.8米");
        singleBed.add("2x1.35米");
        singleBed.add("2x1.1米");
        singleBed.add("2x1.3米");
        HouseBedType single = new HouseBedType("单人床",singleBed);
        mHouseBedTypes.add(single);
        List<String> singleSofa = new ArrayList<>();
        singleSofa.add("2x1.2米");
        HouseBedType singleSofaType = new HouseBedType("单人沙发床",singleSofa);
        mHouseBedTypes.add(singleSofaType);
        List<String> childBed = new ArrayList<>();
        childBed.add("1.5x1米");
        HouseBedType childType = new HouseBedType("儿童床",childBed);
        mHouseBedTypes.add(childType);
       List<String> childDoubleDeb = new ArrayList<>();
        childDoubleDeb.add("1.8x1.2(1)米");
        HouseBedType childDoubleType = new HouseBedType("儿童双层床",childBed);
        mHouseBedTypes.add(childDoubleType);
        List<String> taTaMi = new ArrayList<>();
        taTaMi.add("2x1.5米");
        taTaMi.add("2x1.2米");
        taTaMi.add("2x1.8米");
        taTaMi.add("大通铺");
        HouseBedType tatamiType= new HouseBedType("榻榻米",taTaMi);
        mHouseBedTypes.add(tatamiType);
        List<String> kang = new ArrayList<>();
        kang.add("大通铺");
        kang.add("2x1.5米");
        kang.add("2x1.8米");
        kang.add("2x2.2米");
        HouseBedType kangType= new HouseBedType("炕",taTaMi);
        mHouseBedTypes.add(kangType);

        for (int i = 0; i <mHouseBedTypes.size() ; i++) {
            mBedList.add(mHouseBedTypes.get(i).getType());
        }

        for (int i = 1; i <=10 ; i++) {
            mNums.add(i+"");
        }
    }

    private void showHouseType() {
        View layout = View.inflate(EditTenementDetailActivity.this, R.layout.tenement_details_house_type, null);
        mHouseTypePopw = new PopupWindow(layout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mHouseTypePopw.setFocusable(true); // 点击空白处时，隐藏掉POP窗口
        mHouseTypePopw.setOutsideTouchable(true); // 点击外部时，隐藏掉POP窗口
        mHouseTypePopw.setBackgroundDrawable(new BitmapDrawable());
        mHouseTypePopw.setAnimationStyle(R.style.popupwindow_anim_style);
        mHouseTypePopw.showAtLocation(EditTenementDetailActivity.this.findViewById(R.id.tenmmentDetails), Gravity.BOTTOM, 20, 0);
        final AmountView woshi = layout.findViewById(R.id.edit_ws_number);
        final AmountView keting = layout.findViewById(R.id.edit_kt_number);
        final AmountView weihengjian = layout.findViewById(R.id.edit_wsj_number);
        final AmountView cufang = layout.findViewById(R.id.edit_cf_number);
        final AmountView shufang = layout.findViewById(R.id.edit_sf_number);
        final AmountView yangtai = layout.findViewById(R.id.edit_yt_number);
        woshi.setDataList(number);
        keting.setDataList(number);
        weihengjian.setDataList(number);
        cufang.setDataList(number);
        shufang.setDataList(number);
        yangtai.setDataList(number);
        TextView cancel = layout.findViewById(R.id.cancelAction);
        TextView sure = layout.findViewById(R.id.sureAction);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHouseTypePopw.dismiss();
                setBackgroundAlpha(EditTenementDetailActivity.this, 1.0f);
            }
        });
        mHouseTypePopw.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(EditTenementDetailActivity.this, 1.0f);
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuffer stringBuffer = new StringBuffer();
                if (woshi.getAountText().equals("0")) {
                    toast("请至少选择卧室数量");
                    return;
                }
                stringBuffer.append(woshi.getAountText() + "室");
                stringBuffer.append(keting.getAountText() + "厅");
                stringBuffer.append(weihengjian.getAountText() + "卫");
                stringBuffer.append(cufang.getAountText() + "厨");
                stringBuffer.append(shufang.getAountText() + "书房");
                stringBuffer.append(yangtai.getAountText() + "阳台");
                tvHousetype.setText(stringBuffer);
                mHouseTypePopw.dismiss();
                setBackgroundAlpha(EditTenementDetailActivity.this, 1.0f);
            }
        });

    }



    private void showBedType() {
        View layout = View.inflate(EditTenementDetailActivity.this, R.layout.tenement_details_bed_type, null);
        mBedTypePop = new PopupWindow(layout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mBedTypePop.setFocusable(true); // 点击空白处时，隐藏掉POP窗口
        mBedTypePop.setOutsideTouchable(true); // 点击外部时，隐藏掉POP窗口
        mBedTypePop.setBackgroundDrawable(new BitmapDrawable());
        mBedTypePop.setAnimationStyle(R.style.popupwindow_anim_style);
        mBedTypePop.showAtLocation(EditTenementDetailActivity.this.findViewById(R.id.tenmmentDetails), Gravity.BOTTOM, 20, 0);
        ImageView imgClose = layout.findViewById(R.id.popwindow_bed_close);
        final TextView bedType = layout.findViewById(R.id.bed_type);
        final TextView bedSize = layout.findViewById(R.id.bed_size);
        final RecyclerView typeRecycler = layout.findViewById(R.id.recyclerview_bed_type);
        typeRecycler.setLayoutManager(new LinearLayoutManager(EditTenementDetailActivity.this));
        final RecyclerView sizeRecycler = layout.findViewById(R.id.recyclerview_bed_size);
        sizeRecycler.setLayoutManager(new LinearLayoutManager(EditTenementDetailActivity.this));
        final  RecyclerView numRecycler = layout.findViewById(R.id.recyclerview_bed_num);
        numRecycler.setLayoutManager(new LinearLayoutManager(EditTenementDetailActivity.this));
        final HouseBedAdapter typeAdapter = new HouseBedAdapter(EditTenementDetailActivity.this, mBedList);
        typeRecycler.setAdapter(typeAdapter);
        final HouseBedAdapter sizeAdapter = new HouseBedAdapter(EditTenementDetailActivity.this, strings);
        sizeRecycler.setAdapter(sizeAdapter);
        final HouseBedAdapter numAdapter = new HouseBedAdapter(EditTenementDetailActivity.this, mNums);
        numRecycler.setAdapter(numAdapter);
        typeAdapter.setOnItemClickListener(new TestHouseBedAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                typeAdapter.setSelection(position);
                String type = mBedList.get(position);
                bedType.setText(type);
                bedSize.setVisibility(View.VISIBLE);
                if(strings.size()!=0){
                    strings.clear();
                }
                for (int i = 0; i <mHouseBedTypes.size() ; i++) {
                    if(mHouseBedTypes.get(i).getType().equals(type)){
                       List<String> test = mHouseBedTypes.get(i).getSizeList();
                        for (int j = 0; j <test.size() ; j++) {
                            strings.add(test.get(j));
                        }
                    }
                }
                sizeAdapter.notifyDataSetChanged();
                typeRecycler.setVisibility(View.GONE);
                sizeRecycler.setVisibility(View.VISIBLE);
            }
        });

        sizeAdapter.setOnItemClickListener(new TestHouseBedAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                sizeAdapter.setSelection(position);
                bedSize.setText(strings.get(position));
                sizeRecycler.setVisibility(View.GONE);
               numRecycler.setVisibility(View.VISIBLE);
            }
        });

        numAdapter.setOnItemClickListener(new TestHouseBedAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                numAdapter.setSelection(position);
                mChooiceBed.add(bedType.getText()+" "+bedSize.getText()+" "+mNums.get(position)+"张");
                mReceptionTimeAdapter.notifyDataSetChanged();
                mBedTypePop.dismiss();
            }
        });

        bedType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sizeRecycler.setVisibility(View.GONE);
                numRecycler.setVisibility(View.GONE);
                typeRecycler.setVisibility(View.VISIBLE);
            }
        });

        bedSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeRecycler.setVisibility(View.GONE);
                numRecycler.setVisibility(View.GONE);
                sizeRecycler.setVisibility(View.VISIBLE);
            }
        });

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBedTypePop.dismiss();
                setBackgroundAlpha(EditTenementDetailActivity.this, 1.0f);
            }
        });
        mBedTypePop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(EditTenementDetailActivity.this, 1.0f);
            }
        });
    }


    private void saveHouseDetails(){
        Map<String,String>parmas = new HashMap<>();
        parmas.put("uid",mUid);
        parmas.put("hid",mUid);
        parmas.put("module","layout");
        parmas.put("type",mUid);
        parmas.put("people_number",mUid);
        parmas.put("houseNumber",mUid);
        parmas.put("area",mUid);
        OkHttpUtils.post().url(Constant.SAVE_HOUSE_INFO).params(parmas)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {

            }
        });
    }
}

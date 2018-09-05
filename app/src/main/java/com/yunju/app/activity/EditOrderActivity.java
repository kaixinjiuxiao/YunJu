package com.yunju.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yunju.app.R;
import com.yunju.app.base.BaseActivity;
import com.yunju.app.entity.CommitReserve;
import com.yunju.app.entity.HouseDetails;
import com.yunju.app.util.ActivityManager;
import com.yunju.app.util.Constant;
import com.yunju.app.util.DateUtil;
import com.yunju.app.util.SharedPreferenceUtil;
import com.yunju.app.widget.AmountView;
import com.yunju.app.widget.CalendarPopupWindow;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 填写订单
 * @author Administrator
 */
public class EditOrderActivity extends BaseActivity {


    @BindView(R.id.titleview_title)
    TextView tvTitle;
    @BindView(R.id.edit_order_date)
    TextView tvDate;
    @BindView(R.id.edit_order_stayNum)
    TextView tvStayNum;
    @BindView(R.id.edit_order_amountview_houseNum)
    AmountView amountviewHouseNum;
    @BindView(R.id.edit_order_amountview_personNum)
    AmountView amountviewPersonNum;
    @BindView(R.id.edit_order_name)
    EditText etName;
    @BindView(R.id.edit_order_phone)
    EditText etPhone;
    @BindView(R.id.edit_order_idNo)
    EditText etIdNo;
    @BindView(R.id.edit_order_insurance)
    TextView tvInsurance;
    @BindView(R.id.edit_order_price)
    TextView tvPrice;
    @BindView(R.id.commitOrder)
    TextView comOrder;
    @BindView(R.id.commit_order_title)
    TextView houseName;
    @BindView(R.id.commit_order_condition)
    TextView houseCondition;
    @BindView(R.id.commit_order_imglogo)
    ImageView houseLogo;
    @BindView(R.id.imgAdd)
    ImageView addContact;
    private CalendarPopupWindow popupWindowDate;
    private String oldFromDate,oldToDate,mHouseId,mHousePrice;
    private String fromDate, toDate,housePrice,totalPrice,mToken;
    private int stayNum,oldStayNum;
    private List<HouseDetails.OrderDateBean> mDateList = new ArrayList<>();
    private List<String> mDate = new ArrayList<>();
    private HouseDetails mHouseDetails;
    @Override
    public int getLayoutResource() {
        return R.layout.activity_edit_order;
    }

    @Override
    protected void initView() {
        ActivityManager.getInstance().addActivity(EditOrderActivity.this);
        tvTitle.setText("填写订单");
       // String str = "线上支付\u3000￥100.00"; //xml 空格：& #160; 窄空格：& #8201;
        mToken = SharedPreferenceUtil.getAccessToken();
        mHouseDetails = (HouseDetails) getIntent().getExtras().getSerializable("date");
        mDateList = mHouseDetails.getOrder_date();
        Bundle bundle = getIntent().getExtras();
        String receive = bundle.getString("receive");
        CommitReserve commitReserve = new Gson().fromJson(receive,CommitReserve.class);

        for (int i = 0; i <mDateList.size() ; i++) {
            try {
                int num = DateUtil.daysBetween(mDateList.get(i).getCheck_in_time(),mDateList.get(i).getCheck_out_time());
                for (int j = 0; j <num ; j++) {
                    mDate.add(DateUtil.getDate(mDateList.get(i).getCheck_in_time(),j));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        mHouseId =String.valueOf( commitReserve.getData().getHouse_info().getId());
        mHousePrice = String.valueOf(commitReserve.getData().getHouse_price());
        houseName.setText(commitReserve.getData().getHouse_info().getHname());
        String livingroom = commitReserve.getData().getHouse_info().getLiving_room();
        houseCondition.setText(livingroom+"居/宜住"+livingroom+"人/"+commitReserve.getData().getHouse_info().getBednum()+"床");
        housePrice = commitReserve.getData().getPay_price()+"";
        totalPrice = "线上支付　¥"+commitReserve.getData().getTotal_prices();
        int start = totalPrice.indexOf("¥");
        int end = totalPrice.length();
        SpannableStringBuilder spanBuilder = new SpannableStringBuilder(totalPrice);
        spanBuilder.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary)), start,end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        spanBuilder.setSpan(new RelativeSizeSpan(1.5f), start,end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        tvPrice.setText(spanBuilder);
        oldFromDate = commitReserve.getData().getStime();
        oldToDate = commitReserve.getData().getDtime();
        oldStayNum =Integer.parseInt(commitReserve.getData().getDay());
        tvDate.setText(DateUtil.getWantDate(oldFromDate,"yyyy年MM月dd日")+"-"+DateUtil.getWantDate(oldToDate,"yyyy年MM月dd日"));
        tvStayNum.setText("共"+commitReserve.getData().getDay()+"晚");
      //  Glide.with(this).load(commitReserve.getData().getHouse_info().getI)
    }

    @Override
    protected void initData() {
        List<String> pList = new ArrayList<>();
        pList.add("1人");
        pList.add("2人");
        pList.add("3人");
        pList.add("4人");
        pList.add("5人");
        pList.add("6人");
        pList.add("7人");
        pList.add("8人");
        amountviewPersonNum.setDataList(pList);
        List<String> hList = new ArrayList<>();
        hList.add("1套");
        amountviewHouseNum.setDataList(hList);

        popupWindowDate = new CalendarPopupWindow(this, getWindow(),mDate);
      //  String currentDate = DateUtil.dateToStr(new Date());
//        fromDate = currentDate;
//        toDate = DateUtil.getDate(currentDate, 1);
//        stayNum=1;
//        tvDate.setText(DateUtil.getWantDate(fromDate,"yyyy年MM月dd日")+"-"+DateUtil.getWantDate(toDate,"yyyy年MM月dd日"));
//        tvStayNum.setText("共"+stayNum+"晚");
    }

    @Override
    protected void initEvent() {
        popupWindowDate.setOnDateSubmitListener(new CalendarPopupWindow.OnDateSubmitListener() {
            @Override
            public void submit(String fDate, String tDate, int sNum) {
                fromDate=fDate;
                toDate=tDate;
                stayNum=sNum;
                tvDate.setText(DateUtil.getWantDate(fromDate,"yyyy年MM月dd日")+"-"+DateUtil.getWantDate(toDate,"yyyy年MM月dd日"));
                tvStayNum.setText("共"+stayNum+"晚");
                float d = Float.parseFloat(mHousePrice);
                BigDecimal bigDecimal = new BigDecimal(d*stayNum).setScale(2,BigDecimal.ROUND_HALF_UP);
                totalPrice = "线上支付　¥"+bigDecimal;
                int start = totalPrice.indexOf("¥");
                int end = totalPrice.length();
                SpannableStringBuilder spanBuilder = new SpannableStringBuilder(totalPrice);
                spanBuilder.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary)), start,end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                spanBuilder.setSpan(new RelativeSizeSpan(1.5f), start,end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                tvPrice.setText(spanBuilder);
            }
        });
    }

    @OnClick({R.id.titleview_btnLeft, R.id.edit_order_dateLay,R.id.commitOrder,R.id.imgAdd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.titleview_btnLeft:
                finish();
                break;
            case R.id.edit_order_dateLay:
             //   popupWindowDate.setData(fromDate,toDate,stayNum,mDate);
                popupWindowDate.show();
                break;
            case R.id.commitOrder:
                String name = etName.getText().toString();
                String phone = etPhone.getText().toString();
                String idCard = etIdNo.getText().toString();
                if(TextUtils.isEmpty(name)){
                    toast("入住人姓名不能为空");
                    return;
                }
                if(TextUtils.isEmpty(phone)){
                    toast("入住人手机号不能为空");
                    return;
                }
                if(TextUtils.isEmpty(idCard)){
                    toast("入住人身份证号码不能为空");
                    return;
                }
                if(TextUtils.isEmpty(fromDate)&&TextUtils.isEmpty(toDate)){
                    commitData(mToken,String.valueOf(oldToDate),name,phone,idCard);
                }else{
                    commitReserve(mToken,mHouseId,fromDate,toDate,String.valueOf(stayNum),name,phone,idCard);
                }
                break;
            case R.id.imgAdd:
                Intent intent = new Intent(this,AddEnterPeopleActivity.class);
               startActivityForResult(intent,1);
                break;
        }
    }

    /**
     * 重新预定
     */
    private void commitReserve(final String uid, String hid,final String stime,final String dtime,
                               final String days,final String name,final String phone,final String idCard){
        Map<String,String>parmas = new HashMap<>();
        parmas.put("uid",uid);
        parmas.put("hid",hid);
        parmas.put("stime",stime);
        parmas.put("dtime",dtime);
        parmas.put("day",days);
        OkHttpUtils.post().url(Constant.COMMIT_ORDER)
                .params(parmas)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.getInt("status")==1){
                        commitData(uid,days,name,phone,idCard);
                    }else{
                        toast(jsonObject.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    /**
     * 确定订单
     * @param uid
     * @param num
     * @param name
     * @param mobile
     * @param idCard
     */
    private void commitData(String uid,String num,String name,String mobile,String idCard){
        Map<String,String>parmas = new HashMap<>();
        parmas.put("uid",uid);
        parmas.put("people_number",num);
        parmas.put("occupant_realname",name);
        parmas.put("occupant_mobile",mobile);
        parmas.put("occupant_id_card",idCard);
        OkHttpUtils.post().url(Constant.SURE_ORDER)
                .params(parmas)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        //{"status":1,"msg":"success","data":{"order_sn":"1201805141515023740"}}
                       try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.getInt("status")==1){
                                JSONObject object = jsonObject.getJSONObject("data");
                                String orderCode = object.getString("order_sn");
                                Bundle bundle = new Bundle();
                                bundle.putString("money",tvPrice.getText().toString());
                                bundle.putString("osn",orderCode);
                                openActivity(PayOrderActivity.class,bundle);
                            }else{

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==2&&data !=null){
            etName.setText(data.getStringExtra("name"));
            etPhone.setText(data.getStringExtra("phone"));
            etIdNo.setText(data.getStringExtra("idCard"));
        }
    }
}

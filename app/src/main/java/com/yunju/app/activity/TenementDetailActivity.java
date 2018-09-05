package com.yunju.app.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.yunju.app.R;
import com.yunju.app.adapter.FaclityParentAdapter;
import com.yunju.app.adapter.FaclityRecyclerAdapter;
import com.yunju.app.adapter.NoticeRecyclerAdapter;
import com.yunju.app.adapter.TraceRecyclerAdapter;
import com.yunju.app.base.BaseActivity;
import com.yunju.app.entity.FacilityData;
import com.yunju.app.entity.HouseDetails;
import com.yunju.app.entity.Trace;
import com.yunju.app.interfac.OnRecyclerViewItemClickListener;
import com.yunju.app.util.ActivityManager;
import com.yunju.app.util.BannerImageLoader;
import com.yunju.app.util.Constant;
import com.yunju.app.util.DateUtil;
import com.yunju.app.util.SharedPreferenceUtil;
import com.yunju.app.util.StringUtil;
import com.yunju.app.widget.CalendarPopupWindow;
import com.yunju.app.widget.RecyclerPopupWindow;
import com.yunju.app.widget.calendar.KCalendar;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 酒店详情
 */
public class TenementDetailActivity extends BaseActivity {

    @BindView(R.id.banber)
    Banner banber;
    @BindView(R.id.tenement_detail_title)
    TextView tvTitle;
    @BindView(R.id.tenement_detail_describle)
    TextView tvDescrible;
    @BindView(R.id.tenement_detail_price)
    TextView tvPrice;
    @BindView(R.id.tenement_detail_original_price)
    TextView tvOriginalPrice;
    @BindView(R.id.tenement_detail_scrollview)
    ScrollView scrollview;
    @BindView(R.id.tenement_detail_titleview)
    LinearLayout titleview;
    @BindView(R.id.tenement_detail_kCalendar)
    KCalendar calendar;
    @BindView(R.id.tenement_detail_kCalendar_dateMonth)
    TextView tvCalendarMonth;
    @BindView(R.id.tenement_detail_rvTrace)
    RecyclerView rvTrace;
    @BindView(R.id.enterTime)
    TextView enterTime;
    @BindView(R.id.outTime)
    TextView outTime;
    @BindView(R.id.receptionTime)
    TextView receptionTime;
    @BindView(R.id.deposit)
    TextView deposit;
    @BindView(R.id.call_phone)
    TextView mCallPhone;
    @BindView(R.id.noticRecyceler)
    RecyclerView mNoticeRecycler;
    @BindView(R.id.recyclerFacilities)
    RecyclerView mFacilitiesRecycler;
    @BindView(R.id.collectCbx)
    CheckBox collectCbx;
    @BindView(R.id.houseMoney)
    TextView mHouseMoney;
    private CalendarPopupWindow calendarPopupWindow;
    private String fromDate, toDate, collect, mUserId, mSinglePrice;
    private int stayNum;
    private List<Trace> mTraceList;
    private TraceRecyclerAdapter mTraceRecyclerAdapter;
    private List<String> mNoticeList;
    private NoticeRecyclerAdapter mNoticeRecyclerAdapter;
    private List<HouseDetails.FacilitiesLimitBean> mLimitBeanList;
    private FaclityRecyclerAdapter mFaclityRecyclerAdapter;
    private Gson mGson;
    private List<FacilityData> mList;
    private FaclityParentAdapter mParentAdapter;
    private RecyclerPopupWindow mRecyclerPopupWindow;
    private List<HouseDetails.ImgBean> mBannerList;
    private String mHouseId, mPhone;
    private HouseDetails mHouseDetails;
    private List<String> test = new ArrayList<>();
    private List<HouseDetails.OrderDateBean> mDateList;
    @Override
    public int getLayoutResource() {
        return R.layout.activity_tenement_detail;
    }

    @Override
    protected void initView() {
        tvOriginalPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        ActivityManager.getInstance().addActivity(TenementDetailActivity.this);
    }

    @Override
    protected void initData() {
        mGson = new Gson();
        mList = new ArrayList<>();
        mUserId = SharedPreferenceUtil.getAccessToken();
        mHouseId = getIntent().getExtras().getString("house_id");
        getHouseDetails(mHouseId);


        rvTrace.setLayoutManager(new LinearLayoutManager(this));
        mTraceList = new ArrayList<>();
        mTraceRecyclerAdapter = new TraceRecyclerAdapter(this, mTraceList);
        rvTrace.setAdapter(mTraceRecyclerAdapter);

        mNoticeRecycler.setLayoutManager(new GridLayoutManager(this, 3));
        mNoticeList = new ArrayList<>();
        mNoticeRecyclerAdapter = new NoticeRecyclerAdapter(this, mNoticeList);
        mNoticeRecycler.setAdapter(mNoticeRecyclerAdapter);

        mFacilitiesRecycler.setLayoutManager(new GridLayoutManager(this, 4));
        mLimitBeanList = new ArrayList<>();
        mFaclityRecyclerAdapter = new FaclityRecyclerAdapter(this, mLimitBeanList);
        mFacilitiesRecycler.setAdapter(mFaclityRecyclerAdapter);

        mRecyclerPopupWindow = new RecyclerPopupWindow(this, this.getWindow(), "设施服务", mList);

    }


    private void initCalendarPopupWindow(){

        calendarPopupWindow = new CalendarPopupWindow(this, getWindow(),test);
        String currentDate = DateUtil.dateToStr(new Date());

        tvCalendarMonth.setText(DateUtil.getWantDate(currentDate, "yyyy年MM月"));
        if (StringUtil.isNullString(fromDate)) {
            fromDate = currentDate;
            toDate = DateUtil.getDate(currentDate, 1);
            stayNum = 1;
            setCalendarData(fromDate, toDate, stayNum,test);
        }
        String str = DateUtil.getDate(currentDate, 90);
        String currentMonth = DateUtil.getWantDate(currentDate, "yyyy-MM");
        String animMonth = DateUtil.getWantDate(str, "yyyy-MM");
        String[] vMonths = new String[]{currentMonth, animMonth};
        calendar.setVisibleMonth(vMonths);
        calendarPopupWindow.setVisibleMonth(vMonths);
        calendarPopupWindow.setOnDateSubmitListener(new CalendarPopupWindow.OnDateSubmitListener() {
            @Override
            public void submit(String mFromDate, String mToDate, int mStayNum) {
                fromDate = mFromDate;
                toDate = mToDate;
                stayNum = mStayNum;
                setCalendarData(fromDate, toDate, stayNum,test);
                mHouseMoney.setText("¥" + stayNum * Float.parseFloat(mSinglePrice));
            }
        });
    }



    public void setCalendarData(String fd, String td, int num,List<String>remarksList) {
        calendar.removeAllBgColor();
        calendar.removeAllMarks(KCalendar.FLAG_TEXT_MARK);

        String str="";
        for (int i = 0; i <remarksList.size() ; i++) {
            calendar.addMark(remarksList.get(i),"已预订");
            str+=remarksList.get(i);
        }
        while (str.contains(fd)){
            fd=DateUtil.getDate(fd,1);
            td=DateUtil.getDate(fd,1);
        }
        if(!str.contains(fd)){
            calendar.setCalendarDayBgColor(fd, R.color.colorPrimary);
            calendar.addMark(fd,"入住");
            fromDate=fd;
        }
        if (StringUtil.isNotNullString(td)) {
            calendar.setCalendarDayBgColor(td, R.color.colorPrimary);
            calendar.addMark(td,"离店");
            toDate=td;
        }
        if (num >= 2) {
            List<String> ls=new ArrayList<>();
            for (int i = 1; i < num; i++) {
                ls.add(DateUtil.getDate(fromDate, i));
            }
            calendar.setCalendarDaysBgColor(ls, R.color.colorAccent);
        }
    }

    @Override
    protected void initEvent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            scrollview.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if (scrollY <= 0) {
                        titleview.setBackgroundColor(Color.argb(0, 0, 0, 0));
                    } else {
                        float scale = (float) scrollY / 768;
                        float alpha = (255 * scale);
                        titleview.setBackgroundColor(Color.argb((int) alpha, 0, 0, 0));
                    }
                }
            });
        }
        calendar.setOnCalendarClickListener(new KCalendar.OnCalendarClickListener() {
            @Override
            public void onCalendarClick(int row, int col, String dateFormat) {
                calendarPopupWindow.show();
            }
        });
        calendar.setOnCalendarDateChangedListener(new KCalendar.OnCalendarDateChangedListener() {
            @Override
            public void onCalendarDateChanged(int year, int month) {
                tvCalendarMonth.setText(year + "年" + month + "月");
            }
        });
        mFaclityRecyclerAdapter.setOnRecyclerViewItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onRecyclerViewItemClick(View view, int position) {
                mRecyclerPopupWindow.show();
            }
        });
        banber.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if (mHouseDetails != null) {
                    Intent intent = new Intent(TenementDetailActivity.this, HouseImageActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("image", mHouseDetails);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
    }

    @OnClick({R.id.tenement_detail_back, R.id.tenement_detail_kCalendar_lastMonth,
            R.id.tenement_detail_kCalendar_nextMonth, R.id.tenement_detail_order,
            R.id.tenement_detail_share, R.id.tenement_detail_collect, R.id.collectCbx, R.id.call_phone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tenement_detail_back:
                finish();
                break;
            case R.id.tenement_detail_kCalendar_lastMonth:
                calendar.lastMonth();
                break;
            case R.id.tenement_detail_kCalendar_nextMonth:
                calendar.nextMonth();
                break;
            case R.id.tenement_detail_order:
                if (TextUtils.isEmpty(mUserId)) {
                    openActivity(LoginActivity.class);
                    return;
                }

                commitReserve(mUserId, mHouseId, fromDate, toDate, stayNum);
                break;
            case R.id.tenement_detail_share:
                break;
            case R.id.tenement_detail_collect:
                break;
            case R.id.collectCbx:
                if (TextUtils.isEmpty(mUserId)) {
                    openActivity(LoginActivity.class);
                    return;
                }
                if (collect.equals("0")) {
                    collectOrCancel(mUserId, mHouseId, "1");
                } else {
                    collectOrCancel(mUserId, mHouseId, "0");
                }
                break;
            case R.id.call_phone:
                callPhone(mPhone);
                break;
            default:
                break;
        }
    }

    private void getHouseDetails(String id) {
        Map<String, String> parmas = new HashMap<>();
        parmas.put("id", id);
        if (!TextUtils.isEmpty(mUserId)) {
            parmas.put("uid", mUserId);
        }
        OkHttpUtils.post().url(Constant.HOUSE_DETAILS)
                .params(parmas)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
            }

            @Override
            public void onResponse(String response, int id) {
                mHouseDetails = mGson.fromJson(response, HouseDetails.class);
                mBannerList = mHouseDetails.getImg();
                List<String> titles = new ArrayList<>();
                for (int i = 0; i < mBannerList.size(); i++) {
                    titles.add(mBannerList.get(i).getImgremark());
                }
                banber.setImageLoader(new BannerImageLoader());
                banber.setBannerTitles(titles);
                banber.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);
                banber.setImages(mBannerList);
                banber.start();
                collect = String.valueOf(mHouseDetails.getCollect());
                if (mHouseDetails.getCollect() == 1) {
                    collectCbx.setSelected(true);
                }
                tvTitle.setText(mHouseDetails.getHname());
                mSinglePrice = mHouseDetails.getMoney();
                tvPrice.setText("¥" + mHouseDetails.getMoney());
                mHouseMoney.setText("¥" + mHouseDetails.getMoney());
                tvOriginalPrice.setText("原价 ¥" + mHouseDetails.getOriginal_price());
                enterTime.setText("入住时间：" + mHouseDetails.getCheck_rule().getCheck_in_time());
                outTime.setText("退房时间：" + mHouseDetails.getCheck_rule().getCheck_out_time());
                receptionTime.setText("接待时间：" + mHouseDetails.getCheck_rule().getReception_time());
                if (mHouseDetails.getDeposit_price().equals("0.00")) {
                    deposit.setText("入住押金：无需押金");
                } else {
                    deposit.setText("入住押金：" + mHouseDetails.getDeposit_price() + "元");
                }
                for (int i = 0; i < mHouseDetails.getFacilities_limit().size(); i++) {
                    mLimitBeanList.add(mHouseDetails.getFacilities_limit().get(i));
                }
                mDateList = mHouseDetails.getOrder_date();
                for (int i = 0; i <mDateList.size() ; i++) {
                    try {
                        int num = DateUtil.daysBetween(mDateList.get(i).getCheck_in_time(),mDateList.get(i).getCheck_out_time());
                        for (int j = 0; j <num ; j++) {
                               test.add(DateUtil.getDate(mDateList.get(i).getCheck_in_time(),j));
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }

                initCalendarPopupWindow();

                String totalFac = mHouseDetails.getFacilities_count() + "";
                mLimitBeanList.add(new HouseDetails.FacilitiesLimitBean("", "全部" + totalFac));
                mFaclityRecyclerAdapter.notifyDataSetChanged();
                mPhone = mHouseDetails.getLandlord().getMobile();
                String outRuleDay = mHouseDetails.getRefund_rule().getRefund_remark();
                String outRuleRatio = mHouseDetails.getRefund_rule().getRefund_ratio() + "%";
                mTraceList.add(new Trace("预定成功", "如取消订单，订金全部退还"));
                mTraceList.add(new Trace("入住" + outRuleDay + "12:00前", "如提前退房，收取订金的" + outRuleRatio));
                mTraceList.add(new Trace("入住当天12:00前", "提前离店，收取剩余房费的100%"));
                mTraceList.add(new Trace("离店当天", ""));
                mTraceRecyclerAdapter.notifyDataSetChanged();
                for (int i = 0; i < mHouseDetails.getRules().size(); i++) {
                    mNoticeList.add(mHouseDetails.getRules().get(i));
                }
                mNoticeRecyclerAdapter.notifyDataSetChanged();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject object = jsonObject.getJSONObject("facilities_data");
                    Iterator<?> it = object.keys();
                    while (it.hasNext()) {
                        String aa = it.next().toString();
                        JSONArray array = object.getJSONArray(aa);
                        FacilityData facilityData = new FacilityData();
                        List<FacilityData.DateBean> list = new ArrayList<>();
                        for (int i = 0; i < array.length(); i++) {
                            list.add((mGson.fromJson(array.getJSONObject(i).toString(), FacilityData.DateBean.class)));
                        }
                        facilityData.setStr(aa);
                        facilityData.setList(list);

                        mList.add(facilityData);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * 收藏或者取消
     *
     * @param uid
     * @param hid
     * @param type
     */
    private void collectOrCancel(String uid, String hid, String type) {
        OkHttpUtils.post().url(Constant.COLLECT_OR_CANCEL)
                .addParams("uid", uid)
                .addParams("hid", hid)
                .addParams("type", type)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            collect = jsonObject.getString("type");
                            if (collect.equals("1")) {
                                collectCbx.setSelected(true);
                            } else {
                                collectCbx.setSelected(false);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    /**
     * 提交预定
     */
    private void commitReserve(String uid, String hid, String stime, String dtime, int days) {
        Map<String, String> parmas = new HashMap<>();
        parmas.put("uid", uid);
        parmas.put("hid", hid);
        parmas.put("stime", stime);
        parmas.put("dtime", dtime);
        parmas.put("day", String.valueOf(days));
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
                    if (jsonObject.getInt("status") == 1) {
                        Bundle bundle = new Bundle();
                        bundle.putString("receive", response);
                        bundle.putSerializable("date", mHouseDetails);
                        openActivity(EditOrderActivity.class, bundle);
                    } else {
                        toast(jsonObject.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mRecyclerPopupWindow.isShowing()) {
            mRecyclerPopupWindow.dismiss();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onResume() {
        super.onResume();
        mUserId = SharedPreferenceUtil.getAccessToken();
    }

}

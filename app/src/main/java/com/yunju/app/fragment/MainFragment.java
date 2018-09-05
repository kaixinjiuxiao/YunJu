package com.yunju.app.fragment;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.yunju.app.MyApplication;
import com.yunju.app.R;
import com.yunju.app.activity.SearchListActivity;
import com.yunju.app.adapter.MyFragmentPagerAdapter;
import com.yunju.app.base.BaseFragment;
import com.yunju.app.entity.FilterData;
import com.yunju.app.service.LocationService;
import com.yunju.app.util.DateUtil;
import com.yunju.app.util.DialogUtil;
import com.yunju.app.widget.CalendarPopupWindow;
import com.yunju.app.widget.ListPopupWindow;
import com.yunju.app.widget.SlideViewPager;
import com.zaaach.citypicker.CityPicker;
import com.zaaach.citypicker.adapter.OnPickListener;
import com.zaaach.citypicker.model.City;
import com.zaaach.citypicker.model.HotCity;
import com.zaaach.citypicker.model.LocateState;
import com.zaaach.citypicker.model.LocatedCity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainFragment extends BaseFragment {
    @BindView(R.id.fragment_main_city)
    TextView tvCity;
    @BindView(R.id.fragment_main_locate)
    TextView tvLocate;
    @BindView(R.id.fragment_main_tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.fragment_main_viewPager)
    SlideViewPager viewPager;
    @BindView(R.id.fragment_main_checkinDate)
    TextView tvCheckinDate;
    @BindView(R.id.fragment_main_checkoutDate)
    TextView tvCheckoutDate;
    @BindView(R.id.fragment_main_stayNum)
    TextView tvStayNum;
    @BindView(R.id.fragment_main_person)
    TextView tvPerson;
    private List<String> tabTextList;
    private ArrayList<Fragment> fragmentList;
    private MyFragmentPagerAdapter adapter;
    private CalendarPopupWindow popupWindowDate;
    public ListPopupWindow popupWindowPerson;
    private String fromDate, toDate,mCity,mProvince;
    private int stayNum;
    private FilterData selectedPerson;
    private String permissionInfo;
    private final int SDK_PERMISSION_REQUEST = 127;
    private LocationService locationService;
    private Dialog progressDialog;

    private int currentYear,currentMonth,currentDay;
    private List<String> mList =new ArrayList<>();
    private List<HotCity> hotCities;
    @Override
    public int getLayoutResource() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initData() {
        tabTextList = new ArrayList<>();
        tabTextList.add("精彩");
        tabTextList.add("别墅");
        tabTextList.add("海外");
        tabTextList.add("发现");
        tabTextList.add("特卖");
        hotCities = new ArrayList<>();
        hotCities.add(new HotCity("北京", "北京", "101010100"));
        hotCities.add(new HotCity("上海", "上海", "101020100"));
        hotCities.add(new HotCity("广州", "广东", "101280101"));
        hotCities.add(new HotCity("深圳", "广东", "101280601"));
        hotCities.add(new HotCity("杭州", "浙江", "101210101"));
        fragmentList = new ArrayList<>();
        for (int i = 0; i < tabTextList.size(); i++) {
            tabLayout.addTab(tabLayout.newTab());
            fragmentList.add(MainViewPagerFragment.newInstance(tabTextList.get(i)));
        }
        adapter = new MyFragmentPagerAdapter(getActivity().getSupportFragmentManager(), tabTextList, fragmentList);
        viewPager.setAdapter(adapter);
        viewPager.setSlide(false);
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(1);
        tabLayout.setupWithViewPager(viewPager);

        popupWindowDate = new CalendarPopupWindow(getActivity(), getActivity().getWindow(),mList);
        String currentDate = DateUtil.dateToStr(new Date());

        fromDate = currentDate;
        toDate = DateUtil.getDate(currentDate, 1);
        stayNum = 1;
        tvCheckinDate.setText(fromDate);
        tvCheckoutDate.setText(toDate);
        tvStayNum.setText("共" + stayNum + "晚");

        popupWindowPerson = new ListPopupWindow(getActivity(), getActivity().getWindow(), "选择入住人数", getPersonItemList());
        getPersimmions();



    }

    private List<FilterData> getPersonItemList() {
        List<FilterData> itemList = new ArrayList<>();
        itemList.add(new FilterData(1, -1, "1人"));
        itemList.add(new FilterData(2, -1, "2人"));
        itemList.add(new FilterData(3, -1, "3人"));
        itemList.add(new FilterData(4, -1, "4人"));
        itemList.add(new FilterData(5, -1, "5人"));
        itemList.add(new FilterData(6, -1, "6人"));
        itemList.add(new FilterData(7, -1, "7人"));
        itemList.add(new FilterData(8, -1, "8人"));
        itemList.add(new FilterData(9, -1, "9人"));
        itemList.add(new FilterData(10, -1, "10人及以上"));
        itemList.add(new FilterData(11, -1, "不限人数"));

        selectedPerson = itemList.get(itemList.size() - 1);
        return itemList;
    }

    @Override
    protected void initEvent() {
        popupWindowDate.setOnDateSubmitListener(new CalendarPopupWindow.OnDateSubmitListener() {
            @Override
            public void submit(String fDate, String tDate, int sNum) {
                fromDate = fDate;
                toDate = tDate;
                stayNum = sNum;
                tvCheckinDate.setText(fromDate);
                tvCheckoutDate.setText(toDate);
                tvStayNum.setText("共" + stayNum + "晚");
            }
        });
        popupWindowPerson.setOnMenuCheckedListener(new ListPopupWindow.OnMenuCheckedListener() {
            @Override
            public void onCheck(FilterData menuData) {
                selectedPerson = menuData;
                tvPerson.setText(selectedPerson.getItemText());
            }
        });
    }

    @OnClick({R.id.fragment_main_locate, R.id.fragment_main_selectDate, R.id.fragment_main_person,
            R.id.fragment_main_searchBtn,R.id.fragment_main_city})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_main_locate:
                progressDialog=DialogUtil.showProgressDialog(getActivity(),"定位中",true);
                locationService.start();
                break;
            case R.id.fragment_main_selectDate:
                //选择日期
                popupWindowDate.setData(fromDate, toDate, stayNum);
                String currentDate = DateUtil.dateToStr(new Date());
                String str = DateUtil.getDate(currentDate,90);
                String currentMonth = DateUtil.getWantDate(currentDate, "yyyy-MM");
                String animMonth = DateUtil.getWantDate(str, "yyyy-MM");
                String[] vMonths = new String[]{currentMonth, animMonth};

                //设置背景颜色
               // popupWindowDate.setTodayBeforeBg();
//              popupWindowDate. setAnimDayAfterBg();

               // calendar.setVisibleMonth(vMonths);
                popupWindowDate.setVisibleMonth(vMonths);


                popupWindowDate.show();
                break;
            case R.id.fragment_main_person:
                //选择人数
                popupWindowPerson.setDefaultItem(selectedPerson);
                popupWindowPerson.show();
                break;
            case R.id.fragment_main_searchBtn:
                openActivity(SearchListActivity.class);
                break;
            case R.id.fragment_main_city:
                CityPicker.getInstance()
                        .setFragmentManager(getActivity().getSupportFragmentManager())
                        .enableAnimation(true)
                        .setAnimationStyle(R.style.CustomAnim)
                        .setLocatedCity(new LocatedCity(mCity,mProvince,""))
                        .setHotCities(hotCities)
                        .setOnPickListener(new OnPickListener() {
                            @Override
                            public void onPick(int position, City data) {
                                if (data != null) {
                                   tvCity.setText(data.getName());
                                }
                            }

                            @Override
                            public void onLocate() {
                                //开始定位，这里模拟一下定位
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        CityPicker.getInstance().locateComplete(new LocatedCity(mCity,mProvince,""), LocateState.SUCCESS);
                                    }
                                }, 3000);
                            }
                        })
                        .show();

                break;
                default:
                    break;
        }
    }

    @TargetApi(23)
    private void getPersimmions() {
        ArrayList<String> permissions = new ArrayList<String>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            /***
             * 定位权限为必须权限，用户如果禁止，则每次进入都会申请
             */
            // 定位精确位置
            if (getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if (getActivity().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
            /*
			 * 读写权限和电话状态权限非必要权限(建议授予)只会申请一次，用户同意或者禁止，只会弹一次
			 */
            // 读写权限
            if (addPermission(permissions, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissionInfo += "Manifest.permission.WRITE_EXTERNAL_STORAGE Deny \n";
            }
            // 读取电话状态权限
            if (addPermission(permissions, Manifest.permission.READ_PHONE_STATE)) {
                permissionInfo += "Manifest.permission.READ_PHONE_STATE Deny \n";
            }

            if (permissions.size() > 0) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), SDK_PERMISSION_REQUEST);
            }else{
                locate();
            }
        }
    }

    @TargetApi(23)
    private boolean addPermission(ArrayList<String> permissionsList, String permission) {
        if (getActivity().checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) { // 如果应用没有获得对应权限,则添加到列表中,准备批量申请
            if (shouldShowRequestPermissionRationale(permission)) {
                return true;
            } else {
                permissionsList.add(permission);
                return false;
            }

        } else {
            return true;
        }
    }

    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode) {
            //requestCode即所声明的权限获取码，在checkSelfPermission时传入
            case SDK_PERMISSION_REQUEST:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //获取到权限，做相应处理
                    //调用定位SDK应确保相关权限均被授权，否则会引起定位失败
                    locate();
                } else{
                    //没有获取到权限，做特殊处理
                }
                break;
            default:
                break;
        }

    }

    private void locate() {
        progressDialog=DialogUtil.showProgressDialog(getActivity(),"",true);
        //获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
        locationService = ((MyApplication) getActivity().getApplication()).locationService;
        locationService.registerListener(mListener);
        locationService.setLocationOption(locationService.getDefaultLocationClientOption());
        locationService.start();
    }

    @Override
    public void onStop() {
        if(locationService!=null) {
            locationService.unregisterListener(mListener); //注销掉监听
            locationService.stop(); //停止定位服务
        }
        super.onStop();
    }


    private BDAbstractLocationListener mListener = new BDAbstractLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {
            if(progressDialog!=null){
                progressDialog.dismiss();
            }
            if (null != location && location.getLocType() != BDLocation.TypeServerError && location.getLocType() != BDLocation.TypeCriteriaException) {
               mProvince = location.getProvince();
               mCity = location.getCity();
                tvCity.setText(location.getCity()+location.getDistrict()+location.getStreet());
                locationService.stop();
            } else {
                toast("定位失败~请打开位置权限");
            }
        }
    };


}

package com.yunju.app.activity;

import android.app.Dialog;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.yunju.app.MyApplication;
import com.yunju.app.R;
import com.yunju.app.base.BaseActivity;
import com.yunju.app.entity.FilterData;
import com.yunju.app.service.LocationService;
import com.yunju.app.util.Constant;
import com.yunju.app.util.DialogUtil;
import com.yunju.app.util.SharedPreferenceUtil;
import com.yunju.app.util.StringUtil;
import com.yunju.app.widget.ThreeMenuListPopupWindow;
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
 * 房屋位置
 */
public class EditTenementLocationActivity extends BaseActivity implements BaiduMap.OnMapStatusChangeListener, OnGetGeoCoderResultListener {


    @BindView(R.id.titleview_title)
    TextView tvTitle;
    @BindView(R.id.titleview_btnRight)
    Button btnRight;
    @BindView(R.id.edit_tenement_location_city)
    TextView tvCity;
    @BindView(R.id.edit_tenement_location_address)
    EditText etAddress;
    @BindView(R.id.edit_tenement_location_number)
    EditText etNumber;
    @BindView(R.id.edit_tenement_location_mapView)
    MapView mapView;
    @BindView(R.id.edit_tenement_location_marker)
    ImageView ivMarker;
    private BaiduMap baiduMap;
    private LocationService locationService;
    private Dialog progressDialog;
    private Marker marker;
    private BitmapDescriptor bitmapMarker = BitmapDescriptorFactory.fromResource(R.drawable.icon_marker);
    private GeoCoder geoCoder;
    private ThreeMenuListPopupWindow popupWindowCity;
    private List<FilterData> cityList;
    private FilterData selectedCity;
    private String mUid;
    @Override
    public int getLayoutResource() {
        return R.layout.activity_edit_tenement_location;
    }

    @Override
    protected void initView() {
        tvTitle.setText("您的房屋在什么位置");
        btnRight.setVisibility(View.VISIBLE);
        btnRight.setText("保存");
        mUid = SharedPreferenceUtil.getAccessToken();
    }

    @Override
    protected void initData() {
        cityList = getCityItemList();
        popupWindowCity = new ThreeMenuListPopupWindow(this, getWindow(), cityList);
        popupWindowCity.setPopTitle("城市");
        baiduMap = mapView.getMap();
//        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(14.0f);
//        baiduMap.setMapStatus(msu);
        baiduMap.setMyLocationEnabled(true);
//      设置定位图层配置信息，只有先允许定位图层后设置定位图层配置信息才会生效, setMyLocationEnabled(boolean)
        baiduMap.setMyLocationConfiguration(new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, true, bitmapMarker));
        //获取locationservice实例
        locationService = ((MyApplication) getApplication()).locationService;
        locationService.registerListener(mListener);
        locationService.setLocationOption(locationService.getDefaultLocationClientOption());
        locationService.start();
        geoCoder = GeoCoder.newInstance();
    }

    @Override
    protected void initEvent() {
        popupWindowCity.setOnMenuCheckedListener(new ThreeMenuListPopupWindow.OnMenuCheckedListener() {
            @Override
            public void onCheck(FilterData filterData) {
                selectedCity = filterData;
                for (int i = 0; i < cityList.size(); i++) {
                    if (cityList.get(i).getId() == selectedCity.getpId()) {
                        tvCity.setText(cityList.get(i).getItemText());
                    }
                }
                geoCoder.geocode(new GeoCodeOption().city(tvCity.getText().toString()).address(selectedCity.getItemText()));
            }
        });
        geoCoder.setOnGetGeoCodeResultListener(this);
        baiduMap.setOnMapStatusChangeListener(this);
    }

    @OnClick({R.id.titleview_btnLeft, R.id.titleview_btnRight, R.id.edit_tenement_location_btnNext, R.id.edit_tenement_location_city, R.id.edit_tenement_location_locate})
    public void onViewClicked(View view) {
        String cityStr = tvCity.getText().toString().trim();
        String addressStr = etAddress.getText().toString().trim();
        switch (view.getId()) {
            case R.id.titleview_btnLeft:
                finish();
                break;
            case R.id.titleview_btnRight:
                if (StringUtil.isNullString(cityStr)) {
                    toast("请选择城市");
                    return;
                }
                Intent intent = new Intent(this, PublishTenementActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;
            case R.id.edit_tenement_location_btnNext:
                if (StringUtil.isNullString(cityStr)) {
                    toast("请选择城市");
                    return;
                }
                if (StringUtil.isNullString(addressStr)) {
                    toast("请填写详细地址");
                    return;
                }
                openActivity(EditTenementDetailActivity.class);
                finish();
                break;
            case R.id.edit_tenement_location_city:
                popupWindowCity.setDefaultCheckedData(selectedCity);
                popupWindowCity.show();
                break;
            case R.id.edit_tenement_location_locate:
                progressDialog = DialogUtil.showProgressDialog(this, "定位中", true);
                locationService.start();
                break;
        }
    }


    private void saveLocation(){
        Map<String,String> parmas = new HashMap<>();
        parmas.put("uid",mUid);
        parmas.put("hid","");
        parmas.put("province","");
        parmas.put("city","");
        parmas.put("region","");
        parmas.put("address","");
        parmas.put("number","");
        parmas.put("Xy_point","");

        OkHttpUtils.post().url(Constant.SAVE_LOCATION)
                .params(parmas)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                    //{"status":1,"msg":"success"}
                    }
                });
    }




    private List<FilterData> getCityItemList() {
        List<FilterData> listAll = new ArrayList<>();
        listAll.add(new FilterData(1, -1, "安徽"));
        listAll.add(new FilterData(2, -1, "北京市"));
        listAll.add(new FilterData(3, -1, "重庆市"));
        listAll.add(new FilterData(4, -1, "福建"));
        listAll.add(new FilterData(5, -1, "湖南"));

        listAll.add(new FilterData(6, 1, "安庆"));
        listAll.add(new FilterData(7, 1, "蚌埠"));
        listAll.add(new FilterData(8, 1, "池州"));
        listAll.add(new FilterData(9, 2, "北京"));
        listAll.add(new FilterData(10, 3, "重庆"));
        listAll.add(new FilterData(11, 4, "福州"));
        listAll.add(new FilterData(12, 4, "龙岩"));
        listAll.add(new FilterData(13, 4, "南平"));
        listAll.add(new FilterData(14, 5, "长沙"));
        listAll.add(new FilterData(15, 5, "株洲"));
        listAll.add(new FilterData(16, 5, "湘潭"));

        listAll.add(new FilterData(17, 6, "枞阳县"));
        listAll.add(new FilterData(18, 6, "大观区"));
        listAll.add(new FilterData(19, 6, "怀宁县"));
        listAll.add(new FilterData(20, 7, "蚌山区"));
        listAll.add(new FilterData(21, 7, "淮上区"));
        listAll.add(new FilterData(22, 8, "九华山"));
        listAll.add(new FilterData(23, 8, "青阳"));
        listAll.add(new FilterData(24, 9, "昌平区"));
        listAll.add(new FilterData(25, 9, "朝阳区"));
        listAll.add(new FilterData(26, 9, "东城区"));
        listAll.add(new FilterData(27, 10, "巴南区"));
        listAll.add(new FilterData(28, 10, "璧山县"));
        listAll.add(new FilterData(29, 11, "仓山区"));
        listAll.add(new FilterData(30, 11, "长乐市"));
        listAll.add(new FilterData(31, 12, "连城县"));
        listAll.add(new FilterData(32, 13, "松溪"));
        listAll.add(new FilterData(33, 14, "长沙县"));
        listAll.add(new FilterData(34, 14, "芙蓉区"));
        listAll.add(new FilterData(35, 14, "雨花区"));
        listAll.add(new FilterData(36, 15, "茶陵县"));
        listAll.add(new FilterData(37, 15, "荷塘区"));
        listAll.add(new FilterData(38, 16, "韶山市"));

        return listAll;
    }

    @Override
    public void onStop() {
        locationService.unregisterListener(mListener); //注销掉监听
        super.onStop();
    }
    private LatLng mLatLng;
    private BDAbstractLocationListener mListener = new BDAbstractLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
            locationService.stop();
            if (null != location && location.getLocType() != BDLocation.TypeServerError && location.getLocType() != BDLocation.TypeCriteriaException) {
                tvCity.setText(location.getCountry()+" "+location.getCity()+" "+location.getDistrict());
                etAddress.setText(location.getAddress().address);
                etAddress.setSelection(location.getAddress().address.length());
                mLatLng = new LatLng(location.getLatitude(),location.getLongitude());
                OverlayOptions ooa = new MarkerOptions().position(mLatLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_marker)).zIndex(18).draggable(true);
                marker = (Marker) baiduMap.addOverlay(ooa);

                MapStatus mMapStatus = new MapStatus.Builder()
                        .target(mLatLng)
                        .zoom(18)
                        .build();
                MapStatusUpdate msu = MapStatusUpdateFactory.newMapStatus(mMapStatus);
                baiduMap.setMapStatus(msu);



                TextView textView = new TextView(getApplicationContext());
                textView.setBackgroundResource(R.drawable.popup);
                textView.setPadding(16, 16, 16, 16);
                textView.setText(location.getAddress().address);
                textView.setTextColor(0XFF00bcd5);
                InfoWindow infoWindow = new InfoWindow(textView, mLatLng, -47);
                baiduMap.showInfoWindow(infoWindow);

            } else {
                toast("定位失败~请打开位置权限");
            }
        }
    };

    @Override
    protected void onPause() {
        // MapView的生命周期与Activity同步，当activity挂起时需调用MapView.onPause()
        mapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        // MapView的生命周期与Activity同步，当activity恢复时需调用MapView.onResume()
        mapView.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        // MapView的生命周期与Activity同步，当activity销毁时需调用MapView.destroy()
        mapView.onDestroy();
        bitmapMarker.recycle();
        geoCoder.destroy();
        super.onDestroy();
    }

    @Override
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
//        // 设置定位数据
//        etAddress.setText(geoCodeResult.getAddress());
//        etAddress.setSelection(geoCodeResult.getAddress().length());
//        LatLng latLng = geoCodeResult.getLocation();
//        MyLocationData locData = new MyLocationData.Builder()
//                .latitude(latLng.latitude)
//                .longitude(latLng.longitude)
//                .build();
//        baiduMap.setMyLocationData(locData);
//        baiduMap.animateMapStatus(MapStatusUpdateFactory.newLatLng(latLng));
//        //显示地址
//        baiduMap.clear();
//        TextView textView = new TextView(getApplicationContext());
//        textView.setBackgroundResource(R.drawable.popup);
//        textView.setPadding(16, 16, 16, 16);
//        textView.setText(geoCodeResult.getAddress());
//        InfoWindow infoWindow = new InfoWindow(textView, latLng, -47);
//        baiduMap.showInfoWindow(infoWindow);
    }

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
        String address = reverseGeoCodeResult.getAddress();
        if(!TextUtils.isEmpty(address)){
            etAddress.setText(address);
            TextView textView = new TextView(getApplicationContext());
            textView.setBackgroundResource(R.drawable.popup);
            textView.setPadding(16, 16, 16, 16);
            textView.setText(address);
            textView.setTextColor(0XFF00bcd5);
            InfoWindow infoWindow = new InfoWindow(textView, mLatLng, -47);
            baiduMap.showInfoWindow(infoWindow);
            return;
        }
    }

    @Override
    public void onMapStatusChangeStart(MapStatus mapStatus) {

    }

    @Override
    public void onMapStatusChangeStart(MapStatus mapStatus, int i) {

    }

    @Override
    public void onMapStatusChange(MapStatus mapStatus) {

    }

    @Override
    public void onMapStatusChangeFinish(MapStatus mapStatus) {
        if(mapStatus.target!=null){
            mLatLng = mapStatus.target;
            marker.setPosition(mLatLng);
            geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(mLatLng));
        }
    }
}

package com.jredu.myprojects.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.jredu.myprojects.R;

import java.util.ArrayList;
import java.util.List;

public class RunActivity extends Activity implements View.OnClickListener {
    private MapStatusUpdateFactory msuf;
    private MapStatusUpdate update;
    private MapStatusUpdate update1;
    private LocationClient mLocClient = null;
    private LocationClient mLocClient1 = null;
    private static final int UPDATE_TIME = 10000;
    private static int LOCATION_COUTNS = 0;
    private BMapManager mapManager;
    BDLocation location;
    private Marker marker1;
    private boolean isFirstLoc = true;
    private OverlayOptions options;
    private Chronometer chronometer; // 计时组件
    private Vibrator vibrator;
    Button fragment_open;
    Button fragment_aim;
    Button fragment_find;
    ImageView run_back;
    TextView aimText;
    Button positiveButton;
    BaiduMap mBaiduMap;
    View aimLayout;
    MapView mMapView = null;
    BitmapDescriptor bitmap1;
    SharedPreferences sp = null;
    public static final int COUNT0 = 0;
    public static final int COUNT1 = 1;
    int count = 0;
    int count1 = 0;
    Button btn_start;
    Button btn_stop;
    Button btn_base;
    View runLayout;
    String time;
    private String address = "";
    private Overlay myOverlay;
    private Overlay myLoctionOverlay;
    private Overlay lineOverlay;
    double latitude;
    double longitude;
    double newlatitude;
    double newlongitude;
    int length;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_run);
        fragment_open = (Button) findViewById(R.id.fragment_open);
        fragment_aim = (Button) findViewById(R.id.fragment_aim);
        fragment_find = (Button) findViewById(R.id.fragment_find);
        positiveButton = (Button) findViewById(R.id.positiveButton);
        aimText = (TextView) findViewById(R.id.aimText);
        run_back = (ImageView) findViewById(R.id.run_back);
        aimLayout = findViewById(R.id.aimLayout);
        btn_base = (Button) findViewById(R.id.btn_base);
        btn_start = (Button) findViewById(R.id.btn_start);
        btn_stop = (Button) findViewById(R.id.btn_stop);
        runLayout = findViewById(R.id.runLayout);
        chronometer = (Chronometer) findViewById(R.id.myChronometer);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);// 获取震动服务
        initView();
        initClick();
        addOverlay();

    }


    private void initView() {
        mMapView = (MapView) findViewById(R.id.map);
        mBaiduMap = mMapView.getMap();
        update1 = msuf.zoomTo(15);
        mBaiduMap.animateMapStatus(update1);
        mBaiduMap.setMyLocationEnabled(true);
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
    }

    private void initClick() {
        fragment_open.setOnClickListener(this);
        fragment_aim.setOnClickListener(this);
        fragment_find.setOnClickListener(this);
        btn_base.setOnClickListener(this);
        btn_start.setOnClickListener(this);
        btn_stop.setOnClickListener(this);
        runLayout.setOnClickListener(this);
        run_back.setOnClickListener(this);
        chronometer.setOnChronometerTickListener(new OnChronometerTickListenerImpl()); // 给计时组件设置舰艇对象
    }

    public class OnChronometerTickListenerImpl implements // 计时监听事件，随时随地的监听时间的变化
            Chronometer.OnChronometerTickListener {
        @Override
        public void onChronometerTick(Chronometer chronometer) {
            time = chronometer.getText().toString();
            if ("00:05".equals(time)) {// 判断五秒之后，让手机震动
                vibrator.vibrate(new long[]{1000, 10, 100, 10}, -1);// 设置震动周期和是否循环震动，如果不想循环震动把0改为-1
            }
        }
    }

    /*定位当前位置*/
    public void init() {
        sp = getSharedPreferences("map", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("count", count);
        editor.commit();
        mLocClient = new LocationClient(this);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);
        option.setCoorType("bd09ll");
        option.setPriority(LocationClientOption.NetWorkFirst);  //设置定位优先级
        option.setProdName("LocationDemo");
        option.setScanSpan(UPDATE_TIME);
        mLocClient.setLocOption(option);

        mLocClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation location) {
                if (location == null) {
                    return;
                }
                newlatitude = location.getLatitude();
                newlongitude = location.getLongitude();
                LatLng point = new LatLng(newlatitude, newlongitude);
                update = msuf.newLatLng(point);
                mBaiduMap.animateMapStatus(update);
                BitmapDescriptor bitmap = BitmapDescriptorFactory
                        .fromResource(R.drawable.icon_marka);
                options = new MarkerOptions()
                        .position(point)
                        .icon(bitmap);

                if(myLoctionOverlay!=null){
                    myLoctionOverlay.remove();
                    Distance(latitude,longitude,newlatitude,newlongitude);
                }
                myLoctionOverlay = mBaiduMap.addOverlay(options);
            }

        });

    }

    /*标记目的地点*/
    private void addOverlay(){

        bitmap1 = BitmapDescriptorFactory.fromResource(R.drawable.icon_markb);
        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {

            @Override
            public boolean onMapPoiClick(MapPoi arg0) {
                // TODO Auto-generated method stub
                return false;
            }

            //此方法就是点击地图监听
            @Override
            public void onMapClick(LatLng latLng) {
                //获取经纬度
                latitude = latLng.latitude;
                longitude = latLng.longitude;

                // 定义Maker坐标点
                LatLng point = new LatLng(latitude, longitude);
                // 构建MarkerOption，用于在地图上添加Marker
                MarkerOptions options = new MarkerOptions().position(point)
                        .icon(bitmap1);
                // 在地图上添加Marker，并显示
                if(myOverlay!=null){
                    myOverlay.remove();
                }
                myOverlay = mBaiduMap.addOverlay(options);
                //实例化一个地理编码查询对象
                GeoCoder geoCoder = GeoCoder.newInstance();
                //设置反地理编码位置坐标
                ReverseGeoCodeOption op = new ReverseGeoCodeOption();
                op.location(latLng);
                //发起反地理编码请求(经纬度->地址信息)
                geoCoder.reverseGeoCode(op);
                geoCoder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {

                    @Override
                    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult arg0) {
                        //获取点击的坐标地址
                        address = arg0.getAddress();
                        System.out.println("address=" + address);
                        Toast.makeText(RunActivity.this, address, Toast.LENGTH_SHORT).show();
                        Distance(latitude,longitude,newlatitude,newlongitude);
                    }
                    @Override
                    public void onGetGeoCodeResult(GeoCodeResult arg0) {
                    }
                });
            }
        });

    }
    /*两点距离*/
    public void Distance(double latitude, double longitude,double newlatitude, double newlongitude) {

        Double R=6370996.81;  //地球的半径
    /*
     * 获取两点间x,y轴之间的距离
     */
        Double x = (newlongitude - longitude)*Math.PI*R*Math.cos(((latitude+newlatitude)/2)*Math.PI/180)/180;
        Double y = (newlatitude - latitude)*Math.PI*R/180;
        Double distance = Math.hypot(x, y);   //得到两点之间的直线距离

        LatLng p1 = new LatLng(latitude, longitude);
        LatLng p2 = new LatLng(newlatitude, newlongitude);
        List<LatLng> points = new ArrayList<LatLng>();
        points.add(p1);
        points.add(p2);
        OverlayOptions ooPolyline = new PolylineOptions().width(6).color(0xFF6CBD51).points(points);

        if(lineOverlay!=null){
            lineOverlay.remove();
        }
        lineOverlay =  mBaiduMap.addOverlay(ooPolyline);
  /*
     * 调用Distance方法获取两点间x,y轴之间的距离
     */
        double cc= distance;
        length=(int)cc;
        Toast.makeText(RunActivity.this, "您与运动目的地距离"+length+"米", Toast.LENGTH_SHORT).show();

    }

    public void searchAim() {
        aimLayout.setVisibility(View.VISIBLE);
        aimText.setText(address+"距离"+length+"米");
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                aimLayout.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
        count1=0;
        sp = getSharedPreferences("map", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("mapCount", count1);
        editor.commit();
        if (mLocClient != null && mLocClient.isStarted()) {
            mLocClient.stop();
            mLocClient = null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.run_back:
                onBackPressed();
                break;
            case R.id.fragment_open:
                init();
                if (mLocClient == null) {
                    return;
                }
                if (mLocClient.isStarted()) {
                    mLocClient.stop();
                } else {
                    mLocClient.start();
                    mLocClient.requestLocation();
                }
                sp = getSharedPreferences("map", Context.MODE_PRIVATE);
                count1 = sp.getInt("mapCount", count);
                if ((count1 % 2) == COUNT0) {
                    mMapView.setVisibility(View.VISIBLE);
                    mBaiduMap.setMyLocationEnabled(true);
                    mLocClient.start();
                    count1++;
                    Toast.makeText(RunActivity.this, "打开成功", Toast.LENGTH_SHORT).show();
                } else if ((count1 % 2) == COUNT1) {
                    mMapView.setVisibility(View.GONE);
                    count1++;
                    mLocClient.stop();
                    Toast.makeText(RunActivity.this, "关闭成功", Toast.LENGTH_SHORT).show();
                }
                sp = getSharedPreferences("map", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putInt("mapCount", count1);
                editor.commit();
                break;
            case R.id.fragment_aim:
                btn_start.setEnabled(true);
                btn_stop.setEnabled(true);
                btn_base.setEnabled(true);
                if ((count1 % 2) == COUNT1) {

                     searchAim();
                } else {
                    Toast.makeText(RunActivity.this, "请先打开百度地图", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.fragment_find:
                break;
            case R.id.btn_start:
                chronometer.setBase(SystemClock.elapsedRealtime());
                chronometer.start();// 开始计时
                break;
            case R.id.btn_stop:
                chronometer.stop();// 停止计时
                Intent intent = new Intent();
                intent.setAction("com.my");
                intent.putExtra("data", time);
                sendBroadcast(intent);
                break;
            case R.id.btn_base:
                chronometer.setBase(SystemClock.elapsedRealtime());// 复位键
                break;
        }
    }

}

package emoto.winddy.cn.emotodemo.fragments.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.CoordinateConverter;
import com.baidu.nplatform.comapi.map.MapController;
import com.google.gson.JsonObject;

import java.io.Serializable;

import emoto.winddy.cn.emotodemo.R;
import emoto.winddy.cn.emotodemo.util.ONHttpCallBack;
import emoto.winddy.cn.emotodemo.util.net_util;


/**
 * Created by john on 15-9-10.
 */
public class Map_activity extends Activity  implements Serializable{

    private MapView mv = null;
    private Toast toast;

    private MyLocationData myLocationData;

    private MapController mapController = null;
    private BaiduMap baiduMap=null;
    ProgressBar networkProgressBar;
    boolean isFirstLoc = true;
  private LocationClient locationClient = null;
    private MyLocationConfiguration.LocationMode locationMode;
    private BitmapDescriptor bitmapDescriptor;
    private OverlayOptions vehicle_options;
    private Context context;
    public MyLocationListemer myListener = new MyLocationListemer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.map_activity);
        init();

       networkProgressBar = (ProgressBar) findViewById(R.id.progressbar);
        requestForGPSData();
        Button req_curr = (Button) findViewById(R.id.request_curr);
        req_curr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgress();
                requestForGPSData();
                hideProgress();
            }
        });




    }

    public class MyLocationListemer implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if (bdLocation == null || mv == null) {
                return;

            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(bdLocation.getRadius())
                            // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(0)
                    .latitude(bdLocation.getLatitude())
                    .longitude(bdLocation.getLongitude()).build();
            baiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng latLng = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());// get 经纬度
                baiduMap.setMyLocationConfigeration(new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING, true, null));
                MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.zoomTo(17);
                baiduMap.setMapStatus(mapStatusUpdate);
                
                MapStatusUpdate mapStatusUpdate1 = MapStatusUpdateFactory.newLatLng(latLng);
                baiduMap.animateMapStatus(mapStatusUpdate1);
                // 以上设置新地图位置
            }

        }

        public void onReceivePoi(BDLocation bdLocation) {

        }
    }

    private void showPopupOverrlay(BDLocation location) {

    }

    @Override
    protected void onResume() {
        super.onResume();

        mv.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mv.onPause();
    }

    @Override
    protected void onDestroy() {
        locationClient.stop();
        baiduMap.setMyLocationEnabled(false);


        mv.onDestroy();
        mv = null;
        super.onDestroy();

    }

    void requestForGPSData() {
      //  SharedPreferences preferences = getSharedPreferences(Aging_test.getAgingTest(this).userSharePref, MODE_PRIVATE);

        JsonObject object = new JsonObject();
        object.addProperty("action", "gps");
      //  object.addProperty("user", preferences.getString("userName", "admin"));
        object.addProperty("unitnumber", context.getSharedPreferences("RealData", Context.MODE_PRIVATE).getString("unitnumber", "865067020417682"));
       // System.out.println(preferences.getString("userName", "admin") + "------------------*******************测试******************--------");
        net_util.goFor(net_util.getLocationInfo, new ONHttpCallBack() {
            @Override
            public void onHttpCallBack(JsonObject jsonObject) {
                if (jsonObject == null) {
                    showErrorToast();
                    return;
                }
                double JD = Double.parseDouble(jsonObject.get("JD").getAsString());
                double WD = Double.parseDouble(jsonObject.get("WD").getAsString());

                LatLng pointV = new LatLng(WD, JD);
                CoordinateConverter converter = new CoordinateConverter();
                converter.from(CoordinateConverter.CoordType.GPS);
                converter.coord(pointV);// 坐标转换
                LatLng cPointV = converter.convert();
                BitmapDescriptor bitmapDescriptorV = BitmapDescriptorFactory.fromResource(R.mipmap.icon_marka);
                vehicle_options = new MarkerOptions()
                        .position(cPointV)
                        .icon(bitmapDescriptorV);
                baiduMap.addOverlay(vehicle_options);
                baiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(cPointV));// 设定位置
                MapStatusUpdate u1 = MapStatusUpdateFactory.zoomTo(17);
                baiduMap.setMapStatus(u1);
            }


        }, object);
    }

    void showErrorToast(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(Map_activity.this, "信息为空", Toast.LENGTH_SHORT).show();
            }
        });

    }

    void init() {

        mv = (MapView) findViewById(R.id.location_vehicle_map);
        mv.showScaleControl(true);

        baiduMap = mv.getMap();
        baiduMap.setMyLocationEnabled(true);//允许定位图层
        //initiliase
        locationClient = new LocationClient(this);
          locationClient.registerLocationListener(myListener);
        //register
        MapStatusUpdate u1 = MapStatusUpdateFactory.zoomTo(17);//定位目标时放到到17倍
        baiduMap.setMapStatus(u1);

//        LocationClientOption locationClientOption = new LocationClientOption();
//        locationClientOption.setOpenGps(true);
//        locationClientOption.setCoorType("bd09ll");
//
//        locationClientOption.setScanSpan(3000);
//        locationClient.setLocOption(locationClientOption);
//
//        locationClient.start();


    }

    void hideProgress() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LinearLayout parent = (LinearLayout) networkProgressBar.getParent();
                parent.setVisibility(View.GONE);
            }
        });
    }

    void showProgress() {
        LinearLayout parent = (LinearLayout) networkProgressBar.getParent();
        parent.setVisibility(View.VISIBLE);
    }
}

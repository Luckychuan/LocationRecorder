package com.example.luckychuan.locationrecorder.mvp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.example.luckychuan.locationrecorder.bean.WifiData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GetDataModelImpl implements GetDataModel {

    //测试硬件上的 bssid和路由器序号
    private Map<String,String> mDefaultWifiMap;


    private WifiManager mWifiManager;
    private WifiReceiver mWifiReceiver;
    private Context mContext;
    private Callback mCallback;

    public GetDataModelImpl(Context context) {

        initMap();

        mContext = context;
        mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        mWifiReceiver = new WifiReceiver();
        context.registerReceiver(mWifiReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));

    }

    private void initMap() {
        mDefaultWifiMap = new HashMap<>();
        mDefaultWifiMap.put("6c:3b:6b:44:32:d3", "1");
        mDefaultWifiMap.put("6c:3b:6b:48:bd:ad", "2");
        mDefaultWifiMap.put("6c:3b:6b:48:c0:5f", "3");
        mDefaultWifiMap.put("6c:3b:6b:48:c3:3e", "4");
        mDefaultWifiMap.put("6c:3b:6b:6a:68:0e", "5");
        mDefaultWifiMap.put("6c:3b:6b:68:9a:47", "6");
        mDefaultWifiMap.put("6c:3b:6b:66:28:11", "7");
    }


    @Override
    public void refreshAP(Callback callback) {
        mCallback = callback;
        mWifiManager.startScan();
    }

    @Override
    public void onPresenterDetach() {
        mContext.unregisterReceiver(mWifiReceiver);
    }

    /**
     * 获得wifi数据
     */
    class WifiReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {

                List<ScanResult> scanResults = mWifiManager.getScanResults();
                if (scanResults == null || scanResults.size() == 0) {
                    if (mCallback != null) {
                        mCallback.onFail("搜索失败");
                    }
                    return;
                }

                //获得数据
                List<WifiData> list = new ArrayList<>();
                for (ScanResult tmp : scanResults) {
//                    if (tmp.SSID.equals("EXP_AP")) {
                    // TODO: 2017/6/9  
                        WifiData wifiData = new WifiData();
                        wifiData.setId(tmp.BSSID);
                        String no = mDefaultWifiMap.get(tmp.BSSID);
                        if(no != null){
                            wifiData.setNo(no);
                        }else{
                            wifiData.setNo("0");
                        }
                        wifiData.setRssi(tmp.level + "");
                        list.add(wifiData);
                    }
//                }
                //回调数据
                if (mCallback != null) {
                    mCallback.onSuccess(list);
                    mCallback = null;
                }
            }
        }
    }

}

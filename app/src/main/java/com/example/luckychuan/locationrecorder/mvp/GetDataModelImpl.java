package com.example.luckychuan.locationrecorder.mvp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.example.luckychuan.locationrecorder.bean.DataResult;
import com.example.luckychuan.locationrecorder.bean.WifiData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GetDataModelImpl implements GetDataModel, GetDataModel.Callback<List<WifiData>> {

    private static final String TAG = "GetDataModelImpl";

    //测试路由器上的 bssid
    private List<String> mBssidList;


    private WifiManager mWifiManager;
    private WifiReceiver mWifiReceiver;
    private Context mContext;


    private Callback<List<WifiData>> mRefreshCallback;

    private boolean mIsCancel;
    //记录扫描的次数
    private int mCount = 0;
    private Callback<List<DataResult>> mRecordCallback;
    private List<DataResult> mDataResultList;

    public GetDataModelImpl(Context context) {

        initMap();

        mContext = context;
        mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        mWifiReceiver = new WifiReceiver();
        context.registerReceiver(mWifiReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));

    }

    private void initMap() {
        mBssidList = new ArrayList<>();
        mBssidList.add("6c:3b:6b:44:32:d3");
        mBssidList.add("6c:3b:6b:48:bd:ad");
        mBssidList.add("6c:3b:6b:48:c0:5f");
        mBssidList.add("6c:3b:6b:68:9a:6a");
        mBssidList.add("6c:3b:6b:6a:68:0e");
        mBssidList.add("6c:3b:6b:68:9a:47");
        mBssidList.add("6c:3b:6b:66:28:11");

    }

    @Override
    public void onPresenterDetach() {
        mContext.unregisterReceiver(mWifiReceiver);
    }


    @Override
    public void refreshAP(Callback<List<WifiData>> callback) {
        Log.d(TAG, "refreshAP: ");
        mRefreshCallback = callback;
        mWifiManager.startScan();
    }

    public void onRefreshFinish() {
        mRefreshCallback = null;
    }

    @Override
    public void record(int number, String directionString, Callback<List<DataResult>> callback) {
        mIsCancel = false;
        mRecordCallback = callback;
        mDataResultList = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            DataResult dataResult = new DataResult();
            dataResult.setNumber(number);
            dataResult.setDirectionString(directionString);
            mDataResultList.add(dataResult);
        }

        mCount++;
        Log.d("record", "第 " + mCount + " 次扫描开始");
        refreshAP(this);
    }

    private void onRecordFinish() {
        mCount = 0;
        mRecordCallback = null;
        mDataResultList = null;
    }

    public void cancelRecord(){
        mIsCancel = true;
    }

    /**
     * 实现GetDataModel.Callback<List<WifiData>>接口
     * 当扫描一次结束时的回调
     *
     * @param result
     */
    @Override
    public void onSuccess(List<WifiData> result) {
        onRefreshFinish();

        Log.d("record", "第 " + mCount + " 次扫描结束");


       if(!mIsCancel){

           //扫描的次数为2，3，4时记录数据
           if (mCount != 1) {
               int position = mCount - 2;
               DataResult dataResult = mDataResultList.get(position);
               dataResult.setList(result);
               dataResult.setTime(System.currentTimeMillis());

           }

           if (mCount == 4) {
               //完成当前格子数的测试
               mRecordCallback.onSuccess(mDataResultList);
               onRecordFinish();
               return;
           }


           mCount++;
           Log.d("record", "第 " + mCount + " 次扫描开始");
           refreshAP(this);

       }else{
           mRecordCallback.onFail("任务取消");
           onRecordFinish();
       }


    }

    @Override
    public void onFail(String failMsg) {
        mRecordCallback.onFail(failMsg);
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
                    if (mRefreshCallback != null) {
                        mRefreshCallback.onFail("搜索失败");
                    }
                    return;
                }

                //获得数据
                List<WifiData> list = new ArrayList<>();
                for (ScanResult tmp : scanResults) {
                    if (tmp.SSID.equals("EXP_AP")) {
                        WifiData wifiData = new WifiData();
                        wifiData.setId(tmp.BSSID);
                        int no = mBssidList.indexOf(tmp.BSSID) + 1;
                        wifiData.setNo(no);
                        wifiData.setRssi(tmp.level + "");
                        list.add(wifiData);
                    }
                }

                Collections.sort(list);


                //回调数据
                if (mRefreshCallback != null) {
                    mRefreshCallback.onSuccess(list);
                }

            }
        }
    }

}

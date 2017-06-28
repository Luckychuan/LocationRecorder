package com.example.luckychuan.locationrecorder.mvp;

import com.example.luckychuan.locationrecorder.bean.DataResult;
import com.example.luckychuan.locationrecorder.bean.WifiData;

import java.util.List;

/**
 * Created by Luckychuan on 2017/6/8.
 */

public interface DataView {

    //刷新数据
    void onRefreshSuccess(List<WifiData> list);
    void onRefreshFail(String failMsg);

    //记录数据
    void showProgressDialog();
    void onRecordSuccess(List<DataResult> list);
    void onRecordFail(String failMsg);
    void showNotification();
    void hideProgressDialog();

}

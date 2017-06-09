package com.example.luckychuan.locationrecorder.mvp;

import com.example.luckychuan.locationrecorder.bean.WifiData;

import java.util.List;

/**
 * Created by Luckychuan on 2017/6/8.
 */

public interface DataView {

//    void showProgressDialog();
//    void hideProgressDialog();
//    void showErrorDialog();
//    void showSuccessNotification();
    void onRefreshSuccess(List<WifiData> list);
    void onRefreshFail(String failMsg);
    //void OnRecordSuccess();
    // TODO: 2017/6/8

}

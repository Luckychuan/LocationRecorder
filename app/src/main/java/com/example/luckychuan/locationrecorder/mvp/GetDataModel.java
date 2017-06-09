package com.example.luckychuan.locationrecorder.mvp;

import android.content.Context;

import com.example.luckychuan.locationrecorder.bean.WifiData;

import java.util.List;

/**
 * Created by Luckychuan on 2017/6/8.
 */

public interface GetDataModel {

    void refreshAP(Callback callback);
    void onPresenterDetach();
//    void record(String directionString, );


    interface Callback {
        void onSuccess(List<WifiData> list);
        void onFail(String failMsg);

    }

}

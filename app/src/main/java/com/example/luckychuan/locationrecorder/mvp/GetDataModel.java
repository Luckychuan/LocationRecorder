package com.example.luckychuan.locationrecorder.mvp;

import android.content.Context;

import com.example.luckychuan.locationrecorder.bean.DataResult;
import com.example.luckychuan.locationrecorder.bean.WifiData;

import java.util.List;

/**
 * Created by Luckychuan on 2017/6/8.
 */

public interface GetDataModel {

    void refreshAP(Callback<List<WifiData>> callback);

    void onPresenterDetach();

    void record(int number, String directionString, Callback<List<DataResult>> callback);


    interface Callback<T> {
        void onSuccess(T result);

        void onFail(String failMsg);

    }

}

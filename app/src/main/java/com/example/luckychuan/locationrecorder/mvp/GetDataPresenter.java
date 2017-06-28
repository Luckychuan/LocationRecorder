package com.example.luckychuan.locationrecorder.mvp;

import android.content.Context;

import com.example.luckychuan.locationrecorder.bean.DataResult;
import com.example.luckychuan.locationrecorder.bean.WifiData;

import java.util.List;

/**
 * Created by Luckychuan on 2017/6/8.
 */

public class GetDataPresenter {

    private GetDataModelImpl mModel;
    private DataView mView;

    public GetDataPresenter(DataView view, Context context){
        mView = view;
        mModel = new GetDataModelImpl(context);
    }

    public void requestRefresh(){
        mModel.refreshAP(new GetDataModel.Callback<List<WifiData>>() {
            @Override
            public void onSuccess(List<WifiData> result) {
                mModel.onRefreshFinish();
                mView.onRefreshSuccess(result);
            }

            @Override
            public void onFail(String failMsg) {
                mView.onRefreshFail(failMsg);
            }
        });
    }

    public void requestRecord(int number, String directionString){
        mView.showProgressDialog();
        mModel.record(number, directionString, new GetDataModel.Callback<List<DataResult>>() {
            @Override
            public void onSuccess(List<DataResult> result) {
                mView.onRecordSuccess(result);
                mView.hideProgressDialog();
                mView.showNotification();
            }

            @Override
            public void onFail(String failMsg) {
                mView.onRecordFail(failMsg);
            }
        });
    }

    public void cancelRecord(){
        mModel.cancelRecord();
    }

    public void detach(){
        mView = null;
        mModel.onPresenterDetach();
    }


}

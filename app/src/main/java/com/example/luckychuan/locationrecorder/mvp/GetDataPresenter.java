package com.example.luckychuan.locationrecorder.mvp;

import android.content.Context;

import com.example.luckychuan.locationrecorder.bean.WifiData;

import java.util.List;

/**
 * Created by Luckychuan on 2017/6/8.
 */

public class GetDataPresenter {

    private GetDataModel mModel;
    private DataView mView;

    public GetDataPresenter(DataView view, Context context){
        mView = view;
        mModel = new GetDataModelImpl(context);
    }

    public void requestRefresh(){
        mModel.refreshAP(new GetDataModel.Callback() {
            @Override
            public void onSuccess(List<WifiData> list) {
                mView.onRefreshSuccess(list);
            }

            @Override
            public void onFail(String failMsg) {
                mView.onRefreshFail(failMsg);
            }
        });
    }

    public void detach(){
        mView = null;
        mModel.onPresenterDetach();
    }


}

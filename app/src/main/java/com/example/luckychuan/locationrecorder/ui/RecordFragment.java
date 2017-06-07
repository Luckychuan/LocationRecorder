package com.example.luckychuan.locationrecorder.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.luckychuan.locationrecorder.R;
import com.example.luckychuan.locationrecorder.bean.DataBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Luckychuan on 2017/6/7.
 */

public class RecordFragment extends Fragment {

    private static final String[] NO = new String[]{
            "6c:3b:6b:44:32:d3",
            "6c:3b:6b:48:bd:ad",
            "6c:3b:6b:48:c0:5f",
            "6c:3b:6b:48:c3:3e",
            "6c:3b:6b:6a:68:0e",
            "6c:3b:6b:68:9a:47",
            "6c:3b:6b:66:28:11"};
    private static final String[] ID = new String[]{"1", "2", "3", "4", "5", "6", "7"};

    private SwipeRefreshLayout mRefreshLayout;
    private View mView;
    private SimpleAdapter mAdapter;
    //SimpleAdapter使用的数据集
    private List<HashMap<String, String>> mList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_record, container, false);

        mRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.refresh_layout);

        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRefreshLayout.setColorSchemeColors(Color.parseColor("#03A9F4"));
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //// TODO: 2017/6/7
            }
        });

        ListView listView = (ListView) mView.findViewById(R.id.listView);
        mList = new ArrayList<>();
        mAdapter = new SimpleAdapter(getContext(), mList,
                R.layout.rssi_item, new String[]{"no", "id", "rssi"},
                new int[]{R.id.textView_no, R.id.textView_id, R.id.textView_rssi});
        listView.setAdapter(mAdapter);
        listView.addHeaderView();

        test();
    }


    private void test() {
        for (int i = 0; i < 7; i++) {
            HashMap<String, String> map = new HashMap<>();
            map.put("no", (i + 1) + "");
            map.put("id", "6c:3b:6b:44:32:d3");
            map.put("rssi", "-53");
            mList.add(map);
        }
        mAdapter.notifyDataSetChanged();
    }

}

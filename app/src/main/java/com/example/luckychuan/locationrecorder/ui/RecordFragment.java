package com.example.luckychuan.locationrecorder.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.luckychuan.locationrecorder.R;
import com.example.luckychuan.locationrecorder.bean.DataResult;
import com.example.luckychuan.locationrecorder.bean.WifiData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Luckychuan on 2017/6/7.
 */

public class RecordFragment extends Fragment {

    private static final String TAG = "RecordFragment";

    private SwipeRefreshLayout mRefreshLayout;
    private EditText mEditText;
    private ListView mListView;
    private SimpleAdapter mAdapter;
    private TextView mNumberTextView;
    private TextView mDirectionTextView;
    //SimpleAdapter使用的数据集
    private List<HashMap<String, String>> mList;


    private OnTaskStartListener mListener;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_record, container, false);

        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);
        mEditText = (EditText) view.findViewById(R.id.edit_text);
        mEditText.setText("1");
        mListView = (ListView) view.findViewById(R.id.listView);
        mNumberTextView = (TextView) view.findViewById(R.id.textView_ap_number);
        mDirectionTextView = (TextView) view.findViewById(R.id.textView_direction);
        Button button = (Button) view.findViewById(R.id.button_small);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onButtonClick();
                }
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRefreshLayout.setColorSchemeColors(Color.parseColor("#03A9F4"));
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mListener != null) {
                    mListener.onRefresh();
                }
            }
        });

        mList = new ArrayList<>();
        mAdapter = new SimpleAdapter(getContext(), mList,
                R.layout.rssi_item, new String[]{"no", "bssid", "rssi"},
                new int[]{R.id.textView_no, R.id.textView_bssid, R.id.textView_rssi});
        mListView.setAdapter(mAdapter);
//        test();

    }

    public void setOnTaskStartListener(OnTaskStartListener listener) {
        mListener = listener;
    }


//    private void test() {
//        for (int i = 0; i < 7; i++) {
//            HashMap<String, String> map = new HashMap<>();
//            map.put("no", (i + 1) + "");
//            map.put("bssid", "6c:3b:6b:44:32:d3");
//            map.put("rssi", "-53");
//            mList.add(map);
//        }
//        mAdapter.notifyDataSetChanged();
//    }

    public void setDirectionText(String text) {
        if (mDirectionTextView != null) {
            mDirectionTextView.setText(text);
        }
    }

    public String getDirectionText() {
        if (mDirectionTextView != null) {
            return mDirectionTextView.getText().toString();
        }
        return "";
    }

    public void onRefreshFinish(List<WifiData> list) {
        mList.clear();
        for (WifiData dataBean : list) {
            HashMap<String, String> map = new HashMap<>();
            map.put("no", dataBean.getNo()+"");
            map.put("bssid", dataBean.getId());
            map.put("rssi", dataBean.getRssi());
            mList.add(map);
        }
        mRefreshLayout.setRefreshing(false);
        mNumberTextView.setText(list.size()+"");
        mAdapter.notifyDataSetChanged();

    }

    public int getCurrentNumber() {
        if (!mEditText.getText().toString().equals("")) {
            return Integer.valueOf(mEditText.getText().toString());
        } else {
            return 0;
        }
    }

    public void plusOneCurrentNumber() {
        int currentNumber = Integer.valueOf(mEditText.getText().toString());
        currentNumber++;
        mEditText.setText(currentNumber + "");
    }


}

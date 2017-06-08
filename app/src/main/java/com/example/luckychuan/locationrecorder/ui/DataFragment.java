package com.example.luckychuan.locationrecorder.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.luckychuan.locationrecorder.R;
import com.melnykov.fab.FloatingActionButton;
import com.melnykov.fab.ObservableScrollView;

/**
 * Created by Luckychuan on 2017/6/7.
 */

public class DataFragment extends Fragment implements View.OnClickListener {

    private EditText mEditText;
    private String mDataString;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_data,container,false);

        mEditText = (EditText) view.findViewById(R.id.edit_data);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.floatActionButton);
        ObservableScrollView scrollView = (ObservableScrollView) view.findViewById(R.id.scrollView);
        fab.attachToScrollView(scrollView);
        fab.setOnClickListener(this);

        mDataString = "";

        return view;
    }


    @Override
    public void onClick(View view) {

    }

    public void setText(String text){
        mDataString += text;
        mEditText.setText(mDataString);
    }


}

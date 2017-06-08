package com.example.luckychuan.locationrecorder.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.luckychuan.locationrecorder.R;

/**
 * Created by Luckychuan on 2017/6/7.
 */

public class ButtonFragment extends Fragment implements View.OnClickListener {

    private Button mButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_button,container,false);
        Button button = (Button) view.findViewById(R.id.button_large);
        button.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {

    }
}

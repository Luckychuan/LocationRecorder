package com.example.luckychuan.locationrecorder.ui;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.luckychuan.locationrecorder.R;
import com.melnykov.fab.FloatingActionButton;
import com.melnykov.fab.ObservableScrollView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * Created by Luckychuan on 2017/6/7.
 */

public class DataFragment extends Fragment implements View.OnClickListener {

    private EditText mEditText;
    private String mDataString;

    private String mFilePathName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_data, container, false);

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
        final Handler handler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                //保存完成的回调
                Log.d("io_save", "save finish");
                share();

            }


        };

        handler.post(new Runnable() {
            @Override
            public void run() {
                save(handler);
            }
        });


    }

    private void share() {
        Intent share = new Intent(Intent.ACTION_SEND);
        ComponentName component = new ComponentName("com.tencent.mobileqq","com.tencent.mobileqq.activity.JumpActivity");
        share.setComponent(component);
        File file = new File(mFilePathName);
        share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        share.setType("*/*");
        startActivity(Intent.createChooser(share, "发送"));
    }

    public void setText(String text) {
        mDataString += text;
        mDataString += "\n";
        mEditText.setText(mDataString);
    }


    /**
     * 将数据保存到txt文件
     */
    private void save(Handler handler) {
        //初始化
        String baseDir = Environment.getExternalStorageDirectory().getAbsolutePath();
        baseDir += File.separator;
        baseDir += "RSSI";
        baseDir += File.separator;
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH", Locale.CHINA);
        String dateString = formatter.format(currentTime);
        try {
            File dir = new File(baseDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
        } catch (Exception e) {
            Log.d("File Error", e + "");
        }
        mFilePathName = baseDir + "RSSI-" + dateString + ".txt";
        File file = new File(mFilePathName);
        try {
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
        } catch (Exception e) {
            Log.d("File Error", e + "");
        }

        //写入
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new FileWriter(file, true));
            out.write(mEditText.getText().toString());
            out.newLine();
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        handler.sendMessage(new Message());

    }

}

package com.example.luckychuan.locationrecorder.ui;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.luckychuan.locationrecorder.R;
import com.example.luckychuan.locationrecorder.adapter.TabFragmentPagerAdapter;
import com.example.luckychuan.locationrecorder.bean.DataResult;
import com.example.luckychuan.locationrecorder.bean.WifiData;
import com.example.luckychuan.locationrecorder.mvp.DataView;
import com.example.luckychuan.locationrecorder.mvp.GetDataPresenter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener, OnTaskStartListener, DataView {

    private SensorManager mSensorManager;
    private NotificationManager mNotificationManager;

    private RecordFragment mRecordFragment;
    private DataFragment mDataFragment;

    private GetDataPresenter mPresenter;

    private ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //获取权限
        int readStorageCheck = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int accessLocationCheck = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION);
        if (readStorageCheck == PackageManager.PERMISSION_GRANTED && accessLocationCheck == PackageManager.PERMISSION_GRANTED) {
            afterPermissionGranted();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
        }


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        //当权限获得时
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
                && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            afterPermissionGranted();
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                //弹出对话框提示用户接收权限
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setMessage("程序要获得权限后才能运行");
                dialog.setCancelable(false);
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //请求权限
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
                    }
                });
                dialog.create().show();
            }
        }
    }

    private void afterPermissionGranted() {
        initPages();

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        // 注册传感器，注册监听器
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_UI);

        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

    }

    private void initPages() {
        mDataFragment = new DataFragment();
        mRecordFragment = new RecordFragment();
        mRecordFragment.setOnTaskStartListener(this);
        ButtonFragment fragment3 = new ButtonFragment();
        fragment3.setOnTaskStartListener(this);

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(mDataFragment);
        fragmentList.add(mRecordFragment);
        fragmentList.add(fragment3);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager_main);

        TabFragmentPagerAdapter adapter = new TabFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);
        tabLayout.setupWithViewPager(viewPager);

        mPresenter = new GetDataPresenter(this, this);
        mPresenter.requestRefresh();

    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float currentDirection = sensorEvent.values[0];
        //保留一位小数
        currentDirection = (float) (Math.round(currentDirection * 10)) / 10;
        mRecordFragment.setDirectionText(currentDirection + "");

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detach();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        Log.d("ui_debug", "onRefresh: ");
        mPresenter.requestRefresh();

    }

    @Override
    public void onButtonClick() {

        mNotificationManager.cancel(1);

        int number = mRecordFragment.getCurrentNumber();
        String directionString = mRecordFragment.getDirectionText();
        Log.d("ui_debug", "onButtonClick: " + number);
        if (number != 0) {
            mPresenter.requestRecord(number, directionString);
        } else {
            Toast.makeText(this, "输入正确的格子数", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onRefreshSuccess(List<WifiData> list) {
        mRecordFragment.onRefreshFinish(list);
    }

    @Override
    public void onRefreshFail(String failMsg) {
        Toast.makeText(this, failMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressDialog() {
        mProgressDialog = ProgressDialog.show(this, "扫描和记录", "正在扫描和记录第 " + mRecordFragment.getCurrentNumber() + " 格子");
    }

    @Override
    public void showFailDialog(String failMsg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("失败");
        builder.setMessage(failMsg);
        builder.setCancelable(true);
        builder.setNeutralButton("确定", null);
        builder.show();

    }

    @Override
    public void onRecordSuccess(List<DataResult> list) {
        mRecordFragment.plusOneCurrentNumber();
        DataResult dataResult = list.get(list.size() - 1);
        mRecordFragment.onRefreshFinish(dataResult.getList());
        for (DataResult result : list) {
            mDataFragment.setText(result.toString());
        }
    }

    @Override
    public void showNotification() {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setDefaults(Notification.DEFAULT_ALL);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        mNotificationManager.notify(1, builder.build());
    }

    @Override
    public void hideProgressDialog() {
        mProgressDialog.hide();
    }
}

interface OnTaskStartListener {
    void onRefresh();

    void onButtonClick();
}

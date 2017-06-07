package com.example.luckychuan.locationrecorder.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Luckychuan on 2017/6/7.
 */

public class TabFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mList;
    private String[] mTitle = new String[]{"数据","测量","大按钮"};

    public TabFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        mList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitle[position];
    }
}

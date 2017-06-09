package com.example.luckychuan.locationrecorder.bean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Luckychuan on 2017/6/8.
 */

public class DataResult {

    private int number;
    private List<WifiData> list;
    private String directionString;
    private long time;


    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();
        builder.append(number);
        builder.append("_");
        for (WifiData data : list) {
            builder.append(data.getNo());
            builder.append("_");
            builder.append(data.getRssi());
            builder.append("_");
        }
        builder.append(directionString);
        builder.append("_");
        builder.append(formatTime(time));

        return builder.toString();
    }

    private String formatTime(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.CHINA);
        return sdf.format(time);
    }

    public DataResult(int number, List<WifiData> list, String directionString, long time) {
        this.number = number;
        this.list = list;
        this.directionString = directionString;
        this.time = time;
    }

    public DataResult(){}

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<WifiData> getList() {
        return list;
    }

    public void setList(List<WifiData> list) {
        this.list = list;
    }

    public String getDirectionString() {
        return directionString;
    }

    public void setDirectionString(String directionString) {
        this.directionString = directionString;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}

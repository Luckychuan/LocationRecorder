package com.example.luckychuan.locationrecorder.bean;

/**
 * Created by Luckychuan on 2017/6/7.
 */

public class DataBean {

    private String no;
    private String bssid;
    private String rssi;

    public DataBean(String no, String id, String rssi) {
        this.no = no;
        this.bssid = id;
        this.rssi = rssi;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getId() {
        return bssid;
    }

    public void setId(String id) {
        this.bssid = id;
    }

    public String getRssi() {
        return rssi;
    }

    public void setRssi(String rssi) {
        this.rssi = rssi;
    }

    @Override
    public String toString() {
        return "DataBean{" +
                "no='" + no + '\'' +
                ", id='" + bssid + '\'' +
                ", rssi='" + rssi + '\'' +
                '}';
    }
}

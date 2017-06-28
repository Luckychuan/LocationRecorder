package com.example.luckychuan.locationrecorder.bean;

/**
 * Created by Luckychuan on 2017/6/7.
 */

public class WifiData implements Comparable<WifiData> {

    private int no;
    private String bssid;
    private String rssi;


    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getBssid() {
        return bssid;
    }

    public void setBssid(String bssid) {
        this.bssid = bssid;
    }

    public WifiData(int no, String bssid, String rssi) {

        this.no = no;
        this.bssid = bssid;
        this.rssi = rssi;
    }

    public WifiData() {
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

    @Override
    public int compareTo(WifiData wifiData) {
        return Integer.valueOf(no).compareTo(wifiData.getNo());
    }
}

package com.sshy.yjy.strore.mate.submitorder;

import com.baidu.mapapi.model.LatLng;

/**
 * create date：2019/2/20
 * create by：周正尧
 */
public class MessageEvent {

    public String addrs;
    public String addressId;
    public String niceName;
    public int payWay;
    public LatLng latLng;

    public MessageEvent(String addrs, String addressId, String niceName, int payWay, LatLng latLng) {
        this.addrs = addrs;
        this.addressId = addressId;
        this.niceName = niceName;
        this.payWay = payWay;
        this.latLng = latLng;
    }
}

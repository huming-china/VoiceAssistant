package com.cloudring.magicsound.model;

import com.baidu.mapapi.model.LatLng;

/** 附近
 * Created by hm on 2016/8/19.
 */
public class Near {
    private String name;
    private String address;
    private LatLng location;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }
}

package com.lintech.yougo;

import java.util.Vector;

/**
 * Created by William on 10/8/2016.
 */

public class Trip {

    public String UID, longitude, latitude, radius;
    public Vector<String> followers;

    public Trip(){}

    public Trip(String longitude, String latitude, String rad){
        this.longitude = longitude;
        this.latitude = latitude;
        this.radius = rad;
    }
}

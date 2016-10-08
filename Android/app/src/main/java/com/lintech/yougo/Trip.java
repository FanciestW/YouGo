package com.lintech.yougo;

/**
 * Created by William on 10/8/2016.
 */

public class Trip {

    public String UID, longitude, latitude, radius, email;

    public Trip(){}

    public Trip(String longitude, String latitude, String rad, String email){
        this.longitude = longitude;
        this.latitude = latitude;
        this.radius = rad;
        this.email = email;
    }
}

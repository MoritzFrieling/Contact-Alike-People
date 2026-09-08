package com.project.cap.Backend.retrofit.Requests;

import com.google.gson.annotations.SerializedName;

public class LocationRequest {

    @SerializedName("lat")
    private double latitude;

    @SerializedName("lng")
    private double longitude;

    public LocationRequest(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
package com.project.cap.Backend.retrofit.Responses;

import com.google.gson.annotations.SerializedName;

public class UserOnMapInformationResponse {

    //ToDo add annotations

    @SerializedName("uid")
    private String id;

    @SerializedName("lng")
    private double longitude;

    @SerializedName("lat")
    private double latitude;

    @SerializedName("gender")
    private String gender;

    public UserOnMapInformationResponse(String id, double longitude, double latitude, String gender) {
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
        this.gender = gender;
    }


    // GETTER + SETTER

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}

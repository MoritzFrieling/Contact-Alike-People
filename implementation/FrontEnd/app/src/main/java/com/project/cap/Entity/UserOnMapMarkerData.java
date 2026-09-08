package com.project.cap.Entity;

/**
 * Portraits the location of a user through. Contains all data that is required to display a UserReference on the map.
 * Mostly used as way to transfer data.
 */
public class UserOnMapMarkerData {

    private double lat;
    private double lng;
    private String uid;
    private String gender;

    public UserOnMapMarkerData(){
    }

    public UserOnMapMarkerData(double lat, double lng, String uid, String gender) {
        this.lat = lat;
        this.lng = lng;
        this.uid = uid;
        this.gender = gender;
    }

    //GETTER SETTER FOR LOCRESPONSE


    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


}


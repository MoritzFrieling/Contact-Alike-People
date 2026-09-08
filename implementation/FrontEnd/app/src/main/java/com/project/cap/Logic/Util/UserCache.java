package com.project.cap.Logic.Util;

import android.location.Location;

import com.project.cap.Entity.UserReference;
import com.project.cap.Entity.UserOnMapMarkerData;

import java.util.ArrayList;
import java.util.List;

/**
 * Singleton cache class. Receives account information after user input is done.
 *
 * Holds location references to avoid multiple HTTP-Requests
 *
 * Can be accessed globally (throughout LogicController)
 *
 */
public class UserCache {

    /**
     * JWT
     */
    private String USER_TOKEN;

    //Additional, logic-related fields
    private UserReference localUser;

    private Location ownUserLocation;
    private String ownUid;

    private List<UserOnMapMarkerData> otherMarkers;

    public UserCache() {
        localUser = new UserReference();
        otherMarkers = new ArrayList<>();
    }

    public boolean setLocalUser(final UserReference user) {
        localUser = user;
        return true;
    }

    public Location getOwnUserLocation() {
        return ownUserLocation;
    }

    public void setOwnUserLocation(Location ownUserLocation) {
        this.ownUserLocation = ownUserLocation;
    }

    public UserReference getLocalUser() {
        return localUser;
    }

    public void setUserToken(String token) {
        this.USER_TOKEN = token;
    }

    public String getUserToken() {
        return this.USER_TOKEN;
    }
    //ToDo add cache for other users on map

    public boolean addUserLocationsAndIdentifier(UserOnMapMarkerData o) {
        return otherMarkers.add(o);
    }

    public List<UserOnMapMarkerData> getUserLocations() {
        return otherMarkers;
    }

    public void clearUserLocations() {
        otherMarkers.clear();
    }

    public String getLocalUid() {
        return ownUid;
    }

    public void setOwnUid(String ownUid) {
        this.ownUid = ownUid;
    }

    public void setGender(String gender) {
        this.localUser.setGender(gender);
    }

    public String getOwnGender() {
        return this.localUser.getGender();
    }
}

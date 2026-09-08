package com.project.cap.Backend;

import android.location.Location;

import com.project.cap.Backend.retrofit.Responses.GetAllSocialsResponse;
import com.project.cap.Backend.retrofit.Responses.GetInfoOfRequestorsResponse;
import com.project.cap.Backend.retrofit.Responses.LoginResponse;
import com.project.cap.Backend.retrofit.RetrofitController;
import com.project.cap.Entity.Interest;
import com.project.cap.Entity.InterestData;
import com.project.cap.Entity.SocialData;
import com.project.cap.Entity.Socials;
import com.project.cap.Entity.UserReference;
import com.project.cap.Entity.UserDetails;
import com.project.cap.Entity.UserOnMapMarkerData;
import com.project.cap.Logic.Util.UserCache;
import com.project.cap.Logic.LogicController;

import java.util.List;
import java.util.concurrent.CompletableFuture;


/**
 * Access point to the Backend module.
 *
 * Backend module is responsible to perform the actual HTTP-request that has been requested
 * by the logic layer.
 *
 * Reference to retrofit client
 *
 * */
public class BackendController {

    private static BackendController instance;
    private static RetrofitController retrofitController;

    private BackendController() {
        retrofitController = RetrofitController.getInstance();
    }

    public static BackendController getInstance() {
        if(instance != null) return instance;

        instance = new BackendController();
        return instance;
    }

    public UserCache getUserCacheInstance() {
        return LogicController.getInstance().getUserCache();
    }

    /**
     * Check if user present
     *
     * @param email filled in by user
     * @param password filled in by user
     * @return if entry present, user object. Else, Optional.Null
     */
    public boolean loginRequest(String email, String password) {
        LoginResponse potentialAuthentication = retrofitController.loginUser(email, password);
        if(!potentialAuthentication.getToken().isEmpty() && !potentialAuthentication.getUid().isEmpty()) {
            getUserCacheInstance().setUserToken(potentialAuthentication.getToken());
            getUserCacheInstance().setOwnUid(potentialAuthentication.getUid());
            System.out.println(getUserCacheInstance().getLocalUid());
            return true;
        }
        return false;
    }

    /**
     * Create db-entry for new user/account
     *
     * @param user UserReference-object created based on user-input
     * @return if successful, return user-object. Else, return null
     */
    public UserReference registerUser(UserReference user) {
        //ToDo Adjust backend to handle correct user object
        if(retrofitController.createUser(user)) {
            System.out.println("BackendController.registerUser success");
            System.out.println("For user: \n" + user);
            return user;
        }

        //Registration failed in backend -> return null
        return null;
    }

    /**
     * Asks the controller if the email is already saved.
     * @param mail
     * @return
     */
    public boolean isEmailAvailable(String mail){
        return retrofitController.isEmailAvailable(mail);
    }

    /**
     * Asks the controller to return all saved interests from the db. Mainly used to fill up the recycler later on.
     * @return
     */
    public List<Interest> getAllInterests(){
        return retrofitController.getAllInterests();
    }

    /**
     *
     * @return
     */
    public List<GetAllSocialsResponse> getAllSocials(){
        return retrofitController.getAllSocials();
    }

    public boolean addInterests(InterestData filledInterests) {
        retrofitController.addInterests(filledInterests);
        return true;
    }

    public boolean addSocials(SocialData filledSocials) {
        retrofitController.addSocials(filledSocials);
        return true;
    }

    public boolean updateLocation(Location loc){
        return retrofitController.updateLocation(loc);
    }

    public List<UserOnMapMarkerData> getLocationInfoOfUsers() {return retrofitController.getLocationInfoOfUsers();}

    public List<InterestData> getInterestsById(String id) {
        return retrofitController.getInterestsById(id);
    }

    public List<SocialData> getSocialsById(String id) {
        return retrofitController.getSocialsById(id);
    }

    public String getOwnUid() {
        return retrofitController.getOwnUid();
    }

    public UserDetails getUserDetails(String id) {
        return retrofitController.getUserDetails(id);
    }

    public List<GetInfoOfRequestorsResponse> getInfoOfRequestors() {
        return retrofitController.getInfoOfRequestors();
    }

    public void sendDeviceID(String id) {
        retrofitController.sendDeviceID(id);
    }

    public void sendRequest(String id) {
        retrofitController.sendRequest(id);
    }

    public void sendAccpted(String id) {
        retrofitController.sendAccepted(id);
    }
}

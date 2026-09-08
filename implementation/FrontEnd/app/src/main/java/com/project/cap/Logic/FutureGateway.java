package com.project.cap.Logic;

import android.location.Location;

import com.project.cap.Backend.retrofit.Responses.GetAllSocialsResponse;
import com.project.cap.Backend.retrofit.Responses.GetInfoOfRequestorsResponse;
import com.project.cap.Entity.Interest;
import com.project.cap.Entity.InterestData;
import com.project.cap.Entity.SocialData;
import com.project.cap.Entity.Socials;
import com.project.cap.Entity.UserReference;
import com.project.cap.Entity.UserDetails;
import com.project.cap.Entity.UserOnMapMarkerData;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * This is a Gateway-class. It represents the access point to invoke asynchrous tasks and takes care
 * of data-transfer throughout different layers.
 *
 * To invoke any method, access the LogicController's field of this class.
 *
 * Every method is executed asynchronous, so the source code only proceeds as soon as an "answer" for the request
 * is available.
 *
 */
public class FutureGateway {

    public CompletableFuture<Boolean> processLoginAsync(String email, String password) {
        return CompletableFuture.supplyAsync(() -> loginUser(email, password));
    }

    //ToDo redesign registration flow. Witch each "next", send request
    public CompletableFuture<Boolean> registerUserAsync(UserReference user) {

        return CompletableFuture.supplyAsync(() -> {
            var reg = registerUser(user);
            if(!reg) return false;

            var login = loginUser(user.getEmail(), user.getPassword());
            if(!login) return false;

            addInterest(user.getFilledInterests());
            addSocial(user.getFilledSocials());
            return true;
        });
    }



    /**
     * Check if the email is already in use. True if its available
     * @return
     */
    public CompletableFuture<Boolean> isEmailAvailableAsync(String email) {
        return CompletableFuture.supplyAsync(() -> emailAvailable(email));
    }

    /**
     * Get all interests from the db.
     * @return
     */
    public CompletableFuture<List<Interest>> getAllInterestsAsync(){
        return CompletableFuture.supplyAsync(() -> getAllInterests());
    }

    /**
     * Get information of all people that tried to contact us.
     *
     * @return List of Info-objects
     */
    public CompletableFuture<List<GetInfoOfRequestorsResponse>> getInfoOfRequestorsAsync(){
        return CompletableFuture.supplyAsync(() -> getInfoOfRequestors());
    }

    /**
     * Get all socials from db.
     * @return
     */
    public CompletableFuture<List<GetAllSocialsResponse>> getAllSocialsAsync(){
        return CompletableFuture.supplyAsync(() -> getAllSocials());
    }

    public CompletableFuture<Boolean> updateLocationAsync(Location loc){
        return CompletableFuture.supplyAsync(() -> updateLocation(loc));
    }

    /**
     * Get location information of users that will be displayed on the map.
     * @return
     */
    public CompletableFuture<List<UserOnMapMarkerData>> getLocationInfoOfUsersAsync(){
        return CompletableFuture.supplyAsync(() -> getLocationInfoOfUsers());
    }

    /**
     * Get interest information of a selected user.
     * @return
     */
    public CompletableFuture<List<InterestData>> getInterestsByIdAsync(String id){
        return CompletableFuture.supplyAsync(() -> getInterestsById(id));
    }

    /**
     * Get social media information of a selected user.
     * @return
     */
    public CompletableFuture<List<SocialData>> getSocialsByIdAsync(String id){
        return CompletableFuture.supplyAsync(() -> getSocialsById(id));
    }

    /**
     * Get specific, personal information of a selected user.
     * @param id
     * @return
     */
    public CompletableFuture<UserDetails> getUserDetailsAsync(String id){
        return CompletableFuture.supplyAsync(() -> getUserDetails(id));
    }

    //Private Methods

    /**
     * Login for existing users
     *
     * @param email
     * @param password
     * @return true if user is found in db and set as localUser
     *
     * @exception throw exception if no entry found with user input
     */
    private boolean loginUser(String email, String password) throws IllegalArgumentException {
        var isLoginRequestSuccessful = LogicController.getBackendController().loginRequest(email, password);
        if(isLoginRequestSuccessful) {
            System.out.println("FutureGateway.loginUser success");
            return true;
        }

        throw new IllegalArgumentException("Error! No UserReference found in storage for provided user-data!");
    }

    /**
     * Register a new user / creates new account and log in
     *
     * @param user Get user-entity consisting of UI-input and validate input
     * @return true if registration successful + new UserReference is set as local user. False, if input invalid or
     *          backend error occurred
     */
    private boolean registerUser(UserReference user) {
        if(LogicController.getBackendController().registerUser(user) != null && isInputValid(user)) {
            System.out.println("FutureGateway.registerUser input has been validated");
            return true;
        }
        System.out.println("UserReference creation failed. Backend-error or invalid input!");
        return false;
    }

    /**
     * Validate input made by user. Check for:
     *
     * Password: Capital letter, digit, special sign, length > 8
     * Email: Check for correct syntax
     *
     * @param user
     * @return true if email and password match regex
     */
    private boolean isInputValid(UserReference user) {
        String pw = user.getPassword().trim();
        String email = user.getEmail().trim();

        boolean passwordOK = pw.matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[+#?!@$%^&*-]).{8,}$");
        boolean emailOK = email.matches("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");

        return passwordOK && emailOK;
    }

    /**
     * Check if the email is already registered. Returns true if its not./It is available.
     * @param email
     * @return
     */
    private boolean emailAvailable(String email){
        return LogicController.getBackendController().isEmailAvailable(email);
    }

    /**
     * Get all interests from the db.
     * @return
     */
    private List<Interest> getAllInterests(){
        return LogicController.getBackendController().getAllInterests();
    }

    /**
     * Get all socials from db
     * @return
     */
    private List<GetAllSocialsResponse> getAllSocials(){
        return LogicController.getBackendController().getAllSocials();
    }

    /**
     * set socials selected during registration
     *
     * @param filledSocials socials selected
     * @return true, if successfully set
     */
    private boolean addSocial(List<SocialData> filledSocials) {
        filledSocials.stream()
                .forEach(social -> {
                    LogicController.getBackendController().addSocials(social);
        });
        return true;
    }

    /**
     * set interests selected during registration
     *
     * @param filledInterests interests selected
     * @return true, if successfully set
     */
    private boolean addInterest(List<InterestData> filledInterests) {
        filledInterests.stream()
                .forEach(i -> {
                    LogicController.getBackendController().addInterests(i);
                });
        return true;
    }

    private List<UserOnMapMarkerData> getLocationInfoOfUsers() {
        return LogicController.getBackendController().getLocationInfoOfUsers();
    }

    private boolean updateLocation(Location loc){
        return LogicController.getBackendController().updateLocation(loc);
    }

    private List<InterestData> getInterestsById(String id) {
        return LogicController.getBackendController().getInterestsById(id);
    }

    private List<SocialData> getSocialsById(String id) {
        return LogicController.getBackendController().getSocialsById(id);
    }

    private UserDetails getUserDetails(String id) {
        return LogicController.getBackendController().getUserDetails(id);
    }


    private List<GetInfoOfRequestorsResponse> getInfoOfRequestors() {
        return LogicController.getBackendController().getInfoOfRequestors();
    }
    public void sendDeviceIDAsync(String id) {
        CompletableFuture.supplyAsync(() -> {
            LogicController.getBackendController().sendDeviceID(id);
            return null;
        });
    }

    public void sendRequestAsync(String id){
        CompletableFuture.supplyAsync(() -> {
            LogicController.getBackendController().sendRequest(id);
            return null;
        });
    }

    public void sendAcceptedAsync(String id){
        CompletableFuture.supplyAsync(() -> {
            LogicController.getBackendController().sendAccpted(id);
            return null;
        });
    }
}

package com.project.cap.Backend.retrofit;

import com.project.cap.Backend.retrofit.Requests.AddInterestRequest;
import com.project.cap.Backend.retrofit.Requests.AddSocialRequest;
import com.project.cap.Backend.retrofit.Requests.LocationRequest;
import com.project.cap.Backend.retrofit.Requests.LoginRequest;
import com.project.cap.Backend.retrofit.Requests.NewUserRequest;
import com.project.cap.Backend.retrofit.Responses.GetAllSocialsResponse;
import com.project.cap.Backend.retrofit.Responses.GetInfoOfRequestorsResponse;
import com.project.cap.Backend.retrofit.Responses.UserOnMapInformationResponse;
import com.project.cap.Backend.retrofit.Responses.LoginResponse;
import com.project.cap.Entity.UserDetails;
import com.project.cap.Entity.Interest;
import com.project.cap.Entity.InterestData;
import com.project.cap.Entity.SocialData;
import com.project.cap.Entity.Socials;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 *
 * Interface that holds every endpoint reference.
 * Endpoints must return Call<T> and can take params.
 * Endpoints must contain correct path ("/user/.."), corresponding to Backend-path
 *
 * Possible params: @Path, @Query, @Body, @Header...
 *
 * Documentation: https://guides.codepath.com/android/consuming-apis-with-retrofit#define-the-endpoints
 *
 */
public interface EndpointsAPI {

    @POST("/signup") //adjust to fit db
    Call<Void> createUser(@Body NewUserRequest request); //Adjust params to db expectations

    @POST("/login")
    Call<LoginResponse> login(@Body LoginRequest request);

    //Account creation part//
    @GET("/check-email/{email}")
    Call<Boolean> getMail(@Path("email") String email);

    @POST("/user-interests")
    Call<Void> addInterest(@Body AddInterestRequest interestRequest);

    @POST("/user-socials")
    Call<Void> addSocial(@Body AddSocialRequest socialRequest);
    //End of Account creation part//

    @POST("/location")
    Call<Void> updateLocation(@Body LocationRequest request);

    //Get all interests/socials
    @GET("/interests")
    Call<List<Interest>> getAllInterests();

    @GET("/socials")
    Call<List<GetAllSocialsResponse>> getAllSocials();

    @GET("/discover")
    Call<List<UserOnMapInformationResponse>> getLocationInfoOfUsers();

    @GET("/user-interests-by-user/{uid}")
    Call<List<InterestData>> getInterestsById(@Path("uid") String id);

    @GET("/user-socials-by-user/{uid}")
    Call<List<SocialData>> getSocialsById(@Path("uid") String id);

    @GET("/who-am-i")
    Call<String> getOwnUid();

    @GET("/user-details/{uid}")
    Call<UserDetails> getUserDetails(@Path("uid") String id);


    @GET("/relationships/details")
    Call<List<GetInfoOfRequestorsResponse>> getInfoOfRequestors();

    @PUT("/user-devices/{DeviceID}")
    Call<Void> sendDeviceID(@Path("DeviceID") String id);

    @POST("/relationships/{uid}")
    Call<Void> sendRequest(@Path("uid")String id);

    @PUT("/relationships/{uid}")
    Call<Void> sendAccepted(@Path("uid") String id);
}

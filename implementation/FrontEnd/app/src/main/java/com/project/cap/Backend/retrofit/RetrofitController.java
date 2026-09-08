package com.project.cap.Backend.retrofit;

import android.location.Location;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.project.cap.Backend.BackendController;
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
import com.project.cap.Entity.UserReference;
import com.project.cap.Entity.UserOnMapMarkerData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Controller class - RetrofitClient
 *
 * Manages HTTP-Requests
 *
 * Accessed by BackendController
 *
 */
public class RetrofitController {
    //Singleton field
    private static RetrofitController instance;

    // FOR LOCALHOST: http://10.0.2.2:3000
    public static final String BASE_URL = "https://prj4-and2-cap.herokuapp.com"; //Insert URL


    // necessary to deserialize responses. Get Java out of JSON
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .create();

    // include authorisation token with every request
    OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request newRequest  = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer " + BackendController.getInstance().getUserCacheInstance().getUserToken())
                    .build();
            return chain.proceed(newRequest);
        }
    }).build();

    // necessary to send out network requests
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    // necessary to execute call
    private EndpointsAPI apiService = retrofit.create(EndpointsAPI.class);

    public Retrofit getRetrofit() {
        return retrofit;
    }

    /**
     * Create user
     *
     * @param user
     * @return true after successful creation
     */
    public boolean createUser(UserReference user) {
        //ToDo use UserReference instead of UserRequest
        var call = apiService.createUser(new NewUserRequest(user));
        try {
            Response<Void> execute = call.execute();
            System.out.println(execute.raw());
            return true;
        } catch (IOException e) {
            System.out.println("Exception RetrofitController");
            return false;
        }
    }

    /**
     * Get user by email and password (proivided during login)
     *
     * @param email
     * @param password
     * @return ture after login and after setting user token
     */
    public LoginResponse loginUser(String email, String password) {
        Call<LoginResponse> call = apiService.login(new LoginRequest(email, password));
        System.out.println(call.request());

        try {
            Response<LoginResponse> response = call.execute();
            return response.body();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Asks the database if the mail is already registered.
     *
     * @param mail
     * @return
     */
    public boolean isEmailAvailable(String mail) {
        //SQL request should look similar to this
        //SELECT SUM(email) FROM users WHERE email = @param
        Call<Boolean> call = apiService.getMail(mail);
        try{
            Response<Boolean> response = call.execute();
            return response.body();
        }catch(Exception e){//worst case, didn't work. -> look into backend
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Asks the db to return a list of all interests. Just straight up the whole table.
     * Kinda like Select * From interests
     *
     * In the hope that it converts it automatically to the correct class. (Interest)
     * @return
     */
    public List<Interest> getAllInterests(){
        Call<List<Interest>> call = apiService.getAllInterests();
        try {//works like a charm
              return call.execute().body();
         } catch (IOException e) {//didnt work
              e.printStackTrace();
              return null;
         }
    }

    /**
     * Asks the db to return a list of all socials.
     * The whole table -> Select * From socials or something like that.
     * @return
     */
    public List<GetAllSocialsResponse> getAllSocials(){
        Call<List<GetAllSocialsResponse>> call = apiService.getAllSocials();
        try {
            return call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void addInterests(InterestData interests) {
        //TODO send Interests
        var temp = new AddInterestRequest(interests.getId(), interests.getStartDate(), interests.getProficiency(), interests.getDesc());
        Call<Void> call = apiService.addInterest(temp);
        try {
            call.execute();
            return;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addSocials(SocialData socialData) {
        Call<Void> call = apiService.addSocial(new AddSocialRequest(socialData.getId(), socialData.getUsername()));
        try {
            call.execute();
            return;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean updateLocation(Location loc)
    {
        var call = apiService.updateLocation(new LocationRequest((loc.getLatitude()), (loc.getLongitude())));
        System.out.println(call.request());

        try {
            Response<Void> execute = call.execute();
            System.out.println(execute.raw());

            return true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public List<UserOnMapMarkerData> getLocationInfoOfUsers() {
        Call<List<UserOnMapInformationResponse>> call = apiService.getLocationInfoOfUsers();
        List<UserOnMapMarkerData> results = new ArrayList<>();
        try {
            call.execute().body()
                    .stream()
                    .forEach(o -> {
                        results.add(new UserOnMapMarkerData(o.getLatitude(), o.getLongitude(), o.getId(), o.getGender()));
                    });
            return results;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<InterestData> getInterestsById(String id){
        Call<List<InterestData>> call = apiService.getInterestsById(id);
        try {//works like a charm
            return call.execute().body();
        } catch (IOException e) {//didnt work
            e.printStackTrace();
            return null;
        }
    }

    public List<SocialData> getSocialsById(String id) {
        Call<List<SocialData>> call = apiService.getSocialsById(id);
        try {//works like a charm
            return call.execute().body();
        } catch (IOException e) {//didnt work
            e.printStackTrace();
            return null;
        }
    }

    public void sendAccepted(String id) {
        try {
            apiService.sendAccepted(id).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Singleton

    private RetrofitController(){};

    public static RetrofitController getInstance() {
        if(instance != null) return instance;

        instance = new RetrofitController();
        return instance;
    }

    public String getOwnUid() {
        Call<String> ownUid = apiService.getOwnUid();
        try {
            var result = ownUid.execute().body();
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public UserDetails getUserDetails(String id) {
        Call<UserDetails> userDetails = apiService.getUserDetails(id);
        try {
            return userDetails.execute().body();
        } catch (Exception e) {
            return null;
        }
    }

    public List<GetInfoOfRequestorsResponse> getInfoOfRequestors() {
        var response = apiService.getInfoOfRequestors();
        try {
            return response.execute().body();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void sendDeviceID(String id){
        try {
            apiService.sendDeviceID(id).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendRequest(String id) {
        Call<Void> request = apiService.sendRequest(id);
        try {
            request.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}

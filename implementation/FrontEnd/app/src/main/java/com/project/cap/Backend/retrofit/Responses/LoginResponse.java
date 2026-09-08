package com.project.cap.Backend.retrofit.Responses;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("token")
    private String token;

    @SerializedName("uid")
    private String uid;

    public LoginResponse(String token, String uid) {
        this.token = token;
        this.uid = uid;
    }

    public String getToken() {
        return token;
    }

    public String getUid() {return uid;}
}

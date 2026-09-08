package com.project.cap.Backend.retrofit.Requests;

import com.google.gson.annotations.SerializedName;

public class AddSocialRequest {

    @SerializedName("socialId")
    private int id;

    @SerializedName("handle")
    private String username;

    public AddSocialRequest(int id, String username) {
        this.id = id;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

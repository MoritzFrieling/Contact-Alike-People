package com.project.cap.Backend.retrofit.Responses;

import com.google.gson.annotations.SerializedName;

public class GetAllSocialsResponse {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    public GetAllSocialsResponse(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

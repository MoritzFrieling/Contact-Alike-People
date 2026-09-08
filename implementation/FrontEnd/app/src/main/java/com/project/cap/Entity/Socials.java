package com.project.cap.Entity;

import com.google.gson.annotations.SerializedName;

public class Socials {

    @SerializedName("socialId")
    private int id;

    private String name;

    public Socials(int id, String name){
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}

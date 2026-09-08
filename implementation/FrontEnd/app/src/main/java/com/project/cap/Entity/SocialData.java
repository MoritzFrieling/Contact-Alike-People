package com.project.cap.Entity;

import com.google.gson.annotations.SerializedName;

/**
 *
 *Extended Social object that includes data made by user
 */
public class SocialData extends Socials {

    @SerializedName("handle")
    private final String username;

    public SocialData(int id, String name, String username) {
        super(id, name);
        this.username=username;
    }

    public String getUsername() {
        return username;
    }
}

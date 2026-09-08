package com.project.cap.Entity;

import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class UserDetails {

    @SerializedName("firstname")
    private String forename;

    @SerializedName("lastname")
    private String lastname;

    @SerializedName("bio")
    private String bio;

    @SerializedName("birthdate")
    private Date birthDate;

    @SerializedName("gender")
    private String gender;

    @SerializedName("avatar")
    private String profilePicture;

    public UserDetails(String forename, String lastname, String bio, Date birthDate, String gender, String profilePicture) {
        this.forename = forename;
        this.lastname = lastname;
        this.bio = bio;
        this.birthDate = birthDate;
        this.gender = gender;
        this.profilePicture = profilePicture;
    }

    public String getForename() {
        return forename;
    }

    public String getLastname() {
        return lastname;
    }

    public String getBio() {
        return bio;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public String getGender() {
        return gender;
    }

    public String getProfilePicture() {
        return profilePicture;
    }
}

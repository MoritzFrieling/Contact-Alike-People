package com.project.cap.Backend.retrofit.Requests;

import com.google.gson.annotations.SerializedName;
import com.project.cap.Entity.UserReference;

public class NewUserRequest {

    @SerializedName("firstName")
    private String firstName;

    @SerializedName("lastName")
    private String lastName;

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("bio")
    private String bio;

    @SerializedName("avatar")
    private String avatar;

    @SerializedName("phoneNumber")
    private String phoneNumber;

    @SerializedName("birthDate")
    private String birthDate;

    @SerializedName("gender")
    private String gender;

    public NewUserRequest(String firstName, String lastName, String email, String password,
                          String bio, String avatar, String phoneNumber, String birthDate, String gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.bio = bio;
        this.avatar = avatar;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.gender = gender;
    }

    public NewUserRequest(UserReference user) {
        this.firstName = user.getForename();
        this.lastName = user.getLastname();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.bio = user.getBio();
        this.avatar = user.getEncodedPicture();
        this.phoneNumber = user.getPhoneNumber();
        this.birthDate = user.getBirthDate();
        this.gender = user.getGender();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getBio() {
        return bio;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getGender() {
        return gender;
    }
}

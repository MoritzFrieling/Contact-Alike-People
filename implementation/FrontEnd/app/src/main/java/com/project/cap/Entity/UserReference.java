package com.project.cap.Entity;

import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * UserReference entity based on db-definitions
 *
 */
public class UserReference {

    //Annotations?
    @SerializedName("id")
    private int id;

    @SerializedName("password")
    private String password = "";

    @SerializedName("email")
    private String email = "";

    @SerializedName("forename")
    private String forename = "";

    @SerializedName("lastname")
    private String lastname = "";

    @SerializedName("bio")
    private String bio = "";

    @SerializedName("phone")
    private String phoneNumber = "";

    @SerializedName("birthDate")
    private String birthDate;

    @SerializedName("gender")
    private String gender = "";

    @SerializedName("profilePicture")
    private Bitmap profilePicture;

    private List<SocialData> filledSocials;
    private List<InterestData> filledInterests;
    private String encodedPicture;


    //TODO DB Compability
    private UserOnMapMarkerData location = new UserOnMapMarkerData();


    /**
     * Constructor with every field
     *
     * @param id
     * @param password
     * @param email
     * @param forename
     * @param lastname
     * @param bio
     * @param phoneNumber
     * @param birthDate
     * @param gender
     * @param profilePicture
     */
    public UserReference(int id, String password, String email, String forename,
                         String lastname, String bio, String phoneNumber, String birthDate, String gender,
                         Bitmap profilePicture) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.forename = forename;
        this.lastname = lastname;
        this.bio = bio;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.gender = gender;
        this.profilePicture = profilePicture;
    }

    public UserReference() {}

    public UserReference(UserDetails det) {

    }

    // Getter + Setter

    public UserOnMapMarkerData getLocation() {
        return location;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Bitmap getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Bitmap profilePicture) {
        this.profilePicture = profilePicture;
    }


    public void setFilledSocials(List<SocialData> filledSocials) {
        this.filledSocials = filledSocials;
    }

    public void setFilledInterests(List<InterestData> filledInterests) {
        this.filledInterests = filledInterests;
    }

    public List<SocialData> getFilledSocials() {
        return filledSocials;
    }

    public List<InterestData> getFilledInterests() {
        return filledInterests;
    }
    public String getEncodedPicture() {
        return encodedPicture;
    }

    public void setEncodedPicture(String encodedPicture) {
        this.encodedPicture = encodedPicture;

    }

    @Override
    public String toString() {
        return "UserReference{" +
                "id=" + id +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", forename='" + forename + '\'' +
                ", lastname='" + lastname + '\'' +
                ", bio='" + bio + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", gender='" + gender + '\'' +
                ", profilePicture=" + profilePicture +
                ", location=" + location +
                '}';
    }
}

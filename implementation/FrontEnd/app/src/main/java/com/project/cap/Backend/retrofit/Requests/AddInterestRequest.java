package com.project.cap.Backend.retrofit.Requests;

import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddInterestRequest {

    @SerializedName("interestId")
    private int id;

    @SerializedName("description")
    private String desc;

    @SerializedName("startDate")
    private Date startDate;

    @SerializedName("proficiency")
    private int proficiency;

    public AddInterestRequest(int id, String startDate, String proficiency, String desc) {
        this.id = id;
        try {
            this.startDate = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
            this.startDate.setHours(12);
            this.startDate.setMinutes(12);
            this.startDate.setSeconds(12);
        } catch (Exception e) {
            e.printStackTrace();
        }

        switch (proficiency){
            case("Beginner"):
                this.proficiency = 0;
                break;
            case("Advanced"):
                this.proficiency = 1;
                break;
            case("Pro"):
                this.proficiency = 2;
                break;
        }
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getProficiency() {
        return proficiency;
    }

    public void setProficiency(int proficiency) {
        this.proficiency = proficiency;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

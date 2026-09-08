package com.project.cap.Backend.retrofit.Responses;

import com.google.gson.annotations.SerializedName;

public class GetInfoOfRequestorsResponse {

    @SerializedName("fromUser")
    public String uid;

    @SerializedName("accepted")
    public boolean accepted;

    public GetInfoOfRequestorsResponse(String uid, boolean accepted) {
        this.uid = uid;
        this.accepted = accepted;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public boolean getAccepted() {
        return accepted;
    }

    public void setAccpeted(boolean accc) {
        this.accepted = accepted;
    }
}

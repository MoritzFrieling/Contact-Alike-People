package com.project.cap.Entity;

import java.util.List;

/**
 * Class that holds all the important information for a notification
 */
public class Notification {


    private String UID;

    private List<SocialData> socials;

    public Notification(String UID){
        this.UID = UID;
    }

    public String getUID() {
        return UID;
    }

    public List<SocialData> getSocials() {
        return socials;
    }

    public void setSocials(List<SocialData> socials) {
        this.socials = socials;
    }
}

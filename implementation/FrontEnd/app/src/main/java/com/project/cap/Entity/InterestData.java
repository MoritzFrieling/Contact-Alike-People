package com.project.cap.Entity;

import com.google.gson.annotations.SerializedName;

/**
 * Class to hold the user input with the associated interest
 */
public class InterestData extends Interest{

    @SerializedName("startDate")
    private String startDate;

    @SerializedName("proficiency")
    private String proficiency;

    public InterestData(int id, int type, String name, String desc, String startDate, String proficiency) {
        super(id, type, name, desc);
        this.startDate = startDate;
        this.proficiency = proficiency;
    }
    public InterestData(Interest item, String startDate, String proficiency) {
        super(item.getId(), item.getType(), item.getName(), item.getDesc());
        this.startDate = startDate;
        this.proficiency = proficiency;
    }
    public String getStartDate() {
        return startDate;
    }

    public String getProficiency() {
        return proficiency;
    }

    public void setStartDateToFormattedDate() {
        String[] splitDate = startDate.split("-");
        String perfectString = splitDate[0] + "-" + splitDate[1] + "-" + splitDate[2].substring(0,2);

        startDate = perfectString;
    }
}

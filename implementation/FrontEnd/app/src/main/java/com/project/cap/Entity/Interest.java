package com.project.cap.Entity;

import com.google.gson.annotations.SerializedName;

public class Interest {

    private int id;

    @SerializedName("interestId")
    private int type;

    private String name;

    @SerializedName("description")
    private String desc;

    public Interest(int id, int type, String name, String desc){
        this.id=id;
        this.type=type;
        this.name = name;
        this.desc = desc;
    }
    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

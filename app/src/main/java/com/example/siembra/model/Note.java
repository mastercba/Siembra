package com.example.siembra.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Note {
    @Expose
    @SerializedName("id") private int id;
    @Expose
    @SerializedName("ban") private String ban;
    @Expose
    @SerializedName("desp") private String desp;
    @Expose
    @SerializedName("resp") private String resp;
    @Expose
    @SerializedName("tag") private String tag;
    @Expose
    @SerializedName("color") private int color;
    @Expose
    @SerializedName("date") private String date;
    @Expose
    @SerializedName("success") private Boolean success;
    @Expose
    @SerializedName("message") private String message;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getBan() {
        return ban;
    }

    public void setBan(String ban) {
        this.ban = ban;
    }

    public String getDesp() {
        return desp;
    }

    public void setDesp(String desp) {
        this.desp = desp;
    }

    public String getResp() {
        return resp;
    }

    public void setResp(String resp) {
        this.resp = resp;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }


    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

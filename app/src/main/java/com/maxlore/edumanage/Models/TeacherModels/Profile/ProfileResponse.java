package com.maxlore.edumanage.Models.TeacherModels.Profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Nikhil on 03-07-2016.
 */
public class ProfileResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("profile")
    @Expose
    private Profile profile;
    @SerializedName("message")
    @Expose
    private String message;


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}

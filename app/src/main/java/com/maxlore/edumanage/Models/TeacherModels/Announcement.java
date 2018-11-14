package com.maxlore.edumanage.Models.TeacherModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Nikhil on 05-06-2016.
 */
public class Announcement {
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("posted_on")
    @Expose
    private String postedOn;

    /**
     * @return The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return The postedOn
     */
    public String getPostedOn() {
        return postedOn;
    }

    /**
     * @param postedOn The posted_on
     */
    public void setPostedOn(String postedOn) {
        this.postedOn = postedOn;
    }

}

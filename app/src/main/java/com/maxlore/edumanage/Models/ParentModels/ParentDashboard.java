package com.maxlore.edumanage.Models.ParentModels;

/**
 * Created by maxlore on 26-Aug-16.
 */

import com.maxlore.edumanage.Models.TeacherModels.Announcement;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ParentDashboard {


    @SerializedName("announcement")
    @Expose
    private List<Announcement> announcement ;
    @SerializedName("student")
    @Expose
    private ParentStudent student;
    @SerializedName("image")
    @Expose
    private String image;

    public List<Announcement> getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(List<Announcement> announcement) {
        this.announcement = announcement;
    }

    public ParentStudent getStudent() {
        return student;
    }

    public void setStudent(ParentStudent student) {
        this.student = student;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "ParentDashboard{" +
                "announcement=" + announcement +
                ", student=" + student +
                ", image='" + image + '\'' +
                '}';
    }

}
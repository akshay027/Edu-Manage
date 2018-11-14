package com.maxlore.edumanage.Models.ParentModels.ParentObservation;

/**
 * Created by maxlore on 31-Aug-16.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StudentObservation {

    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("teacher_name")
    @Expose
    private String teacherName;
    @SerializedName("phone_no")
    @Expose
    private String phoneNo;
    @SerializedName("date_of_observation")
    @Expose
    private String date;
    @SerializedName("image")
    @Expose
    private String image;


    /**
     * @return The text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text The text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return The teacherName
     */
    public String getTeacherName() {
        return teacherName;
    }

    /**
     * @param teacherName The teacher_name
     */
    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    /**
     * @return The phoneNo
     */
    public String getPhoneNo() {
        return phoneNo;
    }

    /**
     * @param phoneNo The phone_no
     */
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "StudentObservation{" +
                "text='" + text + '\'' +
                ", teacherName='" + teacherName + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", date='" + date + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}

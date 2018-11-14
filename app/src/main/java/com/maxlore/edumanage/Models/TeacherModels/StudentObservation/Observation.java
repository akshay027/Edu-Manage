package com.maxlore.edumanage.Models.TeacherModels.StudentObservation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by maxlore on 8/22/2016.
 */
//changed by shrey on  04-sep-2017

public class Observation {
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("teacher_name")
    @Expose
    private String teacherName;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("class_name")
    @Expose
    private String standardsection;
    @SerializedName("is_hidden_from_parent")
    @Expose
    private String isHiddenFromParent;

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
     *
     * @return
     * The standard
     */


    /**
     * @return The isHiddenFromParent
     */
    public String getIsHiddenFromParent() {
        return isHiddenFromParent;
    }

    /**
     * @param isHiddenFromParent The is_hidden_from_parent
     */
    public void setIsHiddenFromParent(String isHiddenFromParent) {
        this.isHiddenFromParent = isHiddenFromParent;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStandardsection() {
        return standardsection;
    }

    public void setStandardsection(String standardsection) {
        this.standardsection = standardsection;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Observation{" +
                "text='" + text + '\'' +
                ", teacherName='" + teacherName + '\'' +
                ", image='" + image + '\'' +
                ", date='" + date + '\'' +
                ", standardsection='" + standardsection + '\'' +
                ", isHiddenFromParent='" + isHiddenFromParent + '\'' +
                '}';
    }
}

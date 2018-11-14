package com.maxlore.edumanage.Models.ParentModels;

/**
 * Created by maxlore on 27-Aug-16.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ParentAssignment {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("subject")
    @Expose
    private String subject;
    @SerializedName("teacher_name")
    @Expose
    private String teacherName;

    /**
     *
     * @return
     * The title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     * The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     * The endDate
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     *
     * @param endDate
     * The end_date
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     *
     * @return
     * The subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     *
     * @param subject
     * The subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     *
     * @return
     * The teacherName
     */
    public String getTeacherName() {
        return teacherName;
    }

    /**
     *
     * @param teacherName
     * The teacher_name
     */
    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

}
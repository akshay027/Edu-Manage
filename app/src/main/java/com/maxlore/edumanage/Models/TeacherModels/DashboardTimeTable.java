package com.maxlore.edumanage.Models.TeacherModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Nikhil on 05-06-2016.
 */
public class DashboardTimeTable {

    @SerializedName("class_name")
    @Expose
    private String className;
    @SerializedName("period")
    @Expose
    private String period;
    @SerializedName("subject")
    @Expose
    private String subject;
    @SerializedName("timing")
    @Expose
    private String timing;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTiming() {
        return timing;
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }


    @Override
    public String toString() {
        return "DashboardTimeTable{" +
                "className='" + className + '\'' +
                ", period='" + period + '\'' +
                ", subject='" + subject + '\'' +
                ", timing='" + timing + '\'' +
                '}';
    }
}

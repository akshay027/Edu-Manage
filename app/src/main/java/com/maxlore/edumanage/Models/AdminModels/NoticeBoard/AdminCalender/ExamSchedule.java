package com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminCalender;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by maxlore on 3/28/2017.
 */

public class ExamSchedule {
    @SerializedName("class_name")
    @Expose
    private String className;
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

}

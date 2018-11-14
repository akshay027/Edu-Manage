package com.maxlore.edumanage.Models.AdminModels.NoticeBoard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by maxlore on 09-Mar-17.
 */

public class AdminAbsentStudentResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("absent_students")
    @Expose
    private AdminAbsentStudent absentStudents;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public AdminAbsentStudent getAbsentStudents() {
        return absentStudents;
    }

    public void setAbsentStudents(AdminAbsentStudent absentStudents) {
        this.absentStudents = absentStudents;
    }
}

package com.maxlore.edumanage.Models.AdminModels.NoticeBoard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by maxlore on 10-Mar-17.
 */

public class AdminTeacherAbsentResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("absent_employees")
    @Expose
    private AbsentEmployees absentEmployees;

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

    public AbsentEmployees getAbsentEmployees() {
        return absentEmployees;
    }

    public void setAbsentEmployees(AbsentEmployees absentEmployees) {
        this.absentEmployees = absentEmployees;
    }

}

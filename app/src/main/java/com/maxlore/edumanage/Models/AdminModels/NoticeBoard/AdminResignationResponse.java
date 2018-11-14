package com.maxlore.edumanage.Models.AdminModels.NoticeBoard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by maxlore on 10-Mar-17.
 */

public class AdminResignationResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("resignations")
    @Expose
    private List<AdminResignation> resignations = null;

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

    public List<AdminResignation> getResignations() {
        return resignations;
    }

    public void setResignations(List<AdminResignation> resignations) {
        this.resignations = resignations;
    }

}

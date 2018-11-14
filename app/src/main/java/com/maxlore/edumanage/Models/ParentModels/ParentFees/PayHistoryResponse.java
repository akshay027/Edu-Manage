package com.maxlore.edumanage.Models.ParentModels.ParentFees;

/**
 * Created by Shrey on 31-Jul-17.
 */


import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PayHistoryResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("student")
    @Expose
    private List<PayHistoryData> student = null;
    @SerializedName("message")
    @Expose
    private String message;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<PayHistoryData> getStudent() {
        return student;
    }

    public void setStudent(List<PayHistoryData> student) {
        this.student = student;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "PayHistoryResponse{" +
                "status=" + status +
                ", student=" + student +
                ", message='" + message + '\'' +
                '}';
    }
}
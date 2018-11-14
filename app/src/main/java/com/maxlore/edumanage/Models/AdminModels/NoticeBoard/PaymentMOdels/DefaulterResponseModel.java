package com.maxlore.edumanage.Models.AdminModels.NoticeBoard.PaymentMOdels;

/**
 * Created by Shrey on 24-Jul-17.
 */


import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DefaulterResponseModel {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("defaulters")
    @Expose
    private List<DefaulterDatamodel> defaulters = null;

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

    public List<DefaulterDatamodel> getDefaulters() {
        return defaulters;
    }

    public void setDefaulters(List<DefaulterDatamodel> defaulters) {
        this.defaulters = defaulters;
    }

    @Override
    public String toString() {
        return "DefaulterResponseModel{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", defaulters=" + defaulters +
                '}';
    }
}
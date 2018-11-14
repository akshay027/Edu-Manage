package com.maxlore.edumanage.Models.AdminModels.NoticeBoard.PaymentMOdels;

/**
 * Created by Shrey on 25-Jul-17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FiltersResponse {
    @Override
    public String toString() {
        return "FiltersResponse{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", filters=" + filters +
                '}';
    }

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("filters")
    @Expose
    private FiltersModel filters;

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

    public FiltersModel getFilters() {
        return filters;
    }

    public void setFilters(FiltersModel filters) {
        this.filters = filters;
    }

}
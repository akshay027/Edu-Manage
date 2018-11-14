package com.maxlore.edumanage.Models.ParentModels;

/**
 * Created by maxlore on 26-Aug-16.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ParentDashboardResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("dashboard")
    @Expose
    private ParentDashboard dashboard;
    @SerializedName("message")
    @Expose
    private String message;

    /**
     * @return The status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status The status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return The dashboard
     */
    public ParentDashboard getDashboard() {
        return dashboard;
    }

    /**
     * @param dashboard The dashboard
     */
    public void setDashboard(ParentDashboard dashboard) {
        this.dashboard = dashboard;
    }

    /**
     * @return The message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "DashboardResponse{" +
                "status=" + status +
                ", dashboard=" + dashboard +
                ", message='" + message + '\'' +
                '}';
    }
}
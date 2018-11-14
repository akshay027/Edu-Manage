package com.maxlore.edumanage.Models.TeacherModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Nikhil on 05-06-2016.
 */
public class DashboardResponse {


    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("dashboard")
    @Expose
    private Dashboard dashboard;
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
    public Dashboard getDashboard() {
        return dashboard;
    }

    /**
     * @param dashboard The dashboard
     */
    public void setDashboard(Dashboard dashboard) {
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

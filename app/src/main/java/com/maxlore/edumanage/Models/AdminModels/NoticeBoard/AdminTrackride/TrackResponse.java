package com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminTrackride;

/**
 * Created by Shrey on 13-Mar-17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TrackResponse {

    @SerializedName("bus_details")
    @Expose
    private List<AdminBusDetail> busDetails = null;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<AdminBusDetail> getBusDetails() {
        return busDetails;
    }

    public void setBusDetails(List<AdminBusDetail> busDetails) {
        this.busDetails = busDetails;
    }

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


    @Override
    public String toString() {
        return "TrackResponse{" +
                "busDetails=" + busDetails +
                ", status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}
package com.maxlore.edumanage.Models.TeacherModels.TrackTrip;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Nikhil on 27-08-2016.
 */
public class TripCheckResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("trip_id")
    @Expose
    private String tripId ;
    @SerializedName("message")
    @Expose
    private String message;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "TripCheckResponse{" +
                "status=" + status +
                ", tripId='" + tripId + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}

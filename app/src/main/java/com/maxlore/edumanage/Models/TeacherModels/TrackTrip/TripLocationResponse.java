package com.maxlore.edumanage.Models.TeacherModels.TrackTrip;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nikhil on 27-08-2016.
 */
public class TripLocationResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("trip_detail")
    @Expose
    private List<TripLocation> tripDetail = new ArrayList<TripLocation>();
    @SerializedName("message")
    @Expose
    private String message;


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<TripLocation> getTripDetail() {
        return tripDetail;
    }

    public void setTripDetail(List<TripLocation> tripDetail) {
        this.tripDetail = tripDetail;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    @Override
    public String toString() {
        return "TripLocationResponse{" +
                "status=" + status +
                ", tripDetail=" + tripDetail +
                ", message='" + message + '\'' +
                '}';
    }
}

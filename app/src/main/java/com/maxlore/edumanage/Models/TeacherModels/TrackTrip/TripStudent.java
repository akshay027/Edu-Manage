package com.maxlore.edumanage.Models.TeacherModels.TrackTrip;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Nikhil on 27-08-2016.
 */
public class TripStudent implements Serializable {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("trip_id")
    @Expose
    private String tripId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    @Override
    public String toString() {
        return "TripStudent{" +
                "name='" + name + '\'' +
                ", tripId='" + tripId + '\'' +
                '}';
    }
}

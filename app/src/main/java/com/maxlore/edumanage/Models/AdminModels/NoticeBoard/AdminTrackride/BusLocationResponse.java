package com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminTrackride;

/**
 * Created by Shrey on 15-Mar-17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BusLocationResponse {

    @SerializedName("stops")
    @Expose
    private List<StopNames> stops;
    @SerializedName("bus_location")
    @Expose
    private BusLocation busLocation;
    @SerializedName("driver")
    @Expose
    private Driver driver;
    @SerializedName("attendant")
    @Expose
    private Attendant attendant;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<StopNames> getStops() {
        return stops;
    }

    public void setStops(List<StopNames> stops) {
        this.stops = stops;
    }

    public BusLocation getBusLocation() {
        return busLocation;
    }

    public void setBusLocation(BusLocation busLocation) {
        this.busLocation = busLocation;
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

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Attendant getAttendant() {
        return attendant;
    }

    public void setAttendant(Attendant attendant) {
        this.attendant = attendant;
    }

    @Override
    public String toString() {
        return "BusLocationResponse{" +
                "stops=" + stops +
                ", busLocation=" + busLocation +
                ", driver=" + driver +
                ", attendant=" + attendant +
                ", status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}
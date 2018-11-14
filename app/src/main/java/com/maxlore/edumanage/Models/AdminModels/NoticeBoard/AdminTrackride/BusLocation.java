package com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminTrackride;

/**
 * Created by Shrey on 15-Mar-17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BusLocation {

    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitutde")
    @Expose
    private String longitutde;
    @SerializedName("avg_speed")
    @Expose
    private Double avgSpeed;
    @SerializedName("max_speed")
    @Expose
    private Double maxSpeed;

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitutde() {
        return longitutde;
    }

    public void setLongitutde(String longitutde) {
        this.longitutde = longitutde;
    }

    public Double getAvgSpeed() {
        return avgSpeed;
    }

    public void setAvgSpeed(Double avgSpeed) {
        this.avgSpeed = avgSpeed;
    }

    public Double getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(Double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    @Override
    public String toString() {
        return "BusLocation{" +
                "latitude='" + latitude + '\'' +
                ", longitutde='" + longitutde + '\'' +
                ", avgSpeed=" + avgSpeed +
                ", maxSpeed=" + maxSpeed +
                '}';
    }
}
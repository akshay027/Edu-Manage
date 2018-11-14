package com.maxlore.edumanage.Models.TeacherModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Nikhil on 9/13/2016.
 */
public class ChatDriver {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("driver_id")
    @Expose
    private int driverId;
    @SerializedName("driver_name")
    @Expose
    private String driverName;
    @SerializedName("flag")
    @Expose
    private boolean flag;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }


    @Override
    public String toString() {
        return "ChatDriver{" +
                "name='" + name + '\'' +
                ", driverId=" + driverId +
                ", driverName='" + driverName + '\'' +
                ", flag=" + flag +
                '}';
    }
}

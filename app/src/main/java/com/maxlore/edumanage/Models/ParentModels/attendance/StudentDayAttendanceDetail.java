package com.maxlore.edumanage.Models.ParentModels.attendance;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Nikhil on 9/11/2016.
 */
public class StudentDayAttendanceDetail {
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("attendance_status")
    @Expose
    private int attendanceStatus;
    @SerializedName("flag")
    @Expose
    private boolean currentday;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getAttendanceStatus() {
        return attendanceStatus;
    }

    public void setAttendanceStatus(int attendanceStatus) {
        this.attendanceStatus = attendanceStatus;
    }

    public boolean isCurrentday() {
        return currentday;
    }

    public void setCurrentday(boolean currentday) {
        this.currentday = currentday;
    }

    @Override
    public String toString() {
        return "StudentDayAttendanceDetail{" +
                "date='" + date + '\'' +
                ", attendanceStatus=" + attendanceStatus +
                ", currentday=" + currentday +
                '}';
    }
}

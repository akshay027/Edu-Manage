package com.maxlore.edumanage.Models.TeacherModels.OwnAtt;

/**
 * Created by maxlore on 13-Apr-17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmployeeDayAttendanceDetail {

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("attendance_status")
    @Expose
    private Integer attendanceStatus;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getAttendanceStatus() {
        return attendanceStatus;
    }

    public void setAttendanceStatus(Integer attendanceStatus) {
        this.attendanceStatus = attendanceStatus;
    }

    @Override
    public String toString() {
        return "EmployeeDayAttendanceDetail{" +
                "date='" + date + '\'' +
                ", attendanceStatus=" + attendanceStatus +
                '}';
    }
}
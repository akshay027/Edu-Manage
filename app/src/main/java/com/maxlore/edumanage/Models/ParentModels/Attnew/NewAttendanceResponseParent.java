package com.maxlore.edumanage.Models.ParentModels.Attnew;

/**
 * Created by maxlore on 12-Apr-17.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewAttendanceResponseParent {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("attendance_type")
    @Expose
    private Boolean attendanceType;
    @SerializedName("attendance_detail")
    @Expose
    private List<NewStudentDayAttendanceDetail> studentDayAttendanceDetail = null;
    @SerializedName("message")
    @Expose
    private String message;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Boolean getAttendanceType() {
        return attendanceType;
    }

    public void setAttendanceType(Boolean attendanceType) {
        this.attendanceType = attendanceType;
    }

    public List<NewStudentDayAttendanceDetail> getStudentDayAttendanceDetail() {
        return studentDayAttendanceDetail;
    }

    public void setStudentDayAttendanceDetail(List<NewStudentDayAttendanceDetail> studentDayAttendanceDetail) {
        this.studentDayAttendanceDetail = studentDayAttendanceDetail;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "NewAttendanceResponseParent{" +
                "status=" + status +
                ", attendanceType=" + attendanceType +
                ", studentDayAttendanceDetail=" + studentDayAttendanceDetail +
                ", message='" + message + '\'' +
                '}';
    }
}
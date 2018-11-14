package com.maxlore.edumanage.Models.TeacherModels.OwnAtt;

/**
 * Created by maxlore on 13-Apr-17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OwnAttResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("attendance_detail")
    @Expose
    private List<EmployeeDayAttendanceDetail> employeeDayAttendanceDetail = null;
    @SerializedName("message")
    @Expose
    private String message;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<EmployeeDayAttendanceDetail> getEmployeeDayAttendanceDetail() {
        return employeeDayAttendanceDetail;
    }

    public void setEmployeeDayAttendanceDetail(List<EmployeeDayAttendanceDetail> employeeDayAttendanceDetail) {
        this.employeeDayAttendanceDetail = employeeDayAttendanceDetail;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "OwnAttResponse{" +
                "status=" + status +
                ", employeeDayAttendanceDetail=" + employeeDayAttendanceDetail +
                ", message='" + message + '\'' +
                '}';
    }
}
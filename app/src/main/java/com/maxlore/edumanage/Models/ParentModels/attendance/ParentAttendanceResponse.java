package com.maxlore.edumanage.Models.ParentModels.attendance;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nikhil on 9/11/2016.
 */
public class ParentAttendanceResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("student_day_attendance_detail")
    @Expose
    private List<StudentDayAttendanceDetail> studentDayAttendanceDetail = new ArrayList<StudentDayAttendanceDetail>();
    @SerializedName("message")
    @Expose
    private String message;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<StudentDayAttendanceDetail> getStudentDayAttendanceDetail() {
        return studentDayAttendanceDetail;
    }

    public void setStudentDayAttendanceDetail(List<StudentDayAttendanceDetail> studentDayAttendanceDetail) {
        this.studentDayAttendanceDetail = studentDayAttendanceDetail;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

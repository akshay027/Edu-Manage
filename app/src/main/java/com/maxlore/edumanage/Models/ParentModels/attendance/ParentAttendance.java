package com.maxlore.edumanage.Models.ParentModels.attendance;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nikhil on 9/11/2016.
 */
public class ParentAttendance {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("month_year_list")
    @Expose
    private List<MonthYearList> monthYearList = new ArrayList<MonthYearList>();
    @SerializedName("student_list")
    @Expose
    private List<StudentList> studentList = new ArrayList<StudentList>();
    @SerializedName("message")
    @Expose
    private String message;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<MonthYearList> getMonthYearList() {
        return monthYearList;
    }

    public void setMonthYearList(List<MonthYearList> monthYearList) {
        this.monthYearList = monthYearList;
    }

    public List<StudentList> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<StudentList> studentList) {
        this.studentList = studentList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ParentAttendance{" +
                "status=" + status +
                ", monthYearList=" + monthYearList +
                ", studentList=" + studentList +
                ", message='" + message + '\'' +
                '}';
    }
}

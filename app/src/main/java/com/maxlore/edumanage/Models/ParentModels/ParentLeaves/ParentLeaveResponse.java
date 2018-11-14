package com.maxlore.edumanage.Models.ParentModels.ParentLeaves;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by maxlore on 8/26/2016.
 */
public class ParentLeaveResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("student_id")
    @Expose
    private Integer studentId;
    @SerializedName("leaves")
    @Expose
    private List<ParentLeaveHistory> leaves;
    @SerializedName("class_teacher")
    @Expose
    private String classTeacher;
    @SerializedName("leave_dates")
    @Expose
    private List<String> leaveDates;
    @SerializedName("message")
    @Expose
    private String message;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public List<ParentLeaveHistory> getLeaves() {
        return leaves;
    }

    public void setLeaves(List<ParentLeaveHistory> leaves) {
        this.leaves = leaves;
    }

    public String getClassTeacher() {
        return classTeacher;
    }

    public void setClassTeacher(String classTeacher) {
        this.classTeacher = classTeacher;
    }

    public List<String> getLeaveDates() {
        return leaveDates;
    }

    public void setLeaveDates(List<String> leaveDates) {
        this.leaveDates = leaveDates;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ParentLeaveResponse{" +
                "status=" + status +
                ", name='" + name + '\'' +
                ", studentId=" + studentId +
                ", leaves=" + leaves +
                ", classTeacher='" + classTeacher + '\'' +
                ", leaveDates=" + leaveDates +
                ", message='" + message + '\'' +
                '}';
    }
}

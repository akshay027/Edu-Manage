package com.maxlore.edumanage.Models.ParentModels.ParentLeaves;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by maxlore on 8/26/2016.
 */
public class ParentLeave {


    @SerializedName("student_id")
    @Expose
    private Integer studentId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("class_teacher_name")
    @Expose
    private String classTeacherName;
    @SerializedName("leaves")
    @Expose
    private java.util.List<ParentLeaveHistory> leaves;
    @SerializedName("leave_dates")
    @Expose
    private java.util.List<String> leaveDates;

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassTeacherName() {
        return classTeacherName;
    }

    public void setClassTeacherName(String classTeacherName) {
        this.classTeacherName = classTeacherName;
    }

    public java.util.List<ParentLeaveHistory> getLeaves() {
        return leaves;
    }

    public void setLeaves(List<ParentLeaveHistory> leaves) {
        this.leaves = leaves;
    }

    public java.util.List<String> getLeaveDates() {
        return leaveDates;
    }

    public void setLeaveDates(java.util.List<String> leaveDates) {
        this.leaveDates = leaveDates;
    }


    @Override
    public String toString() {
        return "ParentLeave{" +
                "studentId=" + studentId +
                ", name='" + name + '\'' +
                ", classTeacherName='" + classTeacherName + '\'' +
                ", leaves=" + leaves +
                ", leaveDates=" + leaveDates +
                '}';
    }
}

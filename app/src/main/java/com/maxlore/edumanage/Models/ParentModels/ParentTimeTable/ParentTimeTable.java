package com.maxlore.edumanage.Models.ParentModels.ParentTimeTable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maxlore on 28-Aug-16.
 */
public class ParentTimeTable {

    @SerializedName("student_id")
    @Expose
    private Integer studentId;
    @SerializedName("student_name")
    @Expose
    private String studentName;
    @SerializedName("time_table_structure")
    @Expose
    private List<ParentTimeTableStructure> timeTableStructure = new ArrayList<ParentTimeTableStructure>();

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public List<ParentTimeTableStructure> getTimeTableStructure() {
        return timeTableStructure;
    }

    public void setTimeTableStructure(List<ParentTimeTableStructure> timeTableStructure) {
        this.timeTableStructure = timeTableStructure;
    }

    @Override
    public String toString() {
        return "ParentTimeTable{" +
                "studentId=" + studentId +
                ", studentName='" + studentName + '\'' +
                ", timeTableStructure=" + timeTableStructure +
                '}';
    }
}

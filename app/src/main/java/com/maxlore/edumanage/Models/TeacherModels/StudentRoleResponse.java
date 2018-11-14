package com.maxlore.edumanage.Models.TeacherModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by maxlore on 4/6/2017.
 */

public class StudentRoleResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("students")
    @Expose
    private List<StudentsRole> students = null;
    @SerializedName("message")
    @Expose
    private String message;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<StudentsRole> getStudents() {
        return students;
    }

    public void setStudents(List<StudentsRole> students) {
        this.students = students;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

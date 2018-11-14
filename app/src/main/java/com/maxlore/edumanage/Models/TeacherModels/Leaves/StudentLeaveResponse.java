package com.maxlore.edumanage.Models.TeacherModels.Leaves;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nikhil on 19-06-2016.
 */
public class StudentLeaveResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("student_leave")
    @Expose
    private List<StudentLeave> studentLeave = new ArrayList<StudentLeave>();
    @SerializedName("message")
    @Expose
    private String message;

    /**
     * @return The status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status The status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return The studentLeave
     */
    public List<StudentLeave> getStudentLeave() {
        return studentLeave;
    }

    /**
     * @param studentLeave The student_leave
     */
    public void setStudentLeave(List<StudentLeave> studentLeave) {
        this.studentLeave = studentLeave;
    }

    /**
     * @return The message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

}


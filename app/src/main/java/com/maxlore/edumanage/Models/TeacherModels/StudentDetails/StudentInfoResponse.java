package com.maxlore.edumanage.Models.TeacherModels.StudentDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maxlore on 8/20/2016.
 */
public class StudentInfoResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("student")
    @Expose
    private List<StudentInfo> student = new ArrayList<StudentInfo>();
    @SerializedName("message")
    @Expose
    private String message;

    /**
     *
     * @return
     * The status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     *
     * @return
     * The student
     */
    public List<StudentInfo> getStudent() {
        return student;
    }

    /**
     *
     * @param student
     * The student
     */
    public void setStudent(List<StudentInfo> student) {
        this.student = student;
    }

    /**
     *
     * @return
     * The message
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @param message
     * The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

}


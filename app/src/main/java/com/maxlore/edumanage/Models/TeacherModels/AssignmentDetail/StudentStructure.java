package com.maxlore.edumanage.Models.TeacherModels.AssignmentDetail;

/**
 * Created by maxlore on 11-Aug-16.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class StudentStructure {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("student")
    @Expose
    private List<Student> student = new ArrayList<Student>();
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
    public List<Student> getStudent() {
        return student;
    }

    /**
     *
     * @param student
     * The student
     */
    public void setStudent(List<Student> student) {
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

    @Override
    public String toString() {
        return "StudentStructure{" +
                "status=" + status +
                ", student=" + student +
                ", message='" + message + '\'' +
                '}';
    }
}
package com.maxlore.edumanage.Models.ParentModels.ParentSyllabus;

/**
 * Created by maxlore on 30-Aug-16.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ParentSyllabusResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("student_syllabus")
    @Expose
    private List<ParentSyllabus> studentSyllabus = new ArrayList<ParentSyllabus>();
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
     * The studentSyllabus
     */
    public List<ParentSyllabus> getStudentSyllabus() {
        return studentSyllabus;
    }

    /**
     *
     * @param studentSyllabus
     * The student_syllabus
     */
    public void setStudentSyllabus(List<ParentSyllabus> studentSyllabus) {
        this.studentSyllabus = studentSyllabus;
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
        return "ParentSyllabusResponse{" +
                "status=" + status +
                ", studentSyllabus=" + studentSyllabus +
                ", message='" + message + '\'' +
                '}';
    }
}
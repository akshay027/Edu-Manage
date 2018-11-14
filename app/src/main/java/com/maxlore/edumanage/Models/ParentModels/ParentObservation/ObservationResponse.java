package com.maxlore.edumanage.Models.ParentModels.ParentObservation;

/**
 * Created by maxlore on 31-Aug-16.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class ObservationResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("student_observation")
    @Expose
    private List<StudentObservation> studentObservation = new ArrayList<StudentObservation>();
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
     * The studentObservation
     */
    public List<StudentObservation> getStudentObservation() {
        return studentObservation;
    }

    /**
     *
     * @param studentObservation
     * The student_observation
     */
    public void setStudentObservation(List<StudentObservation> studentObservation) {
        this.studentObservation = studentObservation;
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
        return "ObservationResponse{" +
                "status=" + status +
                ", studentObservation=" + studentObservation +
                ", message='" + message + '\'' +
                '}';
    }
}
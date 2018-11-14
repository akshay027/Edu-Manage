package com.maxlore.edumanage.Models.ParentModels.FeeHistory;

/**
 * Created by maxlore on 04-Nov-16.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class FeeHistoryResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("student")
    @Expose
    private List<FeeHistoryStudent> student = new ArrayList<FeeHistoryStudent>();
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
    public List<FeeHistoryStudent> getStudent() {
        return student;
    }

    /**
     *
     * @param student
     * The student
     */
    public void setStudent(List<FeeHistoryStudent> student) {
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
        return "FeeHistoryResponse{" +
                "status=" + status +
                ", student=" + student +
                ", message='" + message + '\'' +
                '}';
    }
}
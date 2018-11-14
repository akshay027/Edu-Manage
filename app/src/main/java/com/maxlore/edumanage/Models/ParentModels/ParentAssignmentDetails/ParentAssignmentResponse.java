package com.maxlore.edumanage.Models.ParentModels.ParentAssignmentDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by maxlore on 8/31/2016.
 */
public class ParentAssignmentResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("student_assignment")
    @Expose
    private ParentAssignmentStructure studentAssignment;
    @SerializedName("message")
    @Expose
    private String message;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public ParentAssignmentStructure getStudentAssignment() {
        return studentAssignment;
    }

    public void setStudentAssignment(ParentAssignmentStructure studentAssignment) {
        this.studentAssignment = studentAssignment;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ParentAssignmentResponse{" +
                "status=" + status +
                ", studentAssignment=" + studentAssignment +
                ", message='" + message + '\'' +
                '}';
    }
}
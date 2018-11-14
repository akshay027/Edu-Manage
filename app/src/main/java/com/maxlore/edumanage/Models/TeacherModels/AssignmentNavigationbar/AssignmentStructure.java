package com.maxlore.edumanage.Models.TeacherModels.AssignmentNavigationbar;

/**
 * Created by maxlore on 10-Aug-16.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class AssignmentStructure {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("assignment")
    @Expose
    private List<Assignment> assignment = new ArrayList<Assignment>();
    @SerializedName("session_period")
    @Expose
    private Boolean session;
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
     * @return The assignment
     */
    public List<Assignment> getAssignment() {
        return assignment;
    }

    /**
     * @param assignment The assignment
     */
    public void setAssignment(List<Assignment> assignment) {
        this.assignment = assignment;
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

    public Boolean getSession() {
        return session;
    }

    public void setSession(Boolean session) {
        this.session = session;
    }

    @Override
    public String toString() {
        return "AssignmentStructure{" +
                "status=" + status +
                ", assignment=" + assignment +
                ", session=" + session +
                ", message='" + message + '\'' +
                '}';
    }
}
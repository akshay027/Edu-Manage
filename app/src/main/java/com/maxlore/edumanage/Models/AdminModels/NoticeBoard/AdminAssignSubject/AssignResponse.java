package com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminAssignSubject;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maxlore on 9/26/2016.
 */
public class AssignResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("employee")
    @Expose
    private List<Assign> employee = new ArrayList<Assign>();
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
     * The employee
     */
    public List<Assign> getEmployee() {
        return employee;
    }

    /**
     *
     * @param employee
     * The employee
     */
    public void setEmployee(List<Assign> employee) {
        this.employee = employee;
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
        return "AssignResponse{" +
                "status=" + status +
                ", employee=" + employee +
                ", message='" + message + '\'' +
                '}';
    }
}

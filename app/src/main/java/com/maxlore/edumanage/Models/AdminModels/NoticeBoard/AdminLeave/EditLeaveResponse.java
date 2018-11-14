package com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminLeave;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maxlore on 11/9/2016.
 */

public class EditLeaveResponse
{

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("employee_list")
    @Expose
    private List<AdminLeaveApplicantDetails> employeeList = new ArrayList<AdminLeaveApplicantDetails>();
    @SerializedName("leave_type")
    @Expose
    private List<EditData> leaveType = new ArrayList<EditData>();
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
     * The employeeList
     */
    public List<AdminLeaveApplicantDetails> getEmployeeList() {
        return employeeList;
    }

    /**
     *
     * @param employeeList
     * The employee_list
     */
    public void setEmployeeList(List<AdminLeaveApplicantDetails> employeeList) {
        this.employeeList = employeeList;
    }

    /**
     *
     * @return
     * The leaveType
     */
    public List<EditData> getLeaveType() {
        return leaveType;
    }

    /**
     *
     * @param leaveType
     * The leave_type
     */
    public void setLeaveType(List<EditData> leaveType) {
        this.leaveType = leaveType;
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


package com.maxlore.edumanage.Models.TeacherModels.Leaves;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Shrey on 28-03-2017.
 */
public class LeaveHistory {
    @SerializedName("leave_application_id")
    @Expose
    private String leavapplicationid;
    @SerializedName("employee_leave_type")
    @Expose
    private String employeeLeaveType;
    @SerializedName("half_day")
    @Expose
    private Boolean halfDay;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("reason")
    @Expose
    private String reason;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("remark")
    @Expose
    private String remark;

    /**
     * @return The employeeLeaveType
     */
    public String getEmployeeLeaveType() {
        return employeeLeaveType;
    }

    /**
     * @param employeeLeaveType The employee_leave_type
     */
    public void setEmployeeLeaveType(String employeeLeaveType) {
        this.employeeLeaveType = employeeLeaveType;
    }

    /**
     * @return The halfDay
     */
    public Boolean getHalfDay() {
        return halfDay;
    }

    /**
     * @param halfDay The half_day
     */
    public void setHalfDay(Boolean halfDay) {
        this.halfDay = halfDay;
    }

    /**
     * @return The startDate
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * @param startDate The start_date
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * @return The endDate
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * @param endDate The end_date
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * @return The reason
     */
    public String getReason() {
        return reason;
    }

    /**
     * @param reason The reason
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * @return The status
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     * @param status The status
     */
    public void setStatus(Boolean status) {
        this.status = status;
    }

    /**
     * @return The remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark The remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getLeavapplicationid() {
        return leavapplicationid;
    }

    public void setLeavapplicationid(String leavapplicationid) {
        this.leavapplicationid = leavapplicationid;
    }

    @Override
    public String toString() {
        return "LeaveHistory{" +
                "leavapplicationid='" + leavapplicationid + '\'' +
                ", employeeLeaveType='" + employeeLeaveType + '\'' +
                ", halfDay=" + halfDay +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", reason='" + reason + '\'' +
                ", status=" + status +
                ", remark='" + remark + '\'' +
                '}';
    }
}

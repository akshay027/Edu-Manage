package com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminLeave;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by maxlore on 11/9/2016.
 */

public class AdminLeaveApplicantDetails {
    @SerializedName("employee_id")
    @Expose
    private Integer employeeId;
    @SerializedName("leave_id")
    @Expose
    private Integer leaveId;
    @SerializedName("employee_name")
    @Expose
    private String employeeName;
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
    @SerializedName("start_date_display")
    @Expose
    private String startDateDisplay;
    @SerializedName("end_date_display")
    @Expose
    private String endDateDisplay;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("remark")
    @Expose
    private String remark;
    @SerializedName("reason")
    @Expose
    private String reason;
    @SerializedName("photo")
    @Expose
    private String photo;

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    private boolean a, b;
    public String getStartDateDisplay() {
        return startDateDisplay;
    }

    public void setStartDateDisplay(String startDateDisplay) {
        this.startDateDisplay = startDateDisplay;
    }

    public String getEndDateDisplay() {
        return endDateDisplay;
    }

    public void setEndDateDisplay(String endDateDisplay) {
        this.endDateDisplay = endDateDisplay;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getLeaveId() {
        return leaveId;
    }

    public void setLeaveId(Integer leaveId) {
        this.leaveId = leaveId;
    }

    /**
     *
     * @return
     * The employeeId
     */
    public Integer getEmployeeId() {
        return employeeId;
    }

    /**
     *
     * @param employeeId
     * The employee_id
     */
    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    /**
     *
     * @return
     * The employeeName
     */
    public String getEmployeeName() {
        return employeeName;
    }

    /**
     *
     * @param employeeName
     * The employee_name
     */
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    /**
     *
     * @return
     * The employeeLeaveType
     */
    public String getEmployeeLeaveType() {
        return employeeLeaveType;
    }

    /**
     *
     * @param employeeLeaveType
     * The employee_leave_type
     */
    public void setEmployeeLeaveType(String employeeLeaveType) {
        this.employeeLeaveType = employeeLeaveType;
    }

    /**
     *
     * @return
     * The halfDay
     */
    public Boolean getHalfDay() {
        return halfDay;
    }

    /**
     *
     * @param halfDay
     * The half_day
     */
    public void setHalfDay(Boolean halfDay) {
        this.halfDay = halfDay;
    }

    /**
     *
     * @return
     * The startDate
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     *
     * @param startDate
     * The start_date
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     *
     * @return
     * The endDate
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     *
     * @param endDate
     * The end_date
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     *
     * @return
     * The status
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(Boolean status) {
        this.status = status;
    }

    /**
     *
     * @return
     * The remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     *
     * @param remark
     * The remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean isA() {
        return a;
    }

    public void setA(boolean a) {
        this.a = a;
    }

    public boolean isB() {
        return b;
    }

    public void setB(boolean b) {
        this.b = b;
    }

    @Override
    public String toString() {
        return "AdminLeaveApplicantDetails{" +
                "employeeId=" + employeeId +
                ", leaveId=" + leaveId +
                ", employeeName='" + employeeName + '\'' +
                ", employeeLeaveType='" + employeeLeaveType + '\'' +
                ", halfDay=" + halfDay +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", startDateDisplay='" + startDateDisplay + '\'' +
                ", endDateDisplay='" + endDateDisplay + '\'' +
                ", status=" + status +
                ", remark='" + remark + '\'' +
                ", reason='" + reason + '\'' +
                ", a=" + a +
                ", b=" + b +
                '}';
    }
}

package com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminAttendance;

/**
 * Created by maxlore on 04-Oct-16.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class EmployeeAttendance {

    @SerializedName("department_id")
    @Expose
    private Integer departmentId;
    @SerializedName("department_name")
    @Expose
    private String departmentName;
    @SerializedName("staff_count")
    @Expose
    private String staffcount;

    /**
     *
     * @return
     * The departmentId
     */
    public Integer getDepartmentId() {
        return departmentId;
    }

    /**
     *
     * @param departmentId
     * The department_id
     */
    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    /**
     *
     * @return
     * The departmentName
     */
    public String getDepartmentName() {
        return departmentName;
    }

    /**
     *
     * @param departmentName
     * The department_name
     */
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getStaffcount() {
        return staffcount;
    }

    public void setStaffcount(String staffcount) {
        this.staffcount = staffcount;
    }

    @Override
    public String toString() {
        return "EmployeeAttendance{" +
                "departmentId=" + departmentId +
                ", departmentName='" + departmentName + '\'' +
                ", staffcount='" + staffcount + '\'' +
                '}';
    }
}



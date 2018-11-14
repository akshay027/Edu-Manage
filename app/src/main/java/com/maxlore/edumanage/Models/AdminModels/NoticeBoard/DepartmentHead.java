package com.maxlore.edumanage.Models.AdminModels.NoticeBoard;

/**
 * Created by Shrey on 26-Sep-17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DepartmentHead {

    @SerializedName("department_id")
    @Expose
    private Integer departmentId;
    @SerializedName("department_name")
    @Expose
    private String departmentName;
    @SerializedName("department_head_status")
    @Expose
    private Integer departmentHeadStatus;
    @SerializedName("department_head_id")
    @Expose
    private Integer departmentHeadId;
    @SerializedName("department_head_name")
    @Expose
    private String departmentHeadName;
    @SerializedName("employee_id")
    @Expose
    private Integer employeeId;
    @SerializedName("photo")
    @Expose
    private String photo;

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Integer getDepartmentHeadStatus() {
        return departmentHeadStatus;
    }

    public void setDepartmentHeadStatus(Integer departmentHeadStatus) {
        this.departmentHeadStatus = departmentHeadStatus;
    }

    public Integer getDepartmentHeadId() {
        return departmentHeadId;
    }

    public void setDepartmentHeadId(Integer departmentHeadId) {
        this.departmentHeadId = departmentHeadId;
    }

    public String getDepartmentHeadName() {
        return departmentHeadName;
    }

    public void setDepartmentHeadName(String departmentHeadName) {
        this.departmentHeadName = departmentHeadName;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "DepartmentHead{" +
                "departmentId=" + departmentId +
                ", departmentName='" + departmentName + '\'' +
                ", departmentHeadStatus=" + departmentHeadStatus +
                ", departmentHeadId=" + departmentHeadId +
                ", departmentHeadName='" + departmentHeadName + '\'' +
                ", employeeId=" + employeeId +
                ", photo='" + photo + '\'' +
                '}';
    }
}

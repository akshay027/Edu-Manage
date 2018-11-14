package com.maxlore.edumanage.Models.AdminModels.NoticeBoard.ClassteacherModel;

/**
 * Created by maxlore on 28-Sep-16.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListTeacher {

    @SerializedName("employee_id")
    @Expose
    private Integer employeeId;
    @SerializedName("employee_name")
    @Expose
    private String employeeName;
    @SerializedName("employee_code")
    @Expose
    private String employeeCode;
    @SerializedName("photo")
    @Expose
    private String photo;
    private boolean isSelected;

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    /**
     * @return The employeeId
     */

    public Integer getEmployeeId() {
        return employeeId;
    }

    /**
     * @param employeeId The employee_id
     */
    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * @return The employeeName
     */
    public String getEmployeeName() {
        return employeeName;
    }

    /**
     * @param employeeName The employee_name
     */
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public String toString() {
        return "ListTeacher{" +
                "employeeId=" + employeeId +
                ", employeeName='" + employeeName + '\'' +
                ", employeeCode='" + employeeCode + '\'' +
                ", photo='" + photo + '\'' +
                ", isSelected=" + isSelected +
                '}';
    }
}

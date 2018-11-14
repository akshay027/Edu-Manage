package com.maxlore.edumanage.Models.AdminModels.NoticeBoard.SubstituteTeacher;

/**
 * Created by maxlore on 17-Oct-16.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Absentlist {

    @SerializedName("employee_id")
    @Expose
    private Integer employeeId;
    @SerializedName("employee_name")
    @Expose
    private String employeeName;
    @SerializedName("photo")
    @Expose
    private String image;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Absentlist{" +
                "employeeId=" + employeeId +
                ", employeeName='" + employeeName + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
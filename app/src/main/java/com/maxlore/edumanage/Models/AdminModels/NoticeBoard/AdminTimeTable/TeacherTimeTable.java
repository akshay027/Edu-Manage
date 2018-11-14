package com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminTimeTable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by maxlore on 10/4/2016.
 */
public class TeacherTimeTable {

    @SerializedName("employee_id")
    @Expose
    private Integer employeeId;
    @SerializedName("employee_name")
    @Expose
    private String employeeName;
    @SerializedName("photo")
    @Expose
    private String photo;

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
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

    @Override
    public String toString() {
        return "TeacherTimeTable{" +
                "employeeId=" + employeeId +
                ", employeeName='" + employeeName + '\'' +
                '}';
    }
}

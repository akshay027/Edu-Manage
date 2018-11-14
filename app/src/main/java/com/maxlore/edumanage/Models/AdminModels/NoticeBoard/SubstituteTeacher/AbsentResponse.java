package com.maxlore.edumanage.Models.AdminModels.NoticeBoard.SubstituteTeacher;

/**
 * Created by maxlore on 17-Oct-16.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class AbsentResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("employee_list")
    @Expose
    private List<Absentlist> employeeList = new ArrayList<Absentlist>();

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

    /**
     *
     * @return
     * The employeeList
     */
    public List<Absentlist> getEmployeeList() {
        return employeeList;
    }

    /**
     *
     * @param employeeList
     * The employee_list
     */
    public void setEmployeeList(List<Absentlist> employeeList) {
        this.employeeList = employeeList;
    }

    @Override
    public String toString() {
        return "AbsentResponse{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", employeeList=" + employeeList +
                '}';
    }
}

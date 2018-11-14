package com.maxlore.edumanage.Models.AdminModels.NoticeBoard;

/**
 * Created by maxlore on 26-Sep-17.
 */

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DepartmentHeadResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("department_head")
    @Expose
    private List<DepartmentHead> departmentHead = null;
    @SerializedName("message")
    @Expose
    private String message;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<DepartmentHead> getDepartmentHead() {
        return departmentHead;
    }

    public void setDepartmentHead(List<DepartmentHead> departmentHead) {
        this.departmentHead = departmentHead;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "DepartmentHeadResponse{" +
                "status=" + status +
                ", departmentHead=" + departmentHead +
                ", message='" + message + '\'' +
                '}';
    }
}


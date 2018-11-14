package com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminBranchesResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Shrey on 13-Sep-17.
 */

public class AdminBranches {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("branches")
    @Expose
    private List<BranchesData> branchesDataList = null;
    @SerializedName("message")
    @Expose
    private String message;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<BranchesData> getBranchesDataList() {
        return branchesDataList;
    }

    public void setBranchesDataList(List<BranchesData> branchesDataList) {
        this.branchesDataList = branchesDataList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "AdminBranches{" +
                "status=" + status +
                ", branchesDataList=" + branchesDataList +
                ", message='" + message + '\'' +
                '}';
    }
}
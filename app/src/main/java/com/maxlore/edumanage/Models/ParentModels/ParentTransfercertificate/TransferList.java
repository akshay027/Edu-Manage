package com.maxlore.edumanage.Models.ParentModels.ParentTransfercertificate;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by maxlore on 8/28/2016.
 */
public class TransferList {


    @SerializedName("transfer_id")
    @Expose
    private Integer transferId;
    @SerializedName("applied_by")
    @Expose
    private String appliedBy;
    @SerializedName("applied_date")
    @Expose
    private String appliedDate;
    @SerializedName("remark")
    @Expose
    private String remark;
    @SerializedName("reason")
    @Expose
    private String reason;
    @SerializedName("issued_date")
    @Expose
    private String issuedDate;
    @SerializedName("status")
    @Expose
    private String status;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getTransferId() {
        return transferId;
    }

    public void setTransferId(Integer transferId) {
        this.transferId = transferId;
    }

    public String getAppliedBy() {
        return appliedBy;
    }

    public void setAppliedBy(String appliedBy) {
        this.appliedBy = appliedBy;
    }

    public String getAppliedDate() {
        return appliedDate;
    }

    public void setAppliedDate(String appliedDate) {
        this.appliedDate = appliedDate;
    }

    public String getremark() {
        return remark;
    }

    public void setremark(String remark) {
        this.remark = remark;
    }

    public String getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(String issuedDate) {
        this.issuedDate = issuedDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TransferList{" +
                "transferId=" + transferId +
                ", appliedBy='" + appliedBy + '\'' +
                ", appliedDate='" + appliedDate + '\'' +
                ", remark='" + remark + '\'' +
                ", issuedDate='" + issuedDate + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
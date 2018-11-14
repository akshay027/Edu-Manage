package com.maxlore.edumanage.Models.ParentModels.ParentTransfercertificate;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by maxlore on 8/28/2016.
 */
public class ParentTansfercertiResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("student_details")
    @Expose
    private TransferCertificate studentDetails;
    @SerializedName("transfer_certificate")
    @Expose
    private List<TransferList> transferCertificate = null;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("apply_again")
    @Expose
    private Boolean applyAgain;
    @SerializedName("days_left")
    @Expose
    private String daysLeft;

    public String getDaysLeft() {
        return daysLeft;
    }

    public void setDaysLeft(String daysLeft) {
        this.daysLeft = daysLeft;
    }

    public Boolean getApplyAgain() {
        return applyAgain;
    }

    public void setApplyAgain(Boolean applyAgain) {
        this.applyAgain = applyAgain;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public TransferCertificate getStudentDetails() {
        return studentDetails;
    }

    public void setStudentDetails(TransferCertificate studentDetails) {
        this.studentDetails = studentDetails;
    }

    public List<TransferList> getTransferCertificate() {
        return transferCertificate;
    }

    public void setTransferCertificate(List<TransferList> transferCertificate) {
        this.transferCertificate = transferCertificate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    @Override
    public String toString() {
        return "ParentTansfercertiResponse{" +
                "status=" + status +
                ", studentDetails=" + studentDetails +
                ", transferCertificate=" + transferCertificate +
                ", message='" + message + '\'' +
                ", applyAgain=" + applyAgain +
                ", daysLeft=" + daysLeft +
                '}';
    }
}

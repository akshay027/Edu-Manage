package com.maxlore.edumanage.Models.ParentModels.ParentFees;

/**
 * Created by Shrey on 02-Aug-17.
 */

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AcademicFeeResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("total_fees")
    @Expose
    private String totalFees;
    @SerializedName("total_late_fee_fines")
    @Expose
    private Integer totalLateFeeFines;
    @SerializedName("total_consessions")
    @Expose
    private String totalConsessions;
    @SerializedName("total_fees_paid")
    @Expose
    private String totalFeesPaid;
    @SerializedName("total_fees_to_be_paid")
    @Expose
    private String totalFeesToBePaid;
    @SerializedName("term_fees")
    @Expose
    private List<TermFeeData> termFees = null;
    @SerializedName("message")
    @Expose
    private String message;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTotalFees() {
        return totalFees;
    }

    public void setTotalFees(String totalFees) {
        this.totalFees = totalFees;
    }

    public Integer getTotalLateFeeFines() {
        return totalLateFeeFines;
    }

    public void setTotalLateFeeFines(Integer totalLateFeeFines) {
        this.totalLateFeeFines = totalLateFeeFines;
    }

    public String getTotalConsessions() {
        return totalConsessions;
    }

    public void setTotalConsessions(String totalConsessions) {
        this.totalConsessions = totalConsessions;
    }

    public String getTotalFeesPaid() {
        return totalFeesPaid;
    }

    public void setTotalFeesPaid(String totalFeesPaid) {
        this.totalFeesPaid = totalFeesPaid;
    }

    public String getTotalFeesToBePaid() {
        return totalFeesToBePaid;
    }

    public void setTotalFeesToBePaid(String totalFeesToBePaid) {
        this.totalFeesToBePaid = totalFeesToBePaid;
    }

    public List<TermFeeData> getTermFees() {
        return termFees;
    }

    public void setTermFees(List<TermFeeData> termFees) {
        this.termFees = termFees;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    @Override
    public String toString() {
        return "AcademicFeeResponse{" +
                "status=" + status +
                ", totalFees='" + totalFees + '\'' +
                ", totalLateFeeFines=" + totalLateFeeFines +
                ", totalConsessions='" + totalConsessions + '\'' +
                ", totalFeesPaid='" + totalFeesPaid + '\'' +
                ", totalFeesToBePaid='" + totalFeesToBePaid + '\'' +
                ", termFees=" + termFees +
                ", message='" + message + '\'' +
                '}';
    }

}
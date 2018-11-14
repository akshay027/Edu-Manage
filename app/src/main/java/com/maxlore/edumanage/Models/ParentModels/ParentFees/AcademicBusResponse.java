package com.maxlore.edumanage.Models.ParentModels.ParentFees;

/**
 * Created by maxlore on 22-Aug-17.
 */

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AcademicBusResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("total_academic_fees")
    @Expose
    private String totalAcademicFees;
    @SerializedName("total_bus_fees")
    @Expose
    private Integer totalBusFees;
    @SerializedName("total_late_fee_fines")
    @Expose
    private Integer totalLateFeeFines;
    @SerializedName("total_consessions")
    @Expose
    private Integer totalConsessions;
    @SerializedName("total_fees_paid")
    @Expose
    private String totalFeesPaid;
    @SerializedName("total_fees_to_be_paid")
    @Expose
    private String totalFeesToBePaid;
    @SerializedName("term_fees")
    @Expose
    private List<AcademicBusData> termFees = null;
    @SerializedName("message")
    @Expose
    private String message;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTotalAcademicFees() {
        return totalAcademicFees;
    }

    public void setTotalAcademicFees(String totalAcademicFees) {
        this.totalAcademicFees = totalAcademicFees;
    }

    public Integer getTotalBusFees() {
        return totalBusFees;
    }

    public void setTotalBusFees(Integer totalBusFees) {
        this.totalBusFees = totalBusFees;
    }

    public Integer getTotalLateFeeFines() {
        return totalLateFeeFines;
    }

    public void setTotalLateFeeFines(Integer totalLateFeeFines) {
        this.totalLateFeeFines = totalLateFeeFines;
    }

    public Integer getTotalConsessions() {
        return totalConsessions;
    }

    public void setTotalConsessions(Integer totalConsessions) {
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

    public List<AcademicBusData> getTermFees() {
        return termFees;
    }

    public void setTermFees(List<AcademicBusData> termFees) {
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
        return "AcademicBusResponse{" +
                "status=" + status +
                ", totalAcademicFees='" + totalAcademicFees + '\'' +
                ", totalBusFees=" + totalBusFees +
                ", totalLateFeeFines=" + totalLateFeeFines +
                ", totalConsessions=" + totalConsessions +
                ", totalFeesPaid='" + totalFeesPaid + '\'' +
                ", totalFeesToBePaid='" + totalFeesToBePaid + '\'' +
                ", termFees=" + termFees +
                ", message='" + message + '\'' +
                '}';
    }
}
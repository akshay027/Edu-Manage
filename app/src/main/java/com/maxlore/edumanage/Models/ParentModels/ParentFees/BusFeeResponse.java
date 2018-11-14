package com.maxlore.edumanage.Models.ParentModels.ParentFees;

/**
 * Created by Shrey on 21-Aug-17.
 */

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BusFeeResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("total_bus_fees")
    @Expose
    private Integer totalBusFees;
    @SerializedName("total_bus_fees_paid")
    @Expose
    private String totalBusFeesPaid;
    @SerializedName("total_bus_fees_to_be_paid")
    @Expose
    private String totalBusFeesToBePaid;
    @SerializedName("term_bus_fees")
    @Expose
    private List<TermBusFees> termBusFees = null;
    @SerializedName("message")
    @Expose
    private String message;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getTotalBusFees() {
        return totalBusFees;
    }

    public void setTotalBusFees(Integer totalBusFees) {
        this.totalBusFees = totalBusFees;
    }

    public String getTotalBusFeesPaid() {
        return totalBusFeesPaid;
    }

    public void setTotalBusFeesPaid(String totalBusFeesPaid) {
        this.totalBusFeesPaid = totalBusFeesPaid;
    }

    public String getTotalBusFeesToBePaid() {
        return totalBusFeesToBePaid;
    }

    public void setTotalBusFeesToBePaid(String totalBusFeesToBePaid) {
        this.totalBusFeesToBePaid = totalBusFeesToBePaid;
    }

    public List<TermBusFees> getTermBusFees() {
        return termBusFees;
    }

    public void setTermBusFees(List<TermBusFees> termBusFees) {
        this.termBusFees = termBusFees;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "BusFeeResponse{" +
                "status=" + status +
                ", totalBusFees=" + totalBusFees +
                ", totalBusFeesPaid='" + totalBusFeesPaid + '\'' +
                ", totalBusFeesToBePaid='" + totalBusFeesToBePaid + '\'' +
                ", termBusFees=" + termBusFees +
                ", message='" + message + '\'' +
                '}';
    }
}
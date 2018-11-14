package com.maxlore.edumanage.Models.ParentModels.ParentFees;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by maxlore on 9/8/2016.
 */
public class ParentFeeResponse {


    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("fees_structure")
    @Expose
    private ParentFeeStructure feesStructure;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("allow_payment")
    @Expose
    private Boolean allowPayment;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public ParentFeeStructure getFeesStructure() {
        return feesStructure;
    }

    public void setFeesStructure(ParentFeeStructure feesStructure) {
        this.feesStructure = feesStructure;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getAllowPayment() {
        return allowPayment;
    }

    public void setAllowPayment(Boolean allowPayment) {
        this.allowPayment = allowPayment;
    }

    @Override
    public String toString() {
        return "ParentFeeResponse{" +
                "status=" + status +
                ", feesStructure=" + feesStructure +
                ", message='" + message + '\'' +
                ", allowPayment=" + allowPayment +
                '}';
    }
}
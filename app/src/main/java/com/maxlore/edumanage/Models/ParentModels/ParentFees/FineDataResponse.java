package com.maxlore.edumanage.Models.ParentModels.ParentFees;

/**
 * Created by Shrey on 01-Aug-17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FineDataResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("paid")
    @Expose
    private String paid;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("balance")
    @Expose
    private String balance;
    @SerializedName("split_pay")
    @Expose
    private Boolean splitPay;
    @SerializedName("message")
    @Expose
    private String message;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSplitPay() {
        return splitPay;
    }

    public void setSplitPay(Boolean splitPay) {
        this.splitPay = splitPay;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "FineDataResponse{" +
                "status=" + status +
                ", amount=" + amount +
                ", paid='" + paid + '\'' +
                ", id=" + id +
                ", balance='" + balance + '\'' +
                ", splitPay=" + splitPay +
                ", message='" + message + '\'' +
                '}';
    }
}
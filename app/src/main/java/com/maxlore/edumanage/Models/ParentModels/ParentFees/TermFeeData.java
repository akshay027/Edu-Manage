package com.maxlore.edumanage.Models.ParentModels.ParentFees;

/**
 * Created by Shrey on 02-Aug-17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TermFeeData {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("consession_amount")
    @Expose
    private Integer consessionAmount;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("late_fee_fine_amount")
    @Expose
    private Integer lateFeeFineAmount;
    @SerializedName("paid")
    @Expose
    private String paid;
    @SerializedName("split_pay")
    @Expose
    private Boolean splitPay;
    @SerializedName("balance")
    @Expose
    private String balance;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getConsessionAmount() {
        return consessionAmount;
    }

    public void setConsessionAmount(Integer consessionAmount) {
        this.consessionAmount = consessionAmount;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Integer getLateFeeFineAmount() {
        return lateFeeFineAmount;
    }

    public void setLateFeeFineAmount(Integer lateFeeFineAmount) {
        this.lateFeeFineAmount = lateFeeFineAmount;
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
        return "TermFeeData{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", consessionAmount=" + consessionAmount +
                ", amount='" + amount + '\'' +
                ", lateFeeFineAmount=" + lateFeeFineAmount +
                ", paid='" + paid + '\'' +
                ", splitPay=" + splitPay +
                ", balance='" + balance + '\'' +
                '}';
    }
}
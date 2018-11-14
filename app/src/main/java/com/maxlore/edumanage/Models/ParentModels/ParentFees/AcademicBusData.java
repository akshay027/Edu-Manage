package com.maxlore.edumanage.Models.ParentModels.ParentFees;

/**
 * Created by maxlore on 22-Aug-17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AcademicBusData {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("consession_amount")
    @Expose
    private Integer consessionAmount;
    @SerializedName("academic_fee_amount")
    @Expose
    private String academicFeeAmount;
    @SerializedName("bus_fee_amount")
    @Expose
    private Integer busFeeAmount;
    @SerializedName("late_fee_fine_amount")
    @Expose
    private Integer lateFeeFineAmount;
    @SerializedName("paid")
    @Expose
    private String paid;
    @SerializedName("balance")
    @Expose
    private String balance;
    @SerializedName("split_pay")
    @Expose
    private Boolean splitPay;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public String getAcademicFeeAmount() {
        return academicFeeAmount;
    }

    public void setAcademicFeeAmount(String academicFeeAmount) {
        this.academicFeeAmount = academicFeeAmount;
    }

    public Integer getBusFeeAmount() {
        return busFeeAmount;
    }

    public void setBusFeeAmount(Integer busFeeAmount) {
        this.busFeeAmount = busFeeAmount;
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

    @Override
    public String toString() {
        return "AcademicBusData{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", consessionAmount=" + consessionAmount +
                ", academicFeeAmount='" + academicFeeAmount + '\'' +
                ", busFeeAmount=" + busFeeAmount +
                ", lateFeeFineAmount=" + lateFeeFineAmount +
                ", paid=" + paid +
                ", balance='" + balance + '\'' +
                ", splitPay=" + splitPay +
                '}';
    }
}

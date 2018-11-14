package com.maxlore.edumanage.Models.ParentModels.ParentFees;

/**
 * Created by Shrey on 21-Aug-17.
 */

import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class TermBusFees {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("fee_type_fee_amount")
    @Expose
    private Integer feeTypeFeeAmount;
    @SerializedName("fee_type_fee_paid")
    @Expose
    private String feeTypeFeePaid;
    @SerializedName("fee_type_fee_balance")
    @Expose
    private String feeTypeFeeBalance;
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

    public Integer getFeeTypeFeeAmount() {
        return feeTypeFeeAmount;
    }

    public void setFeeTypeFeeAmount(Integer feeTypeFeeAmount) {
        this.feeTypeFeeAmount = feeTypeFeeAmount;
    }

    public String getFeeTypeFeePaid() {
        return feeTypeFeePaid;
    }

    public void setFeeTypeFeePaid(String feeTypeFeePaid) {
        this.feeTypeFeePaid = feeTypeFeePaid;
    }

    public String getFeeTypeFeeBalance() {
        return feeTypeFeeBalance;
    }

    public void setFeeTypeFeeBalance(String feeTypeFeeBalance) {
        this.feeTypeFeeBalance = feeTypeFeeBalance;
    }

    public Boolean getSplitPay() {
        return splitPay;
    }

    public void setSplitPay(Boolean splitPay) {
        this.splitPay = splitPay;
    }

    @Override
    public String toString() {
        return "TermBusFees{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", feeTypeFeeAmount=" + feeTypeFeeAmount +
                ", feeTypeFeePaid='" + feeTypeFeePaid + '\'' +
                ", feeTypeFeeBalance='" + feeTypeFeeBalance + '\'' +
                ", splitPay=" + splitPay +
                '}';
    }
}
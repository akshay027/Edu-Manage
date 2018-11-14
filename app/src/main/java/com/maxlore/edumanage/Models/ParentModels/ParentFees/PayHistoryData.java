package com.maxlore.edumanage.Models.ParentModels.ParentFees;

/**
 * Created by Shrey on 31-Jul-17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PayHistoryData {

    @SerializedName("payment_for")
    @Expose
    private String paymentFor;
    @SerializedName("reciept_no")
    @Expose
    private String recieptNo;
    @SerializedName("reciept_date")
    @Expose
    private String recieptDate;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("mode")
    @Expose
    private String mode;
    @SerializedName("remarks")
    @Expose
    private String remarks;
    @SerializedName("status")
    @Expose
    private String status;

    public String getPaymentFor() {
        return paymentFor;
    }

    public void setPaymentFor(String paymentFor) {
        this.paymentFor = paymentFor;
    }

    public String getRecieptNo() {
        return recieptNo;
    }

    public void setRecieptNo(String recieptNo) {
        this.recieptNo = recieptNo;
    }

    public String getRecieptDate() {
        return recieptDate;
    }

    public void setRecieptDate(String recieptDate) {
        this.recieptDate = recieptDate;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "PayHistoryData{" +
                "paymentFor='" + paymentFor + '\'' +
                ", recieptNo='" + recieptNo + '\'' +
                ", recieptDate='" + recieptDate + '\'' +
                ", amount='" + amount + '\'' +
                ", mode='" + mode + '\'' +
                ", remarks='" + remarks + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
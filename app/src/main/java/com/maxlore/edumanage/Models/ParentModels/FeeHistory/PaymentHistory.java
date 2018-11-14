package com.maxlore.edumanage.Models.ParentModels.FeeHistory;

/**
 * Created by maxlore on 04-Nov-16.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentHistory {

    @SerializedName("receipt_number")
    @Expose
    private String receiptNumber;
    @SerializedName("receipt_date")
    @Expose
    private String receiptDate;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("remarks")
    @Expose
    private String remarks;
    @SerializedName("mode")
    @Expose
    private String mode;
    @SerializedName("cheque_no")
    @Expose
    private String chequeNo;

    /**
     *
     * @return
     * The receiptNumber
     */
    public String getReceiptNumber() {
        return receiptNumber;
    }

    /**
     *
     * @param receiptNumber
     * The receipt_number
     */
    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    /**
     *
     * @return
     * The receiptDate
     */
    public String getReceiptDate() {
        return receiptDate;
    }

    /**
     *
     * @param receiptDate
     * The receipt_date
     */
    public void setReceiptDate(String receiptDate) {
        this.receiptDate = receiptDate;
    }

    /**
     *
     * @return
     * The amount
     */
    public String getAmount() {
        return amount;
    }

    /**
     *
     * @param amount
     * The amount
     */
    public void setAmount(String amount) {
        this.amount = amount;
    }

    /**
     *
     * @return
     * The remarks
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     *
     * @param remarks
     * The remarks
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     *
     * @return
     * The mode
     */
    public String getMode() {
        return mode;
    }

    /**
     *
     * @param mode
     * The mode
     */
    public void setMode(String mode) {
        this.mode = mode;
    }

    /**
     *
     * @return
     * The chequeNo
     */
    public String getChequeNo() {
        return chequeNo;
    }

    /**
     *
     * @param chequeNo
     * The cheque_no
     */
    public void setChequeNo(String chequeNo) {
        this.chequeNo = chequeNo;
    }

    @Override
    public String toString() {
        return "PaymentHistory{" +
                "receiptNumber='" + receiptNumber + '\'' +
                ", receiptDate='" + receiptDate + '\'' +
                ", amount='" + amount + '\'' +
                ", remarks=" + remarks +
                ", mode='" + mode + '\'' +
                ", chequeNo=" + chequeNo +
                '}';
    }
}
package com.maxlore.edumanage.Models.ParentModels.FeeHistory;

/**
 * Created by maxlore on 04-Nov-16.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class PaymentHistoryResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("payment_history")
    @Expose
    private List<PaymentHistory> paymentHistory = new ArrayList<PaymentHistory>();
    @SerializedName("total_fee")
    @Expose
    private Integer totalFee;
    @SerializedName("fee_deposited")
    @Expose
    private Integer feeDeposited;
    @SerializedName("fee_due")
    @Expose
    private Integer feeDue;
    @SerializedName("message")
    @Expose
    private String message;

    /**
     *
     * @return
     * The status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     *
     * @return
     * The paymentHistory
     */
    public List<PaymentHistory> getPaymentHistory() {
        return paymentHistory;
    }

    /**
     *
     * @param paymentHistory
     * The payment_history
     */
    public void setPaymentHistory(List<PaymentHistory> paymentHistory) {
        this.paymentHistory = paymentHistory;
    }

    /**
     *
     * @return
     * The totalFee
     */
    public Integer getTotalFee() {
        return totalFee;
    }

    /**
     *
     * @param totalFee
     * The total_fee
     */
    public void setTotalFee(Integer totalFee) {
        this.totalFee = totalFee;
    }

    /**
     *
     * @return
     * The feeDeposited
     */
    public Integer getFeeDeposited() {
        return feeDeposited;
    }

    /**
     *
     * @param feeDeposited
     * The fee_deposited
     */
    public void setFeeDeposited(Integer feeDeposited) {
        this.feeDeposited = feeDeposited;
    }

    /**
     *
     * @return
     * The feeDue
     */
    public Integer getFeeDue() {
        return feeDue;
    }

    /**
     *
     * @param feeDue
     * The fee_due
     */
    public void setFeeDue(Integer feeDue) {
        this.feeDue = feeDue;
    }

    /**
     *
     * @return
     * The message
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @param message
     * The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "PaymentHistoryResponse{" +
                "status=" + status +
                ", paymentHistory=" + paymentHistory +
                ", totalFee=" + totalFee +
                ", feeDeposited=" + feeDeposited +
                ", feeDue=" + feeDue +
                ", message='" + message + '\'' +
                '}';
    }
}
package com.maxlore.edumanage.Models.AdminModels.NoticeBoard.PaymentMOdels;

/**
 * Created by maxlore on 21-Jul-17.
 */

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdminPaymentHistoryModel {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("payment_histories")
    @Expose
    private List<PaymentHistorymodel> paymentHistories = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<PaymentHistorymodel> getPaymentHistories() {
        return paymentHistories;
    }

    public void setPaymentHistories(List<PaymentHistorymodel> paymentHistories) {
        this.paymentHistories = paymentHistories;
    }

    @Override
    public String toString() {
        return "AdminPaymentHistoryModel{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", paymentHistories=" + paymentHistories +
                '}';
    }
}